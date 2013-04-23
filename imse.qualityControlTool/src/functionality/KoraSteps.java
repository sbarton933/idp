package functionality;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.mathworks.toolbox.javabuilder.MWException;
import com.mathworks.toolbox.javabuilder.MWNumericArray;

import functionality.backend.MainControlsBackend;
import functionality.backend.PlotGUIBackend;

import kora_v2.KORA_v1;
import log.Logger;
import log.MessageType;
import test.KORA_v2;

public class KoraSteps {

	/** TODO check for virtual function call error*/
	private static KORA_v1 matlabKoraFunctions=null;
	private static KORA_v2 matlabKoraFunctions2 = null;
	
	private static int initialEpochPeriode = -1;
	private static int initialFrequency = 1;
	
	public static boolean precalculation(String currentPath, String currentFile, String currentDevice){
		checkKoraFunction();
		checkKoraFunction2();
		// out array for Epoch time
		Object[] out;
		Object[] file_path = {currentPath,currentFile};
		
		// Find sensor name in loading data
		try {
			Object[] cD = matlabKoraFunctions2.FindSensorName(1, file_path);
			if (!cD[0].toString().equals("Could not found!!!")){
				currentDevice = cD[0].toString();
				MainControlsBackend.setCurrentDevice(currentDevice);
			}
			
		} catch (MWException e2) {
			Logger.log(MessageType.ERROR, "Unknown Device Type!");
			e2.printStackTrace();
		}
		
		String currentFrequency;
		//Find Sensor frequency
		try {
			Object[] cF = matlabKoraFunctions2.FindSensorFrequency(1, file_path);
			if (!cF[0].toString().equals("0")){
				currentFrequency = cF[0].toString();
				MainControlsBackend.setCurrentFrequency(currentFrequency);
			}
			
		} catch (MWException e2) {
			Logger.log(MessageType.ERROR, "Unknown Frequency!");
			e2.printStackTrace();
		}
		
		Logger.log(MessageType.NOTIFICATION, "Precalculations where successful. \n Check allocations and press 'Load'");
		return true;
	}
	
	/** step 1 - loading file*/
	public static boolean KoraStep1(String currentPath, String currentFile,
			String currentData, String currentDevice, int currentFrequency){
		
		checkKoraFunction();
		checkKoraFunction2();
		
		Object[] out;
		Object[] file_path = {currentPath,currentFile};
		/** 
		 * Outer switch for different data type.
		 * Needed, because for raw data measurement information about frequency is needed. 
		 * Therefore the input array is different.
		 */
		try{
		switch (currentData){
		
		// data type is in form of count/epoch measurement
		
		case "count/epoch measurement":
//			Object[] file_path = {currentPath,currentFile};
			MWNumericArray ma;
			/**
			 * Inner switch for different sensor type.
			 * Needed, because every sensor saves the data differently.
			 */
			try {
                switch (currentDevice) {
                case "ActiGraph GT3X":
                        out = matlabKoraFunctions.step1_loadFile(1, file_path);
                        ma = (MWNumericArray) out[0];
                        initialEpochPeriode = ma.getInt();
                        break;
                        
                case "ActiGraph GT3X+":
                	Logger.log(MessageType.NOTIFICATION, "No code implemented for GT3X+ count/epoch measurement");
                	break;
                case "GeneActive":
                	Logger.log(MessageType.NOTIFICATION, "No code implemented for GeneActive count/epoch measurement");
                	break;
                case "Somnowatch":
                	Logger.log(MessageType.NOTIFICATION, "No code implemented for Somnowatch count/epoch measurement");
                	break;
                case "Shimmer":
                	Logger.log(MessageType.NOTIFICATION, "No code implemented for Shimmer count/epoch measurement");
                	break;
                case "Default":
                	MainControlsBackend.enableDeviceCombo();
                	Logger.log(MessageType.NOTIFICATION, "Please choose correct device type and browse again!");
                	break;
                }
			} catch (MWException e1) {
                Logger.log(
                                MessageType.MATLAB_ERROR,
                                "Error while loading "
                                                + currentPath
                                                + currentFile
                                                + " into MATLAB routines! Kora Step1 incomplete!! \n Check device type and try again!");
                return false;
			}
			Logger.log(MessageType.NOTIFICATION, "Successfully loaded " + currentPath + currentFile + " into MATLAB routines. Kora Step1 complete!");
			return true;
			
		// data type is in form of raw data measurement
		case "raw data measurement":
			Object[] file_path_hz = {currentPath,currentFile,currentFrequency};
			switch (currentDevice){
			case "ActiGraph GT3X": 
				Logger.log(MessageType.NOTIFICATION, "No code implemented for GT3X raw data measurement");
				break;
			case "ActiGraph GT3X+":
				out = matlabKoraFunctions2.step1_loadFile_GT3xplus(3, file_path_hz);
				ma = (MWNumericArray) out[0];
				initialEpochPeriode = ma.getInt();
				break;
			case "GeneActive":
                out = matlabKoraFunctions2.step1_loadFile_GeneActive(1,file_path);
                ma = (MWNumericArray) out[0];
                initialEpochPeriode = ma.getInt();
                break;
			case "Somnowatch":
				Logger.log(MessageType.NOTIFICATION, "No code implemented for Somnowatch raw data measurement");
				break;
			case "Shimmer":
				Logger.log(MessageType.NOTIFICATION, "No code implemented for Shimmer raw data measurement");
				break;
			case "Default":
				MainControlsBackend.enableDeviceCombo();
				Logger.log(MessageType.NOTIFICATION, "Please choose correct device type and browse again!");
				break;
			}
		}
		} catch (MWException e1) {
            Logger.log(
                            MessageType.MATLAB_ERROR,
                            "Error while loading "
                                            + currentPath
                                            + currentFile
                                            + " into MATLAB routines! Kora Step1 incomplete!! \n Check device type and try again!");
            return false;
		}
		return false;
	}
	
	public static boolean RawDataToEpoch(String currentPath, String currentFile,
			String currentDevice){
		checkKoraFunction2();
		
		Object[] file_path = {currentPath, currentFile};
		
		try {
			matlabKoraFunctions2.calculateCounts(1, file_path);
		} catch (MWException e) {
			Logger.log(MessageType.MATLAB_ERROR, "Error while calculating epoch data out of: " + currentPath + currentFile);
			return false;
		} catch (Exception e) {
			Logger.log(MessageType.ERROR, "Unknown error calculating epoch data!");
			return false;
		}
		
		Logger.log(MessageType.NOTIFICATION, "Successfully calculated " + currentPath + currentFile +".");
		
		return true;
	}
	
	/** step 2 - plot*/
	public static boolean KoraStep2(String path, String file, String currentDataType) {
		checkKoraFunction();
		switch (currentDataType){
		case "count/epoch measurement":
			try {
				matlabKoraFunctions.step2_plotFile();
			} catch (MWException e) {
				Logger.log(MessageType.MATLAB_ERROR, "Error while plotting: " + path + file);
				return false;
			} catch (Exception e) {
				Logger.log(MessageType.ERROR, "Unknown error step2!");
				return false;
			}
			break;
		case "raw data measurement":
			try {
				matlabKoraFunctions.step2_plotFile();
			} catch (MWException e) {
				Logger.log(MessageType.MATLAB_ERROR, "Error while plotting: " + path + file);
				return false;
			} catch (Exception e) {
				Logger.log(MessageType.ERROR, "Unknown error step2!");
				return false;
			}
			break;
		}
		
		PlotGUIBackend shell = new PlotGUIBackend(Display.getDefault(), true);
		shell.open();
		
		Logger.log(MessageType.NOTIFICATION, "Successfully ploted " + path + file +". Kora Step2 complete!");
		
		return true;
	}
	
	/** step 3 - cut plot*/
	public static boolean KoraStep3() {
		checkKoraFunction();
		
		Object[] timeS = {Integer.toString(PlotGUIBackend.startingTime), Integer.toString(PlotGUIBackend.duration)};
		
		try {
			matlabKoraFunctions.step3_cutFile(timeS);
		} catch (MWException e) {
			Logger.log(MessageType.MATLAB_ERROR, "Error while cutting plot!");
			return false;
		} catch (Exception e) {
			Logger.log(MessageType.ERROR, "Unknown error in step3!");
			return false;
		}
		
		Shell shell = new PlotGUIBackend(Display.getDefault(), false);
		shell.open();
		
		Logger.log(MessageType.NOTIFICATION, "Successfully cut data. Kora Step3 complete!");
		
		return true;
	}
	
	/** changes the epoch */
	public static boolean step3To4(int newEpoch){
		checkKoraFunction();
		
		int oldEpoch = getInitialEpochPeriode();
		
		double[] epoch = {(double)newEpoch};
		
		try {
			matlabKoraFunctions.step3to4_changeEpoch(epoch);
		} catch (MWException e) {
			Logger.log(MessageType.MATLAB_ERROR, "Error while changing epoch!");
			return false;
		} catch (Exception e) {
			Logger.log(MessageType.ERROR, "Unknown error in step3to4!");
			return false;
		}
		
		Logger.log(MessageType.NOTIFICATION, "Successfully changed epoch from " + oldEpoch + " to " + newEpoch + "!");
		initialEpochPeriode = newEpoch;
		return true;
	}

	/** Analyses of kora data (according to algorithms from andre)*/
	public static boolean KoraStep4() {
		
		checkKoraFunction();
		
		try {
			matlabKoraFunctions.step4_analyse();
		} catch (MWException e) {
			Logger.log(MessageType.MATLAB_ERROR, "Error while performing data analyses!");
			return false;
		} catch (Exception e) {
			Logger.log(MessageType.ERROR, "Unknown error in step4!");
			return false;
		}
		Logger.log(MessageType.NOTIFICATION, "Successfully performed Kora data analyses (Andre's Algorithms)! Kora Step4 complete!");
		
		return true;
	}
	
	/** calculation of wearing time */
	public static boolean KoraStep5(){
		
		checkKoraFunction();
		
		try {
			matlabKoraFunctions.step5_wearingTime();
		} catch (MWException e) {
			Logger.log(MessageType.MATLAB_ERROR, "Error while performing Wearing Time calculation!");
			return false;
		}catch (Exception e) {
			Logger.log(MessageType.ERROR, "Unknown error in step5!");
			return false;
		}
		
		Logger.log(MessageType.NOTIFICATION, "Successfully performed Wearing Time calculation! Kora Step5 complete!");
		
		return true;
	}
	
	/** Analyses of kora data (according to algorithms from simon)*/
	public static boolean KoraStep6(){
		checkKoraFunction();
		
		try {
			matlabKoraFunctions.step6();
		} catch (MWException e) {
			Logger.log(MessageType.MATLAB_ERROR, "Error while performing data analyses!");
			return false;
		}catch (Exception e) {
			Logger.log(MessageType.ERROR, "Unknown error in step6!");
			return false;
		}
		
		Logger.log(MessageType.NOTIFICATION, "Successfully performed Kora data analyses (Simon's Algorithms)! Kora Step6 complete!");
		
		// test
		System.gc();
		
		return true;
		
	}
	
	public static int getInitialEpochPeriode() {
		return initialEpochPeriode;
	}
	
	public static int getInitialFrequency() {
		return initialFrequency;
	}
	
	
	/** Check if matlab functions are already initiated*/
	private static void checkKoraFunction() {
	
		if(matlabKoraFunctions!=null)
			matlabKoraFunctions.dispose();

		try {
			matlabKoraFunctions= new KORA_v1();
		} catch (MWException e) {
			Logger.log(MessageType.ERROR, "Error while loading Matlab classes! Cannot execute program!");
		}catch (Exception e) {
			Logger.log(MessageType.ERROR, "Unknown error in checkKoraFunction()");
			return;
		}
		
	}
	
	/** Check if matlab functions are already initiated*/
	private static void checkKoraFunction2() {
	
		if(matlabKoraFunctions2 !=null)
			matlabKoraFunctions2.dispose();

		try {
			matlabKoraFunctions2 = new KORA_v2();
		} catch (MWException e) {
			Logger.log(MessageType.ERROR, "Error while loading Matlab classes! Cannot execute program!");
		}catch (Exception e) {
			Logger.log(MessageType.ERROR, "Unknown error in checkKoraFunction2()");
			return;
		}
		
	}
	
	/** disposes existing matlab instance*/
	public static void dispose(){
		if(matlabKoraFunctions!=null)
			matlabKoraFunctions.dispose();
		if(matlabKoraFunctions2!=null)
			matlabKoraFunctions2.dispose();
	}
}
