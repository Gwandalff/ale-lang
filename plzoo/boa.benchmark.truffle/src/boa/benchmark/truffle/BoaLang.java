package boa.benchmark.truffle;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLanguage.ContextPolicy;

import boa.interpreter.boa.BoaPackage;
import boa.interpreter.boa.File;

@TruffleLanguage.Registration(id = "boa", name = "boa", mimeType = "application/boa", contextPolicy = ContextPolicy.SHARED)
public class BoaLang extends TruffleLanguage<Void> {

	public BoaLang() {
		super();
	}

	@Override
	protected Void createContext(final Env env) {
		return null;
	}

	@Override
	protected boolean isObjectOfLanguage(final Object object) {
		if (object instanceof EObject) {
			final EObject eObject = (EObject) object;
			return eObject.eClass().getEPackage().getNsURI().equals(BoaPackage.eINSTANCE.getNsURI());
		} else {
			return false;
		}
	}

	@Override
	public CallTarget parse(final TruffleLanguage.ParsingRequest request) throws Exception {

		return Truffle.getRuntime()
				.createCallTarget(new MainRootNodeExtension(BoaLang.this, BoaBenchmarkTruffleJmh.result));
	}

}
