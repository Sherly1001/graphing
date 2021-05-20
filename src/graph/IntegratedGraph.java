package graph;

import java.util.List;
import java.util.ArrayList;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class IntegratedGraph extends SingleGraph {

	public IntegratedGraph(String id, boolean strictChecking, boolean autoCreate, int initialNodeCapacity,
			int initialEdgeCapacity) {
		super(id, strictChecking, autoCreate, initialNodeCapacity, initialEdgeCapacity);
		// TODO Auto-generated constructor stub
	}

	public IntegratedGraph(String id, boolean strictChecking, boolean autoCreate) {
		super(id, strictChecking, autoCreate);
		// TODO Auto-generated constructor stub
	}

	public IntegratedGraph(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public void loadFromFile(String filePath) {
		// TODO

	}

	public List<List<Edge>> findAllPath(Node source, Node destination) {
		List<List<Edge>> paths = new ArrayList<List<Edge>>();
		// TODO

		return paths;
	}

	public void toImage(String name) {
		// TODO

	}

}
