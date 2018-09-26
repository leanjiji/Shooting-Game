package game.view
/*
 * A class ShootingGame that define the view of the ShootingGame
 *
 * @FIELD
 * player : Player of the game
 * killCount : Kill Count of the player
 * gameText : Text of the game
 * playerSkill : Skill of the player
 * playerSword : Colelction of user sword
 * playerProjectiles : Collection of player projectiles
 * enemyGroup : Collection of the enemy
 * enemyBoss : Boss of the enemy
 * regenGroup : Collection of regeneration
 * bossProjectiles : Collection of boss projectiles
 * bossSkill : Skill of the boss
 * bossSkillGroup : Collection of boss skills
 * spawnDelay : spawn delay of the enemy
 * roundKillCount : Kill Count of the player for the given round
 */
import scala.collection.mutable.ListBuffer
import scala.util.Random

import util.control.Breaks._

import scalafx.Includes._
import scalafx.scene.shape.Circle
import scalafx.scene.transform.Rotate
import scalafx.geometry.Point2D

import gameutil.unit.player.Player
import gameutil.skill.{PlayerSkill, BossSkill}
import gameutil.projectiles.{Projectiles, PlayerProjectiles, BossProjectiles}
import gameutil.unit.enemy.{AnyEnemy, Enemy, EnemyBoss}
import gameutil.unit.AnyUnit
import gameutil.regeneration.Regeneration
import gameutil.Existenceable

import input.Input

class ShootingGame{
	var player = Player()
	var killCount:Int = 0
	var gameText:GameText = GameText(player.hp, killCount)
	var playerSkill = PlayerSkill()
	var playerSword = new ListBuffer[PlayerProjectiles]
	var playerProjectiles = new ListBuffer[PlayerProjectiles]
	var enemyGroup = new ListBuffer[Enemy]
	var enemyBoss:Option[EnemyBoss] = Option(null)
	var regenGroup:ListBuffer[Regeneration] = new ListBuffer[Regeneration]
	var bossProjectiles = new ListBuffer[BossProjectiles]
	var bossSkill = BossSkill()
	var bossSkillGroup = new ListBuffer[BossProjectiles]
	var spawnDelay:Double = 10
	var roundKillCount:Int = 0
	AnyUnit.level = 1
	Player.damage = 100
	
	/* 
	 * Method updateText
	 * no @param
	 * update all the Text
	 */
	def updateText{
		gameText.updateText(player.hp, killCount)
	}

	/*
	 * Method pause
	 * no @param
	 * check if game is paused
	 */
	def pause:Boolean = {
		if(Input.spacePressed) return true
		else return false
	}

	/*
	 * Method checkUsingSkill
	 * no @param
	 * return if using skill
	 */
	def usingSkill(_delta:Double):Circle = {
		val projectiles = playerSkill.usingSkill(_delta, player.posX, player.posY)
		playerSword += projectiles
		projectiles.body
	}

	/*
	 * Method rotateSkill
	 * no @param
	 * Make rotate projectiles
	 */
	def rotateSkill{
		for(i <- playerSword){
			i.body.centerX = i.posX - i.dX / i.dist * 10
			i.body.centerY = i.posY - i.dY / i.dist * 10
			if(playerSword.size > 15)
			{
				val rotate = new Rotate(playerSkill.rotateDirection, player.posX, player.posY)
				i.body.transforms += rotate
			}
		}
	}

	/*
	 * Method clearSword
	 * no @param
	 * Clear useless iteration in collection
	 */
	def clearSword:ListBuffer[PlayerProjectiles] = {
		var toBeRemove:ListBuffer[PlayerProjectiles] = playerSword.clone
		playerSword.clear
		toBeRemove
	}

	/*
	 * Method checkShoot
	 * no @param
	 * return PlayerProjectiles if user is shooting
	 */
	def checkShoot():Option[PlayerProjectiles] = {
		//Check if using is shooting
		if(Input.shiftPressed){
			var temp = PlayerProjectiles(player.posX, player.posY, Player.damage)
			playerProjectiles += temp
			Option(temp)
		}else{
			Option(null)
		}//end if else user is shooring
	}

	/*
	 * Method checkProjectiles
	 * no @param
	 * return list of projectiles that to be remove
	 */
	def checkProjectiles:ListBuffer[Projectiles] = {
		var toBeRemove:ListBuffer[Projectiles] = new ListBuffer[Projectiles]
		for(i <- playerProjectiles){
			if(i.distTravel > 1000){
				toBeRemove += playerProjectiles.remove(playerProjectiles.indexOf(i))
			} else {
				i.distTravel += Projectiles.ms
				i.body.centerX = i.posX - i.dX / i.dist * Projectiles.ms
				i.body.centerY = i.posY - i.dY / i.dist * Projectiles.ms
			}
		}
		for(i <- bossProjectiles){
			if(i.distTravel > 1000){
				toBeRemove += bossProjectiles.remove(bossProjectiles.indexOf(i))
			} else {
				i.distTravel += Projectiles.ms
				i.body.centerX = i.posX - i.dX / i.dist * Projectiles.ms
				i.body.centerY = i.posY - i.dY / i.dist * Projectiles.ms
			}
		}
		for(i <- bossSkillGroup){
			bossSkill.types match{
				case 0 =>
					if(i.distTravel > 1000){
						toBeRemove += bossSkillGroup.remove(bossSkillGroup.indexOf(i))
					} else {
						i.distTravel += Projectiles.ms
						i.body.centerX = i.posX - i.dX / i.dist * Projectiles.ms
						i.body.centerY = i.posY - i.dY / i.dist * Projectiles.ms
					}
				case 1 =>
					if(i.distTravel > 300){
						toBeRemove += bossSkillGroup.remove(bossSkillGroup.indexOf(i))
					} else {
						i.distTravel += Projectiles.ms
						i.body.centerX = i.posX - i.dX / i.dist * Projectiles.ms
						i.body.centerY = i.posY - i.dY / i.dist * Projectiles.ms
						val rotate = new Rotate(bossSkill.rotateDirection, i.fromX, i.fromY)
						i.body.transforms += rotate
					}
				case 2 =>
					if(i.distTravel > 300){
						toBeRemove += bossSkillGroup.remove(bossSkillGroup.indexOf(i))
					} else {
						i.distTravel += Projectiles.ms
						i.body.centerX = i.posX - i.dX / i.dist * Projectiles.ms
						i.body.centerY = i.posY - i.dY / i.dist * Projectiles.ms
						val rotate = new Rotate(bossSkill.rotateDirection, i.fromX, i.fromY)
						i.body.transforms += rotate
					}
				case 3 =>
					if(!bossSkill.isUsingSkill){
						toBeRemove += bossSkillGroup.remove(bossSkillGroup.indexOf(i))
					}
			}
		}
		toBeRemove
	}

	/*
	 * Method checkSpawnDelay
	 * no @param
	 * update the spawnDelay
	 * return option of enemy that to be spawn
	 */
	def checkEnemy(delta:Double):Option[AnyEnemy] = {
		if(spawnDelay > 0){
			spawnDelay -= delta
			Option(null)
		} else {
			if(AnyUnit.level % 5 == 0){
				if(enemyGroup.size > 0){
					spawnDelay = 5
					Option(null)
				} else {
					enemyBoss match{	
						case Some(x) =>
							Option(null)
						case None => 
							enemyBoss = Option(EnemyBoss(100 * AnyUnit.level * AnyUnit.level * killCount))
							enemyBoss
					}
				}
			} else {
				spawnDelay = if(AnyUnit.level < 27 )0.8 - AnyUnit.level * 0.03 else 0.05
				if(enemyGroup.size < AnyUnit.level + 5){
					var temp = Enemy(100 * (AnyUnit.level * AnyUnit.level * AnyUnit.level), AnyUnit.level * 2)
					enemyGroup += temp
					Option(temp)
				} else{
					Option(null)
				}
			}
		}
	}

	/*
	 * Method enemyMove
	 * no @param
	 * move Enemy
	 * return list of enemy that needed to be remove
	 */
	def enemyMove:ListBuffer[Enemy] = {
		val temp:ListBuffer[Enemy] = new ListBuffer[Enemy]
		for(i <- enemyGroup if i.alive){
			val dx = player.posX - i.posX
			val dy = player.posY - i.posY
			val dist = math.sqrt(dx*dx+dy*dy)
			i.body.centerX = i.posX + dx / dist * AnyEnemy.ms
			i.body.centerY = i.posY + dy / dist * AnyEnemy.ms
			if(i.body.radius.toDouble + player.body.radius.toDouble > dist){
				player.hp -= i.dmg
				temp += enemyGroup.remove(enemyGroup.indexOf(i))
			}
		}
		temp
	}

	/*
	 * Method bossMove
	 * @param
	 * delta : Double => timeslice of the timer
	 * return list of projectile shooted by boss
	 */
	def bossMove(delta:Double):ListBuffer[BossProjectiles] = {
		val shootByBoss:ListBuffer[BossProjectiles] = new ListBuffer[BossProjectiles]
		enemyBoss match{
			case Some(x) =>
				if(x.inCoolDown(delta)){
					if(bossSkill.isUsingSkill){
						//Boss is using skill
						for(i <- bossSkill.usingSkill(delta, player.posX, player.posY, x.posX, x.posY)){
							bossSkillGroup += i
							shootByBoss += i
						}
						if(bossSkill.types == 1 || bossSkill.types == 2){
							val dx = player.posX - x.posX
							val dy = player.posY - x.posY
							val dist = math.sqrt(dx*dx+dy*dy)
							x.body.centerX = x.posX + dx / dist * 0.75
							x.body.centerY = x.posY + dy / dist * 0.75
						} else if (bossSkill.types == 3){
							if(bossProjectiles.size < 25){
								//Boss Shoot
								val temp = BossProjectiles(player.posX, player.posY, x.posX, x.posY, AnyUnit.level)
								bossProjectiles += temp
								shootByBoss += temp
							} else {
								//Boss Move
								val dx = player.posX - x.posX
								val dy = player.posY - x.posY
								val dist = math.sqrt(dx*dx+dy*dy)
								x.body.centerX = x.posX + dx / dist * AnyEnemy.ms
								x.body.centerY = x.posY + dy / dist * AnyEnemy.ms
							}
						}
					} else {
						if(bossProjectiles.size < 25){
							//Boss Shoot
							val temp = BossProjectiles(player.posX, player.posY, x.posX, x.posY, AnyUnit.level)
							bossProjectiles += temp
							shootByBoss += temp
						} else {
							//Boss Move
							val dx = player.posX - x.posX
							val dy = player.posY - x.posY
							val dist = math.sqrt(dx*dx+dy*dy)
							x.body.centerX = x.posX + dx / dist * AnyEnemy.ms
							x.body.centerY = x.posY + dy / dist * AnyEnemy.ms
						}
					}
				} else {
					//Boss not in cooldown and gonna use skill
					bossSkill.useSkill
					for(i <- bossSkill.usingSkill(delta, player.posX, player.posY, x.posX, x.posY)) {
						bossSkillGroup += i
						shootByBoss += i
					}
				}

				//Check Collission
				if(x.body.radius.toDouble + player.body.radius.toDouble > 
					findDistance(player.posX, player.posY, x.posX, x.posY)){
					player.hp = 0
				}
			case None =>
		}
		shootByBoss
	}

	/*
	 * Method projectilesKill
	 * no @param
	 * return list of enemy, projectile that to be remove and also regeneration that needed to be add
	 */
	def projectilesKill:(ListBuffer[AnyEnemy], ListBuffer[Projectiles], ListBuffer[Regeneration]) = {
		var enemyToBeRemove = new ListBuffer[AnyEnemy]
		var projectilesToBeRemove = new ListBuffer[Projectiles]
		var regen:ListBuffer[Regeneration] = new ListBuffer[Regeneration]
		//test Player Projectiles
		for(i <- playerProjectiles){
			breakable{
				enemyBoss match{
					case Some(x) => {
						if(i.body.radius.toDouble + x.body.radius.toDouble > findDistance(i.posX, i.posY, x.posX, x.posY)){
							x.hp -= i.dmg
							projectilesToBeRemove += playerProjectiles.remove(playerProjectiles.indexOf(i))
							if(x.hp <= 0){
								enemyToBeRemove += x
								enemyBoss = Option(null)
								val temp = new Regeneration
								regenGroup += temp
								regen += temp
								AnyUnit.level += 1
								killCount += 1
								Player.damage *= 1.6
							}
							break
						}
					}
					case None =>
				}
				for(j <- enemyGroup){
					if(i.body.radius.toDouble + j.body.radius.toDouble > findDistance(i.posX, i.posY, j.posX, j.posY)){
						j.hp -= i.dmg
						projectilesToBeRemove += playerProjectiles.remove(playerProjectiles.indexOf(i))
						if(j.hp <= 0){
							enemyToBeRemove += enemyGroup.remove(enemyGroup.indexOf(j))
							this.addKillCount
							if(Random.nextInt(10) == 0) {
								val temp = new Regeneration
								regenGroup += temp
								regen += temp
							}
						}
						break
					}
				}
				for(j <- bossProjectiles){
					if(i.body.radius.toDouble + j.body.radius.toDouble > findDistance(i.posX, i.posY, j.posX, j.posY)){
						projectilesToBeRemove += playerProjectiles.remove(playerProjectiles.indexOf(i))
						projectilesToBeRemove += bossProjectiles.remove(bossProjectiles.indexOf(j))
						break
					}
				}
				for(j <- bossSkillGroup){
					val pointj :Point2D = j.body.localToParent(j.posX,j.posY);
					if(i.body.radius.toDouble + j.body.radius.toDouble > findDistance(i.posX, i.posY, pointj.x, pointj.y)){
						projectilesToBeRemove += playerProjectiles.remove(playerProjectiles.indexOf(i))
						projectilesToBeRemove += bossSkillGroup.remove(bossSkillGroup.indexOf(j))
						break
					}
				}
			}//end breakable
		}
		//test Player Sword
		for(i <- playerSword){
			breakable{
				val point :Point2D = i.body.localToParent(i.posX,i.posY);
				enemyBoss match{
					case Some(x) => {
						if(i.body.radius.toDouble + x.body.radius.toDouble > findDistance(point.x, point.y, x.posX, x.posY)){
							x.hp -= i.dmg
							projectilesToBeRemove += playerSword.remove(playerSword.indexOf(i))
							if(x.hp <= 0){
								enemyToBeRemove += x
								enemyBoss = Option(null)
								val temp = new Regeneration
								regenGroup += temp
								regen += temp
								AnyUnit.level += 1
								killCount += 1
								Player.damage *= 1.6
							}
							break
						}
					}
					case None =>
				}
				for(j <- enemyGroup){
					if(i.body.radius.toDouble + j.body.radius.toDouble > findDistance(point.x, point.y, j.posX, j.posY)){
						j.hp -= i.dmg
						projectilesToBeRemove += playerSword.remove(playerSword.indexOf(i))
						if(j.hp <= 0){
							enemyToBeRemove += enemyGroup.remove(enemyGroup.indexOf(j))
							this.addKillCount
							if(Random.nextInt(10) == 0) {
								val temp = new Regeneration
								regenGroup += temp
								regen += temp
							}
						}
						break
					}
				}
				for(j <- bossProjectiles){
					if(i.body.radius.toDouble + j.body.radius.toDouble > findDistance(point.x, point.y, j.posX, j.posY)){
						projectilesToBeRemove += playerSword.remove(playerSword.indexOf(i))
						projectilesToBeRemove += bossProjectiles.remove(bossProjectiles.indexOf(j))
						break
					}
				}
				for(j <- bossSkillGroup){
					val pointj :Point2D = j.body.localToParent(j.posX,j.posY);
					if(i.body.radius.toDouble + j.body.radius.toDouble > findDistance(point.x, point.y, pointj.x, pointj.y)){
						projectilesToBeRemove += playerSword.remove(playerSword.indexOf(i))
						projectilesToBeRemove += bossSkillGroup.remove(bossSkillGroup.indexOf(j))
						break
					}
				}
			}//end breakable
		}
		for(i <- bossProjectiles){
			breakable{
				if(i.body.radius.toDouble + player.body.radius.toDouble > 
					findDistance(i.posX, i.posY, player.posX, player.posY)){
					player.hp -= i.dmg
					projectilesToBeRemove += bossProjectiles.remove(bossProjectiles.indexOf(i))
					break
				}
			}
		}
		for(i <- bossSkillGroup){
			val point :Point2D = i.body.localToParent(i.posX,i.posY);
			breakable{
				if(i.body.radius.toDouble + player.body.radius.toDouble > 
					findDistance(point.x, point.y, player.posX, player.posY)){
					player.hp -= i.dmg
					projectilesToBeRemove += bossSkillGroup.remove(bossSkillGroup.indexOf(i))
					break
				}
			}
		}
		(enemyToBeRemove, projectilesToBeRemove, regen)
	}

	/*
	 * Method pickRegen
	 * no @param
	 * return the list of regen to be remove
	 */
	def pickRegen():ListBuffer[Regeneration] = {
		var toBeRemove:ListBuffer[Regeneration] = new ListBuffer[Regeneration]
		for(i <- regenGroup){
			if(i.body.radius.toDouble + player.body.radius.toDouble > 
				findDistance(player.posX, player.posY, i.posX, i.posY)){
				player.hp += Regeneration.hpRegen
				toBeRemove += regenGroup.remove(regenGroup.indexOf(i))
			}
		}
		toBeRemove
	}

	/*
	 * Method addKillCount
	 * no @param
	 * add kill count and update level
	 */
	def addKillCount{
		killCount += 1
		if(AnyUnit.level % 5 != 0){
			roundKillCount += 1
			if(roundKillCount >= 15){
				roundKillCount = 0
				AnyUnit.level += 1
			}
		}
	}

	/*
	 * Method findDistance
	 * @param
	 * fromX : Double => x position of from object
	 * fromY : Double => y position of from object
	 * toX : Double => x position of to object
	 * toY : Double => y position of to object
	 */
	def findDistance(fromX:Double, fromY:Double, toX:Double, toY:Double):Double = {
		val dx = fromX - toX
		val dy = fromY - toY
		math.sqrt(dx*dx+dy*dy)
	}

	/*
	 * Method checkDeath
	 * no @param
	 * return if player is death
	 */
	def checkDeath:Boolean = {
		if (player.hp <= 0) true else false 
	}

	/*
	 * Method restart
	 * no @param
	 * initialize everything
	 * return the list of things that to be remove
	 */
	def restart:ListBuffer[Existenceable] = {
		var toBeRemove = new ListBuffer[Existenceable]
		AnyUnit.level = 1
		killCount = 0
		roundKillCount = 0
		spawnDelay = 1.5
		player.hp = 100
		Player.damage = 100
		bossSkill.enrageTimer = 0
		for(i <- regenGroup) toBeRemove += regenGroup.remove(regenGroup.indexOf(i))
		for(i <- enemyGroup) toBeRemove += enemyGroup.remove(enemyGroup.indexOf(i))
		for(i <- playerProjectiles) toBeRemove += playerProjectiles.remove(playerProjectiles.indexOf(i))
		for(i <- playerSword) toBeRemove += playerSword.remove(playerSword.indexOf(i))
		for(i <- bossProjectiles) toBeRemove += bossProjectiles.remove(bossProjectiles.indexOf(i))
		for(i <- bossSkillGroup) toBeRemove += bossSkillGroup.remove(bossSkillGroup.indexOf(i))
		enemyBoss match{
			case Some(x) => 
				toBeRemove += x
				enemyBoss = Option(null)
			case None =>
		}
		toBeRemove
	}
}

/*
 * Companion Object of ShootingGame
 */
object ShootingGame{

	/*
	 * Factory Method
	 * Apply Method for class ShootingGame
	 * Return new Instance Object of ShootingGame
	 */
	def apply():ShootingGame = new ShootingGame()
}