package kmLogo.interpreter.kmLogo.impl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.Node.Children;
import com.oracle.truffle.api.nodes.NodeInfo;
import java.lang.Object;
import java.lang.Override;
import java.util.Collection;
import kmLogo.interpreter.kmLogo.Instruction;
import kmLogo.interpreter.kmLogo.KmLogoFactory;
import kmLogo.interpreter.kmLogo.KmLogoPackage;
import kmLogo.interpreter.kmLogo.LogoProgram;
import kmLogo.interpreter.kmLogo.Turtle;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.ecoretools.ale.compiler.truffle.MinimalTruffleEObjectImpl;

@NodeInfo(
		description = "LogoProgram"
)
public class LogoProgramImpl extends MinimalTruffleEObjectImpl.TruffleContainer implements LogoProgram {
	protected EList<Instruction> instructions;

	@Children
	private Instruction[] instructionsArr;

	protected LogoProgramImpl() {
		super();
	}

	@Override
	@TruffleBoundary
	protected EClass eStaticClass() {
		return KmLogoPackage.Literals.LOGO_PROGRAM;
	}

	@TruffleBoundary
	public EList<Instruction> getInstructions() {
		if (instructions == null) {
			instructions = new EObjectContainmentEList<Instruction>(Instruction.class, this, KmLogoPackage.LOGO_PROGRAM__INSTRUCTIONS);
		}
		return instructions;
	}

	@Override
	@TruffleBoundary
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID,
			NotificationChain msgs) {
		switch (featureID) {
			case KmLogoPackage.LOGO_PROGRAM__INSTRUCTIONS :
				return ((InternalEList<?>) getInstructions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	@Override
	@TruffleBoundary
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case KmLogoPackage.LOGO_PROGRAM__INSTRUCTIONS :
				return getInstructions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	@Override
	@TruffleBoundary
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case KmLogoPackage.LOGO_PROGRAM__INSTRUCTIONS :
				getInstructions().clear();
				getInstructions().addAll((Collection<? extends Instruction>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	@Override
	@TruffleBoundary
	public void eUnset(int featureID) {
		switch (featureID) {
			case KmLogoPackage.LOGO_PROGRAM__INSTRUCTIONS :
				getInstructions().clear();
				return;
		}
		super.eUnset(featureID);
	}

	@Override
	@TruffleBoundary
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case KmLogoPackage.LOGO_PROGRAM__INSTRUCTIONS :
				return instructions != null && !instructions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	public Turtle eval() {
		Turtle result;
		if (this.instructionsArr == null) {
			CompilerDirectives.transferToInterpreterAndInvalidate();
			if (this.getInstructions() != null) this.instructionsArr = this.getInstructions().toArray(new Instruction[0]);
			else this.instructionsArr = new Instruction[] {};
		}
		Turtle turtle = ((Turtle) (((LogoProgram) (this)).createTurtle()));
		result = (Turtle) (turtle) ;
		for (Instruction it : this.instructionsArr) {
			((Instruction) (it)).eval((Turtle) (turtle));
		}
		result = (Turtle) (turtle) ;

		return result;
	}

	public Turtle createTurtle() {
		Turtle result;
		if (this.instructionsArr == null) {
			CompilerDirectives.transferToInterpreterAndInvalidate();
			if (this.getInstructions() != null) this.instructionsArr = this.getInstructions().toArray(new Instruction[0]);
			else this.instructionsArr = new Instruction[] {};
		}
		Turtle turtle = ((Turtle) (KmLogoFactory.eINSTANCE.createTurtle()));
		turtle.setPosition(KmLogoFactory.eINSTANCE.createPoint());
		turtle.getPosition().setX(0.0);
		turtle.getPosition().setY(0.0);
		turtle.setCallStack(KmLogoFactory.eINSTANCE.createCallStack());
		turtle.getCallStack().getFrames().add(KmLogoFactory.eINSTANCE.createStackFrame());
		result = (Turtle) (turtle) ;

		return result;
	}
}
