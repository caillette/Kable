
package kable2.action

import kable2.Playlist
import kable2.Task
import tooling.Deferred

class Branching(
    val branches : List< Branching.Branch< * > >
) : Task< Task.Result.Compound > {

  class Branch< RESULT >(
      val deferred : Deferred< RESULT >,
      val predicate : ( RESULT ) -> Boolean,
      val playlist : Playlist
  )

}
