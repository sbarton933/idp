package functionality.backend;

import static util.FunctionalityUtil.intToLetter;
import static util.FunctionalityUtil.setupIntToLetterMap;
import static util.GUIUtil.LIGHT_GREY;
import functionality.KoraSteps;
import functionality.StatusDistributionManager;
import functionality.listeners.AnalyseButtonListener;
import functionality.listeners.AnalyseSimonButtonListener;
import functionality.listeners.ChangeEpochButtonListener;
import functionality.listeners.PlotAllButtonListener;
import functionality.listeners.PlotRelevantButtonListener;
import functionality.listeners.RawToCountsListener;
import functionality.listeners.SelectionAdapterBase;
import functionality.listeners.WearingTimeButtonListener;
import gui.MainControls;

import java.io.File;
import java.util.ArrayList;

import log.Logger;
import log.MessageType;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import util.CsvFileLoader;
import org.eclipse.wb.swt.SWTResourceManager;

/**Functionality of {@link MainControls} is implemented here.
 * 
 * @author Johannes
 * 
 *
 */
public class MainControlsBackend extends MainControls {

	/** possible devices */
	private String[] devices = {"ActiGraph GT3X","ActiGraph GT3X+","GeneActive", "Somnowatch", "Shimmer"};
	/** possible data measurements*/
	private String[] data = {"count/epoch measurement", "raw data measurement"};
	/** The currently used csv File loader*/
	private CsvFileLoader csvFile;
	/** current File name*/
	public String currentFile;
	/** current File path*/
	public String currentPath;
	/** The plot button Listener*/
	private PlotAllButtonListener plotAllListener;
	/** The plot button Listener*/
	private RawToCountsListener rawToCountsListener;
	/** Listener for epoch changing*/
	private ChangeEpochButtonListener changeEpochButtonListener;
	/** currently chosen device*/
	public static String currentDevice = "Default";
	/** currently chosen data type*/
	public String currentData = "count/epoch measurement";

	
	/**Constructor */
	public MainControlsBackend(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		frequenzyText.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		frequenzyScale.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		setupFunctionality();
	}

	/** Sets up functionality of all gui elements*/
	private void setupFunctionality() {
		// ----------------------------------------------------------------------------
		// create distribution manager
		StatusDistributionManager distributionManager = new StatusDistributionManager(browseButton);
		// ----------------------------------------------------------------------------
		// device selection
		deviceCombo.setItems(devices);
		deviceCombo.select(0);
		DeviceListener deviceListener = new DeviceListener();
		deviceCombo.addSelectionListener(deviceListener);
		distributionManager.register(deviceListener, null);
		deviceCombo.setEnabled(false);
		// ----------------------------------------------------------------------------
		// data type selection
		dataCombo.setItems(data);
		dataCombo.select(0);
		DataListener dataListener = new DataListener();
		dataCombo.addSelectionListener(dataListener);
		distributionManager.register(dataListener, null);
		// ----------------------------------------------------------------------------
		// epoch periode
		epochScale.addSelectionListener(new EpochScaleSelectionListener());
		// ----------------------------------------------------------------------------
		// frequency
		frequenzyScale.addSelectionListener(new FrequenzyScaleSelectionListener());
		frequenzyScale.setEnabled(false);
		// ----------------------------------------------------------------------------
		// browse functionality
		BrowseButtonListener browseListener = new BrowseButtonListener();
		browseButton.addSelectionListener(browseListener);
		distributionManager.register(browseListener, null);
		// ----------------------------------------------------------------------------
		// Calculate epoch data from raw data
		rawToCountsListener = new RawToCountsListener(currentPath, currentFile, btnRawToCounts);
		distributionManager.register(rawToCountsListener, browseListener);
		btnRawToCounts.addSelectionListener(rawToCountsListener);
		// ----------------------------------------------------------------------------
		// Plot all data + cut functionality
		plotAllListener = new PlotAllButtonListener(currentPath, currentFile, btnPlotAllData, currentData);
		distributionManager.register(plotAllListener, browseListener);
		btnPlotAllData.addSelectionListener(plotAllListener);
		// ----------------------------------------------------------------------------
		// Plot relevant data
		PlotRelevantButtonListener plotRelevantListener = new PlotRelevantButtonListener(btnPlotRelevantData);
		btnPlotRelevantData.addSelectionListener(plotRelevantListener);
		distributionManager.register(plotRelevantListener, plotAllListener);
		// ----------------------------------------------------------------------------
		// Change Epoch functionality
		changeEpochButtonListener = new ChangeEpochButtonListener(btnChangeEpoch);
		btnChangeEpoch.addSelectionListener(changeEpochButtonListener);
		distributionManager.register(changeEpochButtonListener, plotRelevantListener);
		
		// ----------------------------------------------------------------------------
		// wearing time functionality
		WearingTimeButtonListener wearingTimeButtonListener = new WearingTimeButtonListener(btnWearingTime);
		btnWearingTime.addSelectionListener(wearingTimeButtonListener);
		distributionManager.register(wearingTimeButtonListener, plotRelevantListener);
		// ----------------------------------------------------------------------------
		// Analyse btn functionality (andre's algorithms)
		AnalyseButtonListener analyseListener = new AnalyseButtonListener(btnAnalyseData);
		btnAnalyseData.addSelectionListener(analyseListener);
		distributionManager.register(analyseListener, plotRelevantListener);
		// ----------------------------------------------------------------------------
		// Analyse btn functionality (simon's algorithms)
		AnalyseSimonButtonListener analyseSimonButtonListener = new AnalyseSimonButtonListener(btnAnalyseDataSimon);
		btnAnalyseDataSimon.addSelectionListener(analyseSimonButtonListener);
		distributionManager.register(analyseSimonButtonListener, wearingTimeButtonListener);
		// ----------------------------------------------------------------------------
		// csv preview
		// set up util map
		setupIntToLetterMap();
		// ----------------------------------------------------------------------------
		
	
		final Thread distributionThread = new Thread(distributionManager);
		distributionThread.setName("DistributionManager");
		distributionThread.start();
		
		addDisposeListener(new DisposeListener() {
			
			@Override
			public void widgetDisposed(DisposeEvent arg0) {
				distributionThread.interrupt();
				KoraSteps.dispose();
			}
		});
	}
	

	/** Selection Listener for epoch scale*/
	private class EpochScaleSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			Scale s = (Scale)e.getSource();
			int current = s.getSelection();
			epochText.setText(current + "");
			s.setToolTipText(current + "");
			changeEpochButtonListener.setEpochTime(current);
			
			int currentEpoch = KoraSteps.getInitialEpochPeriode();
			epochScale.setIncrement(currentEpoch);
			epochScale.setPageIncrement(currentEpoch);
			
		}
	}
	
	/** Selection Listener for frequenzy scale*/
	private class FrequenzyScaleSelectionListener extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e){
			Scale s = (Scale)e.getSource();
			int current = s.getSelection();
			frequenzyText.setText(current + "");
			s.setToolTipText(current + "");
			
//			int currentFrequenzy = s
			
		}
	}
	
	/** Selection listener for device */
	private class DeviceListener extends SelectionAdapterBase{
		public DeviceListener() {
			this.isStepFinished = false;
		}

		@Override
		public boolean finished() {
			return isStepFinished;
		}
	
		@Override
		public void notifyWaiting() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void reset() {
			isStepFinished = false;
			
		}
		@Override
		public void widgetSelected(SelectionEvent event) {
		currentDevice = deviceCombo.getText();
		
		}
	}
	
	/** Selection listener for data */
	private class DataListener extends SelectionAdapterBase{
		public DataListener() {
			this.isStepFinished = false;
		}

		@Override
		public boolean finished() {
			return isStepFinished;
		}
	
		@Override
		public void notifyWaiting() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void reset() {
			isStepFinished = false;
			
		}
		@Override
		public void widgetSelected(SelectionEvent event) {
		currentData = dataCombo.getText();
		
		if (currentData.equals("raw data measurement")){
			frequenzyScale.setEnabled(true);
			Logger.log(MessageType.NOTIFICATION, "Please adapt frequency");
			return;
		}
		
		if (currentData.equals("count/epoch measurement")){
			frequenzyScale.setEnabled(false);
			return;
		}
		
		}
	}
	
	/** Selection listener for browse button */
	private class BrowseButtonListener extends SelectionAdapterBase{
		
		/** Constructor */
		public BrowseButtonListener() {
			this.isStepFinished = false;
		}
		
		@Override
		public void widgetSelected(SelectionEvent event) {
			// open FileDialog; select only csv files; display them in browse combo
			FileDialog dialog = new FileDialog(new Shell());
			String[] filter = {"*.csv"};
			dialog.setFilterExtensions(filter);
			dialog.open();
			currentPath = dialog.getFilterPath()+"\\";
			currentFile = dialog.getFileName();
			String absolutePath = currentPath+currentFile;
			
			// check selected file
			if(absolutePath.length()	< 5 || !absolutePath.contains(".csv")) {
				Logger.log(MessageType.WARNING, "No file was selected!");
				currentPath = "";
				currentFile = "";
				return;
			}
			
			// add file to combo box
			browseCombo.add(absolutePath,0);
			browseCombo.select(0);
			
			// load file
			csvFile = new CsvFileLoader(new File(absolutePath));
			boolean success = csvFile.load();
			setupCsvTable(csvFile);
			// update plot listener if file loading was successful
			if(success){
				plotAllListener.updatePathAndFile(currentPath, currentFile);
			}
			
			isStepFinished = KoraSteps.KoraStep1(currentPath, currentFile, currentDevice);
			
			deviceCombo.setText(currentDevice);
			
			// update Epoch periode
			int currentEpoch = KoraSteps.getInitialEpochPeriode();
			epochScale.setMinimum(currentEpoch);
			// maximum epoch time is 60s
			int maxEpoch = 60;
			epochScale.setMaximum(maxEpoch);
			epochScale.setIncrement(currentEpoch);
			epochScale.setPageIncrement(currentEpoch);
			epochText.setText(currentEpoch + "");
			changeEpochButtonListener.setEpochTime(currentEpoch);
			
		}
		
		
		@Override
		public boolean finished() {
			return isStepFinished;
		}

		@Override
		public void notifyWaiting() {
			// nothing to do
			// initial function
		}

		@Override
		public void reset() {
			isStepFinished = false;
			
		}
		
	
	}

	/** Displays the csv file */
	private void setupCsvTable(CsvFileLoader csvFile){
		table.removeAll();
		
		// first column for lineNumber
		TableColumn firstCol = new TableColumn(table, SWT.NONE);
		firstCol.setWidth(30);
		firstCol.setText("   ");

		// set up other columns
		for(int i=0; i<csvFile.getMaxLineSize(); ++i){
			TableColumn col = new TableColumn(table, SWT.NONE);
			col.setWidth(75);
			col.setText(intToLetter.get(i));
		}
		
		int lineNr = 0;
		for(ArrayList<String> line : csvFile.getCsvTable()) {
			
			
			TableItem item = new TableItem(table, SWT.READ_ONLY);
			for(int i=0; i<line.size(); ++i) {
				// set line Number
				if(i==0){
					item.setText(i, lineNr+++"");
					item.setBackground(i, LIGHT_GREY);
				}
				
				// i+1 because first column is for line number
				item.setText(i+1, line.get(i));
				
			}
			
		}
		
		// set label
		csvPreviewLabel.setText("Preview of " + csvFile.getFile().getName() + "  (line 0 - " + CsvFileLoader.MAX_LINES_TO_READ +")");
		table.redraw();
		

		
	}

	public static void setCurrentDevice(String currentDevice2) {
		currentDevice = currentDevice2;
		
	}

	public static void enableDeviceCombo() {
		deviceCombo.setEnabled(true);
		
	}
}
