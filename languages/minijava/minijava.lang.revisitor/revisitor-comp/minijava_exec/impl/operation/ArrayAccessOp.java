package minijava_exec.impl.operation;

import miniJava.State;
import miniJava.Value;

public interface ArrayAccessOp extends ExpressionOp {
	Value evaluateExpression(State state);
}
