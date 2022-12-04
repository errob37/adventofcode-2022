package day4

class Day4 {
    companion object {
        fun problem1(): Long = getFullyEnclosedAssignmentPairCount()
        fun problem2(): Long = getOverlapAssignmentPairCount()

        private fun getFullyEnclosedAssignmentPairCount(): Long {
            return getAssignments()
                .map { getElvesCompartmentRange(it) }
                .filter { (elf1, elf2) ->
                    elf1.all { elf1t -> elf1t in elf2 } ||
                            elf2.all { elf2t -> elf2t in elf1 }
                }
                .size.toLong()
        }

        private fun getOverlapAssignmentPairCount(): Long {
            return getAssignments()
                .map { getElvesCompartmentRange(it) }
                .filter { (elf1, elf2) ->
                    elf1.any { elf1t -> elf1t in elf2 } ||
                            elf2.any { elf2t -> elf2t in elf1 }
                }
                .size.toLong()
        }

        private fun getElvesCompartmentRange(compartment: String): Pair<IntRange, IntRange> {
            val x = compartment.split(",")
            return Pair(getCompartment(x[0]), getCompartment(x[1]))
        }

        private fun getCompartment(compartment: String): IntRange {
            val (min, max) = compartment.split("-")
            return IntRange(min.toInt(), max.toInt())
        }

        private fun getAssignments(): List<String> {
            return ResourceFileReader
                .readAocFile(4)
                .split("\n")
                .filter { it.isNotBlank() }
        }
    }
}
