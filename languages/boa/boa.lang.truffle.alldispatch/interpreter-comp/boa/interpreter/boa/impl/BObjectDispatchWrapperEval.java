package boa.interpreter.boa.impl;

import boa.interpreter.boa.BObject;
import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.utilities.CyclicAssumption;

public class BObjectDispatchWrapperEval extends ExprDispatchWrapperEval {
	private RootCallTarget callTarget;

	private final CyclicAssumption callTargetStable;

	protected BObjectDispatchWrapperEval(BObject it) {
		super(it);
		this.callTargetStable = new CyclicAssumption("BObjectDispatchWrapperEval_9.775660697410302E7");
		this.callTarget = Truffle.getRuntime().createCallTarget(new BObjectEvalRootNode(it));
	}

	public RootCallTarget getCallTarget() {
		return callTarget;
	}

	public Assumption getCallTargetStable() {
		return callTargetStable.getAssumption();
	}
}
