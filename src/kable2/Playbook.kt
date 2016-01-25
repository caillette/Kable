package kable2

import kable2.action.Branching


open class Playbook( initializer : Playlist.() -> Unit ) : Playlist() {

  init {
    this.initializer()
  }
/*
  companion object {
    fun new( initializer : Playlist.() -> Unit ) : Playbook {
      val playbook = Playbook()
      playbook.initializer()
      return playbook
    }
  }
*/

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
