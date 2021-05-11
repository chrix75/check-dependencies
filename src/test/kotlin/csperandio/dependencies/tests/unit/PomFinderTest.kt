package csperandio.dependencies.tests.unit

import csperandio.dependencies.files.PomFinder
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.assertTrue

class PomFinderTest {
    @Test
    internal fun findPomInDirectories() {
        val finder = PomFinder(File("./src/test/tree"))
        val pomFiles = finder.all("pom.xml")
        val expected = listOf(
            File("./src/test/tree/dir1/dir11/pom.xml"),
            File("./src/test/tree/dir2/pom.xml"),
            File("./src/test/tree/pom.xml")
        )
        assertTrue(expected.containsAll(pomFiles))
        assertTrue(pomFiles.containsAll(expected))
    }
}