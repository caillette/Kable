package kable.action

import kable.Task

class Stat (
    val file : String
) : Task< Stat.Result > {

  class Result( val exists : Boolean )

}

