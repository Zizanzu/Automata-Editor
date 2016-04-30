package graph.editor;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFrame;

public interface FramesController {

	public void quit();

	public JFrame createFrame();

	public void deleteFrame(JFrame frame);
        
        public void saveFrame(File f, String pathFile, ArrayList<String> edgeList, ArrayList<String> vertexList);
}