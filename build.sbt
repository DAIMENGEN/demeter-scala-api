ThisBuild / version := "demeter-scala-api-1.0.0"
ThisBuild / scalaVersion := "2.13.13"

// Define the basic configuration and version information of Akka.
lazy val AkkaVersion = "2.9.3"
lazy val AkkaHttpVersion = "10.6.3"

// Enable plugins.
enablePlugins(RpmPlugin)
enablePlugins(JavaServerAppPackaging)
enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)

// Specifies the name of the generated package
name := "demeter-scala-api"
// Specifies the maintainer or developer of the package
maintainer := "Advantest China"
// Specifies the name of the generated package
// It is recommended to define both name and packageName properties in sbt builds and set their values to the same value to avoid possible conflicts
packageName := "demeter"
// Specifies an overview of the generated package
packageSummary := "demeter"
// Set the description information of the generated package
packageDescription := """demeter description"""
// Sets the daemon user used when running sbt tasks on Linux operating systems as "root"
Linux / daemonUser  := "root"
// Set the user group of the daemon process used when running sbt tasks on Linux operating systems to "root"
Linux / daemonGroup  := "root"
// Specifies the resource file directory in the sbt project source code directory
Compile / resourceDirectory  := baseDirectory.value /"src" / "main" / "resources"

/**Linux package*/
// Sets the official download link for the generated RPM package
rpmUrl := Some("https://www.advantest.com")
// Specifies the RPM package group to which the package belongs
rpmGroup := Some("Applications/Productivity")
// Set the vendor information for the generated RPM package
rpmVendor := """Advantest China RD"""
// Sets the release version information for the generated RPM package
rpmRelease := "0.10"
// The license associated with software in the RPM.
rpmLicense := Some("Copyright (C) 2005, ADVANTEST Corporation, China")
// The name of the package for the rpm.
Rpm / packageName := packageName.value
// The version of the package for rpm.
Rpm /version := "v0.10"
// Set schema information for generated RPM packages
Rpm / packageArchitecture := "x86_64"
// A brief, one-line summary of the package.
Rpm / packageSummary := packageSummary.value
// A longer, multi-line description of the package.
Rpm / packageDescription := packageDescription.value
// Set the default installation path for RPM packages
defaultLinuxInstallLocation := "/opt/"
// Settings, operations that need to be performed before and after RPM package installation
Rpm / maintainerScripts := Map(
  RpmConstants.Pre -> Seq("""echo "Start the installation of demeter""""),
  RpmConstants.Posttrans -> Seq("""echo "Install complete""""),
  RpmConstants.Preun -> Seq("""echo "Start uninstalling demeter""""),
  RpmConstants.Postun -> Seq("""rm -rf /opt/demeter""","""echo "Uninstall complete"""")
)

// Add additional library resolvers
resolvers ++= Seq(
  // Akka official library resolver, used to fetch Akka-related dependency libraries
  "Akka library repository" at "https://repo.akka.io/maven",
  // Maven Central resolver, used to fetch a wide range of third-party libraries
  "Maven Central" at "https://repo1.maven.org/maven2"
)

// Project dependency management.
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-slf4j" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-discovery" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-jwt" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "mysql" % "mysql-connector-java" % "8.0.33",
  "ch.qos.logback" % "logback-classic" % "1.5.6",
  "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "7.0.2",
  "org.scalatest" %% "scalatest" % "3.2.19" % Test,
)