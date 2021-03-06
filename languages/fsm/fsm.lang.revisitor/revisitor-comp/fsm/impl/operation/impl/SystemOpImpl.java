package fsm.impl.operation.impl;

import fsm.impl.operation.BufferOp;
import fsm.impl.operation.FSMOp;
import fsm.impl.operation.StateOp;
import fsm.impl.operation.SystemOp;
import fsm.impl.operation.TransitionOp;
import fsm.model.fsm.Buffer;
import fsm.model.fsm.FSM;
import fsm.model.fsm.System;
import fsm.model.revisitor.FsmRevisitor;
import java.lang.Boolean;
import java.lang.Integer;

public class SystemOpImpl implements SystemOp {
  private FsmRevisitor<BufferOp, FSMOp, StateOp, SystemOp, TransitionOp> rev;

  private System obj;

  public SystemOpImpl(System obj,
      FsmRevisitor<BufferOp, FSMOp, StateOp, SystemOp, TransitionOp> rev) {
    this.obj = obj;
    this.rev = rev;
  }

  public void intialize() {
    for(FSM fsm: this.obj.getOwnedFsms()) {
      rev.$((FSM)fsm).initialize();
    }
    for(Buffer b: this.obj.getOwnedBuffers()) {
      rev.$((Buffer)b).initialize();
    }
  }

  public void main(int limit) {
    rev.$((System)this.obj).intialize();
    boolean anFSMRan = ((boolean) (true));
    int cptr = ((int) (0));
    while (((anFSMRan) && ((cptr) < (limit)))) {
      anFSMRan = ((Boolean) (false));
      for(FSM fsm: this.obj.getOwnedFsms()) {
        if(!(rev.$((Buffer)fsm.getInputBuffer()).bisEmpty())) {
          rev.$((FSM)fsm).run();
          anFSMRan = ((Boolean) (true));
        }
        cptr = ((Integer) ((cptr) + (1)));
      }
    }
  }
}
