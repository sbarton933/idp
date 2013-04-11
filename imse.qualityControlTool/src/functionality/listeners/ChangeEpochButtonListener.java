package functionality.listeners;

import static util.GUIUtil.APPLICABLE;
import static util.GUIUtil.NOT_APPLICABLE;
import static util.GUIUtil.setButtonColor;
import log.Logger;
import log.MessageType;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import functionality.KoraSteps;

/**SelectionAdapter for changing the epoch 
 * 
 * @author Johannes
 *
 */
public class ChangeEpochButtonListener extends SelectionAdapterBase {
	
	/** current/new epoch*/
	private int newEpoch;
	/** Change epoch button */
	private Button thisButton;

	/** Constructor */
	public ChangeEpochButtonListener(Button thisButton) {
		this.newEpoch = -1;
		this.isWaiting = true;
		
		this.thisButton = thisButton;
	}
	
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(isWaiting){
			Logger.log(MessageType.WARNING, "Cannot change epoch. Precalculation needed!");
			return;
		}
		
		int currentEpoch = KoraSteps.getInitialEpochPeriode();
		
		if(newEpoch%currentEpoch != 0){
			Logger.log(MessageType.ERROR, "Epoch periode must be a multiple of " + currentEpoch + "!");
			return;
		}
		
		KoraSteps.step3To4(newEpoch);
		
		
	}

	
	public void setEpochTime(int epoch){
		this.newEpoch = epoch;
	}


	@Override
	public boolean finished() {
		// this functionality is always finished
		// because change epoch is optional not obligatory
		return true;
	}


	@Override
	public void notifyWaiting() {
		isWaiting = false;
		setButtonColor(thisButton, APPLICABLE);
	}


	@Override
	public void reset() {
		isWaiting = true;
		setButtonColor(thisButton, NOT_APPLICABLE);
	}
}
