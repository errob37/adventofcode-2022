package day5

import ResourceFileReader

class Day5 {
    companion object {
        private val stacks = mapOf<Int, MutableList<String>>(
            1 to mutableListOf(),
            2 to mutableListOf(),
            3 to mutableListOf(),
            4 to mutableListOf(),
            5 to mutableListOf(),
            6 to mutableListOf(),
            7 to mutableListOf(),
            8 to mutableListOf(),
            9 to mutableListOf()
        )

        private val stackCharacterPosition = mapOf(
            1 to 1,
            2 to 5,
            3 to 9,
            4 to 13,
            5 to 17,
            6 to 21,
            7 to 25,
            8 to 29,
            9 to 33
        )

        fun problem1() = getTopCrates(CrateMoverModel.MODEL_9000)
        fun problem2() = getTopCrates(CrateMoverModel.MODEL_9001)

        private fun getTopCrates(crateMover: CrateMoverModel): String {
            val (rawStacks, movesToDo) = getInput()

            val moves = movesToDo
                .split("\n")
                .filter { it.isNotBlank() }
                .map(String::toMove)

            fillStacks(rawStacks)

            moves.forEach {
                val toMoved = mutableListOf<String>()
                for (i in 1..it.howMany) {
                    if (crateMover == CrateMoverModel.MODEL_9000) {
                        stacks[it.to]!!.add(stacks[it.from]!!.removeLast())
                    } else if (crateMover == CrateMoverModel.MODEL_9001) {
                        toMoved.add(stacks[it.from]!!.removeLast())
                    }
                }

                if (crateMover == CrateMoverModel.MODEL_9001) {
                    stacks[it.to]!!.addAll(toMoved.reversed())
                }
            }

            return stacks.values
                .filter { it.isNotEmpty() }
                .joinToString("") { it.last() }
        }

        private fun getInput(): List<String> {
            return ResourceFileReader
                .readAocFile(5)
                .split("\n\n")
                .filter { it.isNotBlank() }
        }

        private fun fillStacks(rawStacks: String) {
            rawStacks
                .split("\n")
                .filter { it.isNotBlank() }
                .dropLast(1)
                .reversed()
                .forEach {
                    val s = it.padEnd(34).toCharArray()

                    stackCharacterPosition.forEach { (k, v) -> stacks[k]!!.addIfNotBlank(s[v].toString()) }
                }
        }
    }
}

fun String.toMove(): Move {
    val (howManyRaw, fromToRaw) = this.split(" from ")

    val fromTo = fromToRaw.split(" to ")

    return Move(
        fromTo.first().toInt(),
        fromTo[1].toInt(),
        howManyRaw.removePrefix("move ").toInt()
    )
}

fun MutableList<String>.addIfNotBlank(element: String): Boolean = if (element.isNotBlank()) this.add(element) else false
class Move(val from: Int, val to: Int, val howMany: Int)
enum class CrateMoverModel { MODEL_9000, MODEL_9001; }
