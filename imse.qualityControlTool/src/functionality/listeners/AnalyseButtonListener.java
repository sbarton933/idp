package functionality.listeners;

import static util.GUIUtil.APPLICABLE;
import static util.GUIUtil.NOT_APPLICABLE;
import static util.GUIUtil.setButtonColor;
import log.Logger;
import log.MessageType;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import functionality.KoraSteps;

/**SelectionAdapter for analyzing data according to Andre's algorithms 
 * 
 * @author Joe
 *
 */
public class AnalyseButtonListener extends SelectionAdapterBase{

	/** Analyze button*/
	private Button thisButton;
	
	/** Constructor */
	public AnalyseButtonListener(Button thisButton) {
		this.thisButton = thisButton;
		this.isStepFinished = false;
		this.isWaiting = true;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(isWaiting ){
			Logger.log(MessageType.WARNING, "Cannot execute analyzation. Precalculation needed!");
			return;
		}
		
		Logger.log(MessageType.NOTIFICATION, "Analyzing data ...");
		isStepFinished = KoraSteps.KoraStep4();
	}
	

	@Override
	public boolean finished() {
		return isStepFinished;
	}

	@Override
	public void notifyWaiting() {
		isWaiting = false;
		setButtonColor(thisButton, APPLICABLE);
	}

	@Override
	public void reset() {
		isStepFinished = false;
		isWaiting = true;
		setButtonColor(thisButton, NOT_APPLICABLE);
	}
	
}
