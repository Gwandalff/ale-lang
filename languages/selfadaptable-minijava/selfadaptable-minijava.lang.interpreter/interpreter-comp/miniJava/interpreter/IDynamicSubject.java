package miniJava.interpreter;

import miniJava.interpreter.miniJava.Value;

public interface IDynamicSubject {
	void attach(IDynamicModule dm);
	void detach(IDynamicModule dm);
	boolean notifyDynamicModulesBefore();
	Value notifyDynamicModulesAfter(Value result);
}
