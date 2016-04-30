package graph.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

public class GraphFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private GraphComponent component;
	private FramesController controller;

	public GraphFrame(FramesController controller) {
		this.controller = controller;

		component = new GraphComponent();
		component.setForeground(Color.BLACK);
		component.setBackground(Color.WHITE);
		component.setOpaque(true);
		component.setPreferredSize(new Dimension(1000, 1000));
		JScrollPane scrollPane = new JScrollPane(component);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu(GraphEditor.MENU_FILE);
		menuBar.add(menu);
		createMenuItem(menu, GraphEditor.MENU_ITEM_NEW, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				GraphFrame.this.controller.createFrame();
			}
		});
                
                createMenuItem(menu, GraphEditor.MENU_ITEM_SAVE, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
                                File f = null;
                                Vertex v = new Vertex(null, null);
                                String pathFile = component.saveFileDialog();
                                System.out.println(pathFile);
                                ArrayList<String> edgeList = new ArrayList<>();
                                ArrayList<String> vertexList = new ArrayList<>();
                                component.saveAutomata();
                                edgeList = component.edgeList;
                                vertexList = component.vertexList;
                                GraphFrame.this.controller.saveFrame(f,pathFile,edgeList,vertexList);
			}
		});
                
                createMenuItem(menu, GraphEditor.MENU_ITEM_LOAD, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
                                String pathFile = component.openFileDialog();
                                System.out.println("Load File: "+pathFile);
                                //component.createVertex(60,60);

			}
		});
                
		createMenuItem(menu, GraphEditor.MENU_ITEM_CLOSE, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				GraphFrame.this.controller.deleteFrame(GraphFrame.this);
			}
		});
		createMenuSeparator(menu);
		createMenuItem(menu, GraphEditor.MENU_ITEM_CHECKSTRING, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				component.checkString();
			}
		});
		createMenuSeparator(menu);
		createMenuItem(menu, GraphEditor.MENU_ITEM_QUIT, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				GraphFrame.this.controller.quit();
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				GraphFrame.this.controller.deleteFrame(GraphFrame.this);
			}
		});

		JToolBar toolbar = new JToolBar();
		toolbar.setLayout(new GridLayout(1, 1));
		JButton b = addShapeButton(toolbar, new Ellipse2D.Double(0, 0, 60, 60), "New Circle");
		b.doClick();
//		addShapeButton(toolbar, new Ellipse2D.Double(0, 0, 50, 50), "Big Circle");
//		addShapeButton(toolbar, new Rectangle2D.Double(0, 0, 20, 20), "Small Square");
//		addShapeButton(toolbar, new Rectangle2D.Double(0, 0, 50, 50), "Big Square");

		Container contentPane = getContentPane();
		contentPane.add(toolbar, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);
	}

	private JButton addShapeButton(JToolBar toolbar, final RectangularShape sample, String name) {
		JButton button = new JButton(name);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				component.setShapeType(sample);
			}
		});
		toolbar.add(button);
		return button;
	}

	private void createMenuItem(JMenu menu, String name, ActionListener action) {
		JMenuItem menuItem = new JMenuItem(name);
		menuItem.addActionListener(action);
		menu.add(menuItem);
	}

	private void createMenuSeparator(JMenu menu) {
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.lightGray);
		menu.add(separator);
	}
        
}
