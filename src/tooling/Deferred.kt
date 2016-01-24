package tooling

import kotlin.reflect.defaultType

open class Deferred< VALUE > {

  operator fun invoke() : VALUE {
    throw UnsupportedOperationException( "TODO: wait for completion" )
  }

  companion object {
    fun< RAW, TRANSFORMED > new(
        raw : Deferred< RAW >,
        transformer : ( RAW ) -> TRANSFORMED
    ) : Deferred< TRANSFORMED > {
      return Deferred() ;
    }
  }

  override fun toString() : String {

    return "${this.javaClass.kotlin.defaultType}@${System.identityHashCode(this)}"
  }
}

public class Flag : Deferred< Boolean >()