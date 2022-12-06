package day6

import ResourceFileReader

class Day6 {
    companion object {
        fun problem1() = getStartOfPacketMarker(4)
        fun problem2() = getStartOfPacketMarker(14)


        private fun getStartOfPacketMarker(packetMarkerLength: Int): Long {
            val signal = getInput()

            return signal
                .toCharArray()
                .mapIndexed { i, _ ->
                    if (i < packetMarkerLength) null
                    else
                        PacketMarkerCandidate(
                            i,
                            packetMarkerLength,
                            signal.subSequence((i - packetMarkerLength), i)
                        )
                }
                .filterNotNull()
                .find { it.isStartPacketMarker() }!!.index.toLong()


        }

        private fun getInput(): String {
            return ResourceFileReader.readAocFile(6).trim()
        }
    }
}

data class PacketMarkerCandidate(val index: Int, val packetMarkerLength: Int, val charSequence: CharSequence) {
    fun isStartPacketMarker() = charSequence.toSet().size == packetMarkerLength
}
