/**
 */
package org.tetrabox.minijava.xminijava.miniJava;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.tetrabox.minijava.xminijava.miniJava.State#getRootFrame <em>Root Frame</em>}</li>
 *   <li>{@link org.tetrabox.minijava.xminijava.miniJava.State#getObjectsHeap <em>Objects Heap</em>}</li>
 *   <li>{@link org.tetrabox.minijava.xminijava.miniJava.State#getOutputStream <em>Output Stream</em>}</li>
 *   <li>{@link org.tetrabox.minijava.xminijava.miniJava.State#getArraysHeap <em>Arrays Heap</em>}</li>
 * </ul>
 *
 * @see org.tetrabox.minijava.xminijava.miniJava.MiniJavaPackage#getState()
 * @model
 * @generated
 */
public interface State extends EObject {
	/**
	 * Returns the value of the '<em><b>Root Frame</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Root Frame</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Root Frame</em>' containment reference.
	 * @see #setRootFrame(Frame)
	 * @see org.tetrabox.minijava.xminijava.miniJava.MiniJavaPackage#getState_RootFrame()
	 * @model containment="true"
	 * @generated
	 */
	Frame getRootFrame();

	/**
	 * Sets the value of the '{@link org.tetrabox.minijava.xminijava.miniJava.State#getRootFrame <em>Root Frame</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Root Frame</em>' containment reference.
	 * @see #getRootFrame()
	 * @generated
	 */
	void setRootFrame(Frame value);

	/**
	 * Returns the value of the '<em><b>Objects Heap</b></em>' containment reference list.
	 * The list contents are of type {@link org.tetrabox.minijava.xminijava.miniJava.ObjectInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Objects Heap</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Objects Heap</em>' containment reference list.
	 * @see org.tetrabox.minijava.xminijava.miniJava.MiniJavaPackage#getState_ObjectsHeap()
	 * @model containment="true"
	 * @generated
	 */
	EList<ObjectInstance> getObjectsHeap();

	/**
	 * Returns the value of the '<em><b>Output Stream</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Stream</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Stream</em>' containment reference.
	 * @see #setOutputStream(OutputStream)
	 * @see org.tetrabox.minijava.xminijava.miniJava.MiniJavaPackage#getState_OutputStream()
	 * @model containment="true"
	 * @generated
	 */
	OutputStream getOutputStream();

	/**
	 * Sets the value of the '{@link org.tetrabox.minijava.xminijava.miniJava.State#getOutputStream <em>Output Stream</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Stream</em>' containment reference.
	 * @see #getOutputStream()
	 * @generated
	 */
	void setOutputStream(OutputStream value);

	/**
	 * Returns the value of the '<em><b>Arrays Heap</b></em>' containment reference list.
	 * The list contents are of type {@link org.tetrabox.minijava.xminijava.miniJava.ArrayInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Arrays Heap</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Arrays Heap</em>' containment reference list.
	 * @see org.tetrabox.minijava.xminijava.miniJava.MiniJavaPackage#getState_ArraysHeap()
	 * @model containment="true"
	 * @generated
	 */
	EList<ArrayInstance> getArraysHeap();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Frame findCurrentFrame();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	Context findCurrentContext();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void println(String string);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void pushNewContext();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void popCurrentContext();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void pushNewFrame(ObjectInstance receiver, Call c, Context newContext);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void popCurrentFrame();

} // State
