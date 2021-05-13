package csperandio.dependencies.dependencies

import org.apache.maven.model.io.xpp3.MavenXpp3Reader
import java.io.Reader

class DependenciesSearch(pom: Reader) {
    private val reader = MavenXpp3Reader()
    private val model = reader.read(pom)

    fun dependencies(): List<Dependency> {
        val allDependencies = mutableListOf<Dependency>()
        with(allDependencies) {
            addAll(mavenDependencies())
            addAll(mavenPluginDependencies())
        }
        return allDependencies
    }

    private fun mavenPluginDependencies() = model.build?.plugins
        ?.filter { it.groupId != null && it.artifactId != null && it.version != null }
        ?.map { Dependency(it.groupId, it.artifactId, it.version) }
        ?.mapNotNull { updateVersionByProperty(it) }
        ?: emptyList()

    private fun mavenDependencies() = model.dependencies
        ?.filter { it.groupId != null && it.artifactId != null && it.version != null }
        ?.map { Dependency(it.groupId, it.artifactId, it.version) }
        ?.mapNotNull { updateVersionByProperty(it) }
        ?: emptyList()

    private fun updateVersionByProperty(dependency: Dependency) =
        if (dependency.version.startsWith("\${")) {
            replaceVersion(dependency)
        } else {
            dependency
        }

    private fun replaceVersion(dependency: Dependency): Dependency? {
        val propName = dependency.version.removePrefix("\${").removeSuffix("}")
        val realVersion = model.properties[propName] as String?
        return if (realVersion == null) {
            null
        } else {
            Dependency(dependency.group, dependency.artifact, realVersion)
        }
    }
}
