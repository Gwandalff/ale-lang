package petrinet.interpreter.petrinet;

import org.eclipse.emf.ecore.EFactory;
import petrinet.interpreter.petrinet.impl.PetrinetFactoryImpl;

public interface PetrinetFactory extends EFactory {
  PetrinetFactory eINSTANCE = PetrinetFactoryImpl.init();

  Petrinet createPetrinet();

  Arc createArc();

  Transition createTransition();

  Place createPlace();
}
