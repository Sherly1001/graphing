package gui;

import java.awt.LayoutManager;

import javax.swing.JPanel;

import graph.IntegratedGraph;

public class ControlPanel extends JPanel {
	private IntegratedGraph graph;

	public ControlPanel() {
		// TODO Auto-generated constructor stub
	}

	public ControlPanel(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public ControlPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public ControlPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public ControlPanel(IntegratedGraph graph) {
		this.graph = graph;
	}

}
