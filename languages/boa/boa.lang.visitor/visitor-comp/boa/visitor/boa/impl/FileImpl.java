package boa.visitor.boa.impl;

import boa.visitor.boa.BoaPackage;
import boa.visitor.boa.File;
import boa.visitor.boa.TopLevelCmd;
import java.lang.Object;
import java.lang.Override;
import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import visitor.VisitorInterface;

public class FileImpl extends MinimalEObjectImpl.Container implements File {
	protected EList<TopLevelCmd> commands;

	protected FileImpl() {
		super();
	}

	@Override
	protected EClass eStaticClass() {
		return BoaPackage.Literals.FILE;
	}

	public EList<TopLevelCmd> getCommands() {
		if (commands == null) {
			commands = new EObjectContainmentEList<TopLevelCmd>(TopLevelCmd.class, this, BoaPackage.FILE__COMMANDS);
		}
		return commands;
	}

	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
			case BoaPackage.FILE__COMMANDS :
				return ((InternalEList<?>) getCommands()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BoaPackage.FILE__COMMANDS :
				return getCommands();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BoaPackage.FILE__COMMANDS :
				getCommands().clear();
				getCommands().addAll((Collection<? extends TopLevelCmd>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BoaPackage.FILE__COMMANDS :
				getCommands().clear();
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BoaPackage.FILE__COMMANDS :
				return commands != null && !commands.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	@Override
	public Object accept(VisitorInterface visitor) {
		return visitor.visitboa__File(this);
	}
}
