package tooling

public final class Deferred< E > {

  val value : E
    get() { throw IllegalStateException() }

  operator fun invoke() : E { throw IllegalStateException() }
}
