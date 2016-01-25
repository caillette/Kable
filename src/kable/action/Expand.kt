package kable.action

import kable.Task

class Expand(
    val archiveFilename : String,
    val destination : String
) : Task< Task.Result.ProcessExecution > {


}

