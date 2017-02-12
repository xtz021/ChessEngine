package Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import Piece.Piece.pieceType;
import board.Board;
import board.Move;
import board.BoardUtils;
import board.Tile;

public class Knight extends Piece{

	private final static int[] CANDIDATE_MOVE_COORDINATE = {-17,-15,-10,-6,6,10,15,17};
	static pieceType pieceType;
	
	public Knight(final Alliance pieceAlliance,final int piecePosition) {
		super(pieceType.KNIGHT , piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List <Move> legalMoves = new ArrayList<>(); 
		
		for (final int currentcandidateOffset : CANDIDATE_MOVE_COORDINATE )
		{
		final int candidatDestinationCoordinate = this.piecePosition + currentcandidateOffset;
			if (BoardUtils.isvalildTileCoordinate(candidatDestinationCoordinate))
			{
				if (isFirstColumnExclusion  (this.piecePosition , currentcandidateOffset) || 
					isSeconColumnExclusion  (this.piecePosition , currentcandidateOffset) ||
					isEigthColumnExclusion  (this.piecePosition , currentcandidateOffset) ||
					isSeventhColumnExclusion(this.piecePosition , currentcandidateOffset)) {
					continue;
				}
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
	public Knight movePiece(Move move) {
		 
		return new Knight(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
	@Override
	public String toString ()
	{
		return pieceType.KNIGHT.toString();
	}
	
	private static boolean isFirstColumnExclusion (final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.FIRST_COLUM [currentPosition] && ((candidateOffset == -17) || (candidateOffset == -10) ||
				candidateOffset == 6 || candidateOffset == 15);
	}
	
	private static boolean isSeconColumnExclusion (final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.SECOND_COLUM [currentPosition] && ((candidateOffset == -10) || (candidateOffset == 6));
	}
	
	private static boolean isSeventhColumnExclusion (final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.SEVENTH_COLUM [currentPosition] && ((candidateOffset == -6) || (candidateOffset == 10));
	}
	
	private static boolean isEigthColumnExclusion (final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.EIGTH_COLUM [currentPosition] && ((candidateOffset ==  17) || (candidateOffset ==  10) ||
				candidateOffset == -6 || candidateOffset == -15);
	}
}
