package miniJava.interpreter.miniJava.impl;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.NodeInfo;
import java.lang.Override;
import miniJava.interpreter.miniJava.MiniJavaPackage;
import miniJava.interpreter.miniJava.SingleTypeRef;
import org.eclipse.emf.ecore.EClass;

@NodeInfo(
		description = "SingleTypeRef"
)
public class SingleTypeRefImpl extends TypeRefImpl implements SingleTypeRef {
	protected SingleTypeRefImpl() {
		super();
	}

	@Override
	@TruffleBoundary
	protected EClass eStaticClass() {
		return MiniJavaPackage.Literals.SINGLE_TYPE_REF;
	}
}
