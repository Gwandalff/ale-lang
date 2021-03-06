package miniJava.interpreter.miniJava.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import miniJava.interpreter.miniJava.Assignment;
import miniJava.interpreter.miniJava.Block;
import miniJava.interpreter.miniJava.BooleanValue;
import miniJava.interpreter.miniJava.Expression;
import miniJava.interpreter.miniJava.ForStatement;
import miniJava.interpreter.miniJava.MiniJavaPackage;
import miniJava.interpreter.miniJava.State;

public class ForStatementImpl extends StatementImpl implements ForStatement {
	protected Block block;

	protected Expression condition;

	protected Assignment declaration;

	protected Assignment progression;

	protected ForStatementImpl() {
		super();
	}

	public NotificationChain basicSetBlock(Block newBlock, NotificationChain msgs) {
		Block oldBlock = block;
		block = newBlock;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MiniJavaPackage.FOR_STATEMENT__BLOCK, oldBlock, newBlock);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	public NotificationChain basicSetCondition(Expression newCondition, NotificationChain msgs) {
		Expression oldCondition = condition;
		condition = newCondition;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MiniJavaPackage.FOR_STATEMENT__CONDITION, oldCondition, newCondition);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	public NotificationChain basicSetDeclaration(Assignment newDeclaration, NotificationChain msgs) {
		Assignment oldDeclaration = declaration;
		declaration = newDeclaration;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MiniJavaPackage.FOR_STATEMENT__DECLARATION, oldDeclaration, newDeclaration);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	public NotificationChain basicSetProgression(Assignment newProgression, NotificationChain msgs) {
		Assignment oldProgression = progression;
		progression = newProgression;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
					MiniJavaPackage.FOR_STATEMENT__PROGRESSION, oldProgression, newProgression);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case MiniJavaPackage.FOR_STATEMENT__DECLARATION:
			return getDeclaration();
		case MiniJavaPackage.FOR_STATEMENT__CONDITION:
			return getCondition();
		case MiniJavaPackage.FOR_STATEMENT__PROGRESSION:
			return getProgression();
		case MiniJavaPackage.FOR_STATEMENT__BLOCK:
			return getBlock();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case MiniJavaPackage.FOR_STATEMENT__DECLARATION:
			return basicSetDeclaration(null, msgs);
		case MiniJavaPackage.FOR_STATEMENT__CONDITION:
			return basicSetCondition(null, msgs);
		case MiniJavaPackage.FOR_STATEMENT__PROGRESSION:
			return basicSetProgression(null, msgs);
		case MiniJavaPackage.FOR_STATEMENT__BLOCK:
			return basicSetBlock(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case MiniJavaPackage.FOR_STATEMENT__DECLARATION:
			return declaration != null;
		case MiniJavaPackage.FOR_STATEMENT__CONDITION:
			return condition != null;
		case MiniJavaPackage.FOR_STATEMENT__PROGRESSION:
			return progression != null;
		case MiniJavaPackage.FOR_STATEMENT__BLOCK:
			return block != null;
		}
		return super.eIsSet(featureID);
	}

	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case MiniJavaPackage.FOR_STATEMENT__DECLARATION:
			setDeclaration((Assignment) newValue);
			return;
		case MiniJavaPackage.FOR_STATEMENT__CONDITION:
			setCondition((Expression) newValue);
			return;
		case MiniJavaPackage.FOR_STATEMENT__PROGRESSION:
			setProgression((Assignment) newValue);
			return;
		case MiniJavaPackage.FOR_STATEMENT__BLOCK:
			setBlock((Block) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	protected EClass eStaticClass() {
		return MiniJavaPackage.Literals.FOR_STATEMENT;
	}

	public void eUnset(int featureID) {
		switch (featureID) {
		case MiniJavaPackage.FOR_STATEMENT__DECLARATION:
			setDeclaration((Assignment) null);
			return;
		case MiniJavaPackage.FOR_STATEMENT__CONDITION:
			setCondition((Expression) null);
			return;
		case MiniJavaPackage.FOR_STATEMENT__PROGRESSION:
			setProgression((Assignment) null);
			return;
		case MiniJavaPackage.FOR_STATEMENT__BLOCK:
			setBlock((Block) null);
			return;
		}
		super.eUnset(featureID);
	}

	public void evaluateStatement(State state) {
		state.pushNewContext();
		this.declaration.evaluateStatement(state);
		BooleanValue continueFor = ((BooleanValue) this.condition.evaluateExpression(state));
		while (continueFor.isValue()) {
			this.block.evaluateStatement(state);
			this.progression.evaluateStatement(state);
			BooleanValue continueFor2 = ((BooleanValue) this.condition.evaluateExpression(state));
			continueFor = continueFor2;
		}
		state.popCurrentContext();
		;
	}

	public Block getBlock() {
		return block;
	}

	public Expression getCondition() {
		return condition;
	}

	public Assignment getDeclaration() {
		return declaration;
	}

	public Assignment getProgression() {
		return progression;
	}

	public void setBlock(Block newBlock) {
		if (newBlock != block) {
			NotificationChain msgs = null;
			if (block != null)
				msgs = ((InternalEObject) block).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - MiniJavaPackage.FOR_STATEMENT__BLOCK, null, msgs);
			if (newBlock != null)
				msgs = ((InternalEObject) newBlock).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - MiniJavaPackage.FOR_STATEMENT__BLOCK, null, msgs);
			msgs = basicSetBlock(newBlock, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MiniJavaPackage.FOR_STATEMENT__BLOCK, newBlock,
					newBlock));
	}

	public void setCondition(Expression newCondition) {
		if (newCondition != condition) {
			NotificationChain msgs = null;
			if (condition != null)
				msgs = ((InternalEObject) condition).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - MiniJavaPackage.FOR_STATEMENT__CONDITION, null, msgs);
			if (newCondition != null)
				msgs = ((InternalEObject) newCondition).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - MiniJavaPackage.FOR_STATEMENT__CONDITION, null, msgs);
			msgs = basicSetCondition(newCondition, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MiniJavaPackage.FOR_STATEMENT__CONDITION,
					newCondition, newCondition));
	}

	public void setDeclaration(Assignment newDeclaration) {
		if (newDeclaration != declaration) {
			NotificationChain msgs = null;
			if (declaration != null)
				msgs = ((InternalEObject) declaration).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - MiniJavaPackage.FOR_STATEMENT__DECLARATION, null, msgs);
			if (newDeclaration != null)
				msgs = ((InternalEObject) newDeclaration).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - MiniJavaPackage.FOR_STATEMENT__DECLARATION, null, msgs);
			msgs = basicSetDeclaration(newDeclaration, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MiniJavaPackage.FOR_STATEMENT__DECLARATION,
					newDeclaration, newDeclaration));
	}

	public void setProgression(Assignment newProgression) {
		if (newProgression != progression) {
			NotificationChain msgs = null;
			if (progression != null)
				msgs = ((InternalEObject) progression).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE - MiniJavaPackage.FOR_STATEMENT__PROGRESSION, null, msgs);
			if (newProgression != null)
				msgs = ((InternalEObject) newProgression).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE - MiniJavaPackage.FOR_STATEMENT__PROGRESSION, null, msgs);
			msgs = basicSetProgression(newProgression, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, MiniJavaPackage.FOR_STATEMENT__PROGRESSION,
					newProgression, newProgression));
	}
}
