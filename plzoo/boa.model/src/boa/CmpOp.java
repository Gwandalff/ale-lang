/**
 */
package boa;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cmp Op</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link boa.CmpOp#getLhs <em>Lhs</em>}</li>
 *   <li>{@link boa.CmpOp#getRhs <em>Rhs</em>}</li>
 * </ul>
 *
 * @see boa.BoaPackage#getCmpOp()
 * @model abstract="true"
 * @generated
 */
public interface CmpOp extends Expr {
	/**
	 * Returns the value of the '<em><b>Lhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lhs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lhs</em>' containment reference.
	 * @see #setLhs(Expr)
	 * @see boa.BoaPackage#getCmpOp_Lhs()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expr getLhs();

	/**
	 * Sets the value of the '{@link boa.CmpOp#getLhs <em>Lhs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lhs</em>' containment reference.
	 * @see #getLhs()
	 * @generated
	 */
	void setLhs(Expr value);

	/**
	 * Returns the value of the '<em><b>Rhs</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rhs</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rhs</em>' containment reference.
	 * @see #setRhs(Expr)
	 * @see boa.BoaPackage#getCmpOp_Rhs()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Expr getRhs();

	/**
	 * Sets the value of the '{@link boa.CmpOp#getRhs <em>Rhs</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rhs</em>' containment reference.
	 * @see #getRhs()
	 * @generated
	 */
	void setRhs(Expr value);

} // CmpOp
