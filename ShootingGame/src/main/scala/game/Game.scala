package game
/*
 * This is Main Method
 */
import scala.collection.mutable.ListBuffer
import util.control.Breaks._

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.{Scene, Group}
import scalafx.scene.input.{KeyCode, KeyEvent, MouseEvent, MouseButton}
import scalafx.scene.shape.Rectangle
import scalafx.scene.paint.Color
import scalafx.scene.layout.AnchorPane
import scalafx.animation.AnimationTimer

import scalafxml.core.{NoDependencyResolver, FXMLView, FXMLLoader}

import javafx.{scene => jfxs}

import input.Input
import game.view.ShootingGame
import gameutil.projectiles.{Projectiles, PlayerProjectiles}
import gameutil.unit.enemy.AnyEnemy
import gameutil.regeneration.Regeneration

object Game extends JFXApp{

	// transform path of RootLayout.fxml to InputStream for resource location.
	val rootResource = getClass.getResourceAsStream("RootLayout.fxml")
    // initialize the loader object.
    val loader = new FXMLLoader(null, NoDependencyResolver)
    // Load root layout from fxml file.
    loader.load(rootResource);
    // retrieve the root component BorderPane from the FXML 
    val roots = loader.getRoot[jfxs.layout.BorderPane]

    //initialize Primary Stage
	stage = new PrimaryStage {
		title = "Game"
		resizable = false
		scene = new Scene{
			root = roots

			onMouseMoved = (e:MouseEvent) => {
				Input.mouseX = e.x
				Input.mouseY = e.y
			}//end onMouseMoved

			onKeyPressed = (e:KeyEvent) => {
				if(e.code == KeyCode.W) Input.wPressed = true
				if(e.code == KeyCode.A) Input.aPressed = true
				if(e.code == KeyCode.S) Input.sPressed = true
				if(e.code == KeyCode.D) Input.dPressed = true
				if(e.code == KeyCode.Q) Input.qPressed = true
				if(e.code == KeyCode.R) Input.rPressed = true
				if(e.code == KeyCode.Shift) Input.shiftPressed = true
				if(e.code == KeyCode.Space) {
					if(Input.spacePressed){
						Input.spacePressed = false
					}//end if spacePressed
					else{
						Input.spacePressed = true
					}//end else spacePressed
				}//end if KeyCode.SPACE
			
			}//end onKeyPressed

			onKeyReleased = (e:KeyEvent) => {
				if(e.code == KeyCode.W) Input.wPressed = false
				if(e.code == KeyCode.A) Input.aPressed = false
				if(e.code == KeyCode.S) Input.sPressed = false
				if(e.code == KeyCode.D) Input.dPressed = false
				if(e.code == KeyCode.Q) Input.qPressed = false
				if(e.code == KeyCode.R) Input.rPressed = false
				if(e.code == KeyCode.Shift) Input.shiftPressed = false
			}//end onKeyReleased

			onMousePressed = (e:MouseEvent) => {
				if(e.button == MouseButton.Primary) Input.primaryMousePressed = true
			}//end onMousePressed

			onMouseReleased = (e:MouseEvent) => {
				if(e.button == MouseButton.Primary) Input.primaryMousePressed = false
			}

		}//end new Scene
	}//end Primary Stage

	/*
	 * Method setStageSize
	 * @param
	 * width : Double => width of the stage size
	 * height : Double => height of the stage size
	 * Set the width and height of the stage size
	 */
	def setStageSize(_width:Double, _height:Double){
		stage.width = _width
		stage.height = _height + 63d
	}//end of setStageSize

	/*
	 * Method showMenu
	 * @param
	 * start : Boolean => determine if it is first time open
	 * show Menu on Root Layout
	 */
	def showMenu(start:Boolean) = {
		val resource = getClass.getResourceAsStream("view/Menu.fxml")
		val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource)
        val roots = loader.getRoot[jfxs.layout.AnchorPane]
        this.roots.setCenter(roots)
		start match{
			case false => setStageSize(600, 400)
			case true => 
		}
	}//end def showMenu

	/*
	 * Method showInstruction
	 * no @param
	 * show Instruction on Root Layout
	 */
	def showInstruction() = {
		val resource = getClass.getResourceAsStream("view/Instruction.fxml")
		val loader = new FXMLLoader(null, NoDependencyResolver)
        loader.load(resource)
        val roots = loader.getRoot[jfxs.layout.AnchorPane]
        this.roots.setCenter(roots)
        setStageSize(600, 400)
	}//end def showInstruction

	/*
	 * Method showGame
	 * no @param
	 * show Game on Root Layout
	 */
	def showGame():Unit = {
		this.roots.center = {
			new AnchorPane(){
				children = new Group(){

					var lastTimer = 0L
					var game = new ShootingGame()
					children.add(Rectangle(1250, 650, Color.Black)) //Game Background
					children.add(game.gameText.hpText) //Add gameText
					children.add(game.gameText.killCountText) //Add gameText
					children.add(game.gameText.levelText) //Add gameText
					children.add(game.gameText.damageText) //Add gameText
					children.add(game.player.body) //Add Player

					val timer:AnimationTimer = AnimationTimer(t =>{
      					val delta = (t-lastTimer)/1e9
      					var swordTimer = 0.6
      					
      					//Move the player
      					game.player.move

      					//pause Game
      					if(game.pause){
      						timer.stop
      						children.add(game.gameText.pauseText)
      						val pause:AnimationTimer = AnimationTimer(t => {
	      						if(!game.pause){
	      							children.remove(game.gameText.pauseText)
	      							timer.start
	      						}//end if not game.pause
	      					})//end AnimationTimer pauseTimer
	      					pause.start
      					}//end if game pause

      					//ClearSword when the timer reach
      					if(!game.playerSkill.isUsingSkill){
							for(i <- game.clearSword){
	      						children.remove(i.body)
	      					}//end for loop
	      				}//end if player is not using skill

	      				//Player use skill
      					if(Input.primaryMousePressed) game.playerSkill.useSkill 
      					if(game.playerSkill.isUsingSkill) children.add(game.usingSkill(delta))
      					//rotate the sword
      					game.rotateSkill 
      					//Check if user is shooting
      					game.checkShoot match{ 
      						case Some(x) => children.add(x.body)
      						case None =>
      					} //end match checkProjectiles
      					//Remove outdated projectiles
      					for(i <- game.checkProjectiles) children.remove(i.body)

      					//to check if spawn enemy
      					game.checkEnemy(delta) match{
      						case Some(x) => children.add(x.body)
      						case None =>
      					} //end check spawn enemy

      					//enemy start moving
      					for(i <- game.enemyMove) children.remove(i.body)
      					for(i <- game.bossMove(delta)) children.add(i.body)

      					//projectiles kill people
      					val toBeRemove:(ListBuffer[AnyEnemy], 
      						ListBuffer[Projectiles], 
      						ListBuffer[Regeneration]) = game.projectilesKill
      					for(i <- toBeRemove._1) children.remove(i.body)
      					for(i <- toBeRemove._2) children.remove(i.body)
      					for(i <- toBeRemove._3) children.add(i.body)

      					//pick regeneration
      					for(i <- game.pickRegen) children.remove(i.body)

      					//check if user is death
      					if(game.checkDeath) {
      						timer.stop
      						children.add(game.gameText.gameOverText)
							children.add(game.gameText.restartText)
							children.add(game.gameText.quitText)
							val gameOver:AnimationTimer = AnimationTimer(t => {
	      						if(Input.qPressed || Input.rPressed){
	      							children.remove(game.gameText.gameOverText)
									children.remove(game.gameText.restartText)
									children.remove(game.gameText.quitText)
									if(Input.qPressed){
										showMenu(false)
									} else {
										for(i <- game.restart) children.remove(i.body)
										timer.start
									}
	      						}//end if q or r pressed
	      					})//end AnimationTimer pauseTimer
	      					gameOver.start
      					}//end if user is death

      					game.updateText //update latest game information
      					lastTimer = t //update lastTimer time
      				})//end AnimationTimer timer
      				timer.start
				}//end Group
			}//end AnchorPane
		}//end this.roots.setCenter
		setStageSize(1250, 650)
	}//end def showGame

	showMenu(true)
}