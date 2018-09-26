package gameutil.skill
/* 
 * A class PlayerSkill that define the player skill
 *
 * @FIELD
 * swordTimer : to determine the swordTimer
 * rotateDirection : Direction of the sword rotation
 */
import scala.util.Random

import gameutil.projectiles.PlayerProjectiles
import gameutil.unit.player.Player

class PlayerSkill{
	var swordTimer:Double = 0
	var rotateDirection:Int = 0

	/*
	 * Method isUsingSkill
	 * no @param
	 * return if the move still on
	 */
	def isUsingSkill:Boolean = {
		if(swordTimer > 0) return true
		false
	}

	/*
	 * Method useSkill
	 * no @param
	 * set user use move
	 */
	def useSkill{
		if(!isUsingSkill){
			swordTimer = 0.5
			if(Random.nextBoolean){
				rotateDirection = 27
			} else
			{
				rotateDirection = -27
			}
		}
	}

	/*
	 * Method useSkill
	 * @param
	 * _delta : The time fragment of timer
	 * posX : position X of the player
	 * posY : position Y of the player
	 * generate projectile when the player use skill
	 */
	def usingSkill(_delta:Double, posX:Double, posY:Double):PlayerProjectiles = {
		swordTimer -= _delta
		PlayerProjectiles(posX, posY, Player.damage*10)
	}
}

/*
 * Companion Object of PlayerSkill
 */
object PlayerSkill{

	/*
	 * Factory Method
	 * Apply Method for class PlayerSkill
	 * Return new Instance Object of PlayerSkill
	 */
	def apply():PlayerSkill = new PlayerSkill()
}