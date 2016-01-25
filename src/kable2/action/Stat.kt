package kable2.action

import kable2.Action

class Stat (
    val file : String
) : Action< Stat.Result > {

  class Result( val exists : Boolean )

}

