package kmLogo.visitor.kmLogo.impl;

import java.lang.Object;
import kmLogo.visitor.kmLogo.Cos;
import kmLogo.visitor.kmLogo.KmLogoPackage;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import visitor.VisitorInterface;

public class CosImpl extends UnaryExpressionImpl implements Cos {
  protected CosImpl() {
    super();
  }

  protected EClass eStaticClass() {
    return KmLogoPackage.Literals.COS;}

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

  public Object accept(VisitorInterface visitor) {
    return visitor.visitkmLogo__Cos(this);}
}