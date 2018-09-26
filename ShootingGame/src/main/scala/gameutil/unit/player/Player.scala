package gameutil.unit.player
/*
 * A class that define player
 * Linear inheritence : AnyUnit, Existenceable
 *
 * @FIELD INHERIT FROM SUPERCLASS
 * level : Game Round
 * hp : Health point of enemy
 * body : Circle represent the appearance of enemy
 * alive : determine whether dead or not
 */
import scalafx.scene.shape.Circle
import scalafx.scene.paint.Color

import gameutil.ExistenceableCharacteristic
import gameutil.unit.{AnyUnit, UnitCharacteristic}
import input.Input

class Player() extends AnyUnit(100, 625, 325, Player.radius, Color.Pink){

	/*
	 * @Deprecated
	 * No longer needed due to direct delay constructor for Circle
	 * Method instE
	 * no @param
	 * initialize circle of the player body
	 */
	@deprecated("this method will be removed", "No longer needed for direct initialization")
	def instE = {
		val x = 625
		val y = 325
		val radius = Player.radius
		super.instE(x, y, radius, Color.Pink)
	}

	/*
	 * Method move
	 * no @param
	 * Player move around the game
	 */
	def move{
		if(Input.wPressed) { if(this.posY > 0) body.centerY = (this.posY - Player.ms)}
		if(Input.aPressed) { if(this.posX > 0) body.centerX = (this.posX - Player.ms)}
		if(Input.sPressed) { if(this.posY < 650) body.centerY = (this.posY + Player.ms)}
		if(Input.dPressed) { if(this.posX < 1250) body.centerX = (this.posX + Player.ms)}
	}

}

/*	
 * Companion Object of Player
 */
object Player extends ExistenceableCharacteristic with UnitCharacteristic{

	var ms = 10.0

	var damage:Double = 100

	/*
	 * Method radius
	 * no @param
	 * return the radius of player body circle
	 */
	def radius:Double = 30

	/*
	 * Factory Method
	 * Apply Method for class Player
	 * Return new Instance Object of Player
	 */
	def apply():Player = new Player()
}
