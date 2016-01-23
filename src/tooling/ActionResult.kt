package tooling

class ActionResult(
    val returnCode : Int,
    val stdout : String,
    val stderr : String,
    val stdoutLines : List< String >,
    val executionDuration : Duration
) {

  companion object {
    val RETURN_CODE_0 : ( ActionResult ) -> Boolean = { it.returnCode == 0 }
  }
}