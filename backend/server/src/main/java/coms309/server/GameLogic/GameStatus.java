package coms309.server.GameLogic;


/**
 * status of the game
 * 0 = init
 * 1 = starting
 * 2 = waiting
 * 3 = in-round
 * 4 = paused
 * 5 = game-over
 */
public enum GameStatus
{
	INIT,
	START,
	WAIT,
	PLAY,
	PAUSE,
	END
}
