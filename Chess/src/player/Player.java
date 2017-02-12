package player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import Piece.Alliance;
import Piece.King;
import Piece.Piece;
import board.Board;
import board.Move;

public abstract class Player {
	
	protected final Board board;	
	protected final King  playerKing;
	protected final Collection <Move> legalMoves;
	private   final boolean isInCheack;
	Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> OpponentMoves) {
		 
		this.board = board;
		this.playerKing = establishKing();
		this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves,calculateKingCastle(legalMoves, OpponentMoves)));
		this.isInCheack = !Player.calculateAttackOnTile (this.playerKing.getPiecePosition(), OpponentMoves).isEmpty();
	}
	
	public King getPlayerKing()
	{
		return this.playerKing;
	}
	
	public Collection<Move> getLegalMoves() {
		return this.legalMoves;
		
	}
	
	public static Collection<Move> calculateAttackOnTile(int piecePosition, Collection<Move> moves) {
		 
		final List<Move> attackMoves = new ArrayList<>();
		for (final Move move : moves)
		{
			if (piecePosition == move.getDestinationCoordinate())
			{
				attackMoves.add(move);
			}
		}
		return Collections.unmodifiableCollection(attackMoves);
	}

	private King establishKing() {
		for (final Piece piece : getActivePieces()) 
		{
			if (piece.getPieceType().isKing())
			{
			  return (King) piece;	
			}
		}
		throw new RuntimeException("Not a valid board ");
	}
	
	
	public boolean isMoveLegal (final Move move)
	{
		return this.legalMoves.contains(move); 
	}   
	
	
	public boolean isInCheck()
	{
		return this.isInCheack;
	}
	
	public boolean isInCheckMate()
	{
		return this.isInCheack && !hasEscapedMoves();
	}
	
	public boolean isInStaleMate() {
		return !this.isInCheack && !hasEscapedMoves();
	}
	
	protected boolean hasEscapedMoves() {
 
		for (final Move move : this.legalMoves)
		{
			final MoveTransition transition = makeMove(move);
			if (transition.getMoveStatus().isDone()) {
				return true;
			}
		}
		return false;
	}

	public boolean isCastled () 
	{
		return false;
	}
	
	 public MoveTransition makeMove (final Move move)
	{
		if (!isMoveLegal(move))
		{
			return new MoveTransition(this.board, move , MoveStatus.ILEEGAL_MOVE);
		}
		
		final Board transittionBoard = move.excute();
		final Collection<Move> knightAttacks = Player.calculateAttackOnTile(transittionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(),
				transittionBoard.currentPlayer().getLegalMoves());
		
		if (!knightAttacks.isEmpty())
		{
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
		}
				return new MoveTransition(transittionBoard, move, MoveStatus.DONE);
	}
	public abstract Collection<Piece> getActivePieces();
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
	protected abstract Collection<Move> calculateKingCastle(Collection<Move> playerLegals, Collection<Move> opponentLegals);
}
