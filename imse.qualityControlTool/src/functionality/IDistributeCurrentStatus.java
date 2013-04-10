package functionality;

/**Interface for status distribution
 * 
 * @author Johannes
 *
 */
public interface IDistributeCurrentStatus {

	/** Returns true if implementing class has already finished */
	public boolean finished();
	
	/** Notifies implementing class */
	public void notifyWaiting();
	
	/** Reset */
	public void reset();
}
