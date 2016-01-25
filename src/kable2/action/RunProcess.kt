package kable2.action

import kable2.Task

class RunProcess (
    val commandLine : String,
    val changeToDirectory : String? = null
) : Task< Task.Result.ProcessExecution > {


}

