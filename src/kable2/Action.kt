package kable2

import tooling.Duration

interface Action< RESULT > {

  interface Result {

    class ProcessExecution(
        val returnCode : Int,
        val stdout : String,
        val stderr : String,
        val stdoutLines : List< String >,
        val executionDuration : Duration
    )

    class None

    class Compound

  }




}
