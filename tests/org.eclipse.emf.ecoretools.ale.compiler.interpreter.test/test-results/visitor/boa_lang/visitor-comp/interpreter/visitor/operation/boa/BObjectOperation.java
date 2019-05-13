package interpreter.visitor.operation.boa;

import interpreter.boa.visitor.boa.Ctx;
import interpreter.boa.visitor.boa.EvalRes;

public interface BObjectOperation extends ExprOperation {
	EvalRes eval(Ctx ctx);
}