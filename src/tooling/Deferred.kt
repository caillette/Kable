package tooling

open class Deferred< E > {

  val value : E
    get() { throw IllegalStateException() }

  operator fun invoke() : E { throw IllegalStateException() }
}

public class Flag : Deferred< Boolean >()