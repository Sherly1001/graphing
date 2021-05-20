package gui;

import org.graphstream.graph.*;
import graph.IntegratedGraph;

public class Main {
	private IntegratedGraph graph;

	public static void main(String[] args) {
		new Main(args);
	}

	public Main(String[] args) {
		System.setProperty("org.graphstream.ui", "swing");
		graph = new IntegratedGraph("Graphing", false, true);
		graph.setAttribute("ui.quality");
		graph.setAttribute("ui.antialias");
		graph.setAttribute("ui.stylesheet", "url('file://bin/gui/graph.css')");

		graph.display();

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
	}
}
