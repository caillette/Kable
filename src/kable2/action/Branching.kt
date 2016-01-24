
package kable2.action

import kable2.Action
import kable2.Playlist
import tooling.Deferred

class Branching(
    val branches : List< Branching.Branch< * > >
) : Action< Action.Result.Compound > {

  class Branch< T >(
      val deferred : Deferred<T>,
      val predicate : ( T ) -> Boolean,
      val playlist : Playlist
  )

}
