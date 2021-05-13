package csperandio.dependencies

import csperandio.dependencies.files.FileFinder
import csperandio.dependencies.repo.ExternalMavenRepo
import java.io.File
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    if (args.size != 2) {
        System.err.println("Usage: [PROJECT ROOT DIR] [RESULT FILE]")
        exitProcess(1)
    }

    val rootDir = File(args[0])
    if (!rootDir.exists()) {
        System.err.print("Directory $rootDir not found.")
        exitProcess(1)
    }

    val finder = FileFinder(rootDir)
    val poms = finder.all("pom.xml")

    val checker = VersionChecker(ExternalMavenRepo("https://repo1.maven.org/maven2"))

    val allDates = checker.dates(poms.map { it.reader() })

    val writer = CsvWriter(File(args[1]))
    writer.write(allDates)
}
