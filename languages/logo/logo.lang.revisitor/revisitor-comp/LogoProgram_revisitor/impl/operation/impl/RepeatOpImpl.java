package LogoProgram_revisitor.impl.operation.impl;

import LogoProgram_revisitor.impl.operation.BackOp;
import LogoProgram_revisitor.impl.operation.BinaryExpOp;
import LogoProgram_revisitor.impl.operation.BlockOp;
import LogoProgram_revisitor.impl.operation.CallStackOp;
import LogoProgram_revisitor.impl.operation.ClearOp;
import LogoProgram_revisitor.impl.operation.ConstantOp;
import LogoProgram_revisitor.impl.operation.ControlStructureOp;
import LogoProgram_revisitor.impl.operation.CosOp;
import LogoProgram_revisitor.impl.operation.DivOp;
import LogoProgram_revisitor.impl.operation.EqualsOp;
import LogoProgram_revisitor.impl.operation.ExpressionOp;
import LogoProgram_revisitor.impl.operation.ForwardOp;
import LogoProgram_revisitor.impl.operation.GreaterOp;
import LogoProgram_revisitor.impl.operation.IfOp;
import LogoProgram_revisitor.impl.operation.InstructionOp;
import LogoProgram_revisitor.impl.operation.LeftOp;
import LogoProgram_revisitor.impl.operation.LogoProgramOp;
import LogoProgram_revisitor.impl.operation.LowerOp;
import LogoProgram_revisitor.impl.operation.MinusOp;
import LogoProgram_revisitor.impl.operation.MultOp;
import LogoProgram_revisitor.impl.operation.ParameterCallOp;
import LogoProgram_revisitor.impl.operation.ParameterOp;
import LogoProgram_revisitor.impl.operation.PenDownOp;
import LogoProgram_revisitor.impl.operation.PenUpOp;
import LogoProgram_revisitor.impl.operation.PlusOp;
import LogoProgram_revisitor.impl.operation.PointOp;
import LogoProgram_revisitor.impl.operation.PrimitiveOp;
import LogoProgram_revisitor.impl.operation.ProcCallOp;
import LogoProgram_revisitor.impl.operation.ProcDeclarationOp;
import LogoProgram_revisitor.impl.operation.RepeatOp;
import LogoProgram_revisitor.impl.operation.RightOp;
import LogoProgram_revisitor.impl.operation.SegmentOp;
import LogoProgram_revisitor.impl.operation.SinOp;
import LogoProgram_revisitor.impl.operation.StackFrameOp;
import LogoProgram_revisitor.impl.operation.TanOp;
import LogoProgram_revisitor.impl.operation.TurtleOp;
import LogoProgram_revisitor.impl.operation.UnaryExpressionOp;
import LogoProgram_revisitor.impl.operation.VariableOp;
import LogoProgram_revisitor.impl.operation.WhileOp;
import java.lang.Double;
import kmLogo.Block;
import kmLogo.Expression;
import kmLogo.Repeat;
import kmLogo.Turtle;
import kmLogo.revisitor.KmLogoRevisitor;

public class RepeatOpImpl extends ControlStructureOpImpl implements RepeatOp {
  private KmLogoRevisitor<BackOp, BinaryExpOp, BlockOp, CallStackOp, ClearOp, ConstantOp, ControlStructureOp, CosOp, DivOp, EqualsOp, ExpressionOp, ForwardOp, GreaterOp, IfOp, InstructionOp, LeftOp, LogoProgramOp, LowerOp, MinusOp, MultOp, ParameterOp, ParameterCallOp, PenDownOp, PenUpOp, PlusOp, PointOp, PrimitiveOp, ProcCallOp, ProcDeclarationOp, RepeatOp, RightOp, SegmentOp, SinOp, StackFrameOp, TanOp, TurtleOp, UnaryExpressionOp, VariableOp, WhileOp> rev;

  private Repeat obj;

  public RepeatOpImpl(Repeat obj,
      KmLogoRevisitor<BackOp, BinaryExpOp, BlockOp, CallStackOp, ClearOp, ConstantOp, ControlStructureOp, CosOp, DivOp, EqualsOp, ExpressionOp, ForwardOp, GreaterOp, IfOp, InstructionOp, LeftOp, LogoProgramOp, LowerOp, MinusOp, MultOp, ParameterOp, ParameterCallOp, PenDownOp, PenUpOp, PlusOp, PointOp, PrimitiveOp, ProcCallOp, ProcDeclarationOp, RepeatOp, RightOp, SegmentOp, SinOp, StackFrameOp, TanOp, TurtleOp, UnaryExpressionOp, VariableOp, WhileOp> rev) {
    super(obj, rev);
    this.obj = obj;
    this.rev = rev;
  }

  public double eval(Turtle turtle) {
    double result;
    double time = ((double) (rev.$((Expression)this.obj.getCondition()).eval(((Turtle) (turtle)))));
    while ((time) > (0.0)) {
      rev.$((Block)this.obj.getBlock()).eval(((Turtle) (turtle)));
      time = ((Double) ((time) - (1.0)));
    }
    result = ((Double) (0.0));
    return result;
  }
}
