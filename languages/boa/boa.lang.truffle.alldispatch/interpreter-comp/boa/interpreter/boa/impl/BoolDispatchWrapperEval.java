package boa.interpreter.boa.impl;

import boa.interpreter.boa.Bool;
import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.utilities.CyclicAssumption;

public class BoolDispatchWrapperEval extends ExprDispatchWrapperEval {
	private RootCallTarget callTarget;

	private final CyclicAssumption callTargetStable;

	protected BoolDispatchWrapperEval(Bool it) {
		super(it);
		this.callTargetStable = new CyclicAssumption("BoolDispatchWrapperEval_9.089204277144934E7");
		this.callTarget = Truffle.getRuntime().createCallTarget(new BoolEvalRootNode(it));
	}

	public RootCallTarget getCallTarget() {
		return callTarget;
	}

	public Assumption getCallTargetStable() {
		return callTargetStable.getAssumption();
	}
}
