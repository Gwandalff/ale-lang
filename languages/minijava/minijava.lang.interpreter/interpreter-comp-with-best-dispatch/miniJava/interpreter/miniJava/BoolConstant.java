package miniJava.interpreter.miniJava;

import com.oracle.truffle.api.nodes.NodeInterface;
import java.lang.String;
import miniJava.interpreter.miniJava.impl.BoolConstantDispatchWrapperEvaluateExpression;
import org.eclipse.emf.ecore.EObject;

public interface BoolConstant extends EObject, NodeInterface, Expression {
  String getValue();

  void setValue(String value);

  Value evaluateExpression(State state);

  BoolConstantDispatchWrapperEvaluateExpression getCachedEvaluateExpression();
}