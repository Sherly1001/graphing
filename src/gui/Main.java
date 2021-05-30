package gui;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Timer;

import javax.imageio.ImageIO;

import javax.swing.*;
import org.graphstream.graph.*;
import org.graphstream.ui.geom.Point2;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swing_viewer.*;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.camera.Camera;

import event.*;
import graph.IntegratedGraph;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	private IntegratedGraph graph = new IntegratedGraph("Graphing", false, true);

	private Container ctn = getContentPane();
	private ControlPanel controlPanel = new ControlPanel(graph);
	private LogPanel logPanel = new LogPanel(graph);

	private List<List<Edge>> paths;
	private List<Edge> selectedRoute;
	private int move[] = { 0 };
	private ScheduledTasks te1;

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

		for (Node n : graph) {
			n.setAttribute("ui.label", n.getId());
		}

		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.setBounds(0, 0, 100, 20);

		JMenuItem openMenuItem = new JMenuItem("Open");
		openMenuItem.setActionCommand("Open");
		openMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.OPEN_FILE));
			}
		});

		JMenuItem exportMenuItem = new JMenuItem("Export");
		exportMenuItem.setActionCommand("Export");
		exportMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.EXPORT_IMAGE));
			}
		});

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setActionCommand("Exit");

		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		try {
			Image file = ImageIO.read(new File("images/file.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			openMenuItem.setIcon(new ImageIcon(file));
			Image export = ImageIO.read(new File("images/export.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			exportMenuItem.setIcon(new ImageIcon(export));
			Image exit = ImageIO.read(new File("images/exit.png")).getScaledInstance(20, 20, Image.SCALE_DEFAULT);
			exitMenuItem.setIcon(new ImageIcon(exit));
		} catch (Exception e) {
		}
		fileMenu.add(openMenuItem);
		fileMenu.add(exportMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);

		controlPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		controlPanel.setPreferredSize(new Dimension(250, 400));
		controlPanel.setMaximumSize(new Dimension(400, 600));
		controlPanel.setMinimumSize(new Dimension(250, 400));
		controlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Controller"));
		controlPanel.setBackground(Color.white);
		controlPanel.setLayout(null);

		ViewPanel view = (ViewPanel) viewer.addDefaultView(false);
		view.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Graph"));
		view.setAlignmentX(Component.LEFT_ALIGNMENT);
		view.setPreferredSize(new Dimension(600, 400));
		view.setMaximumSize(new Dimension(1000, 600));
		view.setMinimumSize(new Dimension(500, 300));

		view.getCamera().setViewPercent(1);
		view.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				e.consume();
				int i = e.getWheelRotation();
				double factor = Math.pow(1.25, i);
				Camera cam = view.getCamera();
				double zoom = cam.getViewPercent() * factor;
				Point2 pxCenter = cam.transformGuToPx(cam.getViewCenter().x, cam.getViewCenter().y, 0);
				Point3 guClicked = cam.transformPxToGu(e.getX(), e.getY());
				double newRatioPx2Gu = cam.getMetrics().ratioPx2Gu / factor;
				double x = guClicked.x + (pxCenter.x - e.getX()) / newRatioPx2Gu;
				double y = guClicked.y - (pxCenter.y - e.getY()) / newRatioPx2Gu;
				cam.setViewCenter(x, y, 0);
				cam.setViewPercent(zoom);
			}
		});

		logPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Logs"));
		logPanel.setBackground(Color.white);
		logPanel.setMinimumSize(new Dimension(1000, 100));

		JSplitPane innerPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, view, controlPanel);
		innerPane.setContinuousLayout(true);
		innerPane.setOneTouchExpandable(true);
		JSplitPane outerPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, innerPane, logPanel);
		outerPane.setContinuousLayout(true);
		outerPane.setOneTouchExpandable(true);

		ctn.add(outerPane, BorderLayout.CENTER);
		setVisible(true);
		setSize(900, 640);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LogEvent.addLogListener(new LogListener() {
			@Override
			public void run(LogEvent e) {
				if (e.cause == LogEvent.Cause.INFO) {
					System.out.println("INFO: " + e.message);
				} else if (e.cause == LogEvent.Cause.ERROR) {
					System.err.println("ERROR: " + e.message);
				} else if (e.cause == LogEvent.Cause.EXPORT_IMAGE) {
					try {
						graph.exportImg(view);
					} catch (IOException e1) {
						LogEvent.emitLogEvent(
								new LogEvent(LogEvent.Cause.ERROR, "Export image error " + e1.getMessage()));
					}
				} else if (e.cause == LogEvent.Cause.OPEN_FILE) {
					try {
						graph.loadFromFile(ImportFile.getUrl());
					} catch (Exception e2) {
						System.out.println(e2);
					}
					for (Node n : graph) {
						n.setAttribute("ui.label", n.getId());
					}
				} else if (e.cause == LogEvent.Cause.FIND_PATH) {
					paths = e.paths;
					graph.nodes().forEach(node -> node.removeAttribute("ui.class"));
					graph.edges().forEach(edge -> edge.removeAttribute("ui.class"));
					if (paths != null && paths.size() > 0) {
						selectedRoute = paths.get(0);
						move[0] = 0;
						paths.get(0).get(0).getSourceNode().setAttribute("ui.class", "marked");

						String stringRoute = "";
						for (Edge edge : selectedRoute) {
							stringRoute += edge.getSourceNode() + "->" + edge.getTargetNode() + ", ";
						}
						LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.INFO,
								"selected route 1: " + stringRoute.replaceAll(", $", "")));
					}
				} else if (e.cause == LogEvent.Cause.SELECT_ROUTE) {
					if (paths != null && paths.size() > 0) {
						selectedRoute = paths.get(Integer.parseInt(e.message.replace("route ", "")) - 1);
						move[0] = 0;
						graph.nodes().forEach(node -> node.removeAttribute("ui.class"));
						graph.edges().forEach(edge -> edge.removeAttribute("ui.class"));
						selectedRoute.get(0).getSourceNode().setAttribute("ui.class", "marked");

						String stringRoute = "";
						for (Edge edge : selectedRoute) {
							stringRoute += edge.getSourceNode() + "->" + edge.getTargetNode() + ", ";
						}
						LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.INFO,
								"selected " + e.message + ": " + stringRoute.replaceAll(", $", "")));
					}
				} else if (e.cause == LogEvent.Cause.NEXT_NODE) {
					if (selectedRoute == null || selectedRoute.size() <= 0) {
						LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.ERROR, "no route selected"));
					} else {
						if (move[0] < selectedRoute.size()) {
							Edge currentEdge = selectedRoute.get(move[0]);
							currentEdge.setAttribute("ui.class", "red");
							currentEdge.getTargetNode().setAttribute("ui.class", "marked");
							move[0] += 1;
						} else {
							LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.ERROR, "no next node"));
						}
					}
				} else if (e.cause == LogEvent.Cause.PERIOUS_NODE) {
					if (selectedRoute == null || selectedRoute.size() <= 0) {
						LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.ERROR, "no route selected"));
					} else {
						if (move[0] == 0) {
							LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.ERROR, "no previous node"));
						} else {
							move[0] -= 1;
							Edge currentEdge = selectedRoute.get(move[0]);
							currentEdge.removeAttribute("ui.class");
							currentEdge.getTargetNode().removeAttribute("ui.class");
						}
					}
				} else if (e.cause == LogEvent.Cause.RUN) {
					te1 = new ScheduledTasks("Run path", selectedRoute, move);
					Timer t = new Timer();
					t.scheduleAtFixedRate(te1, 0, 1000);
					LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.INFO, "Running"));
				} else if (e.cause == LogEvent.Cause.STOP) {
					te1.cancel();
					LogEvent.emitLogEvent(new LogEvent(LogEvent.Cause.INFO, "Stopped"));
				}
			}
		});

	}
}
