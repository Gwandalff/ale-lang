package boa_exec_revisitor.impl.operation;

import boa.Ctx;
import boa.EvalRes;

public interface ArithOpTimesOp extends ArithOpOp {
	EvalRes eval(Ctx ctx);
}