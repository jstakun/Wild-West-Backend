package com.openshift.wildwest.controllers;

import java.util.Hashtable;
import java.util.Random;
import org.springframework.context.annotation.Configuration;

import com.openshift.wildwest.models.*;
import com.openshift.wildwest.models.Game.GameMode;

@Configuration
public class GameController {
	
	Hashtable<String, Game> games = new Hashtable<>();

	public Game createGame() {
		Game newGame = new Game();
		Score gameScore = new Score();
		gameScore.setGameID(this.generateGameID());
		gameScore.setScore(0);
		newGame.setScore(gameScore);
		newGame.setGameMode(this.determineGameMode());

		games.put(newGame.getScore().getGameID(), newGame);

		return newGame;
	}

	public Game getGame(String gameID) {
		return this.games.get(gameID);
	}

	public void deleteGame(String gameID) {
		this.games.remove(gameID);
	}

	private String generateGameID() {
		String randomChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder gameID = new StringBuilder();
		Random rnd = new Random();
		while (gameID.length() < 18) {
			int index = (int) (rnd.nextFloat() * randomChars.length());
			gameID.append(randomChars.charAt(index));
		}

		return gameID.toString();
	}

	private GameMode determineGameMode() {
		GameMode currentMode = GameMode.OPENSHIFT;

		// For now, the game mode is determine by an environment variable in the backend pod named GAME_MODE
		// Given that most people will be using OpenShift as their distribution for this game, a sane default
		// of OpenShift was chosen and will be used if no environment variable is defined.
		if (System.getenv().containsKey("GAME_MODE")) {
			// possible game mode options are kube, k, kubernetes, openshift
			String gameEnvironmentVariable = System.getenv("GAME_MODE");
			switch (gameEnvironmentVariable) {
			case "kube":
			case "kubernetes":
			case "k":
				currentMode = GameMode.KUBERNETES;
				break;
			default:
				currentMode = GameMode.OPENSHIFT;
			}
		}
		return currentMode;
	}

}
