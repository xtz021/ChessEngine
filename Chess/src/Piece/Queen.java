package Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;
import board.Board;
import board.BoardUtils;
import board.Move;
import board.Tile;

public class Queen extends Piece{
	
	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATE = {-9,-8,-7,-1,1,7,8,9};
	static pieceType pieceType;
	
	public Queen(Alliance pieceAlliance, int piecePosition) {
		super(pieceType.QUEEN , piecePosition, pieceAlliance);	
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
	public Queen movePiece(Move move) {
		 
		return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
	
	@Override
	public String toString ()
	{
		return pieceType.QUEEN.toString();
	}
		
		private static boolean isFirstColumnExclusion (final int currentPosition, final int candidateOffset)
		{
			return BoardUtils.FIRST_COLUM[currentPosition] && ( candidateOffset == -1 ||
					candidateOffset == -9 || candidateOffset == 7);
		}
		
		private static boolean isEightColumnExclusion (final int currentPosition, final int candidateOffset)
		{
			return BoardUtils.EIGTH_COLUM[currentPosition] && ( candidateOffset == 1 ||
					candidateOffset == -7 || candidateOffset == 9);
		}

}
