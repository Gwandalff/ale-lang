package emfswitch.operation;

import emfswitch.SwitchImplementation;
import miniJava.Division;
import miniJava.IntegerValue;
import miniJava.MiniJavaFactory;
import miniJava.State;
import miniJava.Value;

public class DivisionOperation extends ExpressionOperation {
	private final Division it;

	private final SwitchImplementation emfswitch;

	public DivisionOperation(Division it, SwitchImplementation emfswitch) {
		super(it, emfswitch);
		this.it = it;
		this.emfswitch = emfswitch;
	}

	public Value evaluateExpression(State state) {
		Value result;
		Value left = ((Value) (((ExpressionOperation) emfswitch.doSwitch(this.it.getLeft())).evaluateExpression((State) (state))));
		Value right = ((Value) (((ExpressionOperation) emfswitch.doSwitch(this.it.getRight())).evaluateExpression((State) (state))));
		if(left instanceof IntegerValue) {
			if(right instanceof IntegerValue) {
				IntegerValue bleft = ((IntegerValue) (left));
				IntegerValue bright = ((IntegerValue) (right));
				IntegerValue tmp = ((IntegerValue) (MiniJavaFactory.eINSTANCE.createIntegerValue()));
				tmp.setValue((bleft.getValue()) / (bright.getValue()));
				result = tmp;
			}
			else {
				result = null;
			}
		}
		else {
			result = null;
		}
		return result;
	}
}
