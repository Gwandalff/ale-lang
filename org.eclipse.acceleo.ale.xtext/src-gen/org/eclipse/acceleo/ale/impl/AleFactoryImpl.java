/**
 * generated by Xtext 2.10.0
 */
package org.eclipse.acceleo.ale.impl;

import org.eclipse.acceleo.ale.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AleFactoryImpl extends EFactoryImpl implements AleFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static AleFactory init()
  {
    try
    {
      AleFactory theAleFactory = (AleFactory)EPackage.Registry.INSTANCE.getEFactory(AlePackage.eNS_URI);
      if (theAleFactory != null)
      {
        return theAleFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new AleFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AleFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case AlePackage.RROOT: return createrRoot();
      case AlePackage.RCLASS: return createrClass();
      case AlePackage.ROPEN_CLASS: return createrOpenClass();
      case AlePackage.RNEW_CLASS: return createrNewClass();
      case AlePackage.ROPERATION: return createrOperation();
      case AlePackage.RPARAMETERS: return createrParameters();
      case AlePackage.RVARIABLE: return createrVariable();
      case AlePackage.RATTRIBUTE: return createrAttribute();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public rRoot createrRoot()
  {
    rRootImpl rRoot = new rRootImpl();
    return rRoot;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public rClass createrClass()
  {
    rClassImpl rClass = new rClassImpl();
    return rClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public rOpenClass createrOpenClass()
  {
    rOpenClassImpl rOpenClass = new rOpenClassImpl();
    return rOpenClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public rNewClass createrNewClass()
  {
    rNewClassImpl rNewClass = new rNewClassImpl();
    return rNewClass;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public rOperation createrOperation()
  {
    rOperationImpl rOperation = new rOperationImpl();
    return rOperation;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public rParameters createrParameters()
  {
    rParametersImpl rParameters = new rParametersImpl();
    return rParameters;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public rVariable createrVariable()
  {
    rVariableImpl rVariable = new rVariableImpl();
    return rVariable;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public rAttribute createrAttribute()
  {
    rAttributeImpl rAttribute = new rAttributeImpl();
    return rAttribute;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AlePackage getAlePackage()
  {
    return (AlePackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static AlePackage getPackage()
  {
    return AlePackage.eINSTANCE;
  }

} //AleFactoryImpl