package functionality.listeners;

import org.eclipse.swt.events.SelectionAdapter;

import functionality.IDistributeCurrentStatus;

/**Base class for SelectionAdapter implementations 
 * 
 * @author Johannes
 *
 */
public abstract class SelectionAdapterBase extends SelectionAdapter
		implements
			IDistributeCurrentStatus {
	
	/** indicates if adapter functionality has been executed*/
	protected boolean isStepFinished;
	
	/** indicates if adapter is waiting */
	protected boolean isWaiting;

	@Override
	public abstract boolean finished();


	@Override
	public abstract void notifyWaiting();
	

	@Override
	public abstract void reset() ;

}
