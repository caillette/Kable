package kable.descriptor


class DrbdCluster(
    val deviceName : String,
    val partitionName : String,
    val mountpoint : String,
    val members : List< DrbdCluster.Member >
) {
  class Member(
      val listenIpAddress : String,
      val listenPort : Int,
      val hostname : String
  )
}

class Filesystem(
    val blockSizeBytes : Long,
    val blockCount : Long,
    val totalSizeBytes : Long,
    val unusuedOnPartition : Long
)

class Partition(
    val fullName : String,
    val sectorSizeBytes : Long,
    val sectorCount : Long,
    val totalSizeBytes : Long
)

class DrbdMetadata(
    val sizeSectors : Long,
    val sizeBytes : Long,
    val alreadyPresent : Boolean
)

class FilesystemResize(
    val newSizeSectors : Long,
    val newSizeMb : Long
)

class DrbdAnalysis(
    val drbdCluster : DrbdCluster,
    val filesystem : Filesystem,
    val partition : Partition,
    val drbdMetadata : DrbdMetadata,
    val filesystemResize : FilesystemResize?
)

