package day7

class FileSystem(private val totalSize: Long?) {
    private val root: Directory = Directory("/")
    fun getRootDirectory() = root

    override fun toString() = "FileSystem(root=$root)"

    fun sumOfSizeForDirectoriesBelow(size: Long): Long {
        return getSubdirectoriesRecursive(root)
            .map { it.size() }
            .filter { it <= size }
            .sum()
    }

    private fun getSubdirectoriesRecursive(currentDirectory: Directory): List<Directory> {
        return currentDirectory.getSubDirectories().map { getSubdirectoriesRecursive(it) + it }.flatten()
    }

    fun ensureFreeSpaceOf(atLeast: Long): Long {
        val toFree = atLeast - (totalSize!! - root.size())

        return getSubdirectoriesRecursive(root)
            .map { it.size() }
            .filter { it >= toFree }
            .min()
    }
}

interface FileSystemItem {
    fun name(): String
    fun size(): Long
    fun addItem(item: FileSystemItem)
    fun isDirectory(): Boolean = false
}

class Directory(private val name: String) : FileSystemItem {
    private val items: MutableList<FileSystemItem> = mutableListOf()
    var parent: Directory? = null

    fun getDirectory(directoryName: String): Directory =
        items.find { it.name() == directoryName && it is Directory } as Directory

    override fun name() = name

    override fun size() = items.map(FileSystemItem::size).sum()

    override fun addItem(item: FileSystemItem) {
        if (item is Directory) item.parent = this
        items.add(item)
    }

    override fun isDirectory(): Boolean = true

    override fun toString() = "Directory(name='$name', items=$items)"

    fun getSubDirectories() = this.items.filter { it.isDirectory() }.map { it as Directory }
}

class File(private val name: String, private val size: Long) : FileSystemItem {
    override fun name() = name

    override fun size() = size

    override fun addItem(item: FileSystemItem) {}

    override fun toString() = "File(name='$name', size=$size)"
}
