
package kable2

import kable2.action.Branching
import kable2.action.ConsoleMessage
import tooling.Connection
import tooling.Deferred
import tooling.Flag
import java.util.*

open class Playlist {

  private val _actions = ArrayList< Task< * > >()

  val tasks : List< Task< * > >
    get() = ArrayList( _actions )


// ====================
// Playlist elaboration
// ====================

  operator fun String.unaryMinus() {
    _actions.add( ConsoleMessage( this ) )
  }

  operator fun< RESULT > Task< RESULT >.unaryMinus() : Deferred< RESULT > {
    /** Here we should tie the [Task] and the [Deferred] objects together. */
    _actions.add( this )
    return Deferred()
  }

  infix fun< RAW, TRANSFORMED > Deferred< RAW >.resultTransformedBy(
      transformer : ( RAW ) -> TRANSFORMED
  ) : Deferred< TRANSFORMED > {
    return Deferred()
  }

  fun modifiers(
      defer : Boolean = false,
      connection : Connection = Connection.REMOTE,
      ignoreErrors : Boolean = true,
      runOnce : Boolean = true,
      timeout : Int = 10000
  ) {
    /** Modify last-added [Task]. */
  }

  interface ContinuationKeywordAcceptor< RAW, TRANSFORMED > {
    infix fun on( deferredResult : Deferred< RAW > ) : Deferred< TRANSFORMED >
  }


// =========
// Factories
// =========

  fun newFlag() : Flag {
    return Flag()
  }

  companion object {
    fun new( initializer : Playlist.() -> Unit ) : Playlist {
      val playlist = Playlist()
      playlist.initializer()
      return playlist
    }
  }


// ============
// Conditionals
// ============

  fun onlyIf( flag : Deferred< Boolean > ) {

  }

  fun< T > onlyIf(
      deferred : Deferred< T >,
      predicate : ( T ) -> Boolean
  ) {

  }

  fun unless( flag : Deferred< Boolean > ) {

  }

  fun< T > unless(
      deferred : Deferred< T >,
      predicate : ( T ) -> Boolean
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
    val branchList : MutableList< Branching.Branch< * > > = ArrayList()
    branchList.add( Branching.Branch( deferred, predicate, Playlist.new( block ) ) )
    _actions.add( Branching( branchList ) )

    // Code below will perform a side-effect on branchList after we added it to _actions
    // But it should be OK.
    return object : ElseClauseAcceptor {
      override fun Else( block : Playlist.() -> Unit ) {
        branchList.add( Branching.Branch(
            deferred, { ! predicate.invoke( it ) }, Playlist.new( block ) ) )
      }
    }
  }

}


interface ElseClauseAcceptor {
  infix fun Else( block : Playlist.() -> Unit )
}
