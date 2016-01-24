package kable2.action

import kable2.Action

class User(
    val name : String,
    group : String? = null,
    val uid : Int? = null
) : Action< Action.Result.ProcessExecution > {

  private val _group : String

  init {
    _group = group ?: name
  }

}

