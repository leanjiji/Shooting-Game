package game.view
/*
 * A class GameText that used to define the text of the game
 *
 * @FIELD
 * hpText : Text that show hp of player
 * killCountText : Text that show kill count of player
 * levelText : Text that show level of player
 * damageText : Text that show damage of the player
 * pauseText : Text that shown when user press pause
 * gameOverText : Text that shown whwen game over
 * restartText : Text that shown when game over
 * quitText : Text that shown when game over
 */
import scalafx.scene.paint.Color
import scalafx.scene.text.{Text, Font}

import gameutil.unit.player.Player
import gameutil.unit.AnyUnit

class GameText(_hp:Double, _killCount:Int){

	var hpText:Text = new Text(20, 30, "HP : " + _hp)
	hpText.setFont(Font.font("Verdana", 20))
	hpText.setFill(Color.White)

	var killCountText:Text = new Text(20, 60, "Kill Count : " + _killCount)
	killCountText.setFont(Font.font("Verdana", 20))
	killCountText.setFill(Color.White)

	var levelText = new Text(20,90,"Level : " + AnyUnit.level)
  	levelText.setFont(Font.font("Verdana",20))
  	levelText.setFill(Color.White)

  	var damageText = new Text(20,120,"Damage : " + Player.damage.toInt)
  	damageText.setFont(Font.font("Verdana",20))
  	damageText.setFill(Color.White)

  	var pauseText = new Text(320, 400, "PAUSED")
  	pauseText.setFont(Font.font("Verdana", 150))
  	pauseText.setFill(Color.Red)

  	var gameOverText = new Text(200, 280, "Game Over")
  	gameOverText.setFont(Font.font("Verdana", 150))
  	gameOverText.setFill(Color.Red)

  	var restartText = new Text(380, 380, "Press \"R\" to restart")
  	restartText.setFont(Font.font("Verdana", 50))
  	restartText.setFill(Color.Red)

  	var quitText = new Text(420, 440, "Press \"Q\" to quit")
  	quitText.setFont(Font.font("Verdana", 50))
  	quitText.setFill(Color.Red)

  	/* 
	 * updateText
	 * @param
	 * _hp : Double => Health Point of the player to be updated to the text
	 * _killCount => killCount of the player to be updated to the text
	 * update all the Text
	 */
  	def updateText(_hp:Double, _killCount:Int) = {
  		hpText.setText("HP : " + _hp)
		killCountText.setText("Kill Count : " + _killCount)
		levelText.setText("Level : " + AnyUnit.level)
		damageText.setText("Damage : " + Player.damage.toInt)
  	}
}

/*
 * Companion Object of class GameText
 */
object GameText{
	/*
	 * Factory Method
	 * Apply Method for class GameText
	 * Return new Instance Object of GameText
	 */
	def apply(_hp:Double, _killCount:Int):GameText = new GameText(_hp, _killCount)
}