
package kable.sample

import kable.Playbook
import kable.action.RunProcess
import kable.action.User

fun main( arguments : Array< String > ) {

  val playbook = Playbook() {

    /**
     * Plain strings are messages printed in the console.
     */
    - "This is a sample Playbook, you can use it at home."

    /**
     * Just run some (possibly remote) process.
     */
    - RunProcess( "echo 'Hello Kotlin!' " )

    /**
     * Run a process again and grab the result in the `echo` value.
     * Its type resolves to [Deferred] because we don't execute the [Task] yet
     * (we just recorded it).
     * Type parameter is [Task.Result.ProcessExecution] so we'll extract
     * a type-safe result when available.
     */
    val echo =
    - RunProcess( "echo 'Hello Kotlin!' " )

    /**
     * Run a [Task] with compound actions and alternatives.
     * [kable2.Playlist#If] (note the uppercase) is a function.
     * First parameter is the [Deferred] object, second parameter is a predicate
     * to apply on it when the [Playbook] runs. The `it` parameter is a default parameter
     * name when there is only one.
     */
    If( echo, { it.stdout.contains( "Hello" ) } ) {
      - "Echo command did run as expected."
    } Else {
      - "This is unlikely."
      - "Should we do something?"
    }

    /**
     * We can run more dedicated [Task]s.
     * This one creates a Unix user with lots of defaults.
     */
    - User( name = "alice" )

    /**
     * Here we specify more values.
     */
    - User( name = "bob", group = "bob", uid = 1002 )


  }

  /**
   * Print every recorded [Task] (and compound [Task]s if any).
   */
  playbook.print()

}

