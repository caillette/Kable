package kable

import java.util.*


class Playbook : Playlist() {

  private val _actions = ArrayList< Action >()

  companion object {
    fun new( initializer : Playlist.() -> Unit ) : Playbook {
      val playbook = Playbook()
      playbook.initializer()
      return playbook
    }
  }

  private fun print( indent : String, playlist : Playlist ) {
    for( action in playlist.actions ) {
      print( indent, action )
    }
  }

  private fun print( indent : String, action : Action ) {
    if( action is BranchingAction ) {
      println( "$indent[Branching]")
      for( branch in action.branches ) {
        println( "$indent  ${branch.deferred} ${branch.predicate}" )
        print( indent + "    ", branch.playlist )
      }
    } else {
      println( "$indent$action")
    }
  }

  fun print() {
    print( "", this )
  }



}
