package boa_exec.impl.operation;

import boa_dynamic.Ctx;
import boa_dynamic.EvalRes;

public interface CmpOpLess extends CmpOp {
  EvalRes eval(Ctx ctx);
}
