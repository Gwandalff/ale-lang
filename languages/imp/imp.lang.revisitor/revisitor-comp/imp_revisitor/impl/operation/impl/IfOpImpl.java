package imp_revisitor.impl.operation.impl;

import imp.model.imp.BoolValue;
import imp.model.imp.Expr;
import imp.model.imp.If;
import imp.model.imp.Stmt;
import imp.model.imp.Store;
import imp.model.imp.Value;
import imp.model.revisitor.ImpRevisitor;
import imp_revisitor.impl.operation.AssignOp;
import imp_revisitor.impl.operation.BinaryOp;
import imp_revisitor.impl.operation.BlockOp;
import imp_revisitor.impl.operation.BoolValueOp;
import imp_revisitor.impl.operation.ExprOp;
import imp_revisitor.impl.operation.IfOp;
import imp_revisitor.impl.operation.IntConstOp;
import imp_revisitor.impl.operation.IntValueOp;
import imp_revisitor.impl.operation.SkipOp;
import imp_revisitor.impl.operation.StmtOp;
import imp_revisitor.impl.operation.StoreOp;
import imp_revisitor.impl.operation.UnaryOp;
import imp_revisitor.impl.operation.ValueOp;
import imp_revisitor.impl.operation.VarOp;
import imp_revisitor.impl.operation.WhileOp;

public class IfOpImpl extends StmtOpImpl implements IfOp {
  private ImpRevisitor<AssignOp, BinaryOp, BlockOp, BoolValueOp, ExprOp, IfOp, IntConstOp, IntValueOp, SkipOp, StmtOp, StoreOp, UnaryOp, ValueOp, VarOp, WhileOp> rev;

  private If obj;

  public IfOpImpl(If obj,
      ImpRevisitor<AssignOp, BinaryOp, BlockOp, BoolValueOp, ExprOp, IfOp, IntConstOp, IntValueOp, SkipOp, StmtOp, StoreOp, UnaryOp, ValueOp, VarOp, WhileOp> rev) {
    super(obj, rev);
    this.obj = obj;
    this.rev = rev;
  }

  public Store execute(Store s) {
    Store result;
    Value conde = ((Value) (rev.$((Expr)this.obj.getCond()).evaluate(((Store) (s)))));
    result = ((Store) (s));
    if(conde instanceof BoolValue) {
      BoolValue condeb = ((BoolValue) (conde));
      if(condeb.isValue()) {
        result = ((Store) (rev.$((Stmt)this.obj.getLhs()).execute(((Store) (s)))));
      }
      else {
        result = ((Store) (rev.$((Stmt)this.obj.getRhs()).execute(((Store) (s)))));
      }
    }
    return result;
  }
}
