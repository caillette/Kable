package kable2.action

import kable2.Task
import java.util.regex.Pattern

class LineInFile (
    val file : String,
    val line : String? = null,
    val regex : Pattern? = null,
    val state : LineInFile.State? =null
) : Task< Stat.Result > {

  enum class State {
    PRESENT, ABSENT
  }

  companion object {
    /**
     * Magic value.
     */
    val BEFORE_END_OF_FILE = Pattern.compile( "" )

    /**
     * Magic value.
     */
    val AFTER_END_OF_FILE = Pattern.compile( "" )
  }
}

