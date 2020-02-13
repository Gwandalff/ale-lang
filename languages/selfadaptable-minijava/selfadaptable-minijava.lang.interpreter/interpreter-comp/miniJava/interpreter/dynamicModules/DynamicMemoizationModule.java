package miniJava.interpreter.dynamicModules;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.EcoreUtil2;

import miniJava.interpreter.IDynamicModule;
import miniJava.interpreter.IDynamicModule.Strategy;
import miniJava.interpreter.IDynamicSubject;
import miniJava.interpreter.miniJava.Expression;
import miniJava.interpreter.miniJava.Method;
import miniJava.interpreter.miniJava.MethodCall;
import miniJava.interpreter.miniJava.MiniJavaPackage;
import miniJava.interpreter.miniJava.Value;

public class DynamicMemoizationModule implements IDynamicModule {
	
	private Map<Method,Map<EList<Expression>,Value>> memoizeTable = new HashMap();
	
	private boolean inMemory = false;
	private Value memoizedValue = null;

	@Override
	public boolean updateBefore(IDynamicSubject self) {
		MethodCall call = ((MethodCall) self);
		Map<EList<Expression>,Value> table = memoizeTable.get(call.getMethod());
		if(table == null) {
			memoizeTable.put(call.getMethod(), new HashMap<EList<Expression>,Value>());
			inMemory = false;
		} else {
			Value val = table.get(call.getArgs());
			if(val == null) {
				inMemory = false;
			} else {
				inMemory = true;
				memoizedValue = val;
			}
		}
		
		return !inMemory;
	}
	
	@Override
	public boolean updateAfter(IDynamicSubject self, Value returned) {
		if(!inMemory) {
			MethodCall call = ((MethodCall) self);
			Map<EList<Expression>,Value> table = memoizeTable.get(call.getMethod());
			table.put(call.getArgs(), returned);
			memoizeTable.put(call.getMethod(), table);
		}
		return inMemory;
	}

	@Override
	public Value byPassResult() {
		return memoizedValue;
	}

	@Override
	public int[] targetedNodes() {
		int[] out = {MiniJavaPackage.METHOD_CALL};
		return out;
	}
	
	@Override
	public Strategy callStrategy() {
		return Strategy.AROUND;
	}

}
