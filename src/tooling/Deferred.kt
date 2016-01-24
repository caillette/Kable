package tooling

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
}

public class Flag : Deferred< Boolean >()