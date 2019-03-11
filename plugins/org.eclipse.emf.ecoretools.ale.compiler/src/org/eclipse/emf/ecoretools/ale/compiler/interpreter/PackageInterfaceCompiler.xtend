package org.eclipse.emf.ecoretools.ale.compiler.interpreter

import com.squareup.javapoet.ClassName
import static javax.lang.model.element.Modifier.*
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import java.io.File
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.emf.ecore.EReference
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EStructuralFeature
import com.squareup.javapoet.MethodSpec
import org.eclipse.emf.ecore.EEnum

class PackageInterfaceCompiler {

	extension InterpreterNamingUtils namingUtils = new InterpreterNamingUtils

	def compilePackageInterface(EPackage abstractSyntax, File directory, String packageRoot) {

		val allClasses = abstractSyntax.EClassifiers.filter(EClass)
		val allEnums = abstractSyntax.EClassifiers.filter(EEnum)

		val packageInterfaceType = ClassName.get(abstractSyntax.packageInterfacePackageName(packageRoot),
			abstractSyntax.packageInterfaceClassName)
		val packageImplementationType = ClassName.get(abstractSyntax.packageImplementationPackageName(packageRoot),
			abstractSyntax.packageImplementationClassName)
			
		val factoryInterfaceType = ClassName.get(abstractSyntax.factoryInterfacePackageName(packageRoot),
			abstractSyntax.factoryInterfaceClassName)

		val eInstanceField = FieldSpec.builder(packageInterfaceType, 'eINSTANCE').initializer('''$T.init()''',
			packageImplementationType).addModifiers(PUBLIC, STATIC, FINAL).build

		// TODO: really bad naming!
		val eNSURIField = FieldSpec.builder(String, 'eNS_URI').
			initializer('$S', '''http://«abstractSyntax.name».«abstractSyntax.name».«abstractSyntax.name»/''').addModifiers(PUBLIC, STATIC, FINAL).build

		val eNameField = FieldSpec.builder(String, 'eNAME').initializer('$S', abstractSyntax.name).addModifiers(PUBLIC, STATIC, FINAL).build

		val eNSPrefixField = FieldSpec.builder(String, 'eNS_PREFIX').initializer('$S', abstractSyntax.name).addModifiers(PUBLIC, STATIC, FINAL).build

		val classFields = newArrayList

		var cptr = 0;
		for (EClass clazz : allClasses) {
			classFields +=
				FieldSpec.builder(int, clazz.name.normalizeUpperField).initializer('''«cptr»''').addModifiers(STATIC,
					PUBLIC, FINAL).build
			cptr = cptr + 1
		}
		
		for(EEnum eEnum : allEnums) {
			classFields +=
				FieldSpec.builder(int, eEnum.name.normalizeUpperField).initializer('''«cptr»''').addModifiers(
					STATIC, PUBLIC, FINAL).build
			cptr = cptr + 1
		}

		val classFieldsLiterals = allClasses.map [ clazz |
			FieldSpec.builder(EClass, clazz.name.normalizeUpperField).
				initializer('''eINSTANCE.get«clazz.name.toFirstUpper»()''').addModifiers(PUBLIC, STATIC, FINAL).build
		]

		val Iterable<FieldSpec> eReferenceFieldsLiterals = allClasses.map[clazz|clazz.EReferences.map[field|
			FieldSpec.builder(EReference, field.name.normalizeUpperField(clazz.name)).initializer('''eINSTANCE.get«clazz.name»_«field.name.toFirstUpper»()''').addModifiers(PUBLIC, STATIC, FINAL).build
		]].flatten
		val Iterable<FieldSpec> eAttributeFieldsLiterals = allClasses.map[clazz|clazz.EAttributes.map[field|
			FieldSpec.builder(EAttribute, field.name.normalizeUpperField(clazz.name)).initializer('''eINSTANCE.get«clazz.name»_«field.name.toFirstUpper»()''').addModifiers(PUBLIC, STATIC, FINAL).build
		]].flatten
		val literalType = TypeSpec.interfaceBuilder('Literals').addFields(classFieldsLiterals +
			eReferenceFieldsLiterals + eAttributeFieldsLiterals).addModifiers(PUBLIC, STATIC).build
			
		val getterFields = allClasses.map[clazz|
			MethodSpec.methodBuilder('''get«clazz.name.toFirstUpper»''').returns(EClass).addModifiers(ABSTRACT, PUBLIC).build
		]
		
		val getterEnumFields = allEnums.map[eEnum|
			MethodSpec.methodBuilder('''get«eEnum.name.toFirstUpper»''').returns(EEnum).addModifiers(ABSTRACT, PUBLIC).build
		]
		
		val Iterable<MethodSpec> getterReferencesFields = allClasses.map[clazz|clazz.EReferences.map[field|
			MethodSpec.methodBuilder('''get«clazz.name»_«field.name.toFirstUpper»''').returns(EReference).addModifiers(ABSTRACT, PUBLIC).build
		]].flatten

		val Iterable<MethodSpec> getterAttributesFields = allClasses.map[clazz|clazz.EAttributes.map[field|
			MethodSpec.methodBuilder('''get«clazz.name»_«field.name.toFirstUpper»''').returns(EAttribute).addModifiers(ABSTRACT, PUBLIC).build
		]].flatten
		
		val fieldsAttributesFields = newArrayList
		
		for(EClass clazz: allClasses) {
			var cptrI = 0
			val offset = countOffset(clazz)
			
			for(EStructuralFeature esf: clazz.EStructuralFeatures) {
				fieldsAttributesFields += FieldSpec.builder(int, esf.name.normalizeUpperField(clazz.name)).initializer('''«cptrI+offset»''').addModifiers(PUBLIC, STATIC, FINAL).build
				cptrI = cptrI + 1
			}
		}
		
		
		
		val getFactoryMethod = MethodSpec.methodBuilder('''get«abstractSyntax.name.toFirstUpper»Factory''').returns(factoryInterfaceType).addModifiers(PUBLIC,ABSTRACT).build
		
		val package = TypeSpec
			.interfaceBuilder(abstractSyntax.packageInterfaceClassName)
			.addSuperinterface(EPackage)
			.addFields(#[eInstanceField, eNSURIField, eNameField, eNSPrefixField] + classFields +  fieldsAttributesFields)
			.addMethods(getterFields + getterEnumFields + getterReferencesFields + getterAttributesFields + #[getFactoryMethod])
			.addType(literalType)
			.addModifiers(PUBLIC)
			.build

		val javaFile = JavaFile.builder(abstractSyntax.packageInterfacePackageName(packageRoot), package)
			.indent('\t')
			.build

		javaFile.writeTo(directory)
	}
	
	def int countOffset(EClass clazz) {
		clazz.EAllSuperTypes.map[it.EStructuralFeatures.size].fold(0, [l,r|l+r]) 
	} 
}
