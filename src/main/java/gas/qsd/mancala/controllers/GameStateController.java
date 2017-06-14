package gas.qsd.mancala.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
//import ch.qos.logback.classic.Logger;

import gas.qsd.mancala.model.MancalaGame;
import gas.qsd.mancala.service.MancalaService;

@RestController
public class GameStateController {
	
	private static Logger LOG;
		
	@RequestMapping(value = "/", method = RequestMethod.PUT, produces="text/plain")
	public String move(@RequestBody String shortHandGameState, @RequestParam("move") String move){
		validateGameInput(shortHandGameState, move);
		return new MancalaService().getNewShortHandGameStateFrom(shortHandGameState, move);
	}
	
	@ExceptionHandler({java.lang.IllegalArgumentException.class})
	public void handleIllegalArgumentException(Exception e, HttpServletResponse response) throws IOException {
		response.getWriter().write(e.getMessage());
	    response.setStatus(HttpStatus.BAD_REQUEST.value());
	    response.flushBuffer();
	    LOG.warn(e.getMessage(), e);
	}
	
	@ExceptionHandler({java.lang.Exception.class})
	public void handleException(Exception e, HttpServletResponse response) throws IOException {
		response.getWriter().write("I cannot handle your message at this time for I am but a teapot");
	    response.setStatus(HttpStatus.I_AM_A_TEAPOT.value());
	    response.flushBuffer();
	    LOG.error(e.getMessage(), e);
	}
	
	private void validateGameInput(String shortHandGameState, String move){
		final String patternDigit = "\\d+";
		final String patternPlayerInfo = "[1-2]";
		final String patternZero = "0";
		final String patternMove = "[1-" + Integer.toString((MancalaGame.POTS_PER_PLAYER-1)) +"]";
		if(!move.matches(patternMove)){
			throw new IllegalArgumentException("Move must be a digit from 1 to " + Integer.toString((MancalaGame.POTS_PER_PLAYER-1)));
		}
		String[] tokens = shortHandGameState.split(",");
				
		if(!tokens[0].trim().matches(patternPlayerInfo)){
			throw new IllegalArgumentException("First digit must be from 1 or 2, as it the id of the player that has the turn");
			
		}
		if(!tokens[1].trim().matches(patternZero)){
			throw new IllegalArgumentException("Second digit must 0, as it is the id of the player that won the game");
		}
		if(tokens.length != (MancalaGame.POTS_PER_PLAYER * 2) + 2){//+2 for the first two digits refer to players, not to pots
			throw new IllegalArgumentException("Number of digits must be " + ((MancalaGame.POTS_PER_PLAYER * 2) + 2));
		}
		for(String token:tokens){
			if(!token.trim().matches(patternDigit)){
				throw new IllegalArgumentException("Input "+ token +" is not a digit");
			}
		}
	}
	
	
}
