package stratego.moves;

import stratego.Game;
import stratego.Game.Direction;
import stratego.Game.Player;
import stratego.io.GameError;
import stratego.pieces.BombPiece;
import stratego.pieces.FlagPiece;
import stratego.pieces.ImpassableTerrain;
import stratego.pieces.Piece;
import stratego.pieces.RegularPiece;
import stratego.util.Position;

/**
 * Represents a move where one pieces strikes a square containing an opponent.
 * At this point, the piece attempts to "take" the other. However, depending on
 * the strength of the two pieces, it may or may not succeed in doing this.
 *
 * @author David J. Pearce
 *
 */
public class StrikeMove extends BasicMove {
	/**
	 * The three possible outcomes when one piece strikes anothyer
	 *
	 */
	public enum Outcome {
		WIN, LOSE, DRAW
	}

	/**
	 * Indicates the outcome of the encounter, from the perspective of the
	 * moving piece.
	 */
	private Outcome outcome;

	/**
	 * Construct a take move for a given player going in one direction for a
	 * number of steps. Also, identifies whether or not the moving player is the
	 * winner.
	 *
	 * @param player
	 *            --- Player making the move
	 * @param position
	 *            --- Starting position of piece to be moved
	 * @param direction
	 *            --- Direction into which to move
	 * @param steps
	 *            --- Number of steps to move
	 * @param outcome
	 *            --- Indicates the outcome of the encounter, from the
	 *            perspective of the moving piece.
	 */
	public StrikeMove(Player player, Position origin, Direction direction,
			int steps, Outcome outcome) {
		super(player, origin, direction, steps);
		this.outcome = outcome;
	}

	@Override
	public void apply(Game game) throws GameError {
		Position newPosition = new Position(position, direction, steps);
		Piece opponent = game.getPiece(newPosition);
		Piece piece = game.getPiece(position);
		// First, check that piece exists at given position, and is owned by
		// this player and that destination contains an opponent.
		if (opponent == null || piece == null) {
            throw new GameError("Invalid move for piece");
        }
		
		// cannot move flag or impassableTerrain
		if (piece instanceof ImpassableTerrain || piece instanceof FlagPiece) {
            throw new GameError("Invalid move for piece");
        }
		
		// cannot defeat impassableTerrain
        if (opponent instanceof ImpassableTerrain) {
            throw new GameError("Invalid move for piece");
        }

        // cannot move the opposite player's piece
        if (player != piece.getPlayer()) {
            throw new GameError("Invalid move for piece");
        }
		
		// Second, check that piece is permitted to move a given number of steps
		// in the given direction.
		if (!piece.isValidMove(position, direction, steps, game)) {
			throw new GameError("Invalid move for piece");
		}
		
		
		if (piece instanceof RegularPiece && opponent instanceof RegularPiece) {
		    RegularPiece rpiece = (RegularPiece) piece;
		    RegularPiece ropponent = (RegularPiece) opponent;
		    
		    // spy attack marshal
		    if (rpiece.getRank()==0 && ropponent.getRank()==9) {
		        if (outcome != Outcome.WIN) {
		            throw new GameError("Invalid move for piece");
		        }
		        
		    // marshal attack spy
		    } else if (rpiece.getRank()==9 && ropponent.getRank()==0) {
		        if (outcome != Outcome.WIN) {
                    throw new GameError("Invalid move for piece");
                }
		        
		    // others, check the rank    
		    } else {
		        if (outcome == Outcome.WIN) {
		            if (rpiece.getRank() <= ropponent.getRank()) {
		                throw new GameError("Invalid move for piece");
		            }
		        } else if (outcome == Outcome.LOSE) {
		            if (rpiece.getRank() >= ropponent.getRank()) {
		                throw new GameError("Invalid move for piece");
		            }
		        } else if (outcome == Outcome.DRAW) {
		            if (rpiece.getRank() != ropponent.getRank()) {
		                throw new GameError("Invalid move for piece");
		            }
		        }
		    }
		}

		// Bomb piece
		if (opponent instanceof BombPiece) {
		    
		    if (piece instanceof RegularPiece) {
		        RegularPiece rpiece = (RegularPiece) piece;
		        
		        if (rpiece.getRank() == 2 && outcome != Outcome.WIN) {
		            throw new GameError("Invalid move for piece");
		        }
		        
		        if (rpiece.getRank() != 2 && outcome != Outcome.LOSE) {
                    throw new GameError("Invalid move for piece");
                }
		        
		    } else {
		        throw new GameError("Invalid move for piece");
		    }
		}
		
		// scout cannot strike through an occupied position
        if (piece instanceof RegularPiece) {
            RegularPiece rp = (RegularPiece)piece;
            if (rp.getRank() == 1) {
                for (int step=1; step < steps; step++) {
                    Position passPosition = new Position(position,direction,step);
                    if (game.getPiece(passPosition) != null) {
                        throw new GameError("Invalid move for piece");
                    }
                }
            }
        }
        
        if (game.isEnded()) {
            throw new GameError("Invalid move for piece");
        }
        
        // flag is captured
        if (opponent instanceof FlagPiece) {
            game.flagCaptured(opponent.getPlayer());
        }

		
		// Finally, apply the move!
		if (outcome == Outcome.WIN) {
		    game.setPiece(newPosition,piece);
		} else if (outcome == Outcome.LOSE) {
            game.setPiece(newPosition,opponent);
        } else if (outcome == Outcome.DRAW) {
            game.setPiece(newPosition,null);
        }
        game.setPiece(position,null);
        
        Player another;
        if (player == Player.BLUE) {
            another = Player.RED;
        } else {
            another = Player.BLUE;
        }
        
        if (game.noPossibleMoves(player)) {
            game.setCannotMove(player);
        }
        
        if (game.noPossibleMoves(another)) {
            game.setCannotMove(another);
        }
        
        
        
		
	}
}
