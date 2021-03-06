package minifsm.interpreter.minifsm;

import java.lang.String;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

public interface FSM extends EObject {
  String getCurrentEvent();

  void setCurrentEvent(String value);

  EList<State> getStates();

  EList<Transition> getTransitions();

  State getCurrentState();

  void setCurrentState(State value);

  void handle(String event);

  void entryPoint();
}
