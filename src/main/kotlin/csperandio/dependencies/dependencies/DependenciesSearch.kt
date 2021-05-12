package csperandio.dependencies.dependencies

import org.apache.maven.model.io.xpp3.MavenXpp3Reader

class DependenciesSearch {

    fun dependencies(pom: String): List<Dependency> {
        val reader = MavenXpp3Reader()
        val model = reader.read(pom.reader())

        return model.dependencies
            .filter { it.groupId != null && it.artifactId != null && it.version != null }
            .map {
                if (it.version.startsWith("\${")) {
                    val propName = it.version.substring(2, it.version.length - 1)
                    val realVersion = model.properties[propName] as String?
                    if (realVersion == null) null else Dependency(it.groupId, it.artifactId, realVersion)
                } else {
                    Dependency(it.groupId, it.artifactId, it.version)
                }
            }
            .filterNotNull()
    }

}
