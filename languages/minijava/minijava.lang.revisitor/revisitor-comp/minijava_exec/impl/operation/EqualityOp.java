package minijava_exec.impl.operation;

import miniJava.State;
import miniJava.Value;

public interface EqualityOp extends ExpressionOp {
	Value evaluateExpression(State state);
}
