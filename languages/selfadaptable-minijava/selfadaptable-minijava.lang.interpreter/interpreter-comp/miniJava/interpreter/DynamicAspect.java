package miniJava.interpreter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import miniJava.interpreter.miniJava.Value;


@Aspect
public class DynamicAspect {

	@Around("execution(Value miniJava.interpreter.miniJava.impl.*.evaluate*(..))")
    public Value aroundExpression(ProceedingJoinPoint pjp) throws Throwable {
		IDynamicSubject node = (IDynamicSubject) pjp.getTarget();
		
		boolean doTheMethod = node.notifyDynamicModulesBefore();
		
		Value out = null;
		if(doTheMethod){
			out = (Value) pjp.proceed();
		}
		
		out = node.notifyDynamicModulesAfter(((Value) out));
		return out;
    }
	
	@Around("execution(void miniJava.interpreter.miniJava.impl.*.evaluate*(..))")
    public void aroundStatement(ProceedingJoinPoint pjp) throws Throwable {
		IDynamicSubject node = (IDynamicSubject) pjp.getTarget();
		
		boolean doTheMethod = node.notifyDynamicModulesBefore();
		
		if(doTheMethod){
			pjp.proceed();
		}
		
		node.notifyDynamicModulesAfter(((Value) null));
    }
}
