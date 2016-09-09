package tools.vitruv.applications.pcmjava.linkingintegration.change2command

import tools.vitruv.framework.change.processing.impl.AbstractChangeProcessor
import tools.vitruv.framework.userinteraction.UserInteracting
import tools.vitruv.framework.correspondence.CorrespondenceModel
import java.util.ArrayList
import tools.vitruv.framework.change.echange.EChange
import tools.vitruv.framework.change.description.VitruviusChangeFactory
import tools.vitruv.framework.change.processing.ChangeProcessorResult
import tools.vitruv.applications.pcmjava.linkingintegration.change2command.internal.IntegrationChange2CommandTransformer
import tools.vitruv.framework.util.command.VitruviusRecordingCommand
import tools.vitruv.framework.change.description.TransactionalChange

class CodeIntegrationChangeProcessor extends AbstractChangeProcessor {
	private val IntegrationChange2CommandTransformer integrationTransformer;
	
	new(UserInteracting userInteracting) {
		super(userInteracting);
		this.integrationTransformer = new IntegrationChange2CommandTransformer(getUserInteracting());
	}
	
	override transformChange(TransactionalChange change, CorrespondenceModel correspondenceModel) {
		val nonIntegratedEChanges = new ArrayList<EChange>();
		val commands = new ArrayList<VitruviusRecordingCommand>();
		for (eChange : change.getEChanges) {
			// Special behavior for changes to integrated elements
			val integrationTransformResult = integrationTransformer.compute(eChange, correspondenceModel);
        	if (integrationTransformResult.isIntegrationChange()) {
        		commands += integrationTransformResult.getCommands();
			} else {
				nonIntegratedEChanges += eChange;
			}
		}
		val resultingChange = if (nonIntegratedEChanges.isEmpty) {
			VitruviusChangeFactory.instance.createEmptyChange(change.getURI);
		} else if (nonIntegratedEChanges.size == 1) {
			VitruviusChangeFactory.instance.createConcreteChange(nonIntegratedEChanges.get(0), change.getURI);
		} else {
			val transactionalChange = VitruviusChangeFactory.instance.createCompositeTransactionalChange();
			for (eChange : nonIntegratedEChanges) {
				transactionalChange.addChange(VitruviusChangeFactory.instance.createConcreteChange(eChange, change.getURI));
			}
			transactionalChange;
		}
		
		return new ChangeProcessorResult(resultingChange, commands);
	}
	
}
