package kable.action

import kable.Task

/**
 * http://docs.ansible.com/ansible/file_module.html
 */
class DoFile(
    val path : String,
    val state : DoFile.State = DoFile.State.FILE
) : Task< Stat.Result > {

  enum class State {
    FILE, LINK, DIRECTORY, HARD, TOUCH, ABSENT
  }

}

