package visitor.operation.boa.impl;

import boa.visitor.boa.BoaFactory;
import boa.visitor.boa.CmpOpUnequal;
import boa.visitor.boa.Ctx;
import boa.visitor.boa.EvalBoolRes;
import boa.visitor.boa.EvalIntRes;
import boa.visitor.boa.EvalRes;
import org.eclipse.emf.ecoretools.ale.compiler.lib.EqualService;
import visitor.VisitorInterface;
import visitor.operation.boa.CmpOpUnequalOperation;
import visitor.operation.boa.ExprOperation;

public class CmpOpUnequalOperationImpl extends CmpOpOperationImpl implements CmpOpUnequalOperation {
	private final CmpOpUnequal it;

	private final VisitorInterface vis;

	public CmpOpUnequalOperationImpl(CmpOpUnequal it, VisitorInterface vis) {
		super(it, vis);
		this.it = it;
		this.vis = vis;
	}

	public EvalRes eval(Ctx ctx) {
		EvalRes result;
		EvalRes vlhs = ((EvalRes) (((ExprOperation)this.it.getLhs().accept(vis)).eval((Ctx) (ctx))));
		EvalRes vrhs = ((EvalRes) (((ExprOperation)this.it.getRhs().accept(vis)).eval((Ctx) (ctx))));
		if(vlhs instanceof EvalIntRes) {
			if(vrhs instanceof EvalIntRes) {
				EvalIntRes ivlhs = ((EvalIntRes) (vlhs));
				EvalIntRes ivrhs = ((EvalIntRes) (vrhs));
				EvalBoolRes ret = ((EvalBoolRes) (BoaFactory.eINSTANCE.createEvalBoolRes()));
				ret.setValue(!EqualService.equals((ivlhs.getValue()), (ivrhs.getValue())));
				result = ((EvalBoolRes) (ret));
			}
			else {
				result = ((EvalRes) (null));
			}
		}
		else {
			result = ((EvalRes) (null));
		}
		return result;
	}
}
