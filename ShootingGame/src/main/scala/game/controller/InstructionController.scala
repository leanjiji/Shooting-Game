package game.controller
/*
 * A class InstructionController
 * A controller for the Instruction layout
 */
import scalafxml.core.macros.sfxml

import scalafx.Includes._

import game.Game

@sfxml
class InstructionController(){

	/*
	 * Method handleOk
	 * no @param
	 * To handle when the button Ok clicked
	 */
	def handleOk = {
		Game.showMenu(false)
	}
}