package functionality;

import java.util.HashMap;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

/** Distribution Thread to handle notifications between Buttons respectively their 
 *  Selection Listeners
 * 
 * @author Johannes
 *
 */
public class StatusDistributionManager implements Runnable{

	/** Hashmap containing all listeners(key) which wait for result of another listener*/
	private HashMap<IDistributeCurrentStatus, IDistributeCurrentStatus> dependency;
	
	/** browse button - initial action of all functionality*/
	@SuppressWarnings("unused")
	private Button browseButton;
	
	/** Constructor */
	public StatusDistributionManager(Button browseButton) {
		dependency = new HashMap<IDistributeCurrentStatus, IDistributeCurrentStatus>();
		this.browseButton = browseButton;
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				for(IDistributeCurrentStatus m : dependency.keySet()){
					IDistributeCurrentStatus waitingFor = dependency.get(m);
					m.reset();
					if(waitingFor != null)
						waitingFor.reset();
				}
			}
		});
		
		// start distribution
		distribute();
	}
	
	/** register for notifications */
	public void register(IDistributeCurrentStatus me, IDistributeCurrentStatus waitingFor) {
		dependency.put(me, waitingFor);
	}
	
	/** distribute status*/
	private void distribute(){
		for(IDistributeCurrentStatus m : dependency.keySet()){
			IDistributeCurrentStatus waitingFor = dependency.get(m);
			// not every listeners is waiting for results from others
			if(waitingFor != null){
				// if listener is finished notify dependent listener
				if(waitingFor.finished()){
					m.notifyWaiting();
				}
			}
		}
	}

	@Override
	public void run() {
		while(true){
			distribute();
		}
	}
	
	
}
