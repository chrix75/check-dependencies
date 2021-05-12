package csperandio.dependencies

import csperandio.dependencies.dependencies.Dependency
import java.io.File

class CsvWriter(val result: File) {
    fun write(allDates: Set<Pair<Dependency, String>>) {
        addHeader()
        allDates.forEach { result.appendText("${it.first.group}:${it.first.artifact},${it.first.version},${it.second}\n") }
    }

    private fun addHeader() {
        result.writeText("dependency,version,date\n")

    }
}