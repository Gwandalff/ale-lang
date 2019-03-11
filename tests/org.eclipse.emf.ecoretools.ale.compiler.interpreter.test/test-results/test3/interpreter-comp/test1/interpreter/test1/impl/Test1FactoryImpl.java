package test1.interpreter.test1.impl;

import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.String;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import test1.interpreter.test1.ConceptA;
import test1.interpreter.test1.ConceptB;
import test1.interpreter.test1.ConceptC;
import test1.interpreter.test1.Test1Factory;
import test1.interpreter.test1.Test1Package;

public class Test1FactoryImpl extends EFactoryImpl implements Test1Factory {
	public Test1FactoryImpl() {
	}

	public static Test1Factory init() {
		try {
			Test1Factory theTest1Factory = (Test1Factory) EPackage.Registry.INSTANCE.getEFactory(Test1Package.eNS_URI);
			if (theTest1Factory != null) {
				return theTest1Factory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new Test1FactoryImpl();
	}

	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case Test1Package.CONCEPT_A :
				return createConceptA();
			case Test1Package.CONCEPT_B :
				return createConceptB();
			case Test1Package.CONCEPT_C :
				return createConceptC();
			default :
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			default :
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			default :
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	public Test1Package getTest1Package() {
		return (Test1Package) getEPackage();
	}

	public ConceptA createConceptA() {
		ConceptAImpl ret = new ConceptAImpl();
		return ret;
	}

	public ConceptB createConceptB() {
		ConceptBImpl ret = new ConceptBImpl();
		return ret;
	}

	public ConceptC createConceptC() {
		ConceptCImpl ret = new ConceptCImpl();
		return ret;
	}
}