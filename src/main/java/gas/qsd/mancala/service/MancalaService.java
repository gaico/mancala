package gas.qsd.mancala.service;

import java.util.Arrays;

import gas.qsd.mancala.model.MancalaGame;
import gas.qsd.mancala.model.Player;
import gas.qsd.mancala.model.Pot;

public class MancalaService {

	public String getNewShortHandGameStateFrom(String shortHandGameState, String move) {
		MancalaTranslator translator = new MancalaTranslator();
		MancalaGame game = translator.gameFromShortHandGameState(shortHandGameState);
		game.move(Integer.parseInt(move));
		return translator.shortHandStringFromGame(game);
	}
}
