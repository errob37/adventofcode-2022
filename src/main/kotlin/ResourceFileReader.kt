class ResourceFileReader {
    companion object {
        fun readAocFile(aocDay: Int) : String {
            return  object {}.javaClass.getResource("/$aocDay")!!.readText()
        }
    }
}
