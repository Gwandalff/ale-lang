package boa.interpreter.boa;

import boa_dynamic.interpreter.boa_dynamic.Ctx;
import boa_dynamic.interpreter.boa_dynamic.EvalRes;
import com.oracle.truffle.api.nodes.NodeInterface;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

public interface BObject extends EObject, NodeInterface, Expr {
  EList<Field> getFields();

  EvalRes eval(Ctx ctx);
}
