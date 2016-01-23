package kable.action

import kable.Action

class User(
    val name : String,
    group : String? = null,
    val uid : Int? = null
) : Action {

  private val _group : String

  init {
    _group = group ?: name
  }

}

