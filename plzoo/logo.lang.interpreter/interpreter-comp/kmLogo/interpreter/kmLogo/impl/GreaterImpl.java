package kmLogo.interpreter.kmLogo.impl;

import java.lang.Object;
import kmLogo.interpreter.kmLogo.Greater;
import kmLogo.interpreter.kmLogo.KmLogoPackage;
import kmLogo.interpreter.kmLogo.Turtle;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

public class GreaterImpl extends BinaryExpImpl implements Greater {
  protected GreaterImpl() {
    super();
  }

  protected EClass eStaticClass() {
    return KmLogoPackage.Literals.GREATER;}

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
    if((this.getLhs().eval(turtle)) > (this.getRhs().eval(turtle))) {
      result = 1.0;
    }
    else {
      result = 0.0;
    }
    return result;
  }
}
