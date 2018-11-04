package boa.interpreter.boa.impl;

import boa.interpreter.boa.BoaPackage;
import boa.interpreter.boa.Ctx;
import boa.interpreter.boa.EvalFunRes;
import boa.interpreter.boa.EvalRes;
import boa.interpreter.boa.Expr;
import boa.interpreter.boa.Fun;
import com.oracle.truffle.api.nodes.Node.Child;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.nodes.NodeInfo;

import java.lang.Object;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

@NodeInfo(description = "Fun")
public class FunImpl extends ExprImpl implements Fun {
	protected static final String NAME_EDEFAULT = null;

	protected String name = NAME_EDEFAULT;

	public static final Map<FunImpl, RootCallTarget> map = new WeakHashMap<>();

	@Child
	protected Expr body;

	protected FunImpl() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBody(Expr newBody) {
		if (newBody != body) {
			NotificationChain msgs = null;
			if (body != null)
				msgs = ((InternalEObject) body).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - boa.interpreter.boa.BoaPackage.FUN__BODY, null, msgs);
			if (newBody != null)
				msgs = ((InternalEObject) newBody).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - boa.interpreter.boa.BoaPackage.FUN__BODY, null, msgs);
			msgs = basicSetBody(newBody, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, boa.interpreter.boa.BoaPackage.FUN__BODY, newBody,
					newBody));
	}

	public NotificationChain basicSetBody(Expr newBody, NotificationChain msgs) {
		Expr oldBody = body;
		body = newBody;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					boa.interpreter.boa.BoaPackage.FUN__BODY, oldBody, newBody);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	public Expr getBody() {
		return body;
	}

	protected EClass eStaticClass() {
		return BoaPackage.Literals.FUN;
	}

	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case BoaPackage.FUN__NAME:
			setName((java.lang.String) newValue);
			return;
		case BoaPackage.FUN__BODY:
			setBody((boa.interpreter.boa.Expr) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	public void eUnset(int featureID) {
		switch (featureID) {
		case BoaPackage.FUN__NAME:
			setName(NAME_EDEFAULT);
			return;
		case BoaPackage.FUN__BODY:
			setBody((boa.interpreter.boa.Expr) null);
			return;
		}
		super.eUnset(featureID);
	}

	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case BoaPackage.FUN__NAME:
			return getName();
		case BoaPackage.FUN__BODY:
			return getBody();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case BoaPackage.FUN__NAME:
			return name != NAME_EDEFAULT;
		case BoaPackage.FUN__BODY:
			return body != null;
		}
		return super.eIsSet(featureID);
	}

	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case boa.interpreter.boa.BoaPackage.FUN__BODY:
			return basicSetBody(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	public EvalRes eval(Ctx ctx) {
		EvalRes result;
		if (!map.containsKey(this)) {
			map.put(this, Truffle.getRuntime().createCallTarget(new RootNodeExtension(this.getBody(), null)));
		}
		// TODO build a cache of result here.
		// add a cached annotation on such methods in the code generation ?
		EvalFunRes ret = ((EvalFunRes) boa.interpreter.boa.BoaFactory.eINSTANCE.createEvalFunRes());
		ret.setExp(this);
		ret.setCtx(ctx);
		ret.setName(this.getName());
		result = ret;
		return result;
	}
}