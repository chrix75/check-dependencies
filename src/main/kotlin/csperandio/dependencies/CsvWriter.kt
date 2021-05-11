package csperandio.dependencies

import csperandio.dependencies.dependencies.Dependency
import java.io.File

class CsvWriter(private val result: File) {
    fun write(allDates: Map<Dependency, String?>) {
        addHeader()
        allDates.forEach { result.appendText("${it.key.group}:${it.key.artifact},${it.key.version},${it.value}\n") }
    }

    private fun addHeader() {
        result.writeText("dependency,version,date\n")

    }
}