/**
 */
package org.eclipse.ecoretools.ale.implementation;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model Unit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.ecoretools.ale.implementation.ModelUnit#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.ecoretools.ale.implementation.ModelUnit#getServices <em>Services</em>}</li>
 *   <li>{@link org.eclipse.ecoretools.ale.implementation.ModelUnit#getClassExtensions <em>Class Extensions</em>}</li>
 *   <li>{@link org.eclipse.ecoretools.ale.implementation.ModelUnit#getClassDefinitions <em>Class Definitions</em>}</li>
 * </ul>
 *
 * @see org.eclipse.ecoretools.ale.implementation.ImplementationPackage#getModelUnit()
 * @model
 * @generated
 */
public interface ModelUnit extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see org.eclipse.ecoretools.ale.implementation.ImplementationPackage#getModelUnit_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.ecoretools.ale.implementation.ModelUnit#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Services</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Services</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Services</em>' attribute list.
	 * @see org.eclipse.ecoretools.ale.implementation.ImplementationPackage#getModelUnit_Services()
	 * @model
	 * @generated
	 */
	EList<String> getServices();

	/**
	 * Returns the value of the '<em><b>Class Extensions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.ecoretools.ale.implementation.ExtendedClass}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Extensions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Extensions</em>' containment reference list.
	 * @see org.eclipse.ecoretools.ale.implementation.ImplementationPackage#getModelUnit_ClassExtensions()
	 * @model containment="true"
	 * @generated
	 */
	EList<ExtendedClass> getClassExtensions();

	/**
	 * Returns the value of the '<em><b>Class Definitions</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.ecoretools.ale.implementation.RuntimeClass}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Class Definitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Class Definitions</em>' containment reference list.
	 * @see org.eclipse.ecoretools.ale.implementation.ImplementationPackage#getModelUnit_ClassDefinitions()
	 * @model containment="true"
	 * @generated
	 */
	EList<RuntimeClass> getClassDefinitions();

} // ModelUnit
