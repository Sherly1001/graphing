package gui;

import java.io.FileNotFoundException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImportFile {
	public static String getUrl() throws FileNotFoundException {
		String url = null;
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("text file", "txt");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			url = chooser.getSelectedFile().getAbsolutePath();
		}
		if (url == null || url == "") {
			throw new FileNotFoundException("File path is empty.");
		}
		return url;
	}
}
