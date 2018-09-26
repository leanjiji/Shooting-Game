package gameutil.skill
/* 
 * A class PlayerSkill that define the player skill
 *
 * @FIELD
 * enrageTimer : to determine the enrageTimer
 * rotateDirection : Direction of the sword rotation
 * types : type of the skills
 * distance : distance traveled for type 3
 * doneRotate : degree rotated for type 3
 */
import scala.util.Random
import scala.collection.mutable.ListBuffer

import scalafx.Includes._
import scalafx.scene.transform.Rotate

import gameutil.projectiles.BossProjectiles
import gameutil.unit.player.Player
import gameutil.unit.AnyUnit

class BossSkill{
	var enrageTimer:Double = 0
	var rotateDirection:Int = 0
	var types:Int = 0
	var distance:Int = 0
	var doneRotate:Int = 0

	/*
	 * Method isUsingSkill
	 * no @param
	 * return if the move still on
	 */
	def isUsingSkill:Boolean = {
		if(enrageTimer > 0) return true
		false
	}

	/*
	 * Method useSkill
	 * no @param
	 * set user use move
	 */
	def useSkill{
		Random.nextInt(4) match {
			case 0 =>
				types = 0
				enrageTimer = 5
			case 1 =>
				types = 1
				enrageTimer = 5
				rotateDirection = if(Random.nextBoolean) 13 else -13
			case 2 =>
				types = 2
				enrageTimer = 5
				rotateDirection = if(Random.nextBoolean) 13 else -13
			case 3 =>
				types = 3
				enrageTimer = 5
				rotateDirection = if(Random.nextBoolean) 27 else -27
				distance = 0
				doneRotate = 0
		}
	}

	/*
	 * Method useSkill
	 * @param
	 * _delta : The time fragment of timer
	 * posX : position X of the boss
	 * posY : position Y of the boss
	 * generate projectile when the player use skill
	 */
	def usingSkill(_delta:Double, playerX:Double, playerY:Double, posX:Double, posY:Double):ListBuffer[BossProjectiles] = {
		enrageTimer -= _delta
		var bossSkill:ListBuffer[BossProjectiles] = new ListBuffer[BossProjectiles]
		types match{
			case 0 =>
				bossSkill += BossProjectiles(playerX, playerY, posX, posY, AnyUnit.level)
				for(i <- 0 to 1){
					val temp =  BossProjectiles(playerX, playerY, posX, posY, AnyUnit.level)
					val rotate = new Rotate(if(i==0) 10 else -10, posX, posY)
					temp.body.transforms += rotate
					bossSkill += temp
				}
			case 1 =>
				for(i <- 0 to 11){
					val temp = new BossProjectiles(playerX, playerY, posX, posY, AnyUnit.level){
						fromX = posX
						fromY = posY
					}
					val rotate = new Rotate(i*30, posX, posY)
					temp.body.transforms += rotate
					bossSkill += temp
				}
				if(rotateDirection < 0) rotateDirection -= 1 else rotateDirection += 1
			case 2 =>
				for(i <- 0 to 23){
					val temp = new BossProjectiles(playerX, playerY, posX, posY, AnyUnit.level){
						fromX = posX
						fromY = posY
					}
					val rotate = new Rotate(i*15, posX, posY)
					temp.body.transforms += rotate
					bossSkill += temp
				}
			case 3 =>
				val temp = BossProjectiles(playerX, playerY, posX, posY, AnyUnit.level)
				temp.body.centerX = temp.posX - temp.dX / temp.dist * distance
				temp.body.centerY = temp.posY - temp.dY / temp.dist * distance
				val rotate = new Rotate(doneRotate, posX, posY)
				temp.body.transforms += rotate
				distance += 4
				doneRotate += rotateDirection
				bossSkill += temp
		}
		bossSkill
	}


}

/*
 * Companion Object of BossSkill
 */
object BossSkill{

	/*
	 * Factory Method
	 * Apply Method for class PlayerSkill
	 * Return new Instance Object of PlayerSkill
	 */
	def apply():BossSkill = new BossSkill()
}