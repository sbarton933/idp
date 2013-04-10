package gui;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

/**Main Controls below menu bar.
 * Only GUI elements. Functionality is implemented in backend.
 * 
 * @author Johannes
 *
 */
public class MainControls extends Composite {

	/** Form Toolkit */
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	
	protected Table table;
	protected Combo browseCombo;
	protected Button browseButton;
	protected Combo deviceCombo;
	protected Combo dataCombo;
	protected Scale epochScale;
	protected Text epochText;
	protected Label csvPreviewLabel;
	private Composite composite_1;
	public Button btnPlotAllData;
	public Button btnPlotRelevantData;
	public Button btnAnalyseData;
	public Button btnChangeEpoch;
	public Button btnAnalyseDataSimon;
	public Button btnWearingTime;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public MainControls(Composite parent, int style) {
		super(parent, style);
		
		
		
		setLayout(new GridLayout(1, false));
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite.setLayout(new GridLayout(4, false));
		
		Label browseLabel = new Label(composite, SWT.NONE);
		browseLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		browseLabel.setText("File (*.csv)");
		
		browseCombo = new Combo(composite, SWT.NONE);
		GridData gd_browseCombo = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_browseCombo.widthHint = 278;
		browseCombo.setLayoutData(gd_browseCombo);
		
		browseButton = new Button(composite, SWT.NONE);
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		browseButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		browseButton.setText("Browse");
		browseButton.setBackground(new Color(null, 0,255, 0));
		new Label(composite, SWT.NONE);
		
		Label deviceLabel = new Label(composite, SWT.NONE);
		deviceLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		deviceLabel.setText("Device");
		
		deviceCombo = new Combo(composite, SWT.READ_ONLY);
		deviceCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label dataLabel = new Label(composite, SWT.NONE);
		dataLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		dataLabel.setText("Data type");
		
		dataCombo = new Combo(composite, SWT.READ_ONLY);
		dataCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		
		Label EpochLabel = new Label(composite, SWT.NONE);
		EpochLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		EpochLabel.setText("Epoch time");
		
		epochScale = new Scale(composite, SWT.NONE);
		epochScale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		epochText = new Text(composite, SWT.BORDER | SWT.RIGHT);
		epochText.setText("-1");
		epochText.setEditable(false);
		epochText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		Label labelSek = new Label(composite, SWT.NONE);
		labelSek.setText("s");
		
		csvPreviewLabel = new Label(this, SWT.NONE);
		GridData gd_csvPreviewLabel = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_csvPreviewLabel.minimumWidth = 100;
		csvPreviewLabel.setLayoutData(gd_csvPreviewLabel);
		csvPreviewLabel.setText("*.csv preview");
		
	
		table = formToolkit.createTable(this, SWT.V_SCROLL | SWT.SINGLE);
		GridData gd_table = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_table.heightHint = 200;
		gd_table.minimumHeight = 100;
		table.setLayoutData(gd_table);
		formToolkit.paintBordersFor(table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));
		GridData gd_composite_1 = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_composite_1.heightHint = 48;
		composite_1.setLayoutData(gd_composite_1);
		formToolkit.adapt(composite_1);
		formToolkit.paintBordersFor(composite_1);
		
		btnPlotAllData = new Button(composite_1, SWT.NONE);
		btnPlotAllData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		formToolkit.adapt(btnPlotAllData, true, true);
		btnPlotAllData.setText("Plot All Data + Cut");
		btnPlotAllData.setBackground(new Color(null, 255, 0, 0));
		
		btnPlotRelevantData = new Button(composite_1, SWT.NONE);
		btnPlotRelevantData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		formToolkit.adapt(btnPlotRelevantData, true, true);
		btnPlotRelevantData.setText("Plot Relevant Data");
		btnPlotRelevantData.setBackground(new Color(null, 255, 0, 0));
		
		btnChangeEpoch = new Button(composite_1, SWT.NONE);
		formToolkit.adapt(btnChangeEpoch, true, true);
		btnChangeEpoch.setText("Change Epoch");
		btnChangeEpoch.setBackground(new Color(null, 255, 0, 0));
		
		btnWearingTime = new Button(composite_1, SWT.NONE);
		btnWearingTime.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		formToolkit.adapt(btnWearingTime, true, true);
		btnWearingTime.setText("Wearing Time");
		btnWearingTime.setBackground(new Color(null, 255, 0, 0));
		
		btnAnalyseData = new Button(composite_1, SWT.NONE);
		formToolkit.adapt(btnAnalyseData, true, true);
		btnAnalyseData.setText("Analyze Data (Andre)");
		btnAnalyseData.setBackground(new Color(null, 255, 0, 0));
		
		btnAnalyseDataSimon = new Button(composite_1, SWT.NONE);
		btnAnalyseDataSimon.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		formToolkit.adapt(btnAnalyseDataSimon, true, true);
		btnAnalyseDataSimon.setText("Analyze Data (Simon)");
		btnAnalyseDataSimon.setBackground(new Color(null, 255, 0, 0));
	
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
