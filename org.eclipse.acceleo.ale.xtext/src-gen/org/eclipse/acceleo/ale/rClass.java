/**
 * generated by Xtext 2.10.0
 */
package org.eclipse.acceleo.ale;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>rClass</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.acceleo.ale.rClass#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ale.rClass#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link org.eclipse.acceleo.ale.rClass#getOperations <em>Operations</em>}</li>
 * </ul>
 *
 * @see org.eclipse.acceleo.ale.AlePackage#getrClass()
 * @model
 * @generated
 */
public interface rClass extends EObject
{
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
   * @see org.eclipse.acceleo.ale.AlePackage#getrClass_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link org.eclipse.acceleo.ale.rClass#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Attributes</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.acceleo.ale.rAttribute}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Attributes</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Attributes</em>' containment reference list.
   * @see org.eclipse.acceleo.ale.AlePackage#getrClass_Attributes()
   * @model containment="true"
   * @generated
   */
  EList<rAttribute> getAttributes();

  /**
   * Returns the value of the '<em><b>Operations</b></em>' containment reference list.
   * The list contents are of type {@link org.eclipse.acceleo.ale.rOperation}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Operations</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Operations</em>' containment reference list.
   * @see org.eclipse.acceleo.ale.AlePackage#getrClass_Operations()
   * @model containment="true"
   * @generated
   */
  EList<rOperation> getOperations();

} // rClass