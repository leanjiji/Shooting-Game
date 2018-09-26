package input
/*
 * An object that track input from user
 *
 * @FIELD
 * mouseX : x position of the mouse
 * mouseY : y position of the mouse
 * wPressed : check if button w pressed
 * aPressed : check if button a pressed
 * sPressed : check if button s pressed
 * dPressed : check if button d pressed
 * qPressed : check if button q pressed
 * rPressed : check if button r pressed
 * shiftPressed : check if button shift pressed
 * spacePressed : check if button space pressed
 * primaryMousePressed : Check if primary mouse button pressed
 */
object Input{
	var mouseX:Double = 0.0
	var mouseY:Double = 0.0
	var wPressed = false
	var aPressed = false
	var sPressed = false
	var dPressed = false
	var qPressed = false
	var rPressed = false
	var shiftPressed = false
	var spacePressed = false
	var primaryMousePressed = false
}