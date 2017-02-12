package Piece;

import java.util.*; 
import java.util.Collection;

import com.google.common.collect.ImmutableList;

import Piece.Piece.pieceType;
import board.Board;
import board.BoardUtils;
import board.Move;
import board.Tile;

public class King extends Piece{

	private final static int[] CANDIDATE_MOVE_COORDINATE = {-9,-8,-7,-1, 1,7,8,9};
	static pieceType pieceType;
	
	public King(final Alliance pieceAlliance,final  int piecePosition) {
		super(pieceType.KING, piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		
		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE)
		{
			final int  candidatDestinationCoordinate = this.piecePosition + currentCandidateOffset;
			if (isFirstColumnExclusion(this.piecePosition,
					currentCandidateOffset) || isEighthColumnExclusion(this.piecePosition,currentCandidateOffset))
			{
				continue;
			}
			
			if (BoardUtils.isvalildTileCoordinate(candidatDestinationCoordinate)) 
			{
				final Tile candidateDestinationCoordinate = board.getTile(candidatDestinationCoordinate);
				if (!candidateDestinationCoordinate.isTileOcupied())
				{
					legalMoves.add(new Move.MajorMove(board,this,candidatDestinationCoordinate));
				}
				else
				{
					final Piece PieceAtDestination = candidateDestinationCoordinate.getPiece();
					final Alliance pieceAlliance = PieceAtDestination.getPieceAlliance(); 
					
					if (this.pieceAlliance != pieceAlliance)
					{
						legalMoves.add(new Move.AttackMove(board, this, candidatDestinationCoordinate, PieceAtDestination));
					}
				}	
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	
	@Override
	public String toString ()
	{
		return pieceType.KING.toString();
	}
	
	private static boolean isFirstColumnExclusion (final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.FIRST_COLUM [currentPosition] && ((candidateOffset == 7) || (candidateOffset == -9) ||
				candidateOffset == -1 );
	}
	
	private static boolean isEighthColumnExclusion (final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.EIGTH_COLUM[currentPosition] && ((candidateOffset == 1) || (candidateOffset == -7)
				|| candidateOffset == 9);
	}

	@Override
	public King movePiece(final Move move) {
		return new King(move.getMovedPiece().getPieceAlliance() , move.getDestinationCoordinate());
	}

}
