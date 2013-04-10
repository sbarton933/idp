package log;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import util.GUIUtil;

/**Class to display all log messages
 * 
 * @author Johannes
 *
 */
public class Logger {
	
	/** Text field to display log messages*/
	private static StyledText textField;
	
	/** Style ranges for text colouring*/
	private static ArrayList<StyleRange> styles = new ArrayList<StyleRange>();
	
	/** Constructor */
	public Logger(StyledText textField){
		Logger.textField = textField;
	}
	
	/** Create a log entry*/
	public static boolean log(MessageType type, String msg){
		// check if text field is instantiated yet
		if(textField == null)
			return false;
		
		// get time stamp
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		Timestamp time = new Timestamp(System.currentTimeMillis());
		String t = sdf.format(time);
		
		String newText = t + "\t" + type.name() + "\t-\t" + msg;
		textField.setText(newText + "\n" + textField.getText());
		
		
		// colour codes for different MessageTypes
		StyleRange styleRange = new StyleRange();
		styleRange.start = newText.indexOf(type.name());
		styleRange.length = type.name().length();
		
		
		switch(type){
			case ERROR:
			case MATLAB_ERROR:
				styleRange.foreground = GUIUtil.RED;
				break;				
			case WARNING:
				styleRange.foreground = GUIUtil.YELLOW;
				break;
			case NOTIFICATION:
				styleRange.foreground = GUIUtil.GREEN;
				break;
			default: 
				break;
				
		}
		
		// modify last style ranges
		for(StyleRange sr : styles) {
			sr.start += newText.length()+1;
		}
		
		styles.add(0, styleRange);
		
		StyleRange[] ranges = styles.toArray(new StyleRange[styles.size()]);
		
		textField.setStyleRanges(ranges);
		
		return true;
	}
	
	
	
}
