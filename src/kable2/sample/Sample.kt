
package kable2.sample

import kable2.Playbook
import kable2.action.Expand
import kable2.action.RaiseFlag
import kable2.action.User
import kable2.extension.PrepareDrbd
import tooling.Connection

fun main( arguments : Array< String > ) {

  val playbook = Playbook.new {

    - "Plain strings are messages printed in the console."

    - "Let's just expand a file."

    - Expand( archiveFilename = "/jre.tar", destination = "/var/lib" )

    - "Let's run the same Action asynchronously, capturing only text output."

    val expand =
    - Expand(
          archiveFilename = "/jre.tar",
          destination = "/var/lib"
      ) resultTransformedWith { it.stdout }
      modifiers(
          defer = true,
          ignoreErrors = true,
          connection = Connection.LOCAL,
          runOnce = true,
          timeout = 100000
      )

    - "Now use custom Action with specific result type."

    val drbdAnalysis =
    - PrepareDrbd()

    val userAlice =
    - User( name = "alice" )

    val flag1 = newFlag()

    If( expand, { it.contains( "OK" ) } ) {
      - "Predicate evaluated to true."
      - RaiseFlag( flag1 )
    } Else {
      - "Predicate evaluated to false."
    }


    println( expand )
    println( userAlice )
  }

  playbook.print()

}

