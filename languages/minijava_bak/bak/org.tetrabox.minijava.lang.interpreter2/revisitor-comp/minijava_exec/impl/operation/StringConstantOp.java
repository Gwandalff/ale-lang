package minijava_exec.impl.operation;

import miniJava.State;
import miniJava.Value;

public interface StringConstantOp extends ExpressionOp {
	Value evaluateExpression(State state);
}