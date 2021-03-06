package boa.interpreter.boa.impl;

import boa.interpreter.boa.BoaPackage;
import boa.interpreter.boa.EvalIntRes;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.NodeInfo;
import java.lang.Object;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

@NodeInfo(
    description = "EvalIntRes"
)
public class EvalIntResImpl extends EvalResImpl implements EvalIntRes {
  protected static final int VALUE_EDEFAULT = 0;

  protected int value = VALUE_EDEFAULT;

  protected EvalIntResImpl() {
    super();
  }

  public int getValue() {
    return value;}

  public void setValue(int value) {
    this.value = value;}

  @TruffleBoundary
  protected EClass eStaticClass() {
    return BoaPackage.Literals.EVAL_INT_RES;}

  @TruffleBoundary
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case BoaPackage.EVAL_INT_RES__VALUE:
    	setValue((int) newValue);
    return;
    }
    super.eSet(featureID, newValue);
  }

  @TruffleBoundary
  public void eUnset(int featureID) {
    switch (featureID) {
    case BoaPackage.EVAL_INT_RES__VALUE:
    	setValue(VALUE_EDEFAULT);
    return;
    }
    super.eUnset(featureID);
  }

  @TruffleBoundary
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case BoaPackage.EVAL_INT_RES__VALUE:
    return getValue();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  @TruffleBoundary
  public boolean eIsSet(int featureID) {
    switch (featureID) {
    case BoaPackage.EVAL_INT_RES__VALUE:
    	return value != VALUE_EDEFAULT;
    }
    return super.eIsSet(featureID);
  }

  @TruffleBoundary
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
      NotificationChain msgs) {
    switch(featureID) {
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }
}
