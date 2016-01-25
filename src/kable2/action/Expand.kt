package kable2.action

import kable2.Task

class Expand(
    val archiveFilename : String,
    val destination : String
) : Task< Task.Result.ProcessExecution > {


}

