package kable

import kable.action.ConsoleMessage
import tooling.ActionResult
import tooling.Connection
import tooling.Deferred
import tooling.Flag
import java.util.*


open class Playlist {

  private val _actions = ArrayList< Action >()

  operator fun String.unaryMinus() {
    _actions.add( ConsoleMessage( this ) )
  }

  operator fun Action.unaryMinus() {
    _actions.add( this )
  }


  fun deferredResult() : Deferred< ActionResult > {
    // TODO: grap a reference to last-added Action, if it supports deferring.
    return Deferred()
  }

  fun newFlag() : Flag {
    return Flag()
  }

  val actions : List< Action >
    get() = ArrayList( _actions )




  companion object {
    fun new( initializer : Playlist.() -> Unit ) : Playlist {
      val playlist = Playlist()
      playlist.initializer()
      return playlist
    }
  }

  fun modifiers(
      connection : Connection = Connection.REMOTE,
      ignoreErrors : Boolean = true,
      runOnce : Boolean = true,
      timeout : Int = 10000
  ) {

  }

  fun If(
      flag : Deferred< Boolean >,
      block : Playlist.() -> Unit
  ) : ElseClauseAcceptor {
    return If( flag, { it }, block )
  }

  fun< T > If(
      deferred : Deferred< T >,
      predicate : ( T ) -> Boolean,
      block : Playlist.() -> Unit
  ) : ElseClauseAcceptor {
    val branchList : MutableList< BranchingAction.Branch< * > > = ArrayList()
    branchList.add( BranchingAction.Branch( deferred, predicate, Playlist.new( block ) ) )
    _actions.add( BranchingAction( branchList ) )

    // Code below will perform a side-effect on branchList after we added it to _actions
    // But it should be OK.
    return object : ElseClauseAcceptor {
      override fun Else( block : Playlist.() -> Unit ) {
        branchList.add( BranchingAction.Branch(
            deferred, { ! predicate.invoke( it ) }, Playlist.new( block ) ) )
      }
    }
  }


}

interface ElseClauseAcceptor {
  infix fun Else( block : Playlist.() -> Unit )
}
