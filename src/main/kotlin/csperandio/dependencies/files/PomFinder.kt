package csperandio.dependencies.files

import java.io.File

class PomFinder(val rootDir: File) {
    fun all(fileName: String): List<File> {
        return rootDir.walk().filter { it.isFile && it.name == fileName }.toList()
    }
}