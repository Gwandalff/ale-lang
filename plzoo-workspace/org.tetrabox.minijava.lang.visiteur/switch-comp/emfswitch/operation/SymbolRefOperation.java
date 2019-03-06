package emfswitch.operation;

import emfswitch.SwitchImplementation;
import miniJava.State;
import miniJava.SymbolRef;
import miniJava.Value;

public class SymbolRefOperation extends ExpressionOperation {
	private final SymbolRef it;

	private final SwitchImplementation emfswitch;

	public SymbolRefOperation(SymbolRef it, SwitchImplementation emfswitch) {
		super(it, emfswitch);
		this.it = it;
		this.emfswitch = emfswitch;
	}

	public Value evaluateExpression(State state) {
		Value result;
		Value tmp = ((emfswitch.operation.ContextOperation) emfswitch.doSwitch(
				/* CASEA */((emfswitch.operation.StateOperation) emfswitch.doSwitch(state)).findCurrentContext()))
						.findBinding(/* CASEC */this.it.getSymbol()).getValue();
		result = (Value) /* CASEF *//* CASEA */((ValueOperation) emfswitch.doSwitch(tmp)).copyj();
		return result;
	}
}
