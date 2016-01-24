
package kable2.sample

import kable2.Playbook
import kable2.action.Expand

fun main( arguments : Array< String > ) {

  val playbook = Playbook.new {

    - "Let's run some Action asynchronously."

//    val expand = capture() on
    - Expand( archiveFilename = "/Downloads/jre.tar", destination = "/var/lib")



  }

  playbook.print()

}

