package kmLogo.interpreter.kmLogo.impl;

import java.lang.Object;
import kmLogo.interpreter.kmLogo.Div;
import kmLogo.interpreter.kmLogo.KmLogoPackage;
import kmLogo.interpreter.kmLogo.Turtle;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

public class DivImpl extends BinaryExpImpl implements Div {
  protected DivImpl() {
    super();
  }

  protected EClass eStaticClass() {
    return KmLogoPackage.Literals.DIV;}

  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    }
    super.eSet(featureID, newValue);
  }

  public void eUnset(int featureID) {
    switch (featureID) {
    }
    super.eUnset(featureID);
  }

  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    }
    return super.eGet(featureID, resolve, coreType);
  }

  public boolean eIsSet(int featureID) {
    switch (featureID) {
    }
    return super.eIsSet(featureID);
  }

  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
      NotificationChain msgs) {
    switch(featureID) {
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  public double eval(Turtle turtle) {
    double result;
    if((this.getRhs().eval(turtle)) != (0.0)) {
      result = (this.getLhs().eval(turtle)) / (this.getRhs().eval(turtle));
    }
    else {
      result = 0.0;
    }
    return result;
  }
}
