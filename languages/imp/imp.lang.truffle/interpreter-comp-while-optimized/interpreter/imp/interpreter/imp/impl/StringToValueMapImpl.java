package interpreter.imp.interpreter.imp.impl;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.Node.Child;
import com.oracle.truffle.api.nodes.NodeInfo;
import interpreter.imp.interpreter.imp.ImpPackage;
import interpreter.imp.interpreter.imp.Value;
import java.lang.Object;
import java.lang.String;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecoretools.ale.compiler.truffle.MinimalTruffleEObjectImpl;

@NodeInfo(
    description = "StringToValueMap"
)
public class StringToValueMapImpl extends MinimalTruffleEObjectImpl.TruffleContainer implements BasicEMap.Entry<String, Value> {
  protected static final String KEY_EDEFAULT = null;

  protected String key = KEY_EDEFAULT;

  @Child
  protected Value value;

  protected int hash = -1;

  protected StringToValueMapImpl() {
    super();
  }

  public String getKey() {
    return key;}

  public void setKey(String key) {
    this.key = key;}

  @TruffleBoundary
  public Value setValue(Value newValue) {
    Value oldValue = value;
    if (newValue != value) {
    	NotificationChain msgs = null;
    	if (value != null)
    		msgs = ((InternalEObject)value).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - interpreter.imp.interpreter.imp.ImpPackage.STRING_TO_VALUE_MAP__VALUE, null, msgs);
    	if (newValue != null)
    		msgs = ((InternalEObject)newValue).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - interpreter.imp.interpreter.imp.ImpPackage.STRING_TO_VALUE_MAP__VALUE, null, msgs);
    	msgs = basicSetValue(newValue, msgs);
    	if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
    	eNotify(new ENotificationImpl(this, Notification.SET, interpreter.imp.interpreter.imp.ImpPackage.STRING_TO_VALUE_MAP__VALUE, newValue, newValue));
    return oldValue;
  }

  @TruffleBoundary
  public NotificationChain basicSetValue(Value newValue, NotificationChain msgs) {
    Value oldValue = value;
    value = newValue;
    if (eNotificationRequired()) {
    	ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, interpreter.imp.interpreter.imp.ImpPackage.STRING_TO_VALUE_MAP__VALUE, oldValue, newValue);
    	if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  @TruffleBoundary
  public Value getValue() {
    return value;}

  @TruffleBoundary
  protected EClass eStaticClass() {
    return ImpPackage.Literals.STRING_TO_VALUE_MAP;}

  @TruffleBoundary
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case ImpPackage.STRING_TO_VALUE_MAP__KEY:
    	setKey((java.lang.String) newValue);
    return;
    case ImpPackage.STRING_TO_VALUE_MAP__VALUE:
    	setValue((interpreter.imp.interpreter.imp.Value) newValue);
    return;
    }
    super.eSet(featureID, newValue);
  }

  @TruffleBoundary
  public void eUnset(int featureID) {
    switch (featureID) {
    case ImpPackage.STRING_TO_VALUE_MAP__KEY:
    	setKey(KEY_EDEFAULT);
    return;
    case ImpPackage.STRING_TO_VALUE_MAP__VALUE:
    	setValue((interpreter.imp.interpreter.imp.Value) null);
    return;
    }
    super.eUnset(featureID);
  }

  @TruffleBoundary
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case ImpPackage.STRING_TO_VALUE_MAP__KEY:
    return getKey();
    case ImpPackage.STRING_TO_VALUE_MAP__VALUE:
    return getValue();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  @TruffleBoundary
  public boolean eIsSet(int featureID) {
    switch (featureID) {
    case ImpPackage.STRING_TO_VALUE_MAP__KEY:
    	return key != KEY_EDEFAULT;
    case ImpPackage.STRING_TO_VALUE_MAP__VALUE:
    	return value != null;
    }
    return super.eIsSet(featureID);
  }

  @TruffleBoundary
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
      NotificationChain msgs) {
    switch(featureID) {
    case interpreter.imp.interpreter.imp.ImpPackage.STRING_TO_VALUE_MAP__VALUE:
    	return basicSetValue(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  @TruffleBoundary
  public void setHash(int hash) {
    this.hash = hash;
  }

  @TruffleBoundary
  public int getHash() {
    if (hash == -1) {
    	Object theKey = getKey();
    	hash = (theKey == null ? 0 : theKey.hashCode());
    }
    return hash;
  }
}
