package game.controller
/*
 * A class MenuController
 * A controller for the Menu layout
 */
import scalafxml.core.macros.sfxml

import scalafx.Includes._

import game.Game

@sfxml
class MenuController(){

	/*
	 * Method handlePlayGame
	 * no @param
	 * To handle when the button Play Game clicked
	 */
	def handlePlayGame = {
		Game.showGame
	}

	/*
	 * Method handleInstruction
	 * no @param
	 * To handle when the button Instruction clicked
	 */
	def handleInstruction = {
		Game.showInstruction
	}

	/*
	 * Method handleQuitGame
	 * no @param
	 * To handle when the button Quit clicked
	 */
	def handleQuitGame = {
		System.exit(0)
	}
}