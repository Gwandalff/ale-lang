package boa.interpreter.boa;

import boa.interpreter.boa.impl.AppDispatchWrapperCallFunc;
import boa.interpreter.boa.impl.AppDispatchWrapperEval;
import com.oracle.truffle.api.nodes.NodeInterface;
import org.eclipse.emf.ecore.EObject;

public interface App extends EObject, NodeInterface, Expr {
  Expr getLhs();

  void setLhs(Expr value);

  Expr getRhs();

  void setRhs(Expr value);

  EvalRes eval(Ctx ctx);

  EvalRes callFunc(EvalFunRes fct, Ctx callCtx);

  AppDispatchWrapperEval getCachedEval();

  AppDispatchWrapperCallFunc getCachedCallFunc();
}
