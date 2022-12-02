import ResourceFileReader.Companion.readAocFile

class Day1 {
    companion object {
        fun problem1() = getElvesFoodCalories().max()
        fun problem2() = getElvesFoodCalories()
            .sortedDescending()
            .subList(0, 3)
            .sum()

        private fun getElvesFoodCalories(): List<Long> {
            return readAocFile(1)
                .split("\n\n")
                .map { it.replace("\n", " ") }
                .filter { it.isNotBlank() }
                .map { it.split(" ") }
                .map { it.map { it2 -> it2.toLong() } }
                .map {it.sum()}
        }
    }
}
