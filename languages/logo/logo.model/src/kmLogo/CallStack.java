/**
 */
package kmLogo;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Call Stack</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kmLogo.CallStack#getFrames <em>Frames</em>}</li>
 * </ul>
 *
 * @see kmLogo.KmLogoPackage#getCallStack()
 * @model
 * @generated
 */
public interface CallStack extends EObject {
	/**
	 * Returns the value of the '<em><b>Frames</b></em>' containment reference list.
	 * The list contents are of type {@link kmLogo.StackFrame}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Frames</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Frames</em>' containment reference list.
	 * @see kmLogo.KmLogoPackage#getCallStack_Frames()
	 * @model containment="true"
	 * @generated
	 */
	EList<StackFrame> getFrames();

} // CallStack
