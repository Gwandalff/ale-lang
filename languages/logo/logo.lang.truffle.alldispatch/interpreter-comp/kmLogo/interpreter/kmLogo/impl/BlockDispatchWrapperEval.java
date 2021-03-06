package kmLogo.interpreter.kmLogo.impl;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.utilities.CyclicAssumption;
import kmLogo.interpreter.kmLogo.Block;

public class BlockDispatchWrapperEval extends InstructionDispatchWrapperEval {
  private RootCallTarget callTarget;

  private final CyclicAssumption callTargetStable;

  protected BlockDispatchWrapperEval(Block it) {
    super(it);
    this.callTargetStable = new CyclicAssumption("BlockDispatchWrapperEval_2.879359963967538E7");
    this.callTarget = Truffle.getRuntime().createCallTarget(new BlockEvalRootNode(it));
  }

  public RootCallTarget getCallTarget() {
    return callTarget;}

  public Assumption getCallTargetStable() {
    return callTargetStable.getAssumption();}
}
