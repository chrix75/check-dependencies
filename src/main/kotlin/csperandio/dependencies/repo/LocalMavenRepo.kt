package csperandio.dependencies.repo

import csperandio.dependencies.dependencies.Dependency

class LocalMavenRepo(val dependencies: MutableMap<String, String> = HashMap()) : MavenRepo {
    override fun date(d: Dependency) = dependencies[d.coordinates]

    operator fun set(dependency: Dependency, date: String) {
        dependencies[dependency.coordinates] = date
    }
}