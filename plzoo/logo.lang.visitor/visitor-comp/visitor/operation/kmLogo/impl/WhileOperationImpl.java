package visitor.operation.kmLogo.impl;

import kmLogo.visitor.kmLogo.Turtle;
import kmLogo.visitor.kmLogo.While;
import visitor.VisitorInterface;
import visitor.operation.kmLogo.WhileOperation;

public class WhileOperationImpl extends ControlStructureOperationImpl implements WhileOperation {
  private final While it;

  private final VisitorInterface vis;

  public WhileOperationImpl(While it, VisitorInterface vis) {
    super(it, vis);
    this.it = it;
    this.vis = vis;
  }

  public double eval(Turtle turtle) {
    double result;
    while ((((visitor.operation.kmLogo.ExpressionOperation)this.it.getCondition().accept(vis)).eval(turtle)) > (0.0)) {
      ((visitor.operation.kmLogo.BlockOperation)this.it.getBlock().accept(vis)).eval(turtle);
    }
    result = 0.0;
    return result;
  }
}