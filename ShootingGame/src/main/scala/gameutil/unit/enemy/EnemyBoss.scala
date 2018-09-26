package gameutil.unit.enemy
/*
 * A class that define normal enemy
 * Linear inheritence : AnyEnemy, AnyUnit, Existenceable
 *
 * @FIELD INHERIT FROM SUPERCLASS
 * level : Game Round
 * hp : Health point of enemy
 * ms : Moving speed of enemy
 * body : Circle represent the appearance of enemy
 * alive : determine whether dead or not

 * @OWN FIELD
 * skillCd : determine if boss is shooting
 */
import scalafx.scene.shape.Circle
import scalafx.scene.paint.Color

import gameutil.ExistenceableCharacteristic

class EnemyBoss(_hp:Double) extends AnyEnemy(_hp, 625 , 325, EnemyBoss.radius, Color.White){

	var skillCd:Double = 5

	/*
	 * @Deprecated
	 * No longer needed due to direct delay constructor for Circle
	 * Method instE
	 * no @param
	 * initialize circle of the enemy body
	 */
	@deprecated("this method will be removed", "No longer needed for direct initialization")
	def instE = {
		val x = 625
		val y = 325
		val radius = EnemyBoss.radius
		super.instE(x, y, radius, Color.White)
	}

	/*
	 * Method inCoolDown
	 * @param
	 * delta : Double => the slice timeframe to be duducted to cooldown
	 * return if the skill is in cooldown
	 */
	def inCoolDown(delta:Double):Boolean = {
		skillCd -= delta
		if(skillCd <= 0){
			skillCd = 10
			false
		} else {
			true
		}
	}

}

/*	
 * Companion Object of EnemyBoss
 */
object EnemyBoss extends ExistenceableCharacteristic{

	/*
	 * Method radius
	 * no @param
	 * return the radius of enemy body circle
	 */
	def radius:Double = 20.0

	/*
	 * Factory Method
	 * Apply Method for class EnemyBoss
	 * Return new Instance Object of EnemyBoss
	 */
	def apply(_hp:Double):EnemyBoss = new EnemyBoss(_hp)
}
