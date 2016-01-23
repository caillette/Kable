package kable

import tooling.Deferred

interface Action {


}

class BranchingAction( val branches : List< BranchingAction.Branch< * > >) : Action {

  class Branch< T >(
      val deferred : Deferred< T >,
      val predicate : ( T ) -> Boolean,
      val playlist : Playlist
  )
}

