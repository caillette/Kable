package kable2.action

import kable2.Action

class ExecuteCommand(
    val executable : String,
    val changeToDirectory : String? = null,
    vararg parameters : String
) : Action< Action.Result.ProcessExecution > {


}

