package Piece;

import java.util.Collection;

import board.Board;
import board.Move;

public abstract class Piece {
	protected final int      piecePosition;
	protected final pieceType pieceType ;
	protected final Alliance pieceAlliance;
	protected final boolean  isFirstMove;
    private final int 		 cachedHashCode;

	
	
	  Piece (final pieceType pieceType, final int piecePosition,  final Alliance pieceAlliance)
	{
		 this.pieceType      = pieceType;
		 this.pieceAlliance  = pieceAlliance;
		 this.piecePosition  = piecePosition;
		 
		 this.isFirstMove    = false;		
		 this.cachedHashCode = computeHashCode();
	}
	
	    private int computeHashCode() {
	        
	    	int result = pieceType.hashCode();
	        result = 31 * result + pieceAlliance.hashCode();
	        result = 31 * result + piecePosition;
	        result = 31 * result + (isFirstMove ? 1 : 0);
	        return result;
	    }

	    
	    @Override
	    public boolean equals(final Object other) {
	        if (this == other) {
	            return true;
	        }
	        if (!(other instanceof Piece)) {
	            return false;
	        }
	        final Piece otherPiece = (Piece) other;
	        return piecePosition == otherPiece.piecePosition && pieceType == otherPiece.pieceType &&
	               pieceAlliance == otherPiece.pieceAlliance && isFirstMove == otherPiece.isFirstMove;
	    }
	    
	    @Override
	    public int hashCode() {
	        return this.cachedHashCode;
	    }


	public int getPiecePosition ()
	{
		return this.piecePosition;
	}
	public Alliance getPieceAlliance () 
	{
		return this.pieceAlliance;
	}
	public boolean isFirstMove ()
	{
		return this.isFirstMove;
	}
	public pieceType getPieceType() {
		return this.pieceType;
	}
	
	
	public abstract Collection<Move> calculateLegalMoves (final Board board);
    public abstract Piece movePiece( Move move);	
	
	public enum pieceType {
		  PAWN("P") {
			@Override
			public boolean isKing() {
   
				return false;
			}

			@Override
			public boolean isRook() {
				
				return false;
			}
		},
		KNIGHT("N") {
			@Override
			public boolean isKing() {
   
				return false;
			}

			@Override
			public boolean isRook() {
				
				return false;
			}
		},
		BISHOP("B") {
			@Override
			public boolean isKing() {
   
				return false;
			}

			@Override
			public boolean isRook() {
				
				return false;
			}
		},
		  ROOK("R") {
			@Override
			public boolean isKing() {
   
				return true;
			}

			@Override
			public boolean isRook() {
				
				return false;
			}
		},
		 QUEEN("Q") {
			@Override
			public boolean isKing() {
   
				return false;
			}

			@Override
			public boolean isRook() {
				
				return false;
			}
		},
		  KING("K") {
			@Override
			public boolean isKing() {
   
				return true;
			}

			@Override
			public boolean isRook() {
				  
				return false;
			}
		}; 
		
		private String pieceName;
		private pieceType(final String pieceName) {
			this.pieceName = pieceName;
		}
		
		public String toString()
		{
			return this.pieceName;
		}
			public abstract boolean isKing();
			public abstract boolean isRook();				

	}
}
