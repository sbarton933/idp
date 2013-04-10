package functionality.listeners;

import static util.GUIUtil.APPLICABLE;
import static util.GUIUtil.NOT_APPLICABLE;
import static util.GUIUtil.setButtonColor;
import log.Logger;
import log.MessageType;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

import functionality.KoraSteps;

/** Selection Adapter for wearing time button
 * 
 * @author Johannes
 *
 */
public class WearingTimeButtonListener extends SelectionAdapterBase{

	/** wearing time button*/
	private Button thisButton;
	
	/** Constructor */
	public WearingTimeButtonListener(Button thisButton) {
		this.thisButton = thisButton;
		this.isWaiting = true;
		this.isStepFinished = false;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(isWaiting){
			Logger.log(MessageType.WARNING, "Cannot execute Wearing Time. Precalculation needed!");
			return;
		}
		
		isStepFinished = KoraSteps.KoraStep5();
		
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
