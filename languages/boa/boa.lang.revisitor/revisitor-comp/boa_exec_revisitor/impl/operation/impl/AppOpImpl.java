package boa_exec_revisitor.impl.operation.impl;

import boa.App;
import boa.BoaFactory;
import boa.Ctx;
import boa.EvalBoundFunRes;
import boa.EvalFunRes;
import boa.EvalRes;
import boa.Expr;
import boa.revisitor.BoaRevisitor;
import boa_exec_revisitor.impl.operation.AppOp;
import boa_exec_revisitor.impl.operation.ArithOpDivideOp;
import boa_exec_revisitor.impl.operation.ArithOpMinusOp;
import boa_exec_revisitor.impl.operation.ArithOpOp;
import boa_exec_revisitor.impl.operation.ArithOpPlusOp;
import boa_exec_revisitor.impl.operation.ArithOpRemainderOp;
import boa_exec_revisitor.impl.operation.ArithOpTimesOp;
import boa_exec_revisitor.impl.operation.AssignOp;
import boa_exec_revisitor.impl.operation.BObjectOp;
import boa_exec_revisitor.impl.operation.BoolOp;
import boa_exec_revisitor.impl.operation.BoolOpAndOp;
import boa_exec_revisitor.impl.operation.BoolOpOp;
import boa_exec_revisitor.impl.operation.BoolOpOrOp;
import boa_exec_revisitor.impl.operation.CmpOpEqualOp;
import boa_exec_revisitor.impl.operation.CmpOpLessOp;
import boa_exec_revisitor.impl.operation.CmpOpOp;
import boa_exec_revisitor.impl.operation.CmpOpUnequalOp;
import boa_exec_revisitor.impl.operation.CopyOp;
import boa_exec_revisitor.impl.operation.CtxOp;
import boa_exec_revisitor.impl.operation.DefOp;
import boa_exec_revisitor.impl.operation.EvalBoolResOp;
import boa_exec_revisitor.impl.operation.EvalBoundFunResOp;
import boa_exec_revisitor.impl.operation.EvalFunResOp;
import boa_exec_revisitor.impl.operation.EvalIntResOp;
import boa_exec_revisitor.impl.operation.EvalMapResOp;
import boa_exec_revisitor.impl.operation.EvalResOp;
import boa_exec_revisitor.impl.operation.ExprOp;
import boa_exec_revisitor.impl.operation.FieldOp;
import boa_exec_revisitor.impl.operation.FileOp;
import boa_exec_revisitor.impl.operation.FunOp;
import boa_exec_revisitor.impl.operation.IfOp;
import boa_exec_revisitor.impl.operation.IntOp;
import boa_exec_revisitor.impl.operation.LetOp;
import boa_exec_revisitor.impl.operation.NotOp;
import boa_exec_revisitor.impl.operation.ProjectOp;
import boa_exec_revisitor.impl.operation.SeqOp;
import boa_exec_revisitor.impl.operation.SkipOp;
import boa_exec_revisitor.impl.operation.ThisOp;
import boa_exec_revisitor.impl.operation.TopLevelCmdOp;
import boa_exec_revisitor.impl.operation.VarOp;
import boa_exec_revisitor.impl.operation.WithOp;
import execboa.MapService;
import java.lang.String;
import org.eclipse.emf.common.util.EMap;

public class AppOpImpl extends ExprOpImpl implements AppOp {
  private BoaRevisitor<AppOp, ArithOpOp, ArithOpDivideOp, ArithOpMinusOp, ArithOpPlusOp, ArithOpRemainderOp, ArithOpTimesOp, AssignOp, BObjectOp, BoolOp, BoolOpOp, BoolOpAndOp, BoolOpOrOp, CmpOpOp, CmpOpEqualOp, CmpOpLessOp, CmpOpUnequalOp, CopyOp, CtxOp, DefOp, EvalBoolResOp, EvalBoundFunResOp, EvalFunResOp, EvalIntResOp, EvalMapResOp, EvalResOp, ExprOp, FieldOp, FileOp, FunOp, IfOp, IntOp, LetOp, NotOp, ProjectOp, SeqOp, SkipOp, ThisOp, TopLevelCmdOp, VarOp, WithOp> rev;

  private App obj;

  public AppOpImpl(App obj,
      BoaRevisitor<AppOp, ArithOpOp, ArithOpDivideOp, ArithOpMinusOp, ArithOpPlusOp, ArithOpRemainderOp, ArithOpTimesOp, AssignOp, BObjectOp, BoolOp, BoolOpOp, BoolOpAndOp, BoolOpOrOp, CmpOpOp, CmpOpEqualOp, CmpOpLessOp, CmpOpUnequalOp, CopyOp, CtxOp, DefOp, EvalBoolResOp, EvalBoundFunResOp, EvalFunResOp, EvalIntResOp, EvalMapResOp, EvalResOp, ExprOp, FieldOp, FileOp, FunOp, IfOp, IntOp, LetOp, NotOp, ProjectOp, SeqOp, SkipOp, ThisOp, TopLevelCmdOp, VarOp, WithOp> rev) {
    super(obj, rev);
    this.obj = obj;
    this.rev = rev;
  }

  public EvalRes eval(Ctx ctx) {
    EvalRes result;
    EvalRes vlhs = ((EvalRes) (rev.$((Expr)this.obj.getLhs()).eval(((Ctx) (ctx)))));
    EvalRes vrhs = ((EvalRes) (rev.$((Expr)this.obj.getRhs()).eval(((Ctx) (ctx)))));
    if(vlhs instanceof EvalFunRes) {
      if(vlhs instanceof EvalBoundFunRes) {
        EvalBoundFunRes fct = ((EvalBoundFunRes) (vlhs));
        Ctx callCtx = ((Ctx) (BoaFactory.eINSTANCE.createCtx()));
        MapService.putAll((EMap) (callCtx.getEnv()), (EMap) (fct.getCtx().getEnv()));
        MapService.put((EMap) (callCtx.getEnv()), (String) (fct.getName()), (EvalRes) (vrhs));
        MapService.replaceWith((EMap) (callCtx.getTh()), (EMap) (fct.getTh()));
        EvalRes fe = ((EvalRes) (rev.$((App)this.obj).callFunc(((EvalBoundFunRes) (fct)), ((Ctx) (callCtx)))));
        if(fe instanceof EvalFunRes) {
          EvalFunRes fun = ((EvalFunRes) (fe));
          EvalBoundFunRes tmp = ((EvalBoundFunRes) (BoaFactory.eINSTANCE.createEvalBoundFunRes()));
          tmp.setExp(fun.getExp());
          tmp.setCtx(fun.getCtx());
          tmp.setName(fun.getName());
          MapService.replaceWith((EMap) (tmp.getTh()), (EMap) (fct.getTh()));
          result = ((EvalBoundFunRes) (tmp));
        }
        else {
          result = ((EvalRes) (fe));
        }
      }
      else {
        EvalFunRes fct = ((EvalFunRes) (vlhs));
        Ctx callCtx = ((Ctx) (BoaFactory.eINSTANCE.createCtx()));
        MapService.putAll((EMap) (callCtx.getEnv()), (EMap) (fct.getCtx().getEnv()));
        MapService.put((EMap) (callCtx.getEnv()), (String) (fct.getName()), (EvalRes) (vrhs));
        MapService.replaceWith((EMap) (callCtx.getTh()), (EMap) (ctx.getTh()));
        EvalRes fe = ((EvalRes) (rev.$((App)this.obj).callFunc(((EvalFunRes) (fct)), ((Ctx) (callCtx)))));
        if(fe instanceof EvalFunRes) {
          EvalFunRes fun = ((EvalFunRes) (fe));
          EvalBoundFunRes tmp = ((EvalBoundFunRes) (BoaFactory.eINSTANCE.createEvalBoundFunRes()));
          tmp.setExp(fun.getExp());
          tmp.setCtx(fun.getCtx());
          tmp.setName(fun.getName());
          MapService.replaceWith((EMap) (tmp.getTh()), (EMap) (ctx.getTh()));
          result = ((EvalBoundFunRes) (tmp));
        }
        else {
          result = ((EvalRes) (fe));
        }
      }
    }
    else {
      result = ((EvalRes) (null));
    }
    return result;
  }

  public EvalRes callFunc(EvalFunRes fct, Ctx callCtx) {
    EvalRes result;
    result = ((EvalRes) (rev.$((Expr)fct.getExp()).eval(((Ctx) (callCtx)))));
    return result;
  }
}
