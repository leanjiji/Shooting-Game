package gameutil
/*
 * An abstract class that
 * Direct Subclass : AnyEnemy, Projectiles
 *
 * @FIELD
 * body : Circle represent the appearance of existenceable
 * alive : determine whether it exist
 */
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle

abstract class Existenceable(_x:Double, _y:Double, _radius:Double, color:Color){
	var body = new Circle {
		centerX = _x
		centerY = _y
		radius = _radius
		fill = color
	}
	var alive:Boolean = true

	/*
	 * Method posX
	 * no @param
	 * return position x of Existence Circle
	 */
	def posX:Double = body.centerX.toDouble

	/*
	 * Method posY
	 * no @param
	 * return position y of Existence Circle
	 */
	def posY:Double = body.centerY.toDouble

}