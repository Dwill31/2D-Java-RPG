package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10];
		mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow];
		
		getTileImage();
		loadMap();
		
	}
	
	public void getTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap() {
		
		try {
			//input stream imports text file(map).
			InputStream is = getClass().getResourceAsStream("/maps/map01.txt");
			//reads text file
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			//sets row and column to 0 
			 while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
				 //br.readLine will read 1 line of the text file and put it into line.
				 String line = br.readLine();
				 
				 while (col < gp.maxScreenCol) {
					 //This is will take line and split into an array.
					 String numbers[] = line.split(" ");
					 
					 //Converts string to integer.
					 int num = Integer.parseInt(numbers[col]);
					 
					 mapTileNum[col][row] = num;
					 col++;
				 }
				 //if column exceeds limit. Moves to next line.
				 if (col == gp.maxScreenCol) {
					 col = 0;
					 row++;
				 }
			 }
			 br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		//fills the screen with grass tiles without manually writing them out.
		while (col < gp.maxScreenCol && row < gp.maxScreenRow) {
			
			int tileNum = mapTileNum[col][row];
			
			//Checks tileNum for number then draws the given tile in the position
			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;
			//if the grass expands pass the the column limit. Will add another row.
			//and reset column and its x value back to 0.
			if (col == gp.maxScreenCol) {
				col = 0;
				x = 0;
				row ++;
				y += gp.tileSize;
			}
		}
	}
}
