package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Button;

/**
 * Plot gui window
 * 
 * @author Johannes
 *
 */
public class PlotGUI extends Shell {
	public Text txtStartTime;
	public Label labelStartTime;
	public Label labelDuration;
	public Text txtDuration;
	private ScrolledComposite scrolledComposite;
	public Label canvas;
	public Button cutDataButton;

	/**
	 * Create the shell.
	 * @param display
	 */
	public PlotGUI(Display display) {
		super(display, SWT.SHELL_TRIM | SWT.RESIZE | SWT.ON_TOP);
		setLayout(new GridLayout(1, false));
		
		scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		canvas = new Label(scrolledComposite, SWT.NONE);
		scrolledComposite.setContent(canvas);
		scrolledComposite.setMinSize(canvas.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new GridLayout(5, false));
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		
		labelStartTime = new Label(composite, SWT.NONE);
		labelStartTime.setText("Start Time");
		
		txtStartTime = new Text(composite, SWT.BORDER);
		txtStartTime.setText("0");
		txtStartTime.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		labelDuration = new Label(composite, SWT.NONE);
		labelDuration.setText("Duration");
		
		txtDuration = new Text(composite, SWT.BORDER);
		txtDuration.setText("0");
		txtDuration.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		cutDataButton = new Button(composite, SWT.NONE);
		cutDataButton.setText("cut data");
		
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("First Plot");
		setBounds(0, 0, 450, 300);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
