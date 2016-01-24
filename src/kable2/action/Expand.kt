package kable2.action

import kable2.Action

class Expand(
    val archiveFilename : String,
    val destination : String
) : Action< Action.Result.ProcessExecution > {


}

