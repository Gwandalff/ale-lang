package miniJava.interpreter.miniJava.impl;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.NodeInfo;
import java.lang.Override;
import miniJava.interpreter.miniJava.MiniJavaPackage;
import miniJava.interpreter.miniJava.VariableDeclaration;
import org.eclipse.emf.ecore.EClass;

@NodeInfo(
		description = "VariableDeclaration"
)
public class VariableDeclarationImpl extends SymbolImpl implements VariableDeclaration {
	protected VariableDeclarationImpl() {
		super();
	}

	@Override
	@TruffleBoundary
	protected EClass eStaticClass() {
		return MiniJavaPackage.Literals.VARIABLE_DECLARATION;
	}
}
