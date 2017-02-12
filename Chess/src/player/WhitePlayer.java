package player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.ImmutableList;

import Piece.Alliance;
import Piece.Piece;
import Piece.Rook;
import board.Board;
import board.Move;
import board.Tile;

public class WhitePlayer extends Player{
	
	public WhitePlayer (final Board board, 
			final Collection<Move> whiteStandardLegalMoves,
			final Collection<Move> blackStandardLegalMoves) {
		
		super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
		
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getWhitePieces();
	}

	@Override
	public Alliance getAlliance() {
 		return Alliance.WHITE;
	}

	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}

	@Override
	protected Collection<Move> calculateKingCastle(final Collection<Move> playerLegals,
												   final Collection<Move> opponentLegals) {
		 
		final List <Move> KingCastles = new ArrayList<>();
		// white king side castle
		if (!this.playerKing.isFirstMove() && !this.isInCheck()){
			if (!this.board.getTile(61).isTileOcupied() && !this.board.getTile(62).isTileOcupied())
			{
				final Tile rookTile = this.board.getTile(63);
				if (rookTile.isTileOcupied() && rookTile.getPiece().isFirstMove())
				{
					if (Player.calculateAttackOnTile(61, opponentLegals).isEmpty() && 
						Player.calculateAttackOnTile(62, opponentLegals).isEmpty() &&
						rookTile.getPiece().getPieceType().isRook()) {
						//TODO
						KingCastles.add (new Move.KingSideCastleMove(this.board, 
																	 this.playerKing,
																	 62,
																	 (Rook)rookTile.getPiece(), 
																	 rookTile.getTileCoordinate(),
																	 61));	
					}
				}
			}
			if (!this.board.getTile(59).isTileOcupied() &&
				!this.board.getTile(58).isTileOcupied() &&
				!this.board.getTile(57).isTileOcupied()) {
				
				final Tile rookTile = this.board.getTile(56);
				if (rookTile.isTileOcupied() && rookTile.getPiece().isFirstMove()) {
					//TODO add castle
					KingCastles.add (new Move.QueenSideCastleMove(
							 this.board, 
							 this.playerKing,
							 58,
							 (Rook)rookTile.getPiece(), 
							 rookTile.getTileCoordinate(),
							 59));	
				}
			}
		}
		return ImmutableList.copyOf(KingCastles);
	}
}
