package kmLogo.interpreter.kmLogo;

import com.oracle.truffle.api.nodes.NodeInterface;
import org.eclipse.emf.ecore.EObject;

public interface Plus extends EObject, NodeInterface, BinaryExp {
	double eval(Turtle turtle);
}
