package kable

import tooling.Duration

interface Task< RESULT > {

  interface Result {

    class ProcessExecution(
        val returnCode : Int,
        val stdout : String,
        val stderr : String,
        val stdoutLines : List< String >,
        val executionDuration : Duration
    ) {
      companion object {
        val RETURN_CODE_0 : ( ProcessExecution ) -> Boolean = { it.returnCode == 0 }
      }

    }

    class None

    class Compound

  }




}
