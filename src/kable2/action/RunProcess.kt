package kable2.action

import kable2.Action

class RunProcess (
    val commandLine : String,
    val changeToDirectory : String? = null
) : Action< Action.Result.ProcessExecution > {


}

