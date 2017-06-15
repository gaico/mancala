package gas.qsd.mancala.service;

import gas.qsd.mancala.model.MancalaGame;
import gas.qsd.mancala.translator.MancalaTranslator;

public class MancalaService {

	public String getNewShortHandGameStateFrom(String shortHandGameState, String move) {
		MancalaTranslator translator = new MancalaTranslator();
		MancalaGame game = translator.gameFromShortHandGameState(shortHandGameState);
		game.move(Integer.parseInt(move));
		return translator.shortHandStringFromGame(game);
	}
}
