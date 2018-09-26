package gameutil.projectiles
/*
 * A class that define PlayerProjectile
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
 */
import scalafx.scene.shape.Circle
import scalafx.scene.paint.Color

import input.Input

class PlayerProjectiles(_x:Double, _y:Double, _dmg:Double) 
	extends Projectiles(Input.mouseX, Input.mouseY, _x, _y, _dmg, Color.Pink){
}

/*
 * Companion Object of PlayerProjectiles
 */
object PlayerProjectiles{
	/*
	 * Factory Method
	 * Apply Method for class PlayerProjectiles
	 * Return new Instance Object of PlayerProjectiles
	 */
	def apply(_x:Double, _y:Double, _dmg:Double):PlayerProjectiles = {
		new PlayerProjectiles(_x, _y, _dmg)
	}
}