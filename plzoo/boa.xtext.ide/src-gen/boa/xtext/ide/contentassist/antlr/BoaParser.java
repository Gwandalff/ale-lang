/*
 * generated by Xtext 2.14.0
 */
package boa.xtext.ide.contentassist.antlr;

import boa.xtext.ide.contentassist.antlr.internal.InternalBoaParser;
import boa.xtext.services.BoaGrammarAccess;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Map;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.ide.editor.contentassist.antlr.AbstractContentAssistParser;

public class BoaParser extends AbstractContentAssistParser {

	@Singleton
	public static final class NameMappings {
		
		private final Map<AbstractElement, String> mappings;
		
		@Inject
		public NameMappings(BoaGrammarAccess grammarAccess) {
			ImmutableMap.Builder<AbstractElement, String> builder = ImmutableMap.builder();
			init(builder, grammarAccess);
			this.mappings = builder.build();
		}
		
		public String getRuleName(AbstractElement element) {
			return mappings.get(element);
		}
		
		private static void init(ImmutableMap.Builder<AbstractElement, String> builder, BoaGrammarAccess grammarAccess) {
			builder.put(grammarAccess.getTopLevelCmdAccess().getAlternatives(), "rule__TopLevelCmd__Alternatives");
			builder.put(grammarAccess.getExprAccess().getAlternatives(), "rule__Expr__Alternatives");
			builder.put(grammarAccess.getEStringAccess().getAlternatives(), "rule__EString__Alternatives");
			builder.put(grammarAccess.getEBooleanAccess().getAlternatives(), "rule__EBoolean__Alternatives");
			builder.put(grammarAccess.getFileAccess().getGroup(), "rule__File__Group__0");
			builder.put(grammarAccess.getFileAccess().getGroup_3(), "rule__File__Group_3__0");
			builder.put(grammarAccess.getFileAccess().getGroup_3_3(), "rule__File__Group_3_3__0");
			builder.put(grammarAccess.getDefAccess().getGroup(), "rule__Def__Group__0");
			builder.put(grammarAccess.getAppAccess().getGroup(), "rule__App__Group__0");
			builder.put(grammarAccess.getVarAccess().getGroup(), "rule__Var__Group__0");
			builder.put(grammarAccess.getThisAccess().getGroup(), "rule__This__Group__0");
			builder.put(grammarAccess.getBoolAccess().getGroup(), "rule__Bool__Group__0");
			builder.put(grammarAccess.getInt0Access().getGroup(), "rule__Int0__Group__0");
			builder.put(grammarAccess.getInt0Access().getGroup_3(), "rule__Int0__Group_3__0");
			builder.put(grammarAccess.getSkipAccess().getGroup(), "rule__Skip__Group__0");
			builder.put(grammarAccess.getProjectAccess().getGroup(), "rule__Project__Group__0");
			builder.put(grammarAccess.getBObjectAccess().getGroup(), "rule__BObject__Group__0");
			builder.put(grammarAccess.getBObjectAccess().getGroup_3(), "rule__BObject__Group_3__0");
			builder.put(grammarAccess.getBObjectAccess().getGroup_3_3(), "rule__BObject__Group_3_3__0");
			builder.put(grammarAccess.getCopyAccess().getGroup(), "rule__Copy__Group__0");
			builder.put(grammarAccess.getWithAccess().getGroup(), "rule__With__Group__0");
			builder.put(grammarAccess.getNotAccess().getGroup(), "rule__Not__Group__0");
			builder.put(grammarAccess.getIfAccess().getGroup(), "rule__If__Group__0");
			builder.put(grammarAccess.getLetAccess().getGroup(), "rule__Let__Group__0");
			builder.put(grammarAccess.getFunAccess().getGroup(), "rule__Fun__Group__0");
			builder.put(grammarAccess.getAssignAccess().getGroup(), "rule__Assign__Group__0");
			builder.put(grammarAccess.getArithOpPlusAccess().getGroup(), "rule__ArithOpPlus__Group__0");
			builder.put(grammarAccess.getArithOpMinusAccess().getGroup(), "rule__ArithOpMinus__Group__0");
			builder.put(grammarAccess.getArithOpTimesAccess().getGroup(), "rule__ArithOpTimes__Group__0");
			builder.put(grammarAccess.getArithOpDivideAccess().getGroup(), "rule__ArithOpDivide__Group__0");
			builder.put(grammarAccess.getArithOpRemainderAccess().getGroup(), "rule__ArithOpRemainder__Group__0");
			builder.put(grammarAccess.getBoolOpAndAccess().getGroup(), "rule__BoolOpAnd__Group__0");
			builder.put(grammarAccess.getBoolOpOrAccess().getGroup(), "rule__BoolOpOr__Group__0");
			builder.put(grammarAccess.getSeqAccess().getGroup(), "rule__Seq__Group__0");
			builder.put(grammarAccess.getCmpOpEqualAccess().getGroup(), "rule__CmpOpEqual__Group__0");
			builder.put(grammarAccess.getCmpOpUnequalAccess().getGroup(), "rule__CmpOpUnequal__Group__0");
			builder.put(grammarAccess.getCmpOpLessAccess().getGroup(), "rule__CmpOpLess__Group__0");
			builder.put(grammarAccess.getEIntAccess().getGroup(), "rule__EInt__Group__0");
			builder.put(grammarAccess.getFieldAccess().getGroup(), "rule__Field__Group__0");
			builder.put(grammarAccess.getFileAccess().getCommandsAssignment_3_2(), "rule__File__CommandsAssignment_3_2");
			builder.put(grammarAccess.getFileAccess().getCommandsAssignment_3_3_1(), "rule__File__CommandsAssignment_3_3_1");
			builder.put(grammarAccess.getDefAccess().getNameAssignment_1(), "rule__Def__NameAssignment_1");
			builder.put(grammarAccess.getDefAccess().getExprAssignment_4(), "rule__Def__ExprAssignment_4");
			builder.put(grammarAccess.getAppAccess().getLhsAssignment_3(), "rule__App__LhsAssignment_3");
			builder.put(grammarAccess.getAppAccess().getRhsAssignment_5(), "rule__App__RhsAssignment_5");
			builder.put(grammarAccess.getVarAccess().getNameAssignment_2(), "rule__Var__NameAssignment_2");
			builder.put(grammarAccess.getBoolAccess().getValueAssignment_1(), "rule__Bool__ValueAssignment_1");
			builder.put(grammarAccess.getInt0Access().getValueAssignment_3_1(), "rule__Int0__ValueAssignment_3_1");
			builder.put(grammarAccess.getProjectAccess().getNameAssignment_1(), "rule__Project__NameAssignment_1");
			builder.put(grammarAccess.getProjectAccess().getExpAssignment_4(), "rule__Project__ExpAssignment_4");
			builder.put(grammarAccess.getBObjectAccess().getFieldsAssignment_3_2(), "rule__BObject__FieldsAssignment_3_2");
			builder.put(grammarAccess.getBObjectAccess().getFieldsAssignment_3_3_1(), "rule__BObject__FieldsAssignment_3_3_1");
			builder.put(grammarAccess.getCopyAccess().getCopyAssignment_3(), "rule__Copy__CopyAssignment_3");
			builder.put(grammarAccess.getWithAccess().getLhsAssignment_3(), "rule__With__LhsAssignment_3");
			builder.put(grammarAccess.getWithAccess().getRhsAssignment_5(), "rule__With__RhsAssignment_5");
			builder.put(grammarAccess.getNotAccess().getValueAssignment_3(), "rule__Not__ValueAssignment_3");
			builder.put(grammarAccess.getIfAccess().getCondAssignment_3(), "rule__If__CondAssignment_3");
			builder.put(grammarAccess.getIfAccess().getThnAssignment_5(), "rule__If__ThnAssignment_5");
			builder.put(grammarAccess.getIfAccess().getElsAssignment_7(), "rule__If__ElsAssignment_7");
			builder.put(grammarAccess.getLetAccess().getNameAssignment_1(), "rule__Let__NameAssignment_1");
			builder.put(grammarAccess.getLetAccess().getLhsAssignment_4(), "rule__Let__LhsAssignment_4");
			builder.put(grammarAccess.getLetAccess().getRhsAssignment_6(), "rule__Let__RhsAssignment_6");
			builder.put(grammarAccess.getFunAccess().getNameAssignment_1(), "rule__Fun__NameAssignment_1");
			builder.put(grammarAccess.getFunAccess().getBodyAssignment_4(), "rule__Fun__BodyAssignment_4");
			builder.put(grammarAccess.getAssignAccess().getNameAssignment_1(), "rule__Assign__NameAssignment_1");
			builder.put(grammarAccess.getAssignAccess().getLhsAssignment_4(), "rule__Assign__LhsAssignment_4");
			builder.put(grammarAccess.getAssignAccess().getRhsAssignment_6(), "rule__Assign__RhsAssignment_6");
			builder.put(grammarAccess.getArithOpPlusAccess().getLhsAssignment_3(), "rule__ArithOpPlus__LhsAssignment_3");
			builder.put(grammarAccess.getArithOpPlusAccess().getRhsAssignment_5(), "rule__ArithOpPlus__RhsAssignment_5");
			builder.put(grammarAccess.getArithOpMinusAccess().getLhsAssignment_3(), "rule__ArithOpMinus__LhsAssignment_3");
			builder.put(grammarAccess.getArithOpMinusAccess().getRhsAssignment_5(), "rule__ArithOpMinus__RhsAssignment_5");
			builder.put(grammarAccess.getArithOpTimesAccess().getLhsAssignment_3(), "rule__ArithOpTimes__LhsAssignment_3");
			builder.put(grammarAccess.getArithOpTimesAccess().getRhsAssignment_5(), "rule__ArithOpTimes__RhsAssignment_5");
			builder.put(grammarAccess.getArithOpDivideAccess().getLhsAssignment_3(), "rule__ArithOpDivide__LhsAssignment_3");
			builder.put(grammarAccess.getArithOpDivideAccess().getRhsAssignment_5(), "rule__ArithOpDivide__RhsAssignment_5");
			builder.put(grammarAccess.getArithOpRemainderAccess().getLhsAssignment_3(), "rule__ArithOpRemainder__LhsAssignment_3");
			builder.put(grammarAccess.getArithOpRemainderAccess().getRhsAssignment_5(), "rule__ArithOpRemainder__RhsAssignment_5");
			builder.put(grammarAccess.getBoolOpAndAccess().getLhsAssignment_3(), "rule__BoolOpAnd__LhsAssignment_3");
			builder.put(grammarAccess.getBoolOpAndAccess().getRhsAssignment_5(), "rule__BoolOpAnd__RhsAssignment_5");
			builder.put(grammarAccess.getBoolOpOrAccess().getLhsAssignment_3(), "rule__BoolOpOr__LhsAssignment_3");
			builder.put(grammarAccess.getBoolOpOrAccess().getRhsAssignment_5(), "rule__BoolOpOr__RhsAssignment_5");
			builder.put(grammarAccess.getSeqAccess().getLhsAssignment_3(), "rule__Seq__LhsAssignment_3");
			builder.put(grammarAccess.getSeqAccess().getRhsAssignment_5(), "rule__Seq__RhsAssignment_5");
			builder.put(grammarAccess.getCmpOpEqualAccess().getLhsAssignment_3(), "rule__CmpOpEqual__LhsAssignment_3");
			builder.put(grammarAccess.getCmpOpEqualAccess().getRhsAssignment_5(), "rule__CmpOpEqual__RhsAssignment_5");
			builder.put(grammarAccess.getCmpOpUnequalAccess().getLhsAssignment_3(), "rule__CmpOpUnequal__LhsAssignment_3");
			builder.put(grammarAccess.getCmpOpUnequalAccess().getRhsAssignment_5(), "rule__CmpOpUnequal__RhsAssignment_5");
			builder.put(grammarAccess.getCmpOpLessAccess().getLhsAssignment_3(), "rule__CmpOpLess__LhsAssignment_3");
			builder.put(grammarAccess.getCmpOpLessAccess().getRhsAssignment_5(), "rule__CmpOpLess__RhsAssignment_5");
			builder.put(grammarAccess.getFieldAccess().getNameAssignment_1(), "rule__Field__NameAssignment_1");
			builder.put(grammarAccess.getFieldAccess().getValueAssignment_4(), "rule__Field__ValueAssignment_4");
		}
	}
	
	@Inject
	private NameMappings nameMappings;

	@Inject
	private BoaGrammarAccess grammarAccess;

	@Override
	protected InternalBoaParser createParser() {
		InternalBoaParser result = new InternalBoaParser(null);
		result.setGrammarAccess(grammarAccess);
		return result;
	}

	@Override
	protected String getRuleName(AbstractElement element) {
		return nameMappings.getRuleName(element);
	}

	@Override
	protected String[] getInitialHiddenTokens() {
		return new String[] { "RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT" };
	}

	public BoaGrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}

	public void setGrammarAccess(BoaGrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
	public NameMappings getNameMappings() {
		return nameMappings;
	}
	
	public void setNameMappings(NameMappings nameMappings) {
		this.nameMappings = nameMappings;
	}
}