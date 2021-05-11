package csperandio.dependencies.tests.unit

import csperandio.dependencies.dependencies.Dependency
import csperandio.dependencies.repo.MavenRepo

class LocalMavenRepo(val dependencies: MutableMap<String, String> = HashMap()) : MavenRepo {
    override fun date(dependency: Dependency) = dependencies[dependency.coordinates]

    operator fun set(dependency: Dependency, date: String) {
        dependencies[dependency.coordinates] = date
    }
}