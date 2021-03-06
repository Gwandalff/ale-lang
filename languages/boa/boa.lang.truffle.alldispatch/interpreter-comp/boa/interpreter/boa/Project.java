package boa.interpreter.boa;

import boa.interpreter.boa.impl.ProjectDispatchWrapperEval;
import boa.interpreter.boa.impl.ProjectDispatchWrapperProject;
import com.oracle.truffle.api.nodes.NodeInterface;
import java.lang.String;
import org.eclipse.emf.ecore.EObject;

public interface Project extends EObject, NodeInterface, Expr {
  String getName();

  void setName(String value);

  Expr getExp();

  void setExp(Expr value);

  EvalRes eval(Ctx ctx);

  EvalRes project(EvalFunRes func, EvalMapRes mvexp);

  ProjectDispatchWrapperEval getCachedEval();

  ProjectDispatchWrapperProject getCachedProject();
}
