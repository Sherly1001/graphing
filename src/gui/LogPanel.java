package gui;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import graph.IntegratedGraph;

public class LogPanel extends JPanel {
	private IntegratedGraph graph;

	public LogPanel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LogPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public LogPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public LogPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public LogPanel(IntegratedGraph graph) {
		this.graph = graph;
	}
}
