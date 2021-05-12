package csperandio.dependencies.tests.unit

import csperandio.dependencies.VersionChecker
import csperandio.dependencies.dependencies.Dependency
import csperandio.dependencies.repo.ExternalMavenRepo
import csperandio.dependencies.repo.MavenRepo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

internal class VersionCheckerTest {

    private lateinit var localRepo: MavenRepo

    @BeforeEach
    internal fun setUp() {
        val deps = mutableMapOf<String, String>()
        deps["junit:junit:3.8.1"] = "2005-09-20"
        localRepo = LocalMavenRepo(deps)
    }

    @Test
    fun infoAboutOldJunit() {
        val checker = VersionChecker(localRepo)
        val dependency = Dependency("junit", "junit", "3.8.1")
        assertEquals("2005-09-20", checker.date(dependency))
    }

    @Test
    fun manageLocalAndExternalRepo() {
        val externalRepo = ExternalMavenRepo("https://repo1.maven.org/maven2")
        val checker = VersionChecker(localRepo, externalRepo)
        val dependency = Dependency("org.apache.commons", "commons-lang3", "3.0")
        val commonsLang3Date = checker.date(dependency)
        assertEquals("2011-07-19", commonsLang3Date)
    }

    @Test
    internal fun unknownDependency() {
        val checker = VersionChecker(localRepo)
        val dependency = Dependency("unknown", "missing", "0.0.1")
        assertNull(checker.date(dependency))
    }
}