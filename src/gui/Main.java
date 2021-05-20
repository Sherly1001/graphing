package gui;

import java.awt.*;
import javax.swing.*;
import org.graphstream.graph.*;
import org.graphstream.ui.swing_viewer.*;
import org.graphstream.ui.view.Viewer;

import graph.IntegratedGraph;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	private IntegratedGraph graph = new IntegratedGraph("Graphing", false, true);

	private Container ctn = getContentPane();
	private JPanel controlPanel = new ControlPanel(graph);

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main();
			}
		});
	}

	public Main() {
		System.setProperty("org.graphstream.ui", "swing");
		System.setProperty("awt.useSystemAAFontSettings", "on");
		System.setProperty("swing.aatext", "true");

		graph.setAttribute("ui.quality");
		graph.setAttribute("ui.antialias");
		graph.setAttribute("ui.stylesheet", "url('file://bin/gui/graph.css')");

		Viewer viewer = graph.display();

		for (int i = 0; i < 10; ++i) {
			for (int j = 0; j < 10; ++j) {
				if (i == j) {
					continue;
				}
				String ida = Character.toString(i + 65);
				String idb = Character.toString(j + 65);
				graph.addEdge(ida + idb, ida, idb);
			}
		}

		for (Node n : graph) {
			n.setAttribute("ui.label", n.getId());
		}

		ctn.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		controlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "controler"));

		gbc.gridx = gbc.gridy = 0;
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = 30;
		gbc.weighty = 100;
		ctn.add(controlPanel, gbc);

		ViewPanel view = (ViewPanel) viewer.getDefaultView();
		view.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "graph"));

		gbc.gridx = 1;
		gbc.weightx = 70;
		ctn.add(view, gbc);

		setVisible(true);
		setSize(860, 640);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
