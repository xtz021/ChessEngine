package Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import board.Board;
import board.BoardUtils;
import board.Move;

public class Pawn extends Piece{

	private final static int[] CANDIDATE_MOVE_COORDINATE = {8,16,7,9};
	static pieceType pieceType;
	
	public Pawn( final Alliance pieceAlliance,final int piecePosition) {
		super(pieceType.PAWN ,piecePosition, pieceAlliance);	 
	}

	
	public Collection<Move> calculateLegalMoves(final Board board) {
		final List<Move> legalMoves = new ArrayList<>();
		
		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE)
		{
			int  candidatDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection()
					* currentCandidateOffset);
			if (!BoardUtils.isvalildTileCoordinate(candidatDestinationCoordinate))
			{
					continue;
			}
			if (currentCandidateOffset == 8 && !board.getTile(candidatDestinationCoordinate).isTileOcupied())
			{
				//TODO
				legalMoves.add(new Move.MajorMove(board, this, candidatDestinationCoordinate));
			}
			else if (currentCandidateOffset == 16 && this.isFirstMove() && (BoardUtils.
					SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) || 
					
					(BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite()) ){
				
				final int behindcandidatDestinationCoordinate = this.piecePosition + 
						(this.pieceAlliance.getDirection() * 8);
				
				if (!board.getTile(behindcandidatDestinationCoordinate).isTileOcupied() && 
						!board.getTile(candidatDestinationCoordinate).isTileOcupied())
				{
						legalMoves.add(new Move.MajorMove(board, this, candidatDestinationCoordinate));
				}
			} else if (currentCandidateOffset == 7 &&
					!((BoardUtils.EIGTH_COLUM[this.piecePosition] && this.pieceAlliance.isWhite()
				  || ( BoardUtils.FIRST_COLUM[this.piecePosition] && this.pieceAlliance.isBlack())))){
					
				if (board.getTile(candidatDestinationCoordinate).isTileOcupied())
				{
					final Piece pieceOnCandidte = board.getTile(candidatDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCandidte.getPieceAlliance())
					{
						// TODO :
						legalMoves.add(new Move.MajorMove(board, this, candidatDestinationCoordinate));
					}
				}
				
			} else if (currentCandidateOffset == 9 && 
					!((BoardUtils.FIRST_COLUM[this.piecePosition] && this.pieceAlliance.isWhite()
				   ||( BoardUtils.EIGTH_COLUM[this.piecePosition] && this.pieceAlliance.isBlack())))){
				
				// TODO :
					   legalMoves.add(new Move.MajorMove(board, this, candidatDestinationCoordinate));
			}
		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public Pawn movePiece (Move move) {	 
		return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
	@Override
	public String toString ()
	{
		return pieceType.PAWN.toString();
	}
}
