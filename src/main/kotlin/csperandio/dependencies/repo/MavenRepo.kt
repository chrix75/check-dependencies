package csperandio.dependencies.repo

import csperandio.dependencies.dependencies.Dependency

interface MavenRepo {
    fun date(dependency: Dependency): String?
}