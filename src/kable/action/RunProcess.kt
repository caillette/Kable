package kable.action

import kable.Task

class RunProcess (
    val commandLine : String,
    val changeToDirectory : String? = null
) : Task< Task.Result.ProcessExecution > {


}

