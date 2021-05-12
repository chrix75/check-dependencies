package csperandio.dependencies.dependencies

import org.apache.maven.model.io.xpp3.MavenXpp3Reader

class DependenciesSearch {

    fun dependencies(pom: String): List<Dependency> {
        val reader = MavenXpp3Reader()
        val model = reader.read(pom.reader())
        return model.dependencies.map { Dependency(it.groupId, it.artifactId, it.version) }
            .filter { !it.version.startsWith("\${") }
    }
}
