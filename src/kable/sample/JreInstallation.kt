
package kable.sample

import kable.Playbook
import kable.action.DoFile
import kable.action.RunProcess
import kable.action.Stat

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

  val wgetCommandLine = "wget --no-cookies --no-check-certificate --header \"Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; oraclelicense=accept-securebackup-cookie\" \"http://download.oracle.com/otn-pub/java/jdk/$jreVersionPrecise/$jreArchive\""
//  val wgetCommandLine = StringBuilder()
//        .append( "wget --no-cookies --no-check-certificate " )
//        .append( "--header \"Cookie: gpw_e24=http%3A%2F%2Fwww.oracle.com%2F; " )
//        .append( "oraclelicense=accept-securebackup-cookie\" " )
//        .append( "\"http://download.oracle.com/otn-pub/java/jdk/" )
//        .append( jreVersionPrecise )
//        .append( "/" )
//        .append( jreArchive )
//        .append( "\"" )
//        .toString()

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


