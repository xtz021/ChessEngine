package player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import Piece.Alliance;
import Piece.Piece;
import Piece.Rook;
import board.*;

public class BlackPlayer extends Player{

	public BlackPlayer(final Board board, 
			final Collection<Move> whiteStandardLegalMoves,
			final Collection<Move> blackStandardLegalMoves) {
		
 	super(board, blackStandardLegalMoves , whiteStandardLegalMoves);
	
	}
	
	@Override
	public Collection<Piece> getActivePieces() 
	{
		return this.board.getBlackPieces();
	}

	@Override
	public Alliance getAlliance() {
		  
		return Alliance.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.whitePlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastle(final Collection<Move> playerLegals,
												   final Collection<Move> opponentLegals) {
		 
		final List <Move> KingCastles = new ArrayList<>();
		// Black king side castle
		if (!this.playerKing.isFirstMove() && !this.isInCheck()){
			if (!this.board.getTile(5).isTileOcupied() && !this.board.getTile(6).isTileOcupied())
			{
				final Tile rookTile = this.board.getTile(7);
				if (rookTile.isTileOcupied() && rookTile.getPiece().isFirstMove())
				{
					if (Player.calculateAttackOnTile(5, opponentLegals).isEmpty() && 
						Player.calculateAttackOnTile(6, opponentLegals).isEmpty() &&
						rookTile.getPiece().getPieceType().isRook()) {
						 
						KingCastles.add(new Move.KingSideCastleMove(this.board,
																	this.playerKing,
																	6, (Rook) rookTile.getPiece(), 
																	rookTile.getTileCoordinate(), 
																	5));	
					}
				}
			}
			if (!this.board.getTile(1).isTileOcupied() &&
				!this.board.getTile(2).isTileOcupied() &&
				!this.board.getTile(3).isTileOcupied()) {
				
				final Tile rookTile = this.board.getTile(0);
				if (rookTile.isTileOcupied() && rookTile.getPiece().isFirstMove()) {
					
					KingCastles.add(new Move.QueenSideCastleMove(this.board,
							this.playerKing,
							2, (Rook) rookTile.getPiece(), 
							rookTile.getTileCoordinate(), 
							3));
				}
			}
		}
		return ImmutableList.copyOf(KingCastles);
	}
}
