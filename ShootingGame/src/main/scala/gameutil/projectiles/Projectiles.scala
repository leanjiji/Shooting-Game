package gameutil.projectiles
/*
 * An abstract class that define projectile that can be shoot out
 * Direct Subclass : PlayerProjectiles, BossProjectiles
 * Linear Inheritence : Existenceable
 *
 * @FIELD INHERIT FROM SUPERCLASS
 * body : Circle represent the appearance of enemy
 * alive : determine whether dead or not
 *
 * @OWN FIELD
 * targetX : x position of projectile target
 * targetY : y position of projectile target
 * x : initial x position of the projectile
 * y : intiial y position of the projectile
 * dmg : Damage of the projectile
 * distTravel : distance that had travel
 */
import scalafx.scene.paint.Color

import gameutil.Existenceable
import gameutil.ExistenceableCharacteristic

abstract class Projectiles(val targetX:Double, val targetY:Double, val x:Double, val y:Double, val dmg:Double, color:Color) 
	extends Existenceable(x, y, Projectiles.radius, color){
	var distTravel:Double = 0

	/*
	 * Method dX
	 * no @param
	 * return dX in graph
	 */
	def dX:Double = x - targetX

	/*
	 * Method dY
	 * no @param
	 * return dY in graph
	 */
	def dY:Double = y - targetY

	/*
	 * Method dist
	 * no @param
	 * return distance of initial position and target position
	 */
	def dist:Double = math.sqrt(this.dX * this.dX + this.dY * this.dY)
}
/*	
 * Companion Object of Projectiles
 * @FIELD
 * ms : Movement speed of projectile
 */
object Projectiles extends ExistenceableCharacteristic with ProjectilesCharacteristic{

	/*
	 * Method ms
	 * no @param
	 * return ms of the projectiles
	 */
	def ms:Double = 15

	/*
	 * Method radius
	 * no @param
	 * return the radius of projectiles body circle
	 */
	def radius:Double = 5.0
}