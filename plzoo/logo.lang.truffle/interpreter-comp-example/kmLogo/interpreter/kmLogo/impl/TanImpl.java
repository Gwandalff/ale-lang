package kmLogo.interpreter.kmLogo.impl;

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.NodeInfo;

import kmLogo.interpreter.kmLogo.KmLogoPackage;
import kmLogo.interpreter.kmLogo.Tan;
import kmLogo.interpreter.kmLogo.Turtle;

@NodeInfo(description = "Tan")
public class TanImpl extends UnaryExpressionImpl implements Tan {
	protected TanImpl() {
		super();
	}

	@TruffleBoundary
	protected EClass eStaticClass() {
		return KmLogoPackage.Literals.TAN;
	}

	@TruffleBoundary
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		}
		super.eSet(featureID, newValue);
	}

	@TruffleBoundary
	public void eUnset(int featureID) {
		switch (featureID) {
		}
		super.eUnset(featureID);
	}

	@TruffleBoundary
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@TruffleBoundary
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		}
		return super.eIsSet(featureID);
	}

	@TruffleBoundary
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	public double eval(Turtle turtle) {
		double result;
		result = org.eclipse.emf.ecoretools.ale.core.interpreter.services.TrigoServices
				.tan(this.getExpression().eval(turtle));
		return result;
	}
}
