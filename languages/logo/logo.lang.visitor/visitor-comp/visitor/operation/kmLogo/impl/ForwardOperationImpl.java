package visitor.operation.kmLogo.impl;

import java.lang.Double;
import kmLogo.visitor.kmLogo.Forward;
import kmLogo.visitor.kmLogo.Turtle;
import visitor.VisitorInterface;
import visitor.operation.kmLogo.ExpressionOperation;
import visitor.operation.kmLogo.ForwardOperation;
import visitor.operation.kmLogo.TurtleOperation;

public class ForwardOperationImpl extends PrimitiveOperationImpl implements ForwardOperation {
	private final Forward it;

	private final VisitorInterface vis;

	public ForwardOperationImpl(Forward it, VisitorInterface vis) {
		super(it, vis);
		this.it = it;
		this.vis = vis;
	}

	public double eval(Turtle turtle) {
		double result;
		double move = ((double) (((ExpressionOperation)this.it.getSteps().accept(vis)).eval((Turtle) (turtle))));
		((TurtleOperation)turtle.accept(vis)).forward((double) (move));
		result = ((Double) (0.0));
		return result;
	}
}
