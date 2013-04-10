package functionality.backend;


import log.Logger;
import log.MessageType;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;

import gui.PlotGUI;

/**Functionality of {@link PlotGUI} 
 * 
 * @author Joe
 *
 */
public class PlotGUIBackend extends PlotGUI {

	/** Path to {@link #FIRST_IMAGE}*/ 
	private static String FIRST_IMAGE = "KoraFirstPlot.png";
	/** Path to {@link #SECOND_IMAGE}*/
	private static String SECOND_IMAGE = "KoraSecondPlot.png";
	/** Starting time*/
	public static int startingTime;
	/** duration */
	public static int duration;
	/** isFirst = true ? plot all data : plot relevant data*/
	private boolean isFirst;
	
	/** Constructor */
	public PlotGUIBackend(Display display, boolean isFirst) {
		super(display);
		this.isFirst = isFirst;
		createContents();
	}

	@Override
	protected void createContents() {
		super.createContents();
		
		Image image;
		
		// plot all data
		if(isFirst) {
			image = new Image(null, FIRST_IMAGE);
			setText("First Plot");
			txtStartTime.setEditable(true);
			txtDuration.setEditable(true);
			
			cutDataButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					String start = txtStartTime.getText();
					String dur = txtDuration.getText();
					
					// check if start and end are integers and > 0
					int s, d;
					try {
						s = Integer.parseInt(start);
						d = Integer.parseInt(dur);
					} catch (NumberFormatException e1) {
						Logger.log(MessageType.ERROR, "Start and end time have to be integer values");
						return;
					}
					if( s < 0 || d < 0){
						Logger.log(MessageType.ERROR, "Start and end time have to be > 0");
						return;
					}
					startingTime = s;
					duration = d;
					
					close();
				}
			});
		}
		// plot relevant data
		else {
			image = new Image(null, SECOND_IMAGE);
			setText("Second Plot");
			txtStartTime.setVisible(false);
			txtDuration.setVisible(false);
			txtDuration.setEditable(false);
			labelDuration.setVisible(false);
			labelStartTime.setVisible(false);
			txtStartTime.setEditable(false);
			cutDataButton.setVisible(false);
			cutDataButton.setEnabled(false);
		}
		
		Rectangle imgBounds = image.getBounds();
		
		Display display = Display.getDefault();
		Rectangle dispBound = display.getBounds();
		Image scaledImage;
		
		if(imgBounds.height >= dispBound.height){
			scaledImage = new Image(display, image.getImageData().scaledTo((int)(imgBounds.width*0.75),(int)(imgBounds.height*0.75)));
		}else{
			scaledImage = image;
		}
		
		this.setSize(scaledImage.getBounds().width, scaledImage.getBounds().height + 100);
		
		canvas.setImage(scaledImage);
		
	}

}
