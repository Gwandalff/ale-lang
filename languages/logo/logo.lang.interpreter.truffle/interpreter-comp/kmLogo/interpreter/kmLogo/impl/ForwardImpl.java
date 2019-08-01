package kmLogo.interpreter.kmLogo.impl;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.Node.Child;
import com.oracle.truffle.api.nodes.NodeInfo;
import java.lang.Object;
import java.lang.Override;
import kmLogo.interpreter.kmLogo.KmLogoPackage;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

@NodeInfo(
		description = "Forward"
)
public class ForwardImpl extends PrimitiveImpl {
	@Child
	protected ExpressionImpl steps;

	protected ForwardImpl() {
		super();
	}

	@Override
	@TruffleBoundary
	protected EClass eStaticClass() {
		return KmLogoPackage.Literals.FORWARD;
	}

	@TruffleBoundary
	public ExpressionImpl getSteps() {
		return steps;
	}

	@TruffleBoundary
	public NotificationChain basicSetSteps(ExpressionImpl newSteps, NotificationChain msgs) {
		ExpressionImpl oldSteps = steps;
		steps = newSteps;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, KmLogoPackage.FORWARD__STEPS, oldSteps, newSteps);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	@TruffleBoundary
	public void setSteps(ExpressionImpl newSteps) {
		if (newSteps != steps) {
			NotificationChain msgs = null;
			if (steps != null)
				msgs = ((InternalEObject) steps).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - KmLogoPackage.FORWARD__STEPS, null, msgs);
			if (newSteps != null)
				msgs = ((InternalEObject) newSteps).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - KmLogoPackage.FORWARD__STEPS, null, msgs);
			msgs = basicSetSteps(newSteps, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, KmLogoPackage.FORWARD__STEPS, newSteps, newSteps));
	}

	@Override
	@TruffleBoundary
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
			case KmLogoPackage.FORWARD__STEPS :
				return basicSetSteps(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	@TruffleBoundary
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case KmLogoPackage.FORWARD__STEPS :
				return getSteps();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	@TruffleBoundary
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case KmLogoPackage.FORWARD__STEPS :
				setSteps((ExpressionImpl) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	@TruffleBoundary
	public void eUnset(int featureID) {
		switch (featureID) {
			case KmLogoPackage.FORWARD__STEPS :
				setSteps((ExpressionImpl) null);
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	@TruffleBoundary
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case KmLogoPackage.FORWARD__STEPS :
				return steps != null;
		}
		return super.eIsSet(featureID);
	}

	public double eval(TurtleImpl turtle) {
		double result;
		double move = ((double) (((ExpressionImpl) (this.getSteps())).eval((TurtleImpl) (turtle))));
		((TurtleImpl) (turtle)).forward((double) (move));
		result = (double) (0.0) ;
		return result;
	}
}