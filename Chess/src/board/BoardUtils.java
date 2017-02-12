package board;

public class BoardUtils {
	
	public static final boolean[] FIRST_COLUM   = initColum(0);
	public static final boolean[] SECOND_COLUM  = initColum(1);
	public static final boolean[] SEVENTH_COLUM = initColum(6);
	public static final boolean[] EIGTH_COLUM   = initColum(7);
	
	public static final boolean[] SECOND_ROW  =  initRow(8);
	public static final boolean[] SEVENTH_ROW =  initRow(48); 
	
	
	public static final int NUM_TILES = 64;
	public static final int NUM_TILE_PER_ROW = 8;
	
	
	private BoardUtils ()
	{
		throw new RuntimeException("You cann't instantiate me !");
	}
	
	private static boolean[] initRow(int rowNumber) 
	{
		final boolean[] row= new boolean [NUM_TILES];
		do {
			row[rowNumber] = true;
			rowNumber += 1;
			
		} while (rowNumber % NUM_TILE_PER_ROW != 0);
		return row;
	}
	private static boolean[] initColum(int columNumber) {
		
		final boolean[] colum = new boolean [NUM_TILES];
		do { 
			colum[columNumber] = true;
			columNumber += NUM_TILE_PER_ROW;
			
		} while (columNumber < NUM_TILES);
		return colum;
	}
	public static boolean isvalildTileCoordinate(final int coordinate) {	
		return coordinate >= 0 && coordinate < NUM_TILES;
	}
}
