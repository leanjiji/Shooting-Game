name := "ShootingGame"

version := "1.0"

scalaVersion := "2.12.0"

offline := true

unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/ext/jfxrt.jar"))

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

scalacOptions ++= Seq("-unchecked", "-deprecation")

libraryDependencies ++= Seq(
  "org.scalafx" % "scalafx_2.12" % "8.0.102-R11",
  "org.scalafx" % "scalafxml-core-sfx8_2.12" % "0.3",
  "com.github.nscala-time" % "nscala-time_2.11" % "2.16.0"
)

mainClass in assembly := Some("game.Game")

fork := true

proguardSettings
ProguardKeys.proguardVersion in Proguard := "5.3.3"
val keepClasses =
"""
  |-keep class scala.** { *; }
  |-dontwarn scala.**
  |-keep class game.** { *; }
  |-dontwarn game.**  
  |-keep class game.controller.** { *; }
  |-dontwarn game.controller.**
  |-keep class java.** { *; }
  |-dontwarn java.** 
  |-keep class com.** { *; }
  |-dontwarn com.** 
  |-keep class org.joda.** { *; }
  |-dontwarn org.joda.** 
  |-keep class scalafxml.** { *; }
  |-dontwarn scalafxml.** 
  |-keep class javafx.** { *; }
  |-dontwarn javafx.** 
  |-keep class scalafx.** { *; }
  |-dontwarn scalafx.** 
  |-keep class org.fusesource.jansi.** { *; }
  |-dontwarn org.fusesource.jansi.**         
  |-keep class !secure.** { *; }  
  |-dontskipnonpubliclibraryclasses
  |-dontskipnonpubliclibraryclassmembers
  |-repackageclasses ''
  |-allowaccessmodification
  |-keepattributes *Annotation*
  |-dontpreverify
  |-dontshrink
  |-dontoptimize
  """.stripMargin

ProguardKeys.options in Proguard += keepClasses

ProguardKeys.inputs in Proguard := Seq(baseDirectory.value / "target" / s"scala-${scalaVersion.value.dropRight(2)}" / s"${name.value}-assembly-${version.value}.jar")

ProguardKeys.libraries in Proguard := Seq()

ProguardKeys.inputFilter in Proguard := { file => None }

ProguardKeys.merge in Proguard := false

(ProguardKeys.proguard in Proguard) := (ProguardKeys.proguard in Proguard).dependsOn(assembly).value