package boa_exec_revisitor.impl.operation.impl;

import boa.revisitor.BoaRevisitor;
import boa_exec_revisitor.impl.operation.App;
import boa_exec_revisitor.impl.operation.ArithOp;
import boa_exec_revisitor.impl.operation.ArithOpDivide;
import boa_exec_revisitor.impl.operation.ArithOpMinus;
import boa_exec_revisitor.impl.operation.ArithOpPlus;
import boa_exec_revisitor.impl.operation.ArithOpRemainder;
import boa_exec_revisitor.impl.operation.ArithOpTimes;
import boa_exec_revisitor.impl.operation.Assign;
import boa_exec_revisitor.impl.operation.BObject;
import boa_exec_revisitor.impl.operation.Bool;
import boa_exec_revisitor.impl.operation.BoolOp;
import boa_exec_revisitor.impl.operation.BoolOpAnd;
import boa_exec_revisitor.impl.operation.BoolOpOr;
import boa_exec_revisitor.impl.operation.CmpOp;
import boa_exec_revisitor.impl.operation.CmpOpEqual;
import boa_exec_revisitor.impl.operation.CmpOpLess;
import boa_exec_revisitor.impl.operation.CmpOpUnequal;
import boa_exec_revisitor.impl.operation.Copy;
import boa_exec_revisitor.impl.operation.Ctx;
import boa_exec_revisitor.impl.operation.Def;
import boa_exec_revisitor.impl.operation.EvalBoolRes;
import boa_exec_revisitor.impl.operation.EvalBoundFunRes;
import boa_exec_revisitor.impl.operation.EvalFunRes;
import boa_exec_revisitor.impl.operation.EvalIntRes;
import boa_exec_revisitor.impl.operation.EvalMapRes;
import boa_exec_revisitor.impl.operation.EvalRes;
import boa_exec_revisitor.impl.operation.Expr;
import boa_exec_revisitor.impl.operation.Field;
import boa_exec_revisitor.impl.operation.File;
import boa_exec_revisitor.impl.operation.Fun;
import boa_exec_revisitor.impl.operation.If;
import boa_exec_revisitor.impl.operation.Int;
import boa_exec_revisitor.impl.operation.Let;
import boa_exec_revisitor.impl.operation.Not;
import boa_exec_revisitor.impl.operation.Project;
import boa_exec_revisitor.impl.operation.Seq;
import boa_exec_revisitor.impl.operation.Skip;
import boa_exec_revisitor.impl.operation.This;
import boa_exec_revisitor.impl.operation.TopLevelCmd;
import boa_exec_revisitor.impl.operation.Var;
import boa_exec_revisitor.impl.operation.With;

public class WithImpl extends ExprImpl implements With {
  private BoaRevisitor<App, ArithOp, ArithOpDivide, ArithOpMinus, ArithOpPlus, ArithOpRemainder, ArithOpTimes, Assign, BObject, Bool, BoolOp, BoolOpAnd, BoolOpOr, CmpOp, CmpOpEqual, CmpOpLess, CmpOpUnequal, Copy, Ctx, Def, EvalBoolRes, EvalBoundFunRes, EvalFunRes, EvalIntRes, EvalMapRes, EvalRes, Expr, Field, File, Fun, If, Int, Let, Not, Project, Seq, Skip, This, TopLevelCmd, Var, With> rev;

  private boa.With obj;

  public WithImpl(boa.With obj,
      BoaRevisitor<App, ArithOp, ArithOpDivide, ArithOpMinus, ArithOpPlus, ArithOpRemainder, ArithOpTimes, Assign, BObject, Bool, BoolOp, BoolOpAnd, BoolOpOr, CmpOp, CmpOpEqual, CmpOpLess, CmpOpUnequal, Copy, Ctx, Def, EvalBoolRes, EvalBoundFunRes, EvalFunRes, EvalIntRes, EvalMapRes, EvalRes, Expr, Field, File, Fun, If, Int, Let, Not, Project, Seq, Skip, This, TopLevelCmd, Var, With> rev) {
    super(obj, rev);
    this.obj = obj;
    this.rev = rev;
  }

  public boa.EvalRes eval(boa.Ctx ctx) {
    boa.EvalRes result;
    boa.EvalRes vlhs = ((boa.EvalRes)rev.$(this.obj.getLhs()).eval(ctx));
    boa.EvalRes vrhs = ((boa.EvalRes)rev.$(this.obj.getRhs()).eval(ctx));
    if(vlhs instanceof boa.EvalMapRes) {
      boa.EvalMapRes mvlhs = ((boa.EvalMapRes)vlhs);
      if(vrhs instanceof boa.EvalMapRes) {
        boa.EvalMapRes mvrhs = ((boa.EvalMapRes)vrhs);
        boa.EvalMapRes ret = ((boa.EvalMapRes)boa.BoaFactory.eINSTANCE.createEvalMapRes());
        execboa.MapService.putAll(ret.getValues(), mvlhs.getValues());
        execboa.MapService.putAll(ret.getValues(), mvrhs.getValues());
        result = ret;
      }
      else {
        result = null;
      }
    }
    else {
      result = null;
    }
    return result;
  }
}