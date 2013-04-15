package gui;
import log.Logger;
import log.MessageType;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import functionality.backend.MainControlsBackend;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Button;

/**
 * Main Application Window including menu bar.
 * 
 * @author Johannes
 *
 */
public class MainWindow {

	private Shell shell;
	private StyledText text;
	
	/** Launch the application. */
	public static void main(String[] args) {
		try {
			MainWindow window = new MainWindow();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Open the window.*/
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
		System.gc();
		System.exit(0);
	}

	/** Create contents of the window. Subclasses must call super!*/
	protected void createContents() {
		shell = new Shell();
		shell.setSize(800, 600);
		shell.setText("Quality Control Tool");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		// Menu Bar 
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		
		MenuItem menuEntry = new MenuItem(menuBar, SWT.CASCADE);
		menuEntry.setText("New");
		
		Menu menu_1 = new Menu(menuEntry);
		menuEntry.setMenu(menu_1);
		
		// Main Controls beneath the menu bar
		Composite composite = new MainControlsBackend(shell, SWT.NONE);
		
		
		// Logger
		Label logLabel = new Label(composite, SWT.NONE);
		logLabel.setText("Log");
		
		text = new StyledText(composite, SWT.BORDER | SWT.READ_ONLY | 
				SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		@SuppressWarnings("unused")
		Logger logger = new Logger(text);
		Logger.log(MessageType.NOTIFICATION, "Logging initiated");
	}
}
