
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import org.mapeditor.core.Map;
import org.mapeditor.core.MapLayer;
import org.mapeditor.core.TileLayer;
import org.mapeditor.io.TMXMapReader;
import org.mapeditor.view.OrthogonalRenderer;

public class PuzzleScreen extends JPanel{
	Map map;
	OrthogonalRenderer or;

	public PuzzleScreen(String fileName) {
		try {
			TMXMapReader mapReader = new TMXMapReader();
			map = mapReader.readMap(fileName);
		} catch (Exception e) {
			System.out.println("Error while reading the map:\n" + e.getMessage());
		}
		or = new OrthogonalRenderer(map);
        setOpaque(true);
	}

	@Override
	public void paintComponent(Graphics g) {
		final Graphics2D g2d = (Graphics2D) g.create();

		for (MapLayer layer : map.getLayers()) {
			or.paintTileLayer(g2d, (TileLayer) layer);
		}
	}
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(800,640);
    }
}
