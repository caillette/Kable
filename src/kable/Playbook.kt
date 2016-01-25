package kable

import kable.action.Branching


open class Playbook( block : Playlist.() -> Unit ) : Playlist() {

  init {
    this.block()
  }

  private fun print( indent : String, playlist : Playlist ) {
    for( action in playlist.tasks) {
      print( indent, action )
    }
  }

  private fun print( indent : String, task : Task< * > ) {
    if( task is Branching ) {
      println( "$indent[Branching]")
      for( branch in task.branches ) {
        println( "$indent  ${branch.deferred} ${branch.predicate}" )
        print( indent + "    ", branch.playlist )
      }
    } else {
      println( "$indent$task")
    }
  }

  fun print() {
    print( "", this )
  }



}
