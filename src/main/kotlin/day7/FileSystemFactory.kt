package day7

class FileSystemFactory {
    companion object {
        fun createFrom(totalSize: Long?, terminalOutput: String): FileSystem {
            val converted = terminalOutput
                .split("\n")
                .filter { it.isNotBlank() }
                .map { if (it.startsWith("$")) it.toCommand() else it.toFileSystemItem() }

            val fileSystem = FileSystem(totalSize)
            var currentDir = fileSystem.getRootDirectory()
            converted.forEach {
                if (it is Command && it.commandType == CommandType.CHANGE_DIRECTORY)
                    currentDir = if (".." == it.arg) currentDir.parent!! else currentDir.getDirectory(it.arg!!)
                else if (it is FileSystemItem) currentDir.addItem(it)
            }

            return fileSystem
        }

        private fun String.toCommand(): Command {
            val splitted = this.removePrefix("$").trim().split(" ")
            val arg = if (splitted.size == 2) splitted[1] else null

            return Command(fromCode(splitted[0]), arg)
        }

        private fun String.toFileSystemItem(): FileSystemItem {
            val (dirOrSize, dirNameOrFileName) = this.split(" ")
            if (dirOrSize.startsWith("dir"))
                return Directory(dirNameOrFileName)
            return File(dirNameOrFileName, dirOrSize.toLong())
        }
    }
}

class Command(val commandType: CommandType, val arg: String? = null) {
    override fun toString() = "Command{$commandType, $arg}"
}

enum class CommandType { CHANGE_DIRECTORY, LIST }

private fun fromCode(code: String): CommandType = when (code) {
    "ls" -> CommandType.LIST
    "cd" -> CommandType.CHANGE_DIRECTORY
    else -> throw IllegalArgumentException()
}
