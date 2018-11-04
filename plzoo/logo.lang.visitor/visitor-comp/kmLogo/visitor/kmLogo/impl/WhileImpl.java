package kmLogo.visitor.kmLogo.impl;

import java.lang.Object;
import kmLogo.visitor.kmLogo.Block;
import kmLogo.visitor.kmLogo.KmLogoPackage;
import kmLogo.visitor.kmLogo.While;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import visitor.VisitorInterface;

public class WhileImpl extends ControlStructureImpl implements While {
  protected Block block;

  protected WhileImpl() {
    super();
  }

  public void setBlock(Block newBlock) {
    if (newBlock != block) {
    	NotificationChain msgs = null;
    	if (block != null)
    		msgs = ((InternalEObject)block).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - kmLogo.visitor.kmLogo.KmLogoPackage.WHILE__BLOCK, null, msgs);
    	if (newBlock != null)
    		msgs = ((InternalEObject)newBlock).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - kmLogo.visitor.kmLogo.KmLogoPackage.WHILE__BLOCK, null, msgs);
    	msgs = basicSetBlock(newBlock, msgs);
    	if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
    	eNotify(new ENotificationImpl(this, Notification.SET, kmLogo.visitor.kmLogo.KmLogoPackage.WHILE__BLOCK, newBlock, newBlock));
  }

  public NotificationChain basicSetBlock(Block newBlock, NotificationChain msgs) {
    Block oldBlock = block;
    block = newBlock;
    if (eNotificationRequired()) {
    	ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, kmLogo.visitor.kmLogo.KmLogoPackage.WHILE__BLOCK, oldBlock, newBlock);
    	if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  public Block getBlock() {
    return block;}

  protected EClass eStaticClass() {
    return KmLogoPackage.Literals.WHILE;}

  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
    case KmLogoPackage.WHILE__BLOCK:
    	setBlock((kmLogo.visitor.kmLogo.Block) newValue);
    return;
    }
    super.eSet(featureID, newValue);
  }

  public void eUnset(int featureID) {
    switch (featureID) {
    case KmLogoPackage.WHILE__BLOCK:
    	setBlock((kmLogo.visitor.kmLogo.Block) null);
    return;
    }
    super.eUnset(featureID);
  }

  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
    case KmLogoPackage.WHILE__BLOCK:
    return getBlock();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  public boolean eIsSet(int featureID) {
    switch (featureID) {
    case KmLogoPackage.WHILE__BLOCK:
    	return block != null;
    }
    return super.eIsSet(featureID);
  }

  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
      NotificationChain msgs) {
    switch(featureID) {
    case kmLogo.visitor.kmLogo.KmLogoPackage.WHILE__BLOCK:
    	return basicSetBlock(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  public Object accept(VisitorInterface visitor) {
    return visitor.visitkmLogo__While(this);}
}