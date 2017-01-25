package interpreter

import org.eclipse.acceleo.query.services.EObjectServices
import java.lang.reflect.Method
import org.eclipse.acceleo.query.runtime.impl.JavaMethodService
import org.eclipse.acceleo.query.ast.Call
import org.eclipse.acceleo.query.runtime.impl.ValidationServices
import org.eclipse.acceleo.query.runtime.IValidationResult
import org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment
import java.util.List
import org.eclipse.acceleo.query.validation.type.IType
import java.util.Set
import org.eclipse.acceleo.query.ast.StringLiteral
import java.util.LinkedHashSet
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EClassifier
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.acceleo.query.validation.type.EClassifierType
import org.eclipse.acceleo.query.validation.type.SequenceType
import java.util.Map
import java.util.Map.Entry
import org.eclipse.acceleo.query.runtime.ICompletionProposal
import java.util.ArrayList
import org.eclipse.acceleo.query.runtime.impl.completion.EFeatureCompletionProposal
import org.eclipse.emf.ecore.EObject
import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException
import implementation.Root

/**
 * Copy/past of EObjectServices.EObjectFeatureAccess cause it's private & final :(
 */
class DynamicFeatureAccessService extends JavaMethodService {
	
	public static final String DON_T_KNOW_WHAT_TO_DO_WITH = "don't know what to do with "
	public static final String NON_EOBJECT_FEATURE_ACCESS = "Attempt to access feature (%s) on a non ModelObject value (%s)."
	public static final String UNKNOWN_FEATURE = "Feature %s not found in EClass %s";
	
	/**
	 * Creates a new service instance given a method and an instance.
	 * 
	 * @param method
	 *            the method that realizes the service
	 * @param serviceInstance
	 *            the instance on which the service must be called
	 */
	new(Method method, DynamicFeatureAccess serviceInstance) {
		super(method, serviceInstance);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.JavaMethodService#getType(org.eclipse.acceleo.query.ast.Call,
	 *      org.eclipse.acceleo.query.runtime.impl.ValidationServices,
	 *      org.eclipse.acceleo.query.runtime.IValidationResult,
	 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.List)
	 */
	override Set<IType> getType(Call call, ValidationServices services, IValidationResult validationResult,
			IReadOnlyQueryEnvironment queryEnvironment, List<IType> argTypes) {
		val featureName = (call.getArguments().get(1) as StringLiteral).getValue();
		val Set<IType> result = featureAccessTypes(services, queryEnvironment, argTypes.get(0),
				featureName);

		return result;
	}

	/**
	 * Gets the type of a feature access.
	 * 
	 * @param queryEnvironment
	 *            the {@link IReadOnlyQueryEnvironment}
	 * @param receiverTypes
	 *            the target types to gets the feature from
	 * @param featureName
	 *            the feature name
	 * @return the type of a feature access
	 */
	def Set<IType> featureAccessTypes(ValidationServices services,
			IReadOnlyQueryEnvironment queryEnvironment, IType receiverType, String featureName) {
		val Set<IType> result = new LinkedHashSet<IType>();

		val Set<EClass> receiverEClasses = new LinkedHashSet<EClass>();
		if (receiverType.getType() instanceof EClass) {
			receiverEClasses.add(receiverType.getType() as EClass);
		} else if (receiverType.getType() instanceof Class) {
			val Set<EClassifier> eClassifiers = queryEnvironment.getEPackageProvider().getEClassifiers(
					receiverType.getType() as Class<?>);
			if (eClassifiers != null) {
				for (EClassifier eCls : eClassifiers) {
					if (eCls instanceof EClass) {
						receiverEClasses.add(eCls as EClass);
					}
				}
			}
		} else {
			throw new IllegalStateException(DON_T_KNOW_WHAT_TO_DO_WITH + receiverType.getType());
		}

		if (receiverEClasses.isEmpty()) {
			result.add(services.nothing(NON_EOBJECT_FEATURE_ACCESS, featureName, receiverType.getType()
					.toString()));
		} else {
			for (EClass eClass : receiverEClasses) {
				val EStructuralFeature feature = eClass.getEStructuralFeature(featureName);
				if (feature == null) {
					result.add(services.nothing(UNKNOWN_FEATURE, featureName, eClass.getName()));
				} else {
					val EClassifierType featureBasicType = new EClassifierType(queryEnvironment,
							feature.getEType());
					if (feature.isMany()) {
						result.add(new SequenceType(queryEnvironment, featureBasicType));
					} else {
						result.add(featureBasicType);
					}
				}
			}
		}

		return result;
		// CHECKSTYLE:OFF
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.acceleo.query.runtime.impl.AbstractService#validateAllType(org.eclipse.acceleo.query.runtime.impl.ValidationServices,
	 *      org.eclipse.acceleo.query.runtime.IReadOnlyQueryEnvironment, java.util.Map)
	 */
	override Set<IType> validateAllType(ValidationServices services,
			IReadOnlyQueryEnvironment queryEnvironment, Map<List<IType>, Set<IType>> allTypes) {
		val Set<IType> result = new LinkedHashSet<IType>();

		val Set<IType> knownReceiverTypes = new LinkedHashSet<IType>();
		for (Entry<List<IType>, Set<IType>> entry : allTypes.entrySet()) {
			if (knownReceiverTypes.add(entry.getKey().get(0))) {
				result.addAll(entry.getValue());
			}
		}

		return result;
	}

	override List<ICompletionProposal> getProposals(IReadOnlyQueryEnvironment queryEnvironment,
			Set<IType> receiverTypes) {
		return getEStructuralFeatureProposals(queryEnvironment, receiverTypes);
	}

	/**
	 * Gets the {@link List} of {@link EFeatureCompletionProposal} for {@link EStructuralFeature}.
	 * 
	 * @param receiverTypes
	 *            the receiver types.
	 * @return the {@link List} of {@link EFeatureCompletionProposal} for {@link EStructuralFeature}
	 */
	def List<ICompletionProposal> getEStructuralFeatureProposals(
			IReadOnlyQueryEnvironment queryEnvironment, Set<IType> receiverTypes) {
		val List<ICompletionProposal> result = new ArrayList<ICompletionProposal>();
		val Set<EClass> eClasses = new LinkedHashSet<EClass>();

		for (IType iType : receiverTypes) {
			if (iType.getType() instanceof EClass) {
				eClasses.add(iType.getType() as EClass);
			}
		}

		for (EStructuralFeature feature : queryEnvironment.getEPackageProvider().getEStructuralFeatures(
				eClasses)) {
			result.add(new EFeatureCompletionProposal(feature));
		}

		return result;
	}
	
	override getPriority() {
		super.getPriority() + 1;
	}
}