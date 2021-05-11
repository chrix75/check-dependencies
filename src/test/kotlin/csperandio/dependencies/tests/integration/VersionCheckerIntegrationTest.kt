package csperandio.dependencies.tests.integration

import csperandio.dependencies.VersionChecker
import csperandio.dependencies.dependencies.Dependency
import csperandio.dependencies.repo.ExternalMavenRepo
import csperandio.dependencies.repo.LocalMavenRepo
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class VersionCheckerIntegrationTest {
    @Test
    internal fun dependencyDatesFromPom() {
        val simplePom =
            "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                    "    <modelVersion>4.0.0</modelVersion>\n" +
                    "    <groupId>example.project.maven</groupId>\n" +
                    "    <artifactId>MavenEclipseExample</artifactId>\n" +
                    "    <version>0.0.1-SNAPSHOT</version>\n" +
                    "    <packaging>jar</packaging>\n" +
                    "    <description>Maven pom example</description>\n" +
                    "    <name>MavenEclipseExample</name>\n" +
                    "    <url>http://maven.apache.org</url>\n" +
                    "\n" +
                    "    <properties>\n" +
                    "        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>\n" +
                    "    </properties>\n" +
                    "\n" +
                    "    <dependencies>\n" +
                    "        <dependency>\n" +
                    "            <groupId>junit</groupId>\n" +
                    "            <artifactId>junit</artifactId>\n" +
                    "            <version>3.8.1</version>\n" +
                    "            <scope>test</scope>\n" +
                    "        </dependency>\n" +
                    "        <dependency>\n" +
                    "            <groupId>log4j</groupId>\n" +
                    "            <artifactId>log4j</artifactId>\n" +
                    "            <version>1.2.17</version>\n" +
                    "        </dependency>\n" +
                    "    </dependencies>\n" +
                    "</project>"

        val externalRepo = ExternalMavenRepo("https://repo1.maven.org/maven2")
        val checker = VersionChecker(LocalMavenRepo(), externalRepo)
        val dates = checker.dates(simplePom)

        val expected = listOf(
            Pair(Dependency("junit", "junit", "3.8.1"), "2005-09-20"),
            Pair(Dependency("log4j", "log4j", "1.2.17"), "2012-05-26")
        )
        assertEquals(expected, dates)

    }
}