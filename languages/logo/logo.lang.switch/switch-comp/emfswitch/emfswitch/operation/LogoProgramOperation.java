package emfswitch.emfswitch.operation;

import emfswitch.emfswitch.EmfswitchSwitchImplementation;
import kmLogo.Instruction;
import kmLogo.KmLogoFactory;
import kmLogo.LogoProgram;
import kmLogo.Turtle;

public class LogoProgramOperation {
	private final LogoProgram it;

	private final EmfswitchSwitchImplementation emfswitch;

	public LogoProgramOperation(LogoProgram it, EmfswitchSwitchImplementation emfswitch) {
		this.it = it;
		this.emfswitch = emfswitch;
	}

	public Turtle eval() {
		Turtle result;
		Turtle turtle = ((Turtle) (((LogoProgramOperation) emfswitch.doSwitch(this.it)).createTurtle()));
		result = ((Turtle) (turtle));
		for (Instruction it: this.it.getInstructions()) {
			((InstructionOperation) emfswitch.doSwitch(it)).eval((Turtle) (turtle));
		}
		result = ((Turtle) (turtle));
		return result;
	}

	public Turtle createTurtle() {
		Turtle result;
		Turtle turtle = ((Turtle) (KmLogoFactory.eINSTANCE.createTurtle()));
		turtle.setPosition(KmLogoFactory.eINSTANCE.createPoint());
		turtle.getPosition().setX(0.0);
		turtle.getPosition().setY(0.0);
		turtle.setCallStack(KmLogoFactory.eINSTANCE.createCallStack());
		turtle.getCallStack().getFrames().add(KmLogoFactory.eINSTANCE.createStackFrame());
		result = ((Turtle) (turtle));
		return result;
	}
}
