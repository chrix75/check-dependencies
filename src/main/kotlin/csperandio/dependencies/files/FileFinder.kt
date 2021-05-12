package csperandio.dependencies.files

import java.io.File

class FileFinder(private val rootDir: File) {
    fun all(fileName: String): List<File> {
        return rootDir.walk().filter { it.isFile && it.name == fileName }.toList()
    }
}