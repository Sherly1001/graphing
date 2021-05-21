package graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.lang.reflect.Array;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.Stack;

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

	public void loadFromFile(String filePath) throws FileNotFoundException {
		// TODO
		File myObj = new File(filePath);
		Scanner myReader = new Scanner(myObj);
		while (myReader.hasNextLine()) {
			String data = myReader.nextLine();
			String startnode = String.valueOf(data.charAt(0));
			for (int i = 2; i < data.length(); i++) {
				if (data.charAt(i) != ' ') {
					String nextnode = String.valueOf(data.charAt(i));
					addEdge(startnode + nextnode, startnode, nextnode, true);
				}
			}
		}
		myReader.close();
	}

	public List<List<Edge>> findAllPath(Node source, Node destination) {
		List<List<Edge>> paths = new ArrayList<List<Edge>>();
		// TODO
		int[][] arr = new int[getNodeCount()][getNodeCount()];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				arr[i][j] = 0;
			}

		}
		edges().forEach(s -> {
			String getedge = s.getId();
			arr[Integer.parseInt(String.valueOf(getedge.charAt(0))) - 1][Integer
					.parseInt(String.valueOf(getedge.charAt(1))) - 1] = 1;
		});
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				System.out.print(arr[i][j]);
			}
			System.out.println();
		}
		return paths;
	}

	public void toImage(String name) {
		// TODO

	}

}
