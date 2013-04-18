package functionality.listeners;

import log.Logger;
import log.MessageType;

import static util.GUIUtil.APPLICABLE;
import static util.GUIUtil.NOT_APPLICABLE;
import static util.GUIUtil.setButtonColor;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

import functionality.KoraSteps;

public class RawToCountsListener extends SelectionAdapterBase {
	
	/** Path of the csv-file */
	private String path;
	/** Name of csv-file */
	private String file;
	/** Plot all button */
	private Button thisButton;
	
	/** Constructor */
	public RawToCountsListener(String path, String file, Button thisButton){
		this.path = path;
		this.file = file;
		this.thisButton = thisButton;
		this.isWaiting = true;
		this.isStepFinished = false;
	}
	
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(isWaiting ){
			Logger.log(MessageType.WARNING, "Cannot calculate data to counts/METs. Raw data is needed!");
			return;
		}
		
		//Calculate Epoch data
		isStepFinished = KoraSteps.RawDataToEpoch(path,file);
		if(!isStepFinished)
			return;
	}
	
	/** updates the current path and file*/
	public void updatePathAndFile(String path, String file){
		this.path = path;
		this.file = file;
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

