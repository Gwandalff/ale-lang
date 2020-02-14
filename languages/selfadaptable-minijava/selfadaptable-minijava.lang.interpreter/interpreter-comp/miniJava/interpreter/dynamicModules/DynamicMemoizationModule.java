package miniJava.interpreter.dynamicModules;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.xtext.EcoreUtil2;

import miniJava.interpreter.IDynamicModule;
import miniJava.interpreter.IDynamicSubject;
import miniJava.interpreter.miniJava.Expression;
import miniJava.interpreter.miniJava.IntConstant;
import miniJava.interpreter.miniJava.Method;
import miniJava.interpreter.miniJava.MethodCall;
import miniJava.interpreter.miniJava.MiniJavaPackage;
import miniJava.interpreter.miniJava.State;
import miniJava.interpreter.miniJava.Value;

public class DynamicMemoizationModule implements IDynamicModule {
	
	private Map<Method,Map<EList<Expression>,Value>> memoizeTable = new HashMap();
	
	private Deque<Boolean> inMemory = new ArrayDeque<>();
	private Value memoizedValue = null;

	@Override
	public boolean updateBefore(IDynamicSubject self, Object[] args) {
		MethodCall call = ((MethodCall) self);
		Map<EList<Expression>,Value> table = memoizeTable.get(call.getMethod());
		EList<Expression> methArgs = call.getArgs();
		EList<Expression> calculatedArgs = new BasicEList<Expression>();
		for (int i = 0; i < methArgs.size(); i++) {
			Value v = methArgs.get(i).evaluateExpression((State) args[0]);
			
		}
		System.out.println("Call fib(" + call.getArgs().get(0) + ")");
		if(table == null) {
			memoizeTable.put(call.getMethod(), new HashMap<EList<Expression>,Value>());
			inMemory.addLast(false);
		} else {
			Value val = table.get(call.getArgs());
			inMemory.addLast(val != null);
		}
		System.out.println("Was " + (inMemory.getLast()?"":"not ") + "in memory");
		
		return !inMemory.getLast();
	}
	
	@Override
	public boolean updateAfter(IDynamicSubject self, Object[] args, Value returned) {
		MethodCall call = ((MethodCall) self);
		Map<EList<Expression>,Value> table = memoizeTable.get(call.getMethod());
		System.out.println("DEBUG MAP :");
		for (EList<Expression> arg : table.keySet()) {
			System.out.println(arg + " -> " + table.get(arg).customToString());
		}
		if(!inMemory.getLast()) {
			System.out.println("Save the result for fib(" + call.getArgs().get(0) + ")");
			System.out.println(returned.customToString() + " was saved");
			table.put(call.getArgs(), EcoreUtil2.copy(returned));
			memoizeTable.put(call.getMethod(), table);
		} else {
			memoizedValue = table.get(call.getArgs());
			System.out.println("Get from memory : " + memoizedValue.customToString());
		}
		boolean override = inMemory.removeLast();
		return override;
	}

	@Override
	public Value byPassResult() {
		System.out.println("The actual result was : " + memoizedValue);
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
