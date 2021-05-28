package gui;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;
import org.graphstream.graph.*;
import org.graphstream.ui.swing_viewer.*;
import org.graphstream.ui.view.Viewer;

import event.LogEvent;
import event.LogListener;
import graph.IntegratedGraph;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	private IntegratedGraph graph = new IntegratedGraph("Graphing", false, true);

	private Container ctn = getContentPane();
	private ControlPanel controlPanel = new ControlPanel(graph);
	private LogPanel logPanel = new LogPanel(graph);

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

		Viewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		viewer.enableAutoLayout();

		try {
			graph.loadFromFile(ImportFile.getUrl());
//			graph.findAllPath("2", "12");
		} catch (Exception e) {
			System.out.println(e);
		}

		for (Node n : graph) {
			n.setAttribute("ui.label", n.getId());
		}

		ctn.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		controlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "controler"));

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.weightx = 30;
		gbc.weighty = 70;
		ctn.add(controlPanel, gbc);

		ViewPanel view = (ViewPanel) viewer.addDefaultView(false);
		view.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "graph"));
		gbc.gridx = 0;
		gbc.weightx = 70;
		ctn.add(view, gbc);

		logPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "logs"));
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.weighty = 30;
		ctn.add(logPanel, gbc);

		setVisible(true);
		setSize(860, 640);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LogEvent.addLogListener(new LogListener() {
			@Override
			public void run(LogEvent e) {
				// TODO Auto-generated method stub
				if (e.cause == LogEvent.Cause.INFO) {
					System.out.println("INFO: " + e.message);
				} else if (e.cause == LogEvent.Cause.EXPORT_IMAGE) {
					try {
						graph.exportImg(view);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						System.out.println("Export image error " + e1.getMessage());
					}
				} else if (e.cause == LogEvent.Cause.FIND_PATH) {
					// graph.findAllPath("2","12");

				}
			}
		});
	}
}
