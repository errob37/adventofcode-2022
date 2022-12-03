package day3

import ResourceFileReader

class Day3 {
    companion object {
        fun problem1() = getRuckStacks().sumOf { getPriority(it) }
        fun problem2() = getRuckStackGroups(3).sumOf { getPriority(it) }

        private fun getPriority(ruckStack: String): Long {
            val middle = ruckStack.length / 2
            val firstCompartment = ruckStack.subSequence(0, middle)
            val secondCompartment = ruckStack.subSequence(middle, ruckStack.length)

            val commonElement = firstCompartment.toSet().intersect(secondCompartment.toSet()).first()

            return commonElement.priority().toLong()
        }

        private fun getPriority(ruckStackGroup: List<String>): Long {
            return ruckStackGroup.map { it.toSet() }
                .reduce { acc, chars -> if (acc.isEmpty()) chars else acc.intersect(chars) }
                .first()
                .priority()
                .toLong()
        }

        private fun getRuckStackGroups(chunkSize: Int): List<List<String>> {
            return ResourceFileReader
                .readAocFile(3)
                .split("\n")
                .filter { it.isNotBlank() }
                .chunked(chunkSize)
        }

        private fun getRuckStacks(): List<String> {
            return ResourceFileReader
                .readAocFile(3)
                .split("\n")
                .filter { it.isNotBlank() }
        }

        private fun Char.priority() =
            if (this.isUpperCase())
                this.code - 'A'.code + 27
            else
                this.code - 'a'.code + 1
    }
}
