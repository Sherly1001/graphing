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
	private int hasPath = 0;
	private int start = 0;
	private int end = 0;
	private static int[] D;
	private static int[] L;

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
			String[] dataNode = data.split(" ");
			String startnode = String.valueOf(dataNode[0]);
			for (int i = 1; i < dataNode.length; i++) {
				addEdge(startnode + dataNode[i], startnode, dataNode[i], true);
			}

		}

		myReader.close();
	}

	public List<List<Edge>> findAllPath(String source, String destination) {
		List<List<Edge>> paths = new ArrayList<List<Edge>>();
		// TODO
		int[][] arr = new int[getNodeCount()][getNodeCount()];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				arr[i][j] = 0;
			}

		}
		nodes().forEach(s -> {
			System.out.print(s.getId() + " ");
			System.out.println(s.getIndex());
		});
		edges().forEach(s -> {
//			System.out.print(s.getSourceNode().getId());
			// System.out.print(" "+s.getTargetNode() + " ");
//			String getedge = s.getId();
			// arr[Integer.parseInt(s.getSourceNode().getId())][Integer.parseInt(s.getTargetNode().getId())]
			// = 1;
			arr[s.getSourceNode().getIndex()][s.getTargetNode().getIndex()] = 1;
		});

//		for (int i = 0; i < arr.length; i++) {
//			for (int j = 0; j < arr.length; j++) {
//				System.out.print(arr[i][j]);
//			}
//			System.out.println();
//		}
//		int start = 0;
//		int end = 0;
		nodes().forEach(s -> {
			if (s.getId().equals(source)) {
				start = s.getIndex();
			}
			if (s.getId().equals(destination)) {
				end = s.getIndex();
			}
		});
		D = new int[getNodeCount()];
		L = new int[getNodeCount()];
		for (int i = 0; i < getNodeCount(); i++) {
			D[i] = 0;
			L[i] = 0;
		}
		D[start] = 1;
		L[0] = start;
		checkPath(1, end, arr);
		if (hasPath == 0) {
			System.out.println("No Path From " + (start + 1) + " to " + (end + 1));
		}
		return paths;
	}

	public void checkPath(int number_edge, int end, int[][] arr) {
		if (L[number_edge - 1] == end) {
			hasPath++;
			System.out.println("Path:");
			System.out.print(getNode(L[0]).getId());
			for (int i = 1; i < number_edge; ++i)
				System.out.print("->" + getNode(L[i]).getId());
			System.out.println();
		} else {
			for (int i = 0; i < getNodeCount(); ++i) {
				if (arr[L[number_edge - 1]][i] != 0 && D[i] == 0) {
					L[number_edge] = i;
					D[i] = 1;
					checkPath(number_edge + 1, end, arr);
					L[number_edge] = 0;
					D[i] = 0;
				}
			}
		}
	}

	public void toImage(String name) {
		// TODO

	}

}
