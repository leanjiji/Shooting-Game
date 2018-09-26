package gameutil.regeneration
/*
 * A class that define regeneration
 * Linear inheritence : Existenceable
 *
 * @FIELD INHERIT FROM SUPERCLASS
 * body : Circle represent the appearance of enemy
 * alive : determine whether dead or not
 */
import scalafx.scene.shape.Circle
import scalafx.scene.paint.Color

import scala.util.Random

import gameutil.{Existenceable, ExistenceableCharacteristic}
import gameutil.unit.AnyUnit

class Regeneration 
	extends Existenceable(Random.nextInt(1250), Random.nextInt(650), Regeneration.radius, Color.Green){
}

/*
 * Companion object of Regeneration
 */
object Regeneration extends ExistenceableCharacteristic with RegenerationCharacteristic{

	/*
	 * Method radius
	 * no @param
	 * return the radius of projectiles body circle
	 */
	def radius:Double = 10

	/*
	 * Method hpRegen
	 * no @param
	 * return hpRegen of the Regeneration
	 */
	def hpRegen:Double = 5.0 * AnyUnit.level

	/*
	 * Factory Method
	 * Apply Method for class Regeneration
	 * Return new Instance Object of Regeneration
	 */
	def apply:Regeneration = new Regeneration
}