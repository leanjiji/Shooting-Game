package gameutil.unit
/*
 * An abstract class that define any Unit, which include enemy and player
 * Direct Subclass : AnyEnemy, Player
 * Linear Inheritence : Existenceable
 *
 * @FIELD INHERIT FROM SUPERCLASS
 * body : Circle represent the appearance of enemy
 * alive : determine whether dead or not
 *
 * @OWN FIELD
 * hp : Health point of Unit
 */
import scalafx.scene.paint.Color

import gameutil.Existenceable

abstract class AnyUnit(var hp:Double, _x:Double, _y:Double, _radius:Double, color:Color) 
	extends Existenceable(_x, _y, _radius, color){

	/*
	 * @Deprecated
	 * No longer needed due to direct delay constructor for Circle
	 * Method instE
	 * For initializing circle
	 * @param
	 * x : Integer => Position x of circle
	 * y : Integer => position y of circle
	 * _radius : Integer => Radius of circle
	 * color : Color => Color of Circle
	 */
	@deprecated("this method will be removed", "No longer needed for direct initialization")
	def instE(x:Double, y:Double, _radius:Double, color:Color) = {
		body.centerX = x
		body.centerY = y
		body.radius = _radius
		body.fill = color
	}
}

/*
 * Companion Object of AnyUnit
 * @FIELD
 * level : game level
 */
object AnyUnit{
	var level:Int = 1
}