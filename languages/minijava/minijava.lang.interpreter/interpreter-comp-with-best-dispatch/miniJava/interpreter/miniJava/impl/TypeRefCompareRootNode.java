package miniJava.interpreter.miniJava.impl;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node.Child;
import com.oracle.truffle.api.nodes.RootNode;
import java.lang.Object;
import java.lang.Override;
import miniJava.interpreter.miniJava.TypeRef;

public class TypeRefCompareRootNode extends RootNode {
  @Child
  private TypeRef it;

  public TypeRefCompareRootNode(TypeRef it) {
    super(null);
    this.it = it;
  }

  @Override
  public Object execute(VirtualFrame frame) {
    miniJava.interpreter.miniJava.TypeRef other = (miniJava.interpreter.miniJava.TypeRef) frame.getArguments()[0];
    return it.compare(other);
  }
}
