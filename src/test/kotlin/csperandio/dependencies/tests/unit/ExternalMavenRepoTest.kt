package csperandio.dependencies.tests.unit

import csperandio.dependencies.dependencies.Dependency
import csperandio.dependencies.repo.ExternalMavenRepo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ExternalMavenRepoTest {
    @Test
    internal fun dateForOldJunit() {
        val mavenRepo = ExternalMavenRepo("https://repo1.maven.org/maven2")
        val dependency = Dependency("junit", "junit", "3.8.1")
        assertEquals("2005-09-20", mavenRepo.date(dependency))
    }
}