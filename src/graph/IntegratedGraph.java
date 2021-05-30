package graph;

import java.util.List;
import java.util.ArrayList;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

import event.LogEvent;

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

		clear();
		setAttribute("ui.quality");
		setAttribute("ui.antialias");
		setAttribute("ui.stylesheet", "url('file://bin/gui/graph.css')");

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
		edges().forEach(s -> {
			arr[s.getSourceNode().getIndex()][s.getTargetNode().getIndex()] = 1;
		});

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
		checkPath(1, end, arr, paths);
		return paths;
	}

	public void checkPath(int number_edge, int end, int[][] arr, List<List<Edge>> paths) {
		if (L[number_edge - 1] == end) {
			hasPath++;
			List<Edge> path = new ArrayList<Edge>();
			for (int i = 1; i < number_edge; ++i) {
				Edge edge = getNode(L[i - 1]).getEdgeBetween(getNode(L[i]));
				path.add(edge);
			}
			paths.add(path);
		} else {
			for (int i = 0; i < getNodeCount(); ++i) {
				if (arr[L[number_edge - 1]][i] != 0 && D[i] == 0) {
					L[number_edge] = i;
					D[i] = 1;
					checkPath(number_edge + 1, end, arr, paths);
					L[number_edge] = 0;
					D[i] = 0;
				}
			}
		}
	}

	public void exportImg(JPanel view) throws IOException {

		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

		int result = fileChooser.showSaveDialog(fileChooser);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			BufferedImage image = new BufferedImage(view.getSize().width, view.getSize().height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = image.createGraphics();
			view.paint(g2);
			try {
				ImageIO.write(image, "png", new File(selectedFile.getAbsolutePath() + ".png"));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void findPath() {
		// TODO Auto-generated method stub

	}

}
