package miniJava.interpreter.miniJava.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import miniJava.interpreter.miniJava.ArrayAccess;
import miniJava.interpreter.miniJava.ArrayRefValue;
import miniJava.interpreter.miniJava.Expression;
import miniJava.interpreter.miniJava.IntegerValue;
import miniJava.interpreter.miniJava.MiniJavaPackage;
import miniJava.interpreter.miniJava.State;
import miniJava.interpreter.miniJava.Value;

public class ArrayAccessImpl extends ExpressionImpl implements ArrayAccess {
	protected Expression index;

	protected Expression object;

	protected ArrayAccessImpl() {
		super();
	}

	public NotificationChain basicSetIndex(Expression newIndex, NotificationChain msgs) {
		Expression oldIndex = index;
		index = newIndex;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MiniJavaPackage.ARRAY_ACCESS__INDEX, oldIndex, newIndex);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	public NotificationChain basicSetObject(Expression newObject, NotificationChain msgs) {
		Expression oldObject = object;
		object = newObject;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MiniJavaPackage.ARRAY_ACCESS__OBJECT, oldObject, newObject);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case MiniJavaPackage.ARRAY_ACCESS__OBJECT:
			return getObject();
		case MiniJavaPackage.ARRAY_ACCESS__INDEX:
			return getIndex();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case MiniJavaPackage.ARRAY_ACCESS__OBJECT:
			return basicSetObject(null, msgs);
		case MiniJavaPackage.ARRAY_ACCESS__INDEX:
			return basicSetIndex(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case MiniJavaPackage.ARRAY_ACCESS__OBJECT:
			return object != null;
		case MiniJavaPackage.ARRAY_ACCESS__INDEX:
			return index != null;
		}
		return super.eIsSet(featureID);
	}

	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case MiniJavaPackage.ARRAY_ACCESS__OBJECT:
			setObject((Expression) newValue);
			return;
		case MiniJavaPackage.ARRAY_ACCESS__INDEX:
			setIndex((Expression) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	protected EClass eStaticClass() {
		return MiniJavaPackage.Literals.ARRAY_ACCESS;
	}

	public void eUnset(int featureID) {
		switch (featureID) {
		case MiniJavaPackage.ARRAY_ACCESS__OBJECT:
			setObject((Expression) null);
			return;
		case MiniJavaPackage.ARRAY_ACCESS__INDEX:
			setIndex((Expression) null);
			return;
		}
		super.eUnset(featureID);
	}

	public Value evaluateExpression(State state) {
		Value result;
		ArrayRefValue arrayVal = ((ArrayRefValue) this.object.evaluateExpression(state));
		IntegerValue indexVal = ((IntegerValue) this.index.evaluateExpression(state));
		result = arrayVal.getInstance().getValue().get(indexVal.getValue()).copy();
		;
		return result;
	}

	public Expression getIndex() {
		return index;
	}

	public Expression getObject() {
		return object;
	}

	public void setIndex(Expression newIndex) {
		if (newIndex != index) {
			NotificationChain msgs = null;
			if (index != null)
				msgs = ((InternalEObject) index).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - MiniJavaPackage.ARRAY_ACCESS__INDEX, null, msgs);
			if (newIndex != null)
				msgs = ((InternalEObject) newIndex).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - MiniJavaPackage.ARRAY_ACCESS__INDEX, null, msgs);
			msgs = basicSetIndex(newIndex, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MiniJavaPackage.ARRAY_ACCESS__INDEX, newIndex,
					newIndex));
	}

	public void setObject(Expression newObject) {
		if (newObject != object) {
			NotificationChain msgs = null;
			if (object != null)
				msgs = ((InternalEObject) object).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - MiniJavaPackage.ARRAY_ACCESS__OBJECT, null, msgs);
			if (newObject != null)
				msgs = ((InternalEObject) newObject).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - MiniJavaPackage.ARRAY_ACCESS__OBJECT, null, msgs);
			msgs = basicSetObject(newObject, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MiniJavaPackage.ARRAY_ACCESS__OBJECT, newObject,
					newObject));
	}
}
