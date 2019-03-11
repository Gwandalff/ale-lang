package emfswitch.operation;

import emfswitch.SwitchImplementation;
import miniJava.BooleanValue;
import miniJava.Inferior;
import miniJava.IntegerValue;
import miniJava.State;
import miniJava.Value;

public class InferiorOperation extends ExpressionOperation {
  private final Inferior it;

  private final SwitchImplementation emfswitch;

  public InferiorOperation(Inferior it, SwitchImplementation emfswitch) {
    super(it, emfswitch);
    this.it = it;
    this.emfswitch = emfswitch;
  }

  public Value evaluateExpression(State state) {
    Value result;
    Value left = ((Value)/*CASEA*/((emfswitch.operation.ExpressionOperation) emfswitch.doSwitch(this.it.getLeft())).evaluateExpression(state));
    Value right = ((Value)/*CASEA*/((emfswitch.operation.ExpressionOperation) emfswitch.doSwitch(this.it.getRight())).evaluateExpression(state));
    if(left instanceof miniJava.IntegerValue) {
      if(right instanceof miniJava.IntegerValue) {
        IntegerValue bleft = ((IntegerValue)left);
        IntegerValue bright = ((IntegerValue)right);
        BooleanValue tmp = ((BooleanValue)miniJava.MiniJavaFactory.eINSTANCE.createBooleanValue());
        tmp.setValue((bleft.getValue()) < (bright.getValue()));
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