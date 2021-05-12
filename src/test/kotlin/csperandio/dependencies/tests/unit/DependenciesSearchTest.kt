package csperandio.dependencies.tests.unit

import csperandio.dependencies.dependencies.DependenciesSearch
import csperandio.dependencies.dependencies.Dependency
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DependenciesSearchTest {
    @Test
    internal fun findTwoDependecies() {
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

        val search = DependenciesSearch()
        val dependencies = search.dependencies(simplePom)

        val expected = listOf(
            Dependency("junit", "junit", "3.8.1"),
            Dependency("log4j", "log4j", "1.2.17")
        )
        assertEquals(expected, dependencies)
    }

    @Test
    internal fun manageVersionByProperty() {
        val pomWithProperties =
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
                    "        <log4j.version>1.2.17</log4j.version>\n" +
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
                    "            <version>\${log4j.version}</version>\n" +
                    "        </dependency>\n" +
                    "    </dependencies>\n" +
                    "</project>"

        val search = DependenciesSearch()
        val dependencies = search.dependencies(pomWithProperties)

        val expected = listOf(
            Dependency("junit", "junit", "3.8.1"),
            Dependency("log4j", "log4j", "1.2.17")
        )
        assertEquals(expected, dependencies)
    }

    @Test
    internal fun managePlugin() {
        val pomWithProperties =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                    "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                    "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                    "    <modelVersion>4.0.0</modelVersion>\n" +
                    "\n" +
                    "    <groupId>deduplication.company</groupId>\n" +
                    "    <artifactId>comparison</artifactId>\n" +
                    "    <version>1.0-SNAPSHOT</version>\n" +
                    "\n" +
                    "    <dependencies>\n" +
                    "        <dependency>\n" +
                    "            <groupId>org.projectlombok</groupId>\n" +
                    "            <artifactId>lombok</artifactId>\n" +
                    "            <version>1.16.20</version>\n" +
                    "        </dependency>\n" +
                    "        <dependency>\n" +
                    "            <groupId>io.cucumber</groupId>\n" +
                    "            <artifactId>cucumber-junit</artifactId>\n" +
                    "            <version>2.3.1</version>\n" +
                    "            <scope>test</scope>\n" +
                    "        </dependency>\n" +
                    "    </dependencies>\n" +
                    "\n" +
                    "    <build>\n" +
                    "        <plugins>\n" +
                    "            <plugin>\n" +
                    "                <groupId>org.apache.maven.plugins</groupId>\n" +
                    "                <artifactId>maven-compiler-plugin</artifactId>\n" +
                    "                <version>3.7.0</version>\n" +
                    "                <configuration>\n" +
                    "                    <source>1.8</source>\n" +
                    "                    <target>1.8</target>\n" +
                    "                </configuration>\n" +
                    "            </plugin>\n" +
                    "        </plugins>\n" +
                    "    </build>\n" +
                    "\n" +
                    "</project>"

        val search = DependenciesSearch()
        val dependencies = search.dependencies(pomWithProperties)

        val expected = listOf(
            Dependency("org.projectlombok", "lombok", "1.16.20"),
            Dependency("io.cucumber", "cucumber-junit", "2.3.1"),
            Dependency("org.apache.maven.plugins", "maven-compiler-plugin", "3.7.0"),
        )
        assertEquals(expected, dependencies)
    }
}