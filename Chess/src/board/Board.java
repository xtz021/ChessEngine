package board;

import java.util.ArrayList; 

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Iterables;

import Piece.Alliance;
import Piece.Bishop;
import Piece.King;
import Piece.Knight;
import Piece.Pawn;
import Piece.Piece;
import Piece.Queen;
import Piece.Rook;
import player.BlackPlayer;
import player.Player;
import player.WhitePlayer;

public class Board {
	
	private final List<Tile> gameBoard;
	private final Collection<Piece> whitePieces;
	private final Collection<Piece> blackPieces;
	
	private final WhitePlayer whitePlayer;
	private final BlackPlayer blackPlayer;
	private final Player 	  currentPlayer;
	
	
	private Board(final Builder builder)
	{
		this.gameBoard = createGameBoard(builder);  
		this.whitePieces = calculateActivePieces(this.gameBoard,Alliance.WHITE);
		this.blackPieces = calculateActivePieces(this.gameBoard,Alliance.BLACK);
	 	
		final Collection <Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces);
		final Collection <Move> blackStandardLegalMoves = calculateLegalMoves(this.whitePieces);
		
		
		this.whitePlayer = new WhitePlayer (this, whiteStandardLegalMoves , blackStandardLegalMoves);
		this.blackPlayer = new BlackPlayer (this, whiteStandardLegalMoves , blackStandardLegalMoves);
		
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(whitePlayer , blackPlayer);
		
	}
 
	private static Collection<Piece> calculateActivePieces(List<Tile> gameBoard,
			Alliance alliance) {
		final List<Piece> activePieces = new ArrayList<Piece>();
		for (final Tile tile : gameBoard) {
			if (tile.isTileOcupied()){
				final Piece piece = tile.getPiece();
				if (piece.getPieceAlliance() == alliance) {
					activePieces.add(piece);
				}
			}
		}
		return Collections.unmodifiableList(activePieces);
	}
 
	public Tile getTile(final int tileCoordiante)
	{
		return gameBoard.get(tileCoordiante);
	}
 
	private static List<Tile> createGameBoard(Builder builder) {
		final ArrayList<Tile> tiles = new ArrayList<Tile> ();
		
		for (int i=0;i<64;i++) {
			tiles.add(Tile.createTile(i, builder.boardConfig.get(i)));
		}
		return Collections.unmodifiableList(tiles);
	}
	
	public static void blackPieces  (Builder builder)
	{
		builder.setPiece(new Rook(Alliance.BLACK, 0));
		builder.setPiece(new Rook(Alliance.BLACK, 7));
		
		builder.setPiece(new Knight(Alliance.BLACK, 1));
		builder.setPiece(new Knight(Alliance.BLACK, 6));
		
		builder.setPiece(new King(Alliance.BLACK,4));
		
		builder.setPiece(new Queen(Alliance.BLACK,3));
		 
		builder.setPiece(new Pawn(Alliance.BLACK,  8));
		builder.setPiece(new Pawn(Alliance.BLACK,  9));
		builder.setPiece(new Pawn(Alliance.BLACK, 10));
		builder.setPiece(new Pawn(Alliance.BLACK, 11));
		builder.setPiece(new Pawn(Alliance.BLACK, 12));
		builder.setPiece(new Pawn(Alliance.BLACK, 13));
		builder.setPiece(new Pawn(Alliance.BLACK, 14));
		builder.setPiece(new Pawn(Alliance.BLACK, 15));
		
		builder.setPiece(new Bishop(Alliance.BLACK, 2));
		builder.setPiece(new Bishop(Alliance.BLACK, 5));	
	}
	public static void whitekPieces (Builder builder)
	{

		builder.setPiece(new Rook(Alliance.WHITE, 56));
		builder.setPiece(new Rook(Alliance.WHITE, 63));		
		
		builder.setPiece(new Bishop(Alliance.WHITE, 61));
		builder.setPiece(new Bishop(Alliance.WHITE, 58));
		
		builder.setPiece(new Knight(Alliance.WHITE, 57));
		builder.setPiece(new Knight(Alliance.WHITE, 62));
		
		builder.setPiece(new Pawn(Alliance.WHITE, 48));
		builder.setPiece(new Pawn(Alliance.WHITE, 49));
		builder.setPiece(new Pawn(Alliance.WHITE, 50));
		builder.setPiece(new Pawn(Alliance.WHITE, 51));
		builder.setPiece(new Pawn(Alliance.WHITE, 52));
		builder.setPiece(new Pawn(Alliance.WHITE, 53));
		builder.setPiece(new Pawn(Alliance.WHITE, 54));
		builder.setPiece(new Pawn(Alliance.WHITE, 55));
		
		builder.setPiece(new King (Alliance.WHITE, 60));
		
		builder.setPiece(new Queen(Alliance.WHITE, 59));

	}
	
	
	public static Board createStandardBoard () {
		
		final Builder builder = new Builder();
		blackPieces (builder);
		whitekPieces(builder);
		
		builder.setMoveMaker(Alliance.WHITE);
		return builder.build();
	}
 
	public Player whitePlayer ()
	{
		return this.whitePlayer;
	}
	public Player blackPlayer ()
	{
		return this.blackPlayer;
	}
	
	public Player currentPlayer()
	{
		return this.currentPlayer;
	}
	private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
		final List<Move> legalMoves = new ArrayList<Move>();
		for (final Piece piece : pieces) {
			legalMoves.addAll(piece.calculateLegalMoves(this));
		}
		return Collections.unmodifiableList(legalMoves);
	}
 
	@Override
	public String toString () {
		final StringBuilder builder = new StringBuilder();
		
		for (int i=0;i<64;i++) {
			final String tileText = this.gameBoard.get(i).toString();
			builder.append(String.format("%3s",tileText));
			if ((i+1)%8 == 0) {
				builder.append("\n");
			}
		}
		return builder.toString();
	}
		
	public Collection<Piece> getBlackPieces()
	{
		return this.blackPieces;
	}
	
	public Collection<Piece> getWhitePieces()
	{
		return this.whitePieces;
	}
	
		public static class Builder {
		
		Map<Integer, Piece> boardConfig;
		Alliance nextMoveMaker;
		Pawn enPassanPawn;
		
		public Builder (){
			this.boardConfig = new HashMap<>();			
		}
		
		public Builder setPiece (final Piece piece)
		{
			this.boardConfig.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		public Builder setMoveMaker (final Alliance nextMoveMaker)
		{
			this.nextMoveMaker = nextMoveMaker;
			return this;
		}
		public Board build (){
			return new Board  (this);
		}

		public void setEnPassanPawn(Pawn enPassanPawn) {
			this.enPassanPawn = enPassanPawn;
			
		}
	}

		public Iterable<Move> getAllLegalMoves() {
			
			return Iterables.unmodifiableIterable(Iterables.concat(this.whitePlayer.getLegalMoves(), this.blackPlayer.getLegalMoves()));
		}
}