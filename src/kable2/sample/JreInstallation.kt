
package kable2.sample

import kable2.Playbook
import kable2.action.DoFile
import kable2.action.RunProcess
import kable2.action.Stat

fun main( arguments : Array< String > ) {

  val playbook = JreInstallationPlaybook(
      jreArchivesDirectory = "/var/lib/java",
      jreHome = "/media/nas/jre-archives",
      jreVersionPrecise = "1.8.0_66"
  )

  playbook.print()

}

class JreInstallationPlaybook(
    jreArchivesDirectory : String,
    jreHome : String,
    jreVersionPrecise : String
) : Playbook( {

  val jreArchive = "jre" + jreVersionPrecise

  val wgetCommandLine = StringBuilder()
        .append( "wget --no-cookies --no-check-certificate " )
        .append( "--header \"Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; " )
        .append( "oraclelicense=accept-securebackup-cookie\" " )
        .append( "\"http://download.oracle.com/otn-pub/java/jdk/" )
        .append( jreVersionPrecise )
        .append( "/" )
        .append( jreArchive )
        .append( "\"" )
        .toString()

  val jrePresent =
  - Stat( file = jreArchivesDirectory + "/" + jreArchive ) resultTransformedBy { it.exists }

  - DoFile( path = jreArchivesDirectory, state = DoFile.State.DIRECTORY )
    unless( jrePresent )

  - RunProcess( wgetCommandLine, changeToDirectory = jreArchivesDirectory)
    unless( jrePresent )

  - RunProcess( "tar -zxvg $jreArchive", changeToDirectory = jreArchivesDirectory)

  - RunProcess( "update-alternatives --install /usr/bin/java java $jreHome/bin/java 100" )

  - RunProcess( "update-alternatives --config java" )

  - DoFile( path = "~/.oracle_jre_usage", state = DoFile.State.ABSENT )

} )


