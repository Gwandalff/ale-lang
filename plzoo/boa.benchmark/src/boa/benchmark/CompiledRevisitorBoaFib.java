package boa.benchmark;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import boa.BoaPackage;
import boa.File;
import boa_exec.impl.Boa_execImplementation;

public class CompiledRevisitorBoaFib {
	public static void main(final String[] args) throws IOException {
		BoaPackage.eINSTANCE.eClass();

		BoaPackage.eINSTANCE.eClass();

		final Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
		final Map<String, Object> m = reg.getExtensionToFactoryMap();
		m.put("xmi", new XMIResourceFactoryImpl());
		final Boa_execImplementation boa_execImplementation = new Boa_execImplementation() {
		};

		final int SIZE = 4000;
		final long[] times = new long[SIZE];
		for (int i = 0; i < SIZE; i++) {
			final ResourceSetImpl resSet = new ResourceSetImpl();
			final URI createFileURI = URI.createFileURI("/home/manuel/runtime-ale-lang/boa.benchmark/fib2.xmi");
			final Resource resource = resSet.getResource(createFileURI, true);
			final File result = (File) resource.getContents().get(0);

			final long start = System.currentTimeMillis();
			boa_execImplementation.$(result).eval();
			times[i] = System.currentTimeMillis() - start;
		}

		System.out.println(Arrays.toString(times));
	}
}
