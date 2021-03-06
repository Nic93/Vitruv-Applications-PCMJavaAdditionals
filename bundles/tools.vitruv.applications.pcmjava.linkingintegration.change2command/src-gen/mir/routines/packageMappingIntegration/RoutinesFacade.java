package mir.routines.packageMappingIntegration;

import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.Field;
import org.emftext.language.java.members.Method;
import org.emftext.language.java.modifiers.AnnotationInstanceOrModifier;
import org.emftext.language.java.parameters.Parameter;
import org.emftext.language.java.types.TypeReference;
import org.palladiosimulator.pcm.repository.BasicComponent;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationRequiredRole;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutinesFacade;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RoutinesFacade extends AbstractRepairRoutinesFacade {
  public RoutinesFacade(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
    super(reactionExecutionState, calledBy);
  }
  
  public boolean renamedMethod(final Method method, final String newMethodName) {
    mir.routines.packageMappingIntegration.RenamedMethodRoutine effect = new mir.routines.packageMappingIntegration.RenamedMethodRoutine(this.executionState, calledBy, method, newMethodName);
    return effect.applyRoutine();
  }
  
  public boolean removedMethodEvent(final Method method) {
    mir.routines.packageMappingIntegration.RemovedMethodEventRoutine effect = new mir.routines.packageMappingIntegration.RemovedMethodEventRoutine(this.executionState, calledBy, method);
    return effect.applyRoutine();
  }
  
  public boolean addedMethodEvent(final ConcreteClassifier clazz, final Method method) {
    mir.routines.packageMappingIntegration.AddedMethodEventRoutine effect = new mir.routines.packageMappingIntegration.AddedMethodEventRoutine(this.executionState, calledBy, clazz, method);
    return effect.applyRoutine();
  }
  
  public boolean createOperationSignature(final OperationInterface opInterface, final Method newMethod) {
    mir.routines.packageMappingIntegration.CreateOperationSignatureRoutine effect = new mir.routines.packageMappingIntegration.CreateOperationSignatureRoutine(this.executionState, calledBy, opInterface, newMethod);
    return effect.applyRoutine();
  }
  
  public boolean createdMethodParameterEvent(final Method method, final Parameter parameter) {
    mir.routines.packageMappingIntegration.CreatedMethodParameterEventRoutine effect = new mir.routines.packageMappingIntegration.CreatedMethodParameterEventRoutine(this.executionState, calledBy, method, parameter);
    return effect.applyRoutine();
  }
  
  public boolean methodParameterNameChangedEvent(final Parameter parameter, final String oldParameterName, final String newParameterName) {
    mir.routines.packageMappingIntegration.MethodParameterNameChangedEventRoutine effect = new mir.routines.packageMappingIntegration.MethodParameterNameChangedEventRoutine(this.executionState, calledBy, parameter, oldParameterName, newParameterName);
    return effect.applyRoutine();
  }
  
  public boolean changedMethodType(final Method method, final TypeReference newType) {
    mir.routines.packageMappingIntegration.ChangedMethodTypeRoutine effect = new mir.routines.packageMappingIntegration.ChangedMethodTypeRoutine(this.executionState, calledBy, method, newType);
    return effect.applyRoutine();
  }
  
  public boolean removedFieldEvent(final Field field) {
    mir.routines.packageMappingIntegration.RemovedFieldEventRoutine effect = new mir.routines.packageMappingIntegration.RemovedFieldEventRoutine(this.executionState, calledBy, field);
    return effect.applyRoutine();
  }
  
  public boolean addedFieldEvent(final ConcreteClassifier clazz, final Field field) {
    mir.routines.packageMappingIntegration.AddedFieldEventRoutine effect = new mir.routines.packageMappingIntegration.AddedFieldEventRoutine(this.executionState, calledBy, clazz, field);
    return effect.applyRoutine();
  }
  
  public boolean createRequiredRole(final BasicComponent basicComponent, final OperationInterface opInterface, final Field field) {
    mir.routines.packageMappingIntegration.CreateRequiredRoleRoutine effect = new mir.routines.packageMappingIntegration.CreateRequiredRoleRoutine(this.executionState, calledBy, basicComponent, opInterface, field);
    return effect.applyRoutine();
  }
  
  public boolean changedFieldTypeEvent(final Field field, final TypeReference oldType, final TypeReference newType) {
    mir.routines.packageMappingIntegration.ChangedFieldTypeEventRoutine effect = new mir.routines.packageMappingIntegration.ChangedFieldTypeEventRoutine(this.executionState, calledBy, field, oldType, newType);
    return effect.applyRoutine();
  }
  
  public boolean removeRequiredRoleAndCorrespondence(final OperationRequiredRole orr, final Field field) {
    mir.routines.packageMappingIntegration.RemoveRequiredRoleAndCorrespondenceRoutine effect = new mir.routines.packageMappingIntegration.RemoveRequiredRoleAndCorrespondenceRoutine(this.executionState, calledBy, orr, field);
    return effect.applyRoutine();
  }
  
  public boolean changedMethodModifierEvent(final Method method, final AnnotationInstanceOrModifier annotationOrModifier) {
    mir.routines.packageMappingIntegration.ChangedMethodModifierEventRoutine effect = new mir.routines.packageMappingIntegration.ChangedMethodModifierEventRoutine(this.executionState, calledBy, method, annotationOrModifier);
    return effect.applyRoutine();
  }
}
