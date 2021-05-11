package csperandio.dependencies.repo

import csperandio.dependencies.dependencies.Dependency

class LocalMavenRepo(val dependencies: MutableMap<String, String> = HashMap()) : MavenRepo {
    override fun date(dependency: Dependency) = dependencies[dependency.coordinates]

    operator fun set(dependency: Dependency, date: String) {
        dependencies[dependency.coordinates] = date
    }
}