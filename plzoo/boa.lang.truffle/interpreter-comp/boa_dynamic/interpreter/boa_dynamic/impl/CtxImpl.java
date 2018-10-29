package boa_dynamic.interpreter.boa_dynamic.impl;

import boa_dynamic.interpreter.boa_dynamic.Boa_dynamicPackage;
import boa_dynamic.interpreter.boa_dynamic.Ctx;
import boa_dynamic.interpreter.boa_dynamic.EvalRes;
import com.oracle.truffle.api.nodes.NodeInfo;
import java.lang.Object;
import java.lang.String;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreEMap;

@NodeInfo(
    description = "Ctx"
)
public class CtxImpl extends MinimalEObjectImpl.Container implements Ctx {
  protected EMap<String, EvalRes> env;

  protected EMap<String, EvalRes> th;

  protected CtxImpl() {
    super();
  }

  public EMap<String, EvalRes> getEnv() {
    if (env == null) {
    	env = new EcoreEMap<String, EvalRes>(Boa_dynamicPackage.Literals.STRING_TO_EVAL_RES_MAP, StringToEvalResMapImpl.class, this, Boa_dynamicPackage.CTX__ENV);
    }
    return env;
  }

  public EMap<String, EvalRes> getTh() {
    if (th == null) {
    	th = new EcoreEMap<String, EvalRes>(Boa_dynamicPackage.Literals.STRING_TO_EVAL_RES_MAP, StringToEvalResMapImpl.class, this, Boa_dynamicPackage.CTX__TH);
    }
    return th;
  }

  protected EClass eStaticClass() {
    return Boa_dynamicPackage.Literals.CTX;}

  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case Boa_dynamicPackage.CTX__ENV:
    	((org.eclipse.emf.ecore.EStructuralFeature.Setting)getEnv()).set(newValue);
    return;
    case Boa_dynamicPackage.CTX__TH:
    	((org.eclipse.emf.ecore.EStructuralFeature.Setting)getTh()).set(newValue);
    return;
    }
    super.eSet(featureID, newValue);
  }

  public void eUnset(int featureID) {
    switch (featureID) {
    case Boa_dynamicPackage.CTX__ENV:
    	getEnv().clear();
    return;
    case Boa_dynamicPackage.CTX__TH:
    	getTh().clear();
    return;
    }
    super.eUnset(featureID);
  }

  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case Boa_dynamicPackage.CTX__ENV:
    if (coreType)
    	return getEnv();
    else
    	return getEnv().map();
    case Boa_dynamicPackage.CTX__TH:
    if (coreType)
    	return getTh();
    else
    	return getTh().map();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  public boolean eIsSet(int featureID) {
    switch (featureID) {
    case Boa_dynamicPackage.CTX__ENV:
    	return env != null && !env.isEmpty();
    case Boa_dynamicPackage.CTX__TH:
    	return th != null && !th.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
      NotificationChain msgs) {
    switch(featureID) {
    case boa_dynamic.interpreter.boa_dynamic.Boa_dynamicPackage.CTX__ENV:
    	return ((org.eclipse.emf.ecore.util.InternalEList<?>) getEnv()).basicRemove(otherEnd, msgs);
    case boa_dynamic.interpreter.boa_dynamic.Boa_dynamicPackage.CTX__TH:
    	return ((org.eclipse.emf.ecore.util.InternalEList<?>) getTh()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }
}
