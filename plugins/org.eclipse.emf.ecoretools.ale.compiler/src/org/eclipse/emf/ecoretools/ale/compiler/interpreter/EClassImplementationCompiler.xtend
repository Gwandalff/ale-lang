package org.eclipse.emf.ecoretools.ale.compiler.interpreter

import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import java.io.File
import java.util.List
import java.util.Map
import java.util.Set
import org.eclipse.emf.codegen.ecore.genmodel.GenModel
import org.eclipse.emf.common.notify.Notification
import org.eclipse.emf.common.notify.NotificationChain
import org.eclipse.emf.common.util.BasicEMap
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.common.util.EMap
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.EcorePackage
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.impl.ENotificationImpl
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl
import org.eclipse.emf.ecore.util.EObjectContainmentEList
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList
import org.eclipse.emf.ecore.util.EcoreEMap
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.emf.ecoretools.ale.compiler.interpreter.ALEInterpreterImplementationCompiler.ResolvedClass
import org.eclipse.emf.ecoretools.ale.core.parser.Dsl
import org.eclipse.emf.ecoretools.ale.core.validation.BaseValidator
import org.eclipse.emf.ecoretools.ale.implementation.ExtendedClass
import org.eclipse.emf.ecoretools.ale.implementation.Method
import org.eclipse.emf.ecoretools.ale.implementation.Statement

import static javax.lang.model.element.Modifier.*
import org.eclipse.emf.ecore.EEnum
import java.util.Comparator
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.emf.ecoretools.ale.implementation.While
import org.eclipse.emf.ecore.util.EDataTypeEList

class EClassImplementationCompiler {
	extension InterpreterNamingUtils namingUtils = new InterpreterNamingUtils
	extension InterpreterCompilerUtils = new InterpreterCompilerUtils
	extension JavaPoetUtils = new JavaPoetUtils
	extension TypeSystemUtils tsu
	extension AleBodyCompiler abc
	
	var Map<String, Pair<EPackage, GenModel>> syntaxes
	var Dsl dsl
	val String packageRoot
	var Set<Method> registreredDispatch = newHashSet
	var Set<String> registreredArrays = newHashSet
	val List<ResolvedClass> resolved
	
	static class CompilerExpressionCtx {
		public val String thisCtxName
		public val ExtendedClass aleClass
		public val EClass eClass 
		new(String thisCtxName, ExtendedClass aleClass, EClass eClass) {
			this.thisCtxName = thisCtxName
			this.aleClass = aleClass
			this.eClass = eClass
		}
	}
	
	new(String packageRoot, List<ResolvedClass> resolved) {
		this.packageRoot = packageRoot
		this.resolved = resolved
	}

	private def TypeSpec.Builder compileEcoreRelated(TypeSpec.Builder builder, EClass eClass, ExtendedClass aleClass) {
		
		
		val isMapElement = eClass.instanceClass !== null && eClass.instanceClass == Map.Entry
//		val eClassInterfaceType = ClassName.get(eClass.classInterfacePackageName(packageRoot), eClass.classInterfaceClassName)
		val ePackageInterfaceType = ClassName.get(eClass.EPackage.packageInterfacePackageName(packageRoot),
			eClass.EPackage.packageInterfaceClassName)

		val hasSuperType = !eClass.ESuperTypes.empty
		val superType = eClass.ESuperTypes.head

		val fieldsEAttributes = eClass.EAttributes.map [ field |
			val fet = field.EType
			val type = fet.scopedTypeRef(packageRoot)
			val isMultiple = field.upperBound > 1 || field.upperBound < 0
			if(isMultiple) {
				val fieldField = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(EList), type), '''«field.name»''', PROTECTED).build
				#[fieldField]
			} else {
				val edefault = if(fet instanceof EEnum) {
					FieldSpec
						.builder(type, '''«field.name.toUpperCase»_EDEFAULT''')
						.initializer('''«IF field.defaultValue === null || field.defaultValue.toString == ''»null«ELSE»«namingUtils.classInterfacePackageName(fet, packageRoot)».«namingUtils.classInterfaceClassName(fet)».valueOf("«field.defaultValue.toString.toUpperCase»")«ENDIF»''')
						.addModifiers(PROTECTED, STATIC, FINAL).build
				} else {
					FieldSpec
						.builder(type, '''«field.name.toUpperCase»_EDEFAULT''')
						.initializer('''«IF field.defaultValue === null || field.defaultValue.toString == ''»null«ELSE»«field.defaultValue»«ENDIF»''')
						.addModifiers(PROTECTED, STATIC, FINAL).build
				}
	
				val fieldField = FieldSpec.builder(type, field.name).initializer('''«field.name.toUpperCase»_EDEFAULT''').
					addModifiers(PROTECTED).build
				#[edefault, fieldField]
				
			}
		].flatten

		val methodsEAttributes = eClass.EAttributes.map [ field |
			val type = field.EType.scopedTypeRef(packageRoot)

			val isMultiple = field.upperBound > 1 || field.upperBound < 0
			if(isMultiple) {
				val typeList = ParameterizedTypeName.get(ClassName.get(EDataTypeEList), type)
				val getter = MethodSpec.methodBuilder('''«IF field.EType.name == "EBoolean"»is«ELSE»get«ENDIF»«field.name.toFirstUpper»''').addModifiers(PUBLIC).
					addCode('''
					if («field.name» == null) {
						«field.name» = new $T($T.class, this, «eClass.EPackage.packageInterfacePackageName(packageRoot)».«eClass.EPackage.packageInterfaceClassName».«field.name.normalizeUpperField(eClass.name)»);
					}
					return «field.name»;					
					''', typeList, type).returns(ParameterizedTypeName.get(ClassName.get(EList), type)).build
				#[getter]
			} else {
				val getter = MethodSpec.methodBuilder('''«IF field.EType.name == "EBoolean"»is«ELSE»get«ENDIF»«field.name.toFirstUpper»''').addModifiers(PUBLIC).
					addCode('''
					return «field.name»;
					''').returns(type).build
				val setter = MethodSpec.methodBuilder('''set«field.name.toFirstUpper»''').addParameter(
					ParameterSpec.builder(type, field.name).build).addCode('''
					this.«field.name» = «field.name»;
					''').
					addModifiers(PUBLIC).build
	
				#[getter, setter]
			}
		].flatten

		/*
		 * Do not generate physical fields for opposite relations to  containment fields
		 */
		val fieldsEReferences = eClass.EReferences.filter[field | if(field.EOpposite !== null) !field.EOpposite.containment else true].map [ field |
			val ert = field.EGenericType.ERawType
			val rt = ert.scopedInterfaceTypeRef(packageRoot)
			val isMultiple = field.upperBound > 1 || field.upperBound < 0
			val fieldType = if (isMultiple) {
				if (ert.instanceClass !== null && ert.instanceClass == Map.Entry) {
					val key = field.EType.eContents.filter(EStructuralFeature).filter[it.name == "key"].head
					val value = field.EType.eContents.filter(EStructuralFeature).filter[it.name == "value"].head
					if(key !== null && value !== null) {
						ParameterizedTypeName.get(ClassName.get(EMap), key.EType.scopedInterfaceTypeRef(packageRoot), value.EType.scopedInterfaceTypeRef(packageRoot))
					} else {
						ParameterizedTypeName.get(ClassName.get(EList), rt)
					}
				} else {
					ParameterizedTypeName.get(ClassName.get(EList), rt)
				}
			} else
				rt
				
			val isMutable = aleClass !== null && aleClass.mutable.exists[it == field.name]
			FieldSpec.builder(fieldType, field.name).applyIfTrue(dsl.dslProp.getOrDefault("child", "false").equals("true") 
				&& !isMultiple && !isMutable 
				&& field.containment
				&& !field.EType.EAnnotations.exists[it.source == 'RuntimeData']
				, [
				addAnnotation(ClassName.get("com.oracle.truffle.api.nodes.Node", "Child"))
			]).addModifiers(PROTECTED).build
		]

		val methodsEReferences = eClass.EReferences.map [ field |
			val ert = field.EGenericType.ERawType
			val rt = ert.scopedInterfaceTypeRef(packageRoot)
			val isMultiple = field.upperBound > 1 || field.upperBound < 0
			val fieldType = if (isMultiple) {
					if (ert.instanceClass !== null && ert.instanceClass == Map.Entry) {
						val key = field.EType.eContents.filter(EStructuralFeature).filter[it.name == "key"].head
						val value = field.EType.eContents.filter(EStructuralFeature).filter[it.name == "value"].head
						if(key !== null && value !== null) {
							ParameterizedTypeName.get(ClassName.get(EMap), key.EType.scopedInterfaceTypeRef(packageRoot), value.EType.scopedInterfaceTypeRef(packageRoot))
						} else {
							ParameterizedTypeName.get(ClassName.get(EList), rt)
						}
					} else {
						ParameterizedTypeName.get(ClassName.get(EList), rt)
					}
				} else
					rt

			val setter = if (!isMultiple) {
					val newName = '''new«field.name.toFirstUpper»'''
					val oldName = '''old«field.name.toFirstUpper»'''
					val name = field.name

					if (field.EOpposite !== null) {
						if(!field.EOpposite.containment) {
							val setter = MethodSpec.methodBuilder('''set«field.name.toFirstUpper»''')
							.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
							.addParameter(ParameterSpec.builder(fieldType, newName).build).addCode('''
								if («newName» != «name») {
									$1T msgs = null;
									«IF field.EOpposite !== null»
										if («name» != null)
											msgs = (($2T) «name»).eInverseRemove(this, $5T.«field.EOpposite.name.normalizeUpperField((field.EOpposite.eContainer as EClass).name)», «rt».class, msgs);
										if («newName» != null)
											msgs = (($2T) «newName»).eInverseAdd(this, $5T.«field.EOpposite.name.normalizeUpperField((field.EOpposite.eContainer as EClass).name)», «rt».class,
													msgs);
									«ELSE»
										if («name» != null)
											msgs = (($2T) «name»).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - $5T.«field.name.normalizeUpperField(eClass.name)», null, msgs);
										if («newName» != null)
											msgs = (($2T) «newName»).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - $5T.«field.name.normalizeUpperField(eClass.name)», null, msgs);
									«ENDIF»
									msgs = basicSet«name.toFirstUpper»(«newName», msgs);
									if (msgs != null)
										msgs.dispatch();
								} else if (eNotificationRequired())
									eNotify(new $3T(this, $4T.SET, $5T.«field.name.normalizeUpperField(eClass.name)», «newName», «newName»));
							''', NotificationChain, InternalEObject, ENotificationImpl, Notification,
								ePackageInterfaceType).addModifiers(PUBLIC).build
							val basicSetMethod = MethodSpec.methodBuilder('''basicSet«field.name.toFirstUpper»''')
							.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
							.returns(
								NotificationChain).addParameter(
								ParameterSpec.builder(fieldType, '''new«field.name.toFirstUpper»''').build).addParameter(
								ParameterSpec.builder(NotificationChain, 'msgsp').build).addCode('''
								$1T msgs = msgsp;
								$2T «oldName» = «name»;
								«name» = «newName»;
								if (eNotificationRequired()) {
									$3T notification = new $3T(this, $4T.SET, $5T.«field.name.normalizeUpperField(eClass.name)»,
											«oldName», «newName»);
									if (msgs == null)
										msgs = notification;
									else
										msgs.add(notification);
								}
								return msgs;
							''', NotificationChain, fieldType, ENotificationImpl, Notification,
								ePackageInterfaceType).addModifiers(PRIVATE).build
							
								#[setter, basicSetMethod]
							} else {
								val setter = MethodSpec.methodBuilder('''set«field.name.toFirstUpper»''')
								.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
								.addParameter(ParameterSpec.builder(fieldType, newName).build).addCode('''
								if («newName» != eInternalContainer() || (eContainerFeatureID() != $1T.«field.name.normalizeUpperField(eClass.name)» && «newName» != null)) {
									if ($2T.isAncestor(this, «newName»))
										throw new $3T("Recursive containment not allowed for " + toString());
									$4T msgs = null;
									if (eInternalContainer() != null)
										msgs = eBasicRemoveFromContainer(msgs);
									if («newName» != null)
										msgs = (($5T)«newName»).eInverseAdd(this, $1T.«field.EOpposite.name.normalizeUpperField((field.eContainer as EClass).name)» , $6T.class, msgs);
									msgs = basicSet«field.name.toFirstUpper»(«newName», msgs);
									if (msgs != null) msgs.dispatch();
								}
								else if (eNotificationRequired())
									eNotify(new $7T(this, $8T.SET, $1T.«field.name.normalizeUpperField(eClass.name)» , «newName», «newName»));
							''', ePackageInterfaceType, EcoreUtil, IllegalArgumentException, NotificationChain, InternalEObject, fieldType, ENotificationImpl, Notification).addModifiers(PUBLIC).build
							
							val basicSetMethod = MethodSpec.methodBuilder('''basicSet«field.name.toFirstUpper»''')
							.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
							.returns(
								NotificationChain).addParameter(
								ParameterSpec.builder(fieldType, '''new«field.name.toFirstUpper»''').build).addParameter(
								ParameterSpec.builder(NotificationChain, 'msgs').build).addCode('''
								msgs = eBasicSetContainer(($1T)new«field.name.toFirstUpper», $2T.«field.name.normalizeUpperField(eClass.name)», msgs);
								return msgs;
							''', InternalEObject, ePackageInterfaceType).addModifiers(PUBLIC).build
							#[setter, basicSetMethod]
							}

						
						
					} else {
						if(field.containment) {
							val isEnum = field.EType instanceof EEnum
							val setter = if(isMapElement) {
								MethodSpec.methodBuilder('''set«field.name.toFirstUpper»''')
									.returns(fieldType)
									.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
									.addParameter(ParameterSpec.builder(fieldType, newName).build)
									.addCode('''
									«IF isEnum»«ENDIF»
									$5T «oldName» = «field.name»;
									if («newName» != «field.name») {
										$4T msgs = null;
										if («field.name» != null)
											msgs = (($1T)«field.name»).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - «eClass.EPackage.packageInterfacePackageName(packageRoot)».«eClass.EPackage.packageInterfaceClassName».«field.name.normalizeUpperField(eClass.name)», null, msgs);
										if («newName» != null)
											msgs = (($1T)«newName»).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - «eClass.EPackage.packageInterfacePackageName(packageRoot)».«eClass.EPackage.packageInterfaceClassName».«field.name.normalizeUpperField(eClass.name)», null, msgs);
										msgs = basicSet«field.name.toFirstUpper»(«newName», msgs);
										if (msgs != null) msgs.dispatch();
									}
									else if (eNotificationRequired())
										eNotify(new $2T(this, $3T.SET, «eClass.EPackage.packageInterfacePackageName(packageRoot)».«eClass.EPackage.packageInterfaceClassName».«field.name.normalizeUpperField(eClass.name)», «newName», «newName»));
									return «oldName»;
									''', InternalEObject, ENotificationImpl, Notification, NotificationChain, fieldType)
									.addModifiers(PUBLIC)
									.build
							} else {
								MethodSpec.methodBuilder('''set«field.name.toFirstUpper»''')
									.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
									.addParameter(ParameterSpec.builder(fieldType, newName).build)
									.addCode('''
									«IF isEnum»«ENDIF»
									if («newName» != «field.name») {
										$4T msgs = null;
										if («field.name» != null)
											msgs = (($1T)«field.name»).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - «eClass.EPackage.packageInterfacePackageName(packageRoot)».«eClass.EPackage.packageInterfaceClassName».«field.name.normalizeUpperField(eClass.name)», null, msgs);
										if («newName» != null)
											msgs = (($1T)«newName»).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - «eClass.EPackage.packageInterfacePackageName(packageRoot)».«eClass.EPackage.packageInterfaceClassName».«field.name.normalizeUpperField(eClass.name)», null, msgs);
										msgs = basicSet«field.name.toFirstUpper»(«newName», msgs);
										if (msgs != null) msgs.dispatch();
									}
									else if (eNotificationRequired())
										eNotify(new $2T(this, $3T.SET, «eClass.EPackage.packageInterfacePackageName(packageRoot)».«eClass.EPackage.packageInterfaceClassName».«field.name.normalizeUpperField(eClass.name)», «newName», «newName»));
									''', InternalEObject, ENotificationImpl, Notification, NotificationChain)
									.addModifiers(PUBLIC)
									.build
							}
						val basicSetter = MethodSpec.methodBuilder('''basicSet«field.name.toFirstUpper»''')
						.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
						.returns(NotificationChain)
							.addParameter(ParameterSpec.builder(fieldType, newName).build)
							.addParameter(ParameterSpec.builder(NotificationChain, 'msgs').build)
							.addCode('''
							$1T «oldName» = «field.name»;
							«field.name» = «newName»;
							if (eNotificationRequired()) {
								$2T notification = new $2T(this, $3T.SET, «eClass.EPackage.packageInterfacePackageName(packageRoot)».«eClass.EPackage.packageInterfaceClassName».«field.name.normalizeUpperField(eClass.name)», «oldName», «newName»);
								if (msgs == null) msgs = notification; else msgs.add(notification);
							}
							return msgs;
							''', fieldType, ENotificationImpl, Notification)
							.addModifiers(PUBLIC)
						.build
						
							#[setter, basicSetter]
						} else { 
							val isMapValueField = (field.eContainer as EClass).instanceClass !== null && (field.eContainer as EClass).instanceClass == Map.Entry && field.name == "value"
							if(isMapValueField) {
								val setter = MethodSpec.methodBuilder('''set«field.name.toFirstUpper»''')
								.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
								.returns(
									fieldType).addParameter(ParameterSpec.builder(fieldType, newName).build).addCode('''
									$1T «oldName» = this.«field.name»;
									this.«field.name» = «newName»;
									return «oldName»;
								''', fieldType).
									addModifiers(PUBLIC).build
		
								#[setter]						
							} else {
								val setter = MethodSpec.methodBuilder('''set«field.name.toFirstUpper»''')
								
								.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
								.addParameter(
									ParameterSpec.builder(fieldType, newName).build).addCode('''
									$1T «oldName» = «field.name»;
									«field.name» = «newName»;
									if (eNotificationRequired())
										eNotify(new $2T(this, $3T.SET, $4T.«field.name.normalizeUpperField(eClass.name)», «oldName», «field.name»));
								''', fieldType, ENotificationImpl, Notification,
									ePackageInterfaceType).addModifiers(PUBLIC).build
		
								#[setter]
							
							}
						
						}
					}

				} else
					#[]

			val getter = if (isMultiple) {
				if(ert.instanceClass !== null && ert.instanceClass == Map.Entry) {
					val key = field.EType.eContents.filter(EStructuralFeature).filter[it.name == "key"].head
					val value = field.EType.eContents.filter(EStructuralFeature).filter[it.name == "value"].head
					MethodSpec.methodBuilder('''get«field.name.toFirstUpper»''').returns(fieldType)
					.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
							.addModifiers(PUBLIC).addCode('''
								if («field.name» == null) {
									«field.name» = new $1T($2T.Literals.«(field.EType as EClass).name.normalizeUpperField», $3T.class, this, $2T.«field.name.normalizeUpperField(eClass.name)»);
								}
								return «field.name»;
							''', ParameterizedTypeName.get(ClassName.get(EcoreEMap), key.EType.scopedInterfaceTypeRef(packageRoot), value.EType.scopedInterfaceTypeRef(packageRoot)), ePackageInterfaceType,
							ClassName.get((field.EType as EClass).classImplementationPackageName(packageRoot), (field.EType as EClass).classImplementationClassName)).build
							// EcoreEMap<String,EvalRes>
				}
					else if(field.EOpposite !== null) {
						MethodSpec.methodBuilder('''get«field.name.toFirstUpper»''').returns(fieldType)
						.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
							.addModifiers(PUBLIC).addCode('''
								if («field.name» == null) {
									«field.name» = new $1T($2T.class, this, $3T.«field.name.normalizeUpperField(eClass.name)», $3T.«field.EOpposite.name.normalizeUpperField((field.EOpposite.eContainer as EClass).name)»);
								}
								return «field.name»;
							''', ParameterizedTypeName.get(ClassName.get(EObjectContainmentWithInverseEList), rt), rt, ePackageInterfaceType).build
					} else {
						MethodSpec.methodBuilder('''get«field.name.toFirstUpper»''').returns(fieldType)
						.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
							.addModifiers(PUBLIC).addCode('''
								if («field.name» == null) {
									«field.name» = new $1T(«rt».class, this, $2T.«field.name.normalizeUpperField(eClass.name)»);
								}
								return «field.name»;
							''', ParameterizedTypeName.get(ClassName.get(EObjectContainmentEList), rt),
								ePackageInterfaceType).build
					}
				} else {
					if(field.EOpposite !== null && field.EOpposite.containment) {
						MethodSpec.methodBuilder('''«IF field.EType.name == "EBoolean"»is«ELSE»get«ENDIF»«field.name.toFirstUpper»''').returns(fieldType)
						.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
						.addModifiers(PUBLIC).addCode('''
						if (eContainerFeatureID() != $1T.«field.name.normalizeUpperField(eClass.name)») return null;
						return ($2T)eInternalContainer();
						''', ePackageInterfaceType, fieldType).build
					}  else {
						if(field.isContainment) {
							MethodSpec.methodBuilder('''«IF field.EType.name == "EBoolean"»is«ELSE»get«ENDIF»«field.name.toFirstUpper»''').returns(fieldType)
							.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
							.addModifiers(PUBLIC).addCode('''
							return «field.name»;
							''').build	
						} else {
						MethodSpec.methodBuilder('''«IF field.EType.name == "EBoolean"»is«ELSE»get«ENDIF»«field.name.toFirstUpper»''').returns(fieldType)
							.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
							.addModifiers(PUBLIC).addCode('''
							if («field.name» != null && «field.name».eIsProxy()) {
								$T old«field.name» = ($T) «field.name»;
								«field.name» = ($T) eResolveProxy(old«field.name»);
								if («field.name» != old«field.name») {
									if (eNotificationRequired())
										eNotify(new $T(this, $T.RESOLVE, $T.«field.name.normalizeUpperField(eClass.name)»,
												old«field.name», «field.name»));
								}
							}
							return «field.name»;
							''', TypeName.get(InternalEObject), TypeName.get(InternalEObject), fieldType, TypeName.get(ENotificationImpl), TypeName.get(Notification), ePackageInterfaceType).build
							
							}
					}
				}

			setter + #[getter]
		].flatten

		val eStaticClassMethod = MethodSpec.methodBuilder('eStaticClass').returns(EClass).addModifiers(PROTECTED)
			.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
			.addCode('''
			return $1T.Literals.«eClass.name.normalizeUpperField»;
			''', ePackageInterfaceType).build

		val eSetMethod = if(!eClass.EStructuralFeatures.empty) {
			val MethodSpec ret = MethodSpec.methodBuilder('eSet').addParameter(int, 'featureID')
			.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
			.addParameter(Object, 'newValue').addModifiers(PUBLIC).addCode( '''
			switch (featureID) {
				«FOR esf : eClass.EStructuralFeatures»
					case $1T.«esf.name.normalizeUpperField(eClass.name)» :
						«IF esf instanceof EAttribute»
							«IF esf.upperBound <= 1 && esf.upperBound >= 0»
							set«esf.name.toFirstUpper»((«esf.EType.scopedTypeRef(packageRoot)») newValue);
							«ELSE»
							get«esf.name.toFirstUpper»().clear();
							get«esf.name.toFirstUpper»().addAll((java.util.Collection<? extends String>) newValue);
							«ENDIF»
						«ELSE»
							«IF esf.upperBound <= 1 && esf.upperBound >= 0»
								set«esf.name.toFirstUpper»((«(esf.EGenericType.ERawType as EClass).classInterfacePackageName(packageRoot)».«(esf.EGenericType.ERawType as EClass).classInterfaceClassName») newValue);
							«ELSE»
								«IF (esf.EType.instanceClass !== null && esf.EType.instanceClass == Map.Entry)»
									((org.eclipse.emf.ecore.EStructuralFeature.Setting)get«esf.name.toFirstUpper»()).set(newValue);
								«ELSE»
								get«esf.name.toFirstUpper»().clear();
								get«esf.name.toFirstUpper»().addAll((java.util.Collection<? extends «(esf.EType as EClass).classInterfacePackageName(packageRoot)».«(esf.EType as EClass).classInterfaceClassName»>) newValue);
								«ENDIF»
							«ENDIF»
						«ENDIF»
						return;
				«ENDFOR»
			}
			super.eSet(featureID, newValue);
		''', ePackageInterfaceType).build
		
		val MethodSpec  eUnsetMethod = MethodSpec.methodBuilder('eUnset').addParameter(int, 'featureID').addModifiers(PUBLIC)
			.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
			.addCode('''
				switch (featureID) {
					«FOR esf : eClass.EStructuralFeatures»
					case $1T.«esf.name.normalizeUpperField(eClass.name)» :
						«IF esf instanceof EAttribute»
						«IF esf.upperBound <= 1 && esf.upperBound >= 0»
						set«esf.name.toFirstUpper»(«esf.name.toUpperCase»_EDEFAULT);
						«ELSE»
						get«esf.name.toFirstUpper»().clear();
						«ENDIF»
						«ELSE»
						«IF esf.upperBound <= 1 && esf.upperBound >= 0»
						set«esf.name.toFirstUpper»((«(esf.EGenericType.ERawType as EClass).classInterfacePackageName(packageRoot)».«(esf.EGenericType.ERawType as EClass).classInterfaceClassName») null);
						«ELSE»
						get«esf.name.toFirstUpper»().clear();
						«ENDIF»
						«ENDIF»
						return;
					«ENDFOR»
				}
				super.eUnset(featureID);
			''', ePackageInterfaceType).build
		val MethodSpec  eGetMethod = MethodSpec.methodBuilder('eGet').returns(Object).addParameter(int, 'featureID')
			.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
			.addParameter(boolean, 'resolve').addParameter(boolean, 'coreType').addModifiers(PUBLIC).addCode('''
				switch (featureID) {
					«FOR esf : eClass.EStructuralFeatures»
					case $1T.«esf.name.normalizeUpperField(eClass.name)» :
						«IF esf.EType.instanceClass !== null && esf.EType.instanceClass == Map.Entry»
						if (coreType)
							return get«esf.name.toFirstUpper»();
						else
							return get«esf.name.toFirstUpper»().map();
						«ELSE»
							return «IF esf.EType.name == "EBoolean"»is«ELSE»get«ENDIF»«esf.name.toFirstUpper»();
						«ENDIF»
					«ENDFOR»
				}
				return super.eGet(featureID, resolve, coreType);
			''', ePackageInterfaceType).build
		val MethodSpec  eIsSetMethod = MethodSpec.methodBuilder('eIsSet').returns(boolean).addParameter(int, 'featureID')
			.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
			.addModifiers(PUBLIC).addCode( '''
				switch (featureID) {
					«FOR esf : eClass.EStructuralFeatures»
					case $1T.«esf.name.normalizeUpperField(eClass.name)» :
						«IF esf instanceof EAttribute»
							«IF esf.upperBound <= 1 && esf.upperBound >= 0»
							return «esf.name» != «esf.name.toUpperCase»_EDEFAULT;
							«ELSE»
							return «esf.name» != null && !«esf.name».isEmpty();
							«ENDIF»
						«ELSE»
							«IF esf.upperBound <= 1»
								«IF (esf as EReference).EOpposite !== null && (esf as EReference).EOpposite.containment»
								return get«esf.name.toFirstUpper»() != null;
								«ELSE»
								«IF esf.upperBound == 0 || esf.upperBound == 1»
								return «esf.name» != null;
								«ELSE»
								return «esf.name» != null && !«esf.name».isEmpty();
								«ENDIF»
								«ENDIF»
							«ELSE»
								throw new RuntimeException("Not Implemented");
							«ENDIF»
						«ENDIF»
					«ENDFOR»
				}
				return super.eIsSet(featureID);
			''', ePackageInterfaceType).build
					
		val  eInverseRemove = if (!eClass.EReferences.filter[it.containment || it.EOpposite !== null].empty) {
						val reteir = MethodSpec.methodBuilder('eInverseRemove').returns(NotificationChain).
							addModifiers(PUBLIC).applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [
								addAnnotation(
									ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))
							]).addParameter(ParameterSpec.builder(InternalEObject, 'otherEnd').build).addParameter(
								ParameterSpec.builder(int, 'featureID').build).addParameter(
								ParameterSpec.builder(NotificationChain, 'msgs').build).addCode('''
								switch (featureID) {
									«FOR ref : eClass.EReferences.filter[it.containment || it.EOpposite !== null]» 
										case «eClass.EPackage.packageInterfacePackageName(packageRoot)».«eClass.EPackage.packageInterfaceClassName».«ref.name.normalizeUpperField(eClass.name)» :
											«IF ref.upperBound == 0 || ref.upperBound == 1»
												return basicSet«ref.name.toFirstUpper»(null, msgs);
											«ELSE»
												return ((org.eclipse.emf.ecore.util.InternalEList<?>) get«ref.name.toFirstUpper»()).basicRemove(otherEnd, msgs);
											«ENDIF»
									«ENDFOR»
								}
								return super.eInverseRemove(otherEnd, featureID, msgs);
							''').build
						#[reteir]
					} else
						#[]
				#[ret, eUnsetMethod, eGetMethod, eIsSetMethod] + eInverseRemove
			} else
				#[]

		val eInverseAdd = if (!eClass.EReferences.filter[it.EOpposite !== null].empty) {
				#[
					MethodSpec.methodBuilder('eInverseAdd').returns(NotificationChain)
						.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
						.addParameter(InternalEObject, 'otherEnd').addParameter(int, 'featureID').addParameter(NotificationChain, 'msgs2').addCode('''
						$1T msgs = msgs2;
						switch (featureID) {
							«FOR ref : eClass.EReferences.filter[it.EOpposite !== null]»
							case $2T.«ref.name.normalizeUpperField(eClass.name)» :
								«IF ref.upperBound == 0 || ref.upperBound == 1»
									«IF ref.EOpposite !== null && ref.EOpposite.containment»
									if (eInternalContainer() != null)
											msgs = eBasicRemoveFromContainer(msgs);
										return basicSet«ref.name.toFirstUpper»((«(ref.EType as EClass).classInterfacePackageName(packageRoot)».«(ref.EType as EClass).classInterfaceClassName»)otherEnd, msgs);
									«ELSE»
									if («ref.name» != null)
										msgs = ((org.eclipse.emf.ecore.InternalEObject) «ref.name»).eInverseRemove(this, $2T.«ref.EOpposite.name.normalizeUpperField((ref.EOpposite.eContainer as EClass).name)», «(ref.EOpposite.eContainer as EClass).name».class,
												msgs);
									return basicSet«ref.name.toFirstUpper»((«(ref.EOpposite.eContainer as EClass).classInterfacePackageName(packageRoot)».«(ref.EOpposite.eContainer as EClass).classInterfaceClassName») otherEnd, msgs);
									«ENDIF»
								«ELSE»
									return ((org.eclipse.emf.ecore.util.InternalEList<org.eclipse.emf.ecore.InternalEObject>) (org.eclipse.emf.ecore.util.InternalEList<?>) get«ref.name.toFirstUpper»()).basicAdd(otherEnd, msgs);
								«ENDIF»
							«ENDFOR»
						}
						return super.eInverseAdd(otherEnd, featureID, msgs);
					''', NotificationChain, ePackageInterfaceType).addModifiers(PUBLIC).build
				]
			} else
				#[]
				
		val key = eClass.eContents.filter(EStructuralFeature).filter[it.name == "key"].head
		val value = eClass.eContents.filter(EStructuralFeature).filter[it.name == "value"].head

		builder.applyIfTrue(eClass.isAbstract, [addModifiers(ABSTRACT)]).applyIfTrue(hasSuperType, [
			superclass(ClassName.get(superType.classImplementationPackageName(packageRoot), superType.classImplementationClassName))
		]).applyIfTrue(isMapElement, [
			it.addSuperinterface(
				ParameterizedTypeName.get(ClassName.get(BasicEMap.Entry), key.EType.scopedInterfaceTypeRef(packageRoot),
					value.EType.scopedInterfaceTypeRef(packageRoot)))
		]).applyIfTrue(!hasSuperType, [
			if(dsl.dslProp.getOrDefault("truffle", "false") == 'true') {
				if(!eClass.EAnnotations.exists[it.source == 'RuntimeData']) {
					superclass(ClassName.get("org.eclipse.emf.ecoretools.ale.compiler.truffle", "MinimalTruffleEObjectImpl", "TruffleContainer"))
				} else { 
					superclass(ClassName.get(MinimalEObjectImpl.Container))
					}
			} else {
				superclass(ClassName.get(MinimalEObjectImpl.Container))
			}
		]).applyIfTrue(
			!isMapElement, [
				addSuperinterface(ClassName.get(eClass.classInterfacePackageName(packageRoot), eClass.classInterfaceClassName))
			])
			.addFields(fieldsEAttributes + fieldsEReferences)
			.addMethods(methodsEAttributes + methodsEReferences + #[eStaticClassMethod] + eSetMethod + eInverseAdd)
			.applyIfTrue(isMapElement, [
				it.addField(FieldSpec.builder(int, 'hash', PROTECTED).initializer('-1').build).addMethod(
				MethodSpec.methodBuilder('setHash')
					.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
					.addParameter(int, 'hash').addCode('''
					this.hash = hash;
				''').addModifiers(PUBLIC).build)
					.addMethod(MethodSpec.methodBuilder('getHash')
					.applyIfTrue(dsl.dslProp.getProperty('truffle', "false") == "true", [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
					.returns(int)
					.addCode('''
				if (hash == -1) {
					Object theKey = getKey();
					hash = (theKey == null ? 0 : theKey.hashCode());
				}
				return hash;
			''').addModifiers(PUBLIC).build)
		])
	}
	
	def dispatch compileEClassImplementation(EClassifier eClass, ExtendedClass aleClass, File directory,
		Map<String, Pair<EPackage, GenModel>> syntaxes, List<ResolvedClass> resolved,
		Map<String, Class<?>> registeredServices, Dsl dsl, BaseValidator base) {
			
	}

	def dispatch compileEClassImplementation(EClass eClass, ExtendedClass aleClass, File directory,
		Map<String, Pair<EPackage, GenModel>> syntaxes, List<ResolvedClass> resolved,
		Map<String, Class<?>> registeredServices, Dsl dsl, BaseValidator base) {
		this.syntaxes = syntaxes
		this.dsl = dsl
		val isTruffle = dsl.dslProp.getOrDefault("truffle", "false") == "true"
		tsu = new TypeSystemUtils(syntaxes, packageRoot, base, resolved)
		abc = new AleBodyCompiler(syntaxes, packageRoot, base, resolved, registreredDispatch, registreredArrays, registeredServices, isTruffle)
		
		val implPackage = eClass.classImplementationPackageName(packageRoot)
		
		
		val aleMethods = if(aleClass !== null) {
			val ret = newArrayList
			for(x : aleClass.methods) {
				ret += x.compile(aleClass, eClass, isTruffle)
			}
			ret
		} else #[]
		
		val whileOps = if(aleClass !== null) {
			EcoreUtil2.getAllContentsOfType(aleClass, While)
		} else #[]
		
		val factory = TypeSpec.classBuilder(eClass.classImplementationClassName).compileEcoreRelated(eClass, aleClass)
			.applyIfTrue(aleClass !== null, [addMethods(aleMethods)])
			.applyIfTrue(isTruffle, [addFields(registreredArrays.map[fieldName|
				val x = eClass.EAllReferences.filter[it.name == fieldName].head.EType.resolveType
				val xa = ArrayTypeName.of(x)
				FieldSpec
					.builder(xa, '''«fieldName»Arr''', PRIVATE)
					.applyIfTrue(isTruffle && !eClass.EAnnotations.exists[it.source == 'RuntimeData'], [addAnnotation(ClassName.get('com.oracle.truffle.api.nodes.Node', 'Children'))])						
					.build
			])])
			.applyIfTrue(isTruffle && !eClass.EAnnotations.exists[it.source == 'RuntimeData'], [
				addAnnotation(
					AnnotationSpec.builder(ClassName.get("com.oracle.truffle.api.nodes", "NodeInfo")).addMember(
						"description", '$S', eClass.name).build
				)
			])
			.applyIfTrue(aleClass !== null, [
				addFields(aleClass.methods.filter [
					it.dispatch && dsl.dslProp.getOrDefault('dispatch', 'false') == 'true'
				].map [
					FieldSpec.builder(ClassName.get(eClass.classImplementationPackageName(packageRoot), '''«eClass.name»DispatchWrapper«it.operationRef.name.toFirstUpper»'''), '''cached«it.operationRef.name.toFirstUpper»''')
						.addModifiers(PRIVATE)
						.addAnnotation(ClassName.get('com.oracle.truffle.api.CompilerDirectives', 'CompilationFinal'))
						.build
				])
			])
			.applyIfTrue(aleClass !== null, [
				addMethods(aleClass.methods.filter [
					it.dispatch && dsl.dslProp.getOrDefault('dispatch', 'false') == 'true'
				].map [
					MethodSpec.methodBuilder('''getCached«it.operationRef.name.toFirstUpper»''')
						.returns(ClassName.get(eClass.classImplementationPackageName(packageRoot), '''«eClass.name»DispatchWrapper«it.operationRef.name.toFirstUpper»'''))
						.addModifiers(PUBLIC)
						.addCode('''
						return this.cached«it.operationRef.name.toFirstUpper»;
						''')
						.build
				])
			])
			.applyIfTrue(isTruffle, [addFields(registreredDispatch.toList.map[method|
				FieldSpec
					.builder(ClassName.get(implPackage, '''«(method.eContainer as ExtendedClass).normalizeExtendedClassName»Dispatch«method.operationRef.name.toFirstUpper»'''), '''dispatch«(method.eContainer as ExtendedClass).normalizeExtendedClassName»«method.operationRef.name.toFirstUpper»''', PRIVATE)
					.addAnnotation(ClassName.get('com.oracle.truffle.api.nodes.Node', 'Child'))
					.build
			])])
			.addMethod(MethodSpec.constructorBuilder.addCode('''
				super();
				«IF aleClass !== null»
					«FOR method: aleClass.methods.filter[it.dispatch && dsl.dslProp.getOrDefault('dispatch', 'false') == 'true']»
						this.cached«method.operationRef.name.toFirstUpper» = new «eClass.classImplementationPackageName(packageRoot)».«eClass.name»DispatchWrapper«method.operationRef.name.toFirstUpper»(this);
					«ENDFOR»
					«FOR w:whileOps»
					«ENDFOR»
				«ENDIF»
				«IF isTruffle»
				«FOR method: registreredDispatch.toList»
					this.dispatch«(method.eContainer as ExtendedClass).normalizeExtendedClassName»«method.operationRef.name.toFirstUpper» = «implPackage».«(method.eContainer as ExtendedClass).normalizeExtendedClassName»Dispatch«method.operationRef.name.toFirstUpper»NodeGen.create(); 
				«ENDFOR»
				«ENDIF»
				''')
				.addModifiers(PROTECTED)
				.build
			)
			.addModifiers(PUBLIC).build

		val javaFile = JavaFile.builder(eClass.classImplementationPackageName(packageRoot), factory)
			.indent('\t')
			.build
		javaFile.writeTo(directory)
		
		
		generateDispatchClasses(aleClass, directory, eClass)
		generateDispatchWrapperClasses(aleClass, directory, eClass)
		generateRootNodes(aleClass, directory, eClass)

		registreredArrays = newHashSet
		registreredDispatch = newHashSet
	}
	
	def generateRootNodes(ExtendedClass aleClass, File directory, EClass eClass) {
		if (aleClass !== null) {
			// Generation of the dispatch classes
			aleClass.methods.filter[it.dispatch && dsl.dslProp.getOrDefault('dispatch', 'false') == 'true'].forEach [ method |

				val packageFQN = eClass.classImplementationPackageName(packageRoot)
				val rootNodeName = '''«eClass.name»«method.operationRef.name.toFirstUpper»RootNode'''
				val eClassInterfaceType = ClassName.get(eClass.classInterfacePackageName(packageRoot), eClass.classInterfaceClassName)
				val virtualFrameType = ClassName.get('com.oracle.truffle.api.frame', 'VirtualFrame')
				
				val factoryDispatch = TypeSpec
					.classBuilder(rootNodeName)
					.superclass(ClassName.get('com.oracle.truffle.api.nodes', 'RootNode'))
					// TODO: add conditional, must be a child only if it type is a Node
					.addField(FieldSpec
							.builder(eClassInterfaceType, 'it', PRIVATE)
							.addAnnotation(ClassName.get('com.oracle.truffle.api.nodes.Node', 'Child'))
							.build)
					.addMethod(MethodSpec
						.constructorBuilder
						.addParameter(eClassInterfaceType, 'it')
						.addCode('''
							super(null);
							this.it = it;
							''')
						.addModifiers(PUBLIC)
						.build
					)
					.addMethod(MethodSpec
						.methodBuilder('execute')
						.addAnnotation(Override)
						.addParameter(virtualFrameType, 'frame')
						.returns(Object)
						.mapParameters(method)
						.addCode('''
						«IF method.operationRef.EType !== null»return «ENDIF»it.«method.operationRef.name»(«FOR p: method.operationRef.EParameters SEPARATOR ', '»«p.name»«ENDFOR»);
						«IF method.operationRef.EType === null»return null;«ENDIF»
						''')
						.addModifiers(PUBLIC)
						.build
					)
					.addModifiers(PUBLIC)
					.build

				val javaFileDispatch = JavaFile.builder(packageFQN, factoryDispatch)
					.indent('\t')
					.build
				javaFileDispatch.writeTo(directory)

			]
		}
	}
	
	def generateDispatchWrapperClasses(ExtendedClass aleClass, File directory, EClass eClass) {
		if (aleClass !== null) {
			// Generation of the dispatch classes
			aleClass.methods.filter[it.dispatch && dsl.dslProp.getOrDefault('dispatch', 'false') == 'true'].forEach [ method |

				val packageFQN = eClass.classImplementationPackageName(packageRoot)
				val cyclicAssumptionType = ClassName.get('com.oracle.truffle.api.utilities', 'CyclicAssumption')
				val rootCalltargetType = ClassName.get('com.oracle.truffle.api', 'RootCallTarget')
				val assumptionType = ClassName.get('com.oracle.truffle.api', 'Assumption')
				val truffleType = ClassName.get('com.oracle.truffle.api', 'Truffle')
				val rootNodeName = '''«eClass.name»«method.operationRef.name.toFirstUpper»RootNode'''
				val rootNodeType = ClassName.get(packageFQN, rootNodeName)
				val eClassInterfaceType = ClassName.get(eClass.classInterfacePackageName(packageRoot), eClass.classInterfaceClassName)
				
				val name = '''«eClass.name»DispatchWrapper«method.operationRef.name.toFirstUpper»_«Math.random * 99999999»'''
				
				/* Look for the closest method that is overriden by the current method and that also has a dispatch annotation */
				val s0 = resolved.filter[eClass.EAllSuperTypes.contains(it.eCls)]
				val s1 = s0.filter[it.aleCls !== null]
				val s2 = s1.map[resolved|
					resolved.aleCls.allMethods.filter[
					it.operationRef.name == method.operationRef.name && method.dispatch
					].map[resolved -> it]
				].flatten

				val overridenMethods = s2.filter[x | x.value.dispatch ]
					.sortWith(new Comparator<Pair<ResolvedClass, Method>>() {
						override compare(Pair<ResolvedClass, Method> arg0, Pair<ResolvedClass, Method> arg1) {
							if(arg0.key.eCls instanceof EClass) {
								val eCls = arg0.key.eCls as EClass
								if(eCls.EAllSuperTypes.contains(arg1.key.eCls)) {
									-1
								} else {
									1
								}
							} else {
								0
							}
						}
					}).head

				val factoryDispatch = TypeSpec.classBuilder(namingUtils.dispatchWrapperClassName(eClass, method))
					.applyIfTrue(overridenMethods !== null, [it.superclass(ClassName.get(packageFQN, namingUtils.dispatchWrapperClassName(overridenMethods.key.eCls as EClass, overridenMethods.value)))])
					.addField(ClassName.get('com.oracle.truffle.api', 'RootCallTarget'), 'callTarget', PRIVATE)
					.addField(cyclicAssumptionType, 'callTargetStable', PRIVATE, FINAL)
					.addMethod(MethodSpec
						.constructorBuilder
						.addParameter(eClassInterfaceType, 'it')
						.addCode('''
						«IF overridenMethods !== null»super(it);«ENDIF»
						this.callTargetStable = new $T($S);
						this.callTarget = $T.getRuntime().createCallTarget(new $T(it));
						''', cyclicAssumptionType, name, truffleType, rootNodeType)
						.addModifiers(PROTECTED)
						.build)
					.addMethod(MethodSpec
						.methodBuilder('getCallTarget')
						.returns(rootCalltargetType)
						.addCode('''return callTarget;''')
						.addModifiers(PUBLIC)
						.build
					)
					.addMethod(MethodSpec
						.methodBuilder('getCallTargetStable')
						.returns(assumptionType)
						.addCode('''return callTargetStable.getAssumption();''')
						.addModifiers(PUBLIC)
						.build
					)
					.addModifiers(PUBLIC)
					.build

				val javaFileDispatch = JavaFile.builder(packageFQN, factoryDispatch)
					.indent('\t')
					.build
				javaFileDispatch.writeTo(directory)

			]
		}
	}
	
	def generateDispatchClasses(ExtendedClass aleClass, File directory, EClass eClass) {
		if (aleClass !== null) {
			// Generation of the dispatch classes
			aleClass.methods.filter[it.dispatch && dsl.dslProp.getOrDefault('dispatch', 'false') == 'true'].forEach [ method |

				val specializationType = ClassName.get('com.oracle.truffle.api.dsl', 'Specialization')
				val cachedType = ClassName.get('com.oracle.truffle.api.dsl', 'Cached')

				val factoryDispatch = TypeSpec.
					classBuilder('''«eClass.name»Dispatch«method.operationRef.name.toFirstUpper»''').superclass(
						ClassName.get("com.oracle.truffle.api.nodes", "Node")).addField(
						FieldSpec.builder(int, 'INLINE_CACHE_SIZE', PUBLIC, STATIC, FINAL).initializer('3').build).
					addMethod(
						MethodSpec.methodBuilder('''executeDispatch''').addModifiers(PUBLIC, ABSTRACT).
							addParameter(Object, 'function').addParameter(typeof(Object[]), 'arguments').
							returns(Object).build).addMethod(
						MethodSpec.methodBuilder('doDirect').addAnnotation(
							AnnotationSpec.builder(specializationType).
								addMember('limit', '"INLINE_CACHE_SIZE"').addMember('guards',
									'"function.getCallTarget() == cachedTarget"')
								.addMember('assumptions', '"callTargetStable"').build
						).addModifiers(PROTECTED, STATIC).returns(Object).addParameter(
							ParameterSpec.builder(ClassName.get(eClass.classImplementationPackageName(packageRoot), '''«eClass.name»DispatchWrapper«method.operationRef.name.toFirstUpper»'''), 'function').
								build
						).addParameter(typeof(Object[]), 'arguments').addParameter(
							ParameterSpec.builder(ClassName.get('com.oracle.truffle.api', 'Assumption'),
								'callTargetStable').addAnnotation(
								AnnotationSpec.builder(ClassName.get('com.oracle.truffle.api.dsl', 'Cached')).
									addMember('value', '"function.getCallTargetStable()"')
									.build
							).build
						).addParameter(
							ParameterSpec.builder(ClassName.get('com.oracle.truffle.api', 'RootCallTarget'),
								'cachedTarget').addAnnotation(
								AnnotationSpec.builder(ClassName.get('com.oracle.truffle.api.dsl', 'Cached')).
									addMember('value', '"function.getCallTarget()"')
									.build
							).build
						).addParameter(
							ParameterSpec.builder(ClassName.get('com.oracle.truffle.api.nodes', 'DirectCallNode'),
								'callNode').addAnnotation(
								AnnotationSpec.builder(cachedType).
									addMember('value', '"create(cachedTarget)"')
									.build
							).build
						)
						.addCode("return callNode.call(arguments);")
						.build
					)
					.addMethod(MethodSpec
						.methodBuilder('doIndirect')
						.addAnnotation(AnnotationSpec
							.builder(specializationType)
							.addMember('replaces', '"doDirect"')
							.build
						)
						.addParameter(
							ParameterSpec.builder(ClassName.get(eClass.classImplementationPackageName(packageRoot), '''«eClass.name»DispatchWrapper«method.operationRef.name.toFirstUpper»'''), 'function').
								build
						)
						.addParameter(typeof(Object[]), 'arguments')
						.addParameter(ParameterSpec
							.builder(ClassName.get('com.oracle.truffle.api.nodes', 'IndirectCallNode'), 'callNode')
							.addAnnotation(AnnotationSpec.builder(cachedType).addMember('value', '"create()"').build)
							.build
						)
						.addCode('''return callNode.call(function.getCallTarget(), arguments);''')
						.returns(Object)
						.addModifiers(PROTECTED, STATIC)
						.build
					)
					.addModifiers(PUBLIC, ABSTRACT).build

				val javaFileDispatch = JavaFile.builder(eClass.classImplementationPackageName(packageRoot), factoryDispatch)
					.indent('\t')
					.build
				javaFileDispatch.writeTo(directory)

			]
		}
	}

	def MethodSpec compile(Method method, ExtendedClass aleClass, EClass aClass, boolean isTruffle) {
		val retType = if (method.operationRef.EType !== null) {
				if (method.operationRef.EType instanceof EClass &&
					!(method.operationRef.EType.EPackage == EcorePackage.eINSTANCE)) {
					ClassName.get((method.operationRef.EType as EClass).classInterfacePackageName(packageRoot),
						(method.operationRef.EType as EClass).name)
				} else {
					TypeName.get(method.operationRef.EType.instanceClass)

				}
			} else
				null

		MethodSpec.methodBuilder(method.operationRef.name).addModifiers(PUBLIC).applyIfTrue(retType !== null, [
			returns(retType)
		])
		.applyIfTrue(aClass.EAnnotations.exists[it.source == 'RuntimeData'], [addAnnotation(ClassName.get("com.oracle.truffle.api.CompilerDirectives", "TruffleBoundary"))])
		.addParameters(method.operationRef.EParameters.map [
			if (it.EType.instanceClass !== null) {
				if (it.EType instanceof EClass && !(it.EType.EPackage == EcorePackage.eINSTANCE)) {
					ParameterSpec.builder(
						ClassName.get((it.EType as EClass).classInterfacePackageName(packageRoot), (it.EType as EClass).name),
						it.name).build
				} else {
					ParameterSpec.builder(it.EType.instanceClass, it.name).build

				}
			} else {
				ParameterSpec.builder(it.EType.resolveType, it.name).build
			}
		])
		.openMethod(method.operationRef.EType)
		.compileBodyAndPrefix(method.body, new CompilerExpressionCtx('this', aleClass, aClass), isTruffle)
		.closeMethod(method.operationRef.EType).build
		
		
	}
	
	def MethodSpec.Builder compileBodyAndPrefix(MethodSpec.Builder builder, Statement body,
		CompilerExpressionCtx ctx, boolean isTruffle) {
		val cbb = compileBody(CodeBlock.builder, body, ctx)
		if(isTruffle) {
			registreredArrays.fold(builder, [b, array|
			val x = ctx.eClass.EAllReferences.filter[it.name == array].head.EType.resolveType
			b.addStatement('''
			if (this.«array»Arr == null) {
				com.oracle.truffle.api.CompilerDirectives.transferToInterpreterAndInvalidate();
				if (this.«array» != null) this.«array»Arr = this.«array».toArray(new «x»[0]);
				else this.«array»Arr = new «x»[] {};
				
			}
			''')
		]).addStatement(cbb.build.toString)
		
		} else {
			val ts = cbb.build.toString
			builder.addCode(ts)
		}
		
	}

	def MethodSpec.Builder closeMethod(MethodSpec.Builder builder, EClassifier type) {
		if (type !== null) {
			builder.addStatement("return result")
		} else {
			builder
		}
	}
	

	def MethodSpec.Builder openMethod(MethodSpec.Builder builder, EClassifier type) {
		if (type !== null) {
			builder.addStatement('''$T result''', type.solveType)
		} else {
			builder
		}
	}

	def MethodSpec.Builder mapParameters(MethodSpec.Builder builderSeed, Method method) {
		var builder = builderSeed
		for (var i = 0; i < method.operationRef.EParameters.size; i = i + 1) {

			val parameter = method.operationRef.EParameters.get(i)

			val type = if (parameter.EType.instanceClass !== null) {
					if (parameter.EType instanceof EClass && !(parameter.EType.EPackage == EcorePackage.eINSTANCE)) {
						ClassName.get((parameter.EType as EClass).classInterfacePackageName(packageRoot),
							(parameter.EType as EClass).name)
					} else {
						ClassName.get(parameter.EType.instanceClass)
					}
				} else {
					parameter.EType.resolveType
				}
			builder = builder.addStatement('''«type» «parameter.name» = («type») frame.getArguments()[«i»]''')
		}

		return builder
	}

	def getEcoreInterfacesPackage() {
		val gm = syntaxes.get(dsl.allSyntaxes.head).value
		gm.genPackages.head.qualifiedPackageName
	}

	def returnType(MethodSpec.Builder builder, EClassifier type) {
		if (type !== null) {
			if (type.instanceClass !== null) {
				builder.returns(type.instanceClass)
			} else {
				builder.returns(type.resolveType)
			}
		} else {
			builder
		}
	}
}
