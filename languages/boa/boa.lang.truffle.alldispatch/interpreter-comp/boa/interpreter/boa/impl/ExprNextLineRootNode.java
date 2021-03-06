package boa.interpreter.boa.impl;

import boa.interpreter.boa.Expr;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node.Child;
import com.oracle.truffle.api.nodes.RootNode;
import java.lang.Object;
import java.lang.Override;

public class ExprNextLineRootNode extends RootNode {
  @Child
  private Expr it;

  public ExprNextLineRootNode(Expr it) {
    super(null);
    this.it = it;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    boa.interpreter.boa.Ctx ctx = (boa.interpreter.boa.Ctx) frame.getArguments()[0];
    it.nextLine(ctx);
    return null;
  }
}
