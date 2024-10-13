// for auto-plugins https://github.com/sbt/sbt-native-packager
addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.9.4")
/**
Examples
# universal zip
  sbt universal:packageBin

  # debian package
sbt debian:packageBin

  # rpm package
sbt rpm:packageBin

  # docker image
  sbt docker:publishLocal

# graalvm image
  sbt graalvm-native-image:packageBin
 */