package gameutil.unit.enemy
/*
 * An abstract class that define any enermy, which include boss and normal enemy
 * Direct Subclass : Enemy, EnemyBoss
 * Linear Inheritence : AnyUnit, Existenceable
 *
 * @FIELD INHERIT FROM SUPERCLASS
 * body : Circle represent the appearance of enemy
 * alive : determine whether dead or not
 * hp : Health point of enemy
 * level : Game Round
 */
import scalafx.scene.paint.Color

import gameutil.unit.UnitCharacteristic
import gameutil.unit.AnyUnit

abstract class AnyEnemy(_hp:Double, _x:Double, _y:Double, _radius:Double, color:Color) 
	extends AnyUnit(_hp, _x, _y, _radius, color){
}

/*
 * Companion Object of AnyEnemy
 * @FIELD
 * ms : Moving speed of enemy
 */
object AnyEnemy extends UnitCharacteristic{
	var ms:Double = 1
}