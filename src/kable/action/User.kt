package kable.action

import kable.Task

class User(
    val name : String,
    group : String? = null,
    val uid : Int? = null
) : Task< Task.Result.ProcessExecution > {

  private val _group : String

  init {
    _group = group ?: name
  }

}

