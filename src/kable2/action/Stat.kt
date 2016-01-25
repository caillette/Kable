package kable2.action

import kable2.Task

class Stat (
    val file : String
) : Task< Stat.Result > {

  class Result( val exists : Boolean )

}

