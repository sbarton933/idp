package util;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;

/**Util class for GUI functionality 
 * 
 * @author Johannes
 *
 */
public class GUIUtil {
	
	public static Color LIGHT_GREY = new Color(null, 220,220,220);
	public static Color RED = new Color(null, 255,0,0);
	public static Color YELLOW = new Color(null, 255, 160,   0);
	public static Color GREEN = new Color(null, 0, 127,   0);
	
	public static Color NOT_APPLICABLE = RED;
	public static Color APPLICABLE = new Color(null, 0, 255,  0);
	
	/** changes background color of the given button*/
	public static void setButtonColor(final Button button, final Color color){
		Display.getDefault().syncExec(new Runnable() {
			@Override
			public void run() {
				if(!button.isDisposed())
					button.setBackground(color);
			}
		});
	}
	
	
}
