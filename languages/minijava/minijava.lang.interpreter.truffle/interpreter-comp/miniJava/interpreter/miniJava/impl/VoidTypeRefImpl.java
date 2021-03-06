package miniJava.interpreter.miniJava.impl;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.NodeInfo;
import java.lang.Override;
import miniJava.interpreter.miniJava.MiniJavaPackage;
import miniJava.interpreter.miniJava.VoidTypeRef;
import org.eclipse.emf.ecore.EClass;

@NodeInfo(
		description = "VoidTypeRef"
)
public class VoidTypeRefImpl extends SingleTypeRefImpl implements VoidTypeRef {
	protected VoidTypeRefImpl() {
		super();
	}

	@Override
	@TruffleBoundary
	protected EClass eStaticClass() {
		return MiniJavaPackage.Literals.VOID_TYPE_REF;
	}
}
