/*
 * generated by Xtext 2.14.0
 */
package imp.lang.xtext.parser.antlr;

import com.google.inject.Inject;
import imp.lang.xtext.parser.antlr.internal.InternalImpParser;
import imp.lang.xtext.services.ImpGrammarAccess;
import org.eclipse.xtext.parser.antlr.AbstractAntlrParser;
import org.eclipse.xtext.parser.antlr.XtextTokenStream;

public class ImpParser extends AbstractAntlrParser {

	@Inject
	private ImpGrammarAccess grammarAccess;

	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	

	@Override
	protected InternalImpParser createParser(XtextTokenStream stream) {
		return new InternalImpParser(stream, getGrammarAccess());
	}

	@Override 
	protected String getDefaultRuleName() {
		return "Stmt";
	}

	public ImpGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(ImpGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
}