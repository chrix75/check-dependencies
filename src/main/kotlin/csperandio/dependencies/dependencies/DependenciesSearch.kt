package csperandio.dependencies.dependencies

import org.apache.maven.model.Model
import org.apache.maven.model.io.xpp3.MavenXpp3Reader

class DependenciesSearch {

    fun dependencies(pom: String): List<Dependency> {
        val reader = MavenXpp3Reader()
        val model = reader.read(pom.reader())

        val allDependencies = mutableListOf<Dependency>()
        with(allDependencies) {
            addAll(mavenDependencies(model))
            addAll(mavenPluginDependencies(model))
        }
        return allDependencies
    }

    private fun mavenPluginDependencies(model: Model): List<Dependency> {
        return model.build?.plugins?.filter { it.groupId != null && it.artifactId != null && it.version != null }
            ?.mapNotNull {
                if (it.version.startsWith("\${")) {
                    val propName = it.version.substring(2, it.version.length - 1)
                    val realVersion = model.properties[propName] as String?
                    if (realVersion == null) null else Dependency(it.groupId, it.artifactId, realVersion)
                } else {
                    Dependency(it.groupId, it.artifactId, it.version)
                }
            }
            ?: emptyList()
    }

    private fun mavenDependencies(model: Model): List<Dependency> {
        return model.dependencies
            .filter { it.groupId != null && it.artifactId != null && it.version != null }
            .mapNotNull {
                if (it.version.startsWith("\${")) {
                    val propName = it.version.substring(2, it.version.length - 1)
                    val realVersion = model.properties[propName] as String?
                    if (realVersion == null) null else Dependency(it.groupId, it.artifactId, realVersion)
                } else {
                    Dependency(it.groupId, it.artifactId, it.version)
                }
            }
    }

}
