package visitor.operation.miniJava.impl;

import miniJava.visitor.miniJava.BooleanValue;
import miniJava.visitor.miniJava.IntegerValue;
import miniJava.visitor.miniJava.MiniJavaFactory;
import miniJava.visitor.miniJava.State;
import miniJava.visitor.miniJava.SuperiorOrEqual;
import miniJava.visitor.miniJava.Value;
import visitor.VisitorInterface;
import visitor.operation.miniJava.ExpressionOperation;
import visitor.operation.miniJava.SuperiorOrEqualOperation;

public class SuperiorOrEqualOperationImpl extends ExpressionOperationImpl implements SuperiorOrEqualOperation {
	private final SuperiorOrEqual it;

	private final VisitorInterface vis;

	public SuperiorOrEqualOperationImpl(SuperiorOrEqual it, VisitorInterface vis) {
		super(it, vis);
		this.it = it;
		this.vis = vis;
	}

	public Value evaluateExpression(State state) {
		Value result;
		Value left = ((Value) (((ExpressionOperation)this.it.getLeft().accept(vis)).evaluateExpression((State) (state))));
		Value right = ((Value) (((ExpressionOperation)this.it.getRight().accept(vis)).evaluateExpression((State) (state))));
		if(left instanceof IntegerValue) {
			if(right instanceof IntegerValue) {
				IntegerValue bleft = ((IntegerValue) (left));
				IntegerValue bright = ((IntegerValue) (right));
				BooleanValue tmp = ((BooleanValue) (MiniJavaFactory.eINSTANCE.createBooleanValue()));
				tmp.setValue((bleft.getValue()) >= (bright.getValue()));
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
