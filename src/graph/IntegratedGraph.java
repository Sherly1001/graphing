package graph;

import java.io.*;
import java.util.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class IntegratedGraph extends SingleGraph {

	private int hasPath = 0;
	private int start = 0;
	private int end = 0;
	private int has_start = 0;
	private int has_end = 0;
	private int move = 0;
	private static int[] visited;
	private static int[] load_path;

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
				has_start += 1;
			}
			if (s.getId().equals(destination)) {
				end = s.getIndex();
				has_end += 1;
			}
		});
		if (has_start == 0 || has_end == 0) {
			if (has_start == 0) {
				System.out.println("Start node has not found!");
			}
			if (has_end == 0) {
				System.out.println("End node has not found!");
			}
		} else {
			visited = new int[getNodeCount()];
			load_path = new int[getNodeCount()];
			for (int i = 0; i < getNodeCount(); i++) {
				visited[i] = 0;
				load_path[i] = 0;
			}
			visited[start] = 1;
			load_path[0] = start;
			checkPath(1, end, arr, paths);
//		for (Iterator iterator = paths.iterator(); iterator.hasNext();) {
//			List<Edge> list = (List<Edge>) iterator.next();
//			for (Iterator iterator2 = list.iterator(); iterator2.hasNext();) {
//				Edge edge = (Edge) iterator2.next();
//				System.out.println(edge.getId());
//			}
//		}
		}
		return paths;
	}

	public void checkPath(int number_edge, int end, int[][] arr, List<List<Edge>> paths) {
		if (load_path[number_edge - 1] == end) {
			hasPath++;
			List<Edge> path = new ArrayList<Edge>();
			for (int i = 1; i < number_edge; ++i) {
				Edge edge = getNode(load_path[i - 1]).getEdgeBetween(getNode(load_path[i]));
				path.add(edge);
			}
			paths.add(path);
		} else {
			for (int i = 0; i < getNodeCount(); ++i) {
				if (arr[load_path[number_edge - 1]][i] != 0 && visited[i] == 0) {
					load_path[number_edge] = i;
					visited[i] = 1;
					checkPath(number_edge + 1, end, arr, paths);
					load_path[number_edge] = 0;
					visited[i] = 0;
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

	public void choise_path(int index, String source, String destination, int choise) {
		List<List<Edge>> pathList = findAllPath(source, destination);
		List<Edge> path = new ArrayList<Edge>();
		if (index > pathList.size() - 1) {
			System.out.println("Path invalid");
		} else {
			path = pathList.get(index);
			move += choise;
			if (move > path.size() || move < 0) {
				System.out.println("You shall not pass");
				move--;
			} else {
				for (int i = 0; i < move; i++) {
					System.out.println(path.get(i).getId());
				}
			}

		}
	}

}
