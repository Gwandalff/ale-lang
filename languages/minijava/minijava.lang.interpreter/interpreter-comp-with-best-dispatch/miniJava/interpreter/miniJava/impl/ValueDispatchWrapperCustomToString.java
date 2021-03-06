package miniJava.interpreter.miniJava.impl;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.utilities.CyclicAssumption;
import miniJava.interpreter.miniJava.Value;

public class ValueDispatchWrapperCustomToString {
  private RootCallTarget callTarget;

  private final CyclicAssumption callTargetStable;

  protected ValueDispatchWrapperCustomToString(Value it) {
    this.callTargetStable = new CyclicAssumption("ValueDispatchWrapperCustomToString_3.995987392217195E7");
    this.callTarget = Truffle.getRuntime().createCallTarget(new ValueCustomToStringRootNode(it));
  }

  public RootCallTarget getCallTarget() {
    return callTarget;}

  public Assumption getCallTargetStable() {
    return callTargetStable.getAssumption();}
}
