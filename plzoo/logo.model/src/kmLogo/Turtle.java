/**
 */
package kmLogo;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Turtle</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link kmLogo.Turtle#getPosition <em>Position</em>}</li>
 *   <li>{@link kmLogo.Turtle#getHeading <em>Heading</em>}</li>
 *   <li>{@link kmLogo.Turtle#isPenUp <em>Pen Up</em>}</li>
 *   <li>{@link kmLogo.Turtle#getDrawings <em>Drawings</em>}</li>
 *   <li>{@link kmLogo.Turtle#getCallStack <em>Call Stack</em>}</li>
 * </ul>
 *
 * @see kmLogo.KmLogoPackage#getTurtle()
 * @model
 * @generated
 */
public interface Turtle extends EObject {
	/**
	 * Returns the value of the '<em><b>Position</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Position</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Position</em>' containment reference.
	 * @see #setPosition(Point)
	 * @see kmLogo.KmLogoPackage#getTurtle_Position()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Point getPosition();

	/**
	 * Sets the value of the '{@link kmLogo.Turtle#getPosition <em>Position</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Position</em>' containment reference.
	 * @see #getPosition()
	 * @generated
	 */
	void setPosition(Point value);

	/**
	 * Returns the value of the '<em><b>Heading</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Heading</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Heading</em>' attribute.
	 * @see #setHeading(double)
	 * @see kmLogo.KmLogoPackage#getTurtle_Heading()
	 * @model
	 * @generated
	 */
	double getHeading();

	/**
	 * Sets the value of the '{@link kmLogo.Turtle#getHeading <em>Heading</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Heading</em>' attribute.
	 * @see #getHeading()
	 * @generated
	 */
	void setHeading(double value);

	/**
	 * Returns the value of the '<em><b>Pen Up</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Pen Up</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Pen Up</em>' attribute.
	 * @see #setPenUp(boolean)
	 * @see kmLogo.KmLogoPackage#getTurtle_PenUp()
	 * @model
	 * @generated
	 */
	boolean isPenUp();

	/**
	 * Sets the value of the '{@link kmLogo.Turtle#isPenUp <em>Pen Up</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Pen Up</em>' attribute.
	 * @see #isPenUp()
	 * @generated
	 */
	void setPenUp(boolean value);

	/**
	 * Returns the value of the '<em><b>Drawings</b></em>' containment reference list.
	 * The list contents are of type {@link kmLogo.Segment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Drawings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Drawings</em>' containment reference list.
	 * @see kmLogo.KmLogoPackage#getTurtle_Drawings()
	 * @model containment="true"
	 * @generated
	 */
	EList<Segment> getDrawings();

	/**
	 * Returns the value of the '<em><b>Call Stack</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Call Stack</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Call Stack</em>' containment reference.
	 * @see #setCallStack(CallStack)
	 * @see kmLogo.KmLogoPackage#getTurtle_CallStack()
	 * @model containment="true" required="true"
	 * @generated
	 */
	CallStack getCallStack();

	/**
	 * Sets the value of the '{@link kmLogo.Turtle#getCallStack <em>Call Stack</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Call Stack</em>' containment reference.
	 * @see #getCallStack()
	 * @generated
	 */
	void setCallStack(CallStack value);

} // Turtle
