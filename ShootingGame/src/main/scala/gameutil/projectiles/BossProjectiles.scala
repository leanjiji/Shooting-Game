package gameutil.projectiles
/*
 * A class that define BossProjectile
 * Linear Inheritence : Projectiles, Existenceable
 *
 * @FIELD INHERIT FROM SUPERCLASS
 * body : Circle represent the appearance of enemy
 * alive : determine whether dead or not
 * targetX : x position of projectile target
 * targetY : y position of projectile target
 * dmg : Damage of the projectile
 * distTravel : distance that had travel
 * ms : Movement speed of projectile
 *
 * @OWN FIELD
 * fromX : x position of from
 * fromY : y position of from
 */
import scalafx.scene.shape.Circle
import scalafx.scene.paint.Color

class BossProjectiles(_targetX:Double, _targetY:Double, _x:Double, _y:Double, _dmg:Double) 
	extends Projectiles(_targetX, _targetY, _x, _y, _dmg, Color.White){
	var fromX:Double = 0
	var fromY:Double = 0
}

/*
 * Companion Object of BossProjectiles
 */
object BossProjectiles{
	/*
	 * Factory Method
	 * Apply Method for class BossProjectiles
	 * Return new Instance Object of BossProjectiles
	 */
	def apply(_targetX:Double, _targetY:Double, _x:Double, _y:Double, _dmg:Double):BossProjectiles = {
		new BossProjectiles(_targetX, _targetY, _x, _y, _dmg)
	}
}