package miniJava.interpreter.miniJava;

import java.lang.String;
import org.eclipse.emf.ecore.EObject;

public interface BooleanValue extends EObject, Value {
	boolean isValue();

	void setValue(boolean value);

	String customToString();

	Value copyj();
}
