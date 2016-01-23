package kable.sample

import kable.Playbook
import kable.action.Expand
import kable.action.RaiseFlag
import kable.action.User
import kable.action.WaitForCompletion
import tooling.ActionResult
import tooling.Connection

fun main( arguments : Array< String > ) {

  val playbook = Playbook.new {

    - "Say something in the console."

    - Expand( archiveFilename = "/Downloads/jre.tar", destination = "/var/lib" )
      val expand = deferredResult() // Switches to asynchronous execution.
      modifiers( connection = Connection.LOCAL, ignoreErrors = true, runOnce = true )

    - User( name = "alice" )
    - User( name = "bob" )
      val userBob = deferredResult() // Switches to asynchronous execution.

    - WaitForCompletion( expand ) // Kind of barrier, wait for 'Expand' to end.

    val flag1 = newFlag()

    // Evaluating 'expand' waits for completion.
    // Then we apply some predicate on the result itself. There are predefined ones.
    If( userBob, ActionResult.RETURN_CODE_0 ) {
      - "Predicate evaluated to true."
      - RaiseFlag( flag1 )
    } Else {
      - "Predicate evaluated to false."
    }

    If( flag1 ) {
      - "Restart some service."
    }
  }

  playbook.print()

}


