package board;

import java.util.HashMap;
import java.util.Map;
import Piece.Piece;

public abstract class Tile {
	protected final int tileCoordinate;
	private static final Map <Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTile();
	
	private Tile (final int tileCoordinate){
		this.tileCoordinate = tileCoordinate;
	}
	
	private static Map<Integer, EmptyTile> createAllPossibleEmptyTile() {
		
		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<> ();
		for (int i = 0; i < BoardUtils.NUM_TILES ; i += 1)
		{
			emptyTileMap.put(i, new EmptyTile (i));
		}
		return emptyTileMap;
	}
    public static Tile createTile (final int tileCoordinate,final Piece piece )
    {
    	return (piece != null) ? new OccupiedTile (tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }
	public abstract boolean isTileOcupied();
	public abstract Piece getPiece();
	
	public int getTileCoordinate() {
		return this.tileCoordinate;		
	}
	
	public static final class EmptyTile extends Tile{
	
		 private EmptyTile (final int coordinate){
			 super (coordinate);
		 }
		 
		@Override
		public String toString (){
			return "-";
		}
		
		@Override
		public boolean isTileOcupied() {
			return false;
		}
		@Override
		public Piece getPiece() {
			return null;
		}
	}
	public static final class OccupiedTile extends Tile {
		private final Piece pieceOnTile;
		
		private OccupiedTile (int tileCoordinate, Piece pieceOnTile)
		{
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}
		
		@Override
		public String toString (){
			return getPiece().getPieceAlliance().isBlack() ? getPiece().toString().toLowerCase() :
				getPiece().toString();
		}
		@Override
		public boolean isTileOcupied() {
			return true;
		}

		@Override
		public Piece getPiece() {
			return this.pieceOnTile;
		}		
	}
}
