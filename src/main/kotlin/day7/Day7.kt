package day7

import ResourceFileReader

class Day7 {
    companion object {
        fun problem1() = getFileSystem().sumOfSizeForDirectoriesBelow(100000L)

        fun problem2() = getFileSystem(70000000L).ensureFreeSpaceOf(30000000L)

        private fun getFileSystem(totalSize: Long? = null) =
            FileSystemFactory.createFrom(totalSize, ResourceFileReader.readAocFile(7))
    }
}
