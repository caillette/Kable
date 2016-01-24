package kable.sample

import kable.Playbook
import kable.action.Expand
import kable.action.RaiseFlag
import kable.action.User
import kable.action.WaitForCompletion
import kable.descriptor.DrbdAnalysis
import kable.descriptor.DrbdCluster
import kable.descriptor.DrbdCluster.Member
import kable.descriptor.DrbdMetadata
import kable.descriptor.Filesystem
import kable.descriptor.FilesystemResize
import kable.descriptor.Partition
import tooling.ActionResult
import tooling.Connection
import java.util.Arrays.asList

fun main( arguments : Array< String > ) {


  val playbook = Playbook.new {

    val flag1 = newFlag()
    val flag2 = newFlag()

    - "Say something in the console."

    - Expand( archiveFilename = "/Downloads/jre.tar", destination = "/var/lib" )
      val expand = deferredResult() // Switches to asynchronous execution.
      modifiers( connection = Connection.LOCAL, ignoreErrors = true, runOnce = true )

    - User( name = "alice" )
      val userAlice = deferredResult( { it.stdout } ) // Result transformation.

    - User( name = "bob" )
      val userBob = deferredResult() // Switches to asynchronous execution.

    val userCharlie = justCapture() on
    - User( name = "charlie" )

    val userDelta = capture< ActionResult, String >( transformer = { it.stdout } ) on
    - User( name = "delta" )

    - WaitForCompletion( expand ) // Kind of barrier, wait for 'Expand' to end.


    // Evaluating 'expand' waits for completion.
    // Then we apply some predicate on the result itself. There are predefined ones.
    If( userBob, ActionResult.RETURN_CODE_0 ) {
      - "Predicate evaluated to true."
      - RaiseFlag( flag1 )
    } Else {
      - "Predicate evaluated to false."
    }

    If( flag1 ) {
      - "Restart some service."
    }

    - Expand( "some/file", "destination" )
    unless( flag2 ) // Raise an error, flag2 was never set.

    If( userAlice, { it.contains( "created" ) } ) {
      - "Pre-processed execution result matches expected value."
    }
  }

  playbook.print()



  val drbdAnalysis = DrbdAnalysis(
      DrbdCluster(
          deviceName = "/dev/drbd0",
          partitionName = "/dev/mapper/vgos-realm",
          mountpoint = "/var/realm",
          members = asList(
              Member( "192.168.100.112", 7789, "host-1" ),
              Member( "192.168.100.122", 7789, "host-2" )
          )
      ),
      Filesystem(
          blockSizeBytes = 1024,
          blockCount = 102400,
          totalSizeBytes = 104857600,
          unusuedOnPartition = 0
      ),
      Partition(
          fullName = "/dev/mapper/vgos-realm",
          sectorCount = 204800,
          sectorSizeBytes = 512,
          totalSizeBytes = 104857600
      ),
      DrbdMetadata(
          sizeSectors = 80,
          sizeBytes = 40960,
          alreadyPresent = false
      ),
      FilesystemResize(
          newSizeMb = 99,
          newSizeSectors = 204720
      )
  )


}


