
package kable2.sample

import kable2.Playbook
import kable2.action.Expand
import kable2.action.RaiseFlag
import kable2.action.RunProcess
import kable2.action.User
import kable2.extension.PrepareDrbd
import tooling.Connection

fun main( arguments : Array< String > ) {

  val playbook = Playbook.new {



    - "Run some complex task, and capture output."
    val find =
    - RunProcess( "find /home -name *.pdf" )

    - "Let's declare a boolean variable known as a 'flag'."
    val pdfPresenceFlag = newFlag()

    - "Now we set if if we found something."
    - "The 'If' (uppercase 'I') is a function. It receives the flag as a separate argument, "
    - "so it can wait until the value gets assigned (in this case, the 'find' command ran) "
    - "synchronously, so it is). After that it applies a predicate."
    - "The 'it' value is closure's parameter, when there is only one."
    - "Please note that static typing applies."
    If( find, { it.returnCode == 0 } ) {
      - "Found something."
      - RaiseFlag( pdfPresenceFlag )
    }

    - "Let's run some custom Action."
    val drbdAnalysis =
    - PrepareDrbd()

    - "Here again, Action's result type is the one expected."
    If( drbdAnalysis, { it.filesystemResize == null } ) {
      - "DRBD already installed."
    } Else {
      - "DRBD needs installation."
    }


    - "Let's run the same Action asynchronously, capturing only text output."
    val expand =
    - Expand(
          archiveFilename = "/jre.tar",
          destination = "/var/lib"
      ) resultTransformedBy { it.stdout }
      modifiers(
          defer = true,
          ignoreErrors = true,
          connection = Connection.LOCAL,
          runOnce = true,
          timeout = 100000
      )
      unless( pdfPresenceFlag )

    - "Now use custom Action with specific result type."

    val userBob =
    - User( name = "bob" )

    val flag1 = newFlag()

    If( expand, { it.contains( "OK" ) } ) {
      - "Predicate evaluated to true."
      - RaiseFlag( flag1 )
    } Else {
      - "Predicate evaluated to false."
    }


    println( expand )
    println( userBob )
  }

  playbook.print()

//  Thread.sleep( 5000 )

}

