package functionality.listeners;

import static util.GUIUtil.APPLICABLE;
import static util.GUIUtil.NOT_APPLICABLE;
import static util.GUIUtil.setButtonColor;
import log.Logger;
import log.MessageType;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

import functionality.KoraSteps;

public class LoadButtonListener extends SelectionAdapterBase {

	/** Path of the csv-file */
	private String path;
	/** Name of csv-file */
	private String file;
	/** Plot all button */
	private Button thisButton;
	
	private String currentData;
	
	private String currentDevice;
	
	private int currentFrequency;
	
	/** Constructor */
	public LoadButtonListener(String path, String file, Button thisButton, 
			String currentData, String currentDevice,int currentFrequency){
		this.path = path;
		this.file = file;
		this.thisButton = thisButton;
		this.isWaiting = true;
		this.isStepFinished = false;
		this.currentData = currentData;
		this.currentDevice = currentDevice;
		this.currentFrequency = currentFrequency;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(isWaiting) {
			Logger.log(MessageType.WARNING, "Please select a file first!");
			return;
		}
			
			isStepFinished = KoraSteps.KoraStep1(path,file,currentData, currentDevice, currentFrequency);
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
	
	public void updateDeviceAndDataType(String device, String data){
		this.currentData = data;
		this.currentDevice = device;
	}
	
	public void updateFrequency(int frequency){
		this.currentFrequency = frequency;
	}
	
	public void updateDevice(String device){
		this.currentDevice = device;
	}
	
	public void updateDataType(String data){
		this.currentData = data;
	}
	
}
