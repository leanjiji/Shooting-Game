package gameutil.unit.enemy
/*
 * A class that define normal enemy
 * Linear Inheritence : AnyEnemy, AnyUnit, Existenceable
 *
 * @FIELD INHERIT FROM SUPERCLASS
 * level : Game Round
 * hp : Health point of enemy
 * ms : Moving speed of enemy
 * body : Circle represent the appearance of enemy
 * alive : determine whether dead or not
 *
 * @OWN FIELD
 * dmg : Damage of enemy when hit
 */
import scalafx.scene.shape.Circle
import scalafx.scene.paint.Color

import scala.util.Random

import gameutil.ExistenceableCharacteristic
import gameutil.unit.AnyUnit

class Enemy(_hp:Double, val dmg:Double) extends AnyEnemy(_hp, Random.nextInt(1250), Random.nextInt(650)
	, Enemy.radius, Color.Yellow){

	/*
	 * @Deprecated
	 * No longer needed due to direct delay constructor for Circle
	 * Method instE
	 * no @param
	 * initialize circle of the enemy body
	 */
	@deprecated("this method will be removed", "No longer needed for direct initialization")
	def instE = {
		val x = Random.nextInt(1250)
		val y = Random.nextInt(650)
		val radius = Enemy.radius
		super.instE( x, y, radius, Color.Yellow)
	}
}

/*	
 * Companion Object of Enemy
 */
object Enemy extends ExistenceableCharacteristic{

	/*
	 * Method radius
	 * no @param
	 * return the radius of enemy body circle
	 */
	def radius:Double = if(AnyUnit.level < 38) 50-AnyUnit.level*1.25 else 3

	/*
	 * Factory Method
	 * Apply Method for class Enemy
	 * Return new Instance Object of Enemy
	 */
	def apply(_hp:Double, _dmg:Double):Enemy = new Enemy(_hp, _dmg)
}
