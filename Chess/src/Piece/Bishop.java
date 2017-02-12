package Piece;

import java.util.List;
import com.google.common.collect.ImmutableList;

import Piece.Piece.pieceType;

import java.util.ArrayList;
import java.util.Collection;
import board.*;

public class Bishop extends Piece{

	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATE = {-9,-7,7,9};
	static pieceType pieceType;
	
	
	
	public Bishop(final Alliance pieceAlliance,final  int piecePosition) {
		
	    super(pieceType.BISHOP, piecePosition, pieceAlliance);
	
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		for (final int candidateDestinationCoordinateOffset :CANDIDATE_MOVE_VECTOR_COORDINATE)
		{
			int candidateDestinationCoordinate = this.piecePosition;
			while(BoardUtils.isvalildTileCoordinate(candidateDestinationCoordinate))
			{
				if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateDestinationCoordinateOffset)
					|| isEightColumnExclusion(candidateDestinationCoordinate, candidateDestinationCoordinateOffset))
				{
					break;
				}			
				candidateDestinationCoordinate += candidateDestinationCoordinateOffset;
				if (BoardUtils.isvalildTileCoordinate(candidateDestinationCoordinate))
				{
					final Tile candidatDestinationTile = board.getTile(candidateDestinationCoordinate);
					if (!candidatDestinationTile.isTileOcupied())
					{
						legalMoves.add(new Move.MajorMove(board,this,candidateDestinationCoordinate));
					}
					else
					{
						final Piece PieceAtDestination = candidatDestinationTile.getPiece();
						final Alliance pieceAlliance = PieceAtDestination.getPieceAlliance(); 
						
						if (this.pieceAlliance != pieceAlliance)
						{
							legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, PieceAtDestination));
						}
						break;
					}	
				}
			}
		}
			return ImmutableList.copyOf(legalMoves);
	}
		
	@Override
	public String toString ()
	{
		return pieceType.BISHOP.toString();
	}
	
	private static boolean isFirstColumnExclusion (final int currentPosition, final int candidateOffset)
		{
			return BoardUtils.FIRST_COLUM[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
		}
		
		private static boolean isEightColumnExclusion (final int currentPosition, final int candidateOffset)
		{
			return BoardUtils.EIGTH_COLUM[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
		}
	 
	 	public Bishop movePiece(final Move move) {
			return new Bishop(move.getMovedPiece().getPieceAlliance(),move.getDestinationCoordinate());
		}
	}
