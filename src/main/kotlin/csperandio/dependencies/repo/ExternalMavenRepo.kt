package csperandio.dependencies.repo

import csperandio.dependencies.dependencies.Dependency
import org.jsoup.Jsoup

class ExternalMavenRepo(val url: String) : MavenRepo {
    override fun date(dependency: Dependency): String? {
        val lines = fileList(dependency)
        return versionDate(lines, dependency.artifact)
    }

    private fun versionDate(lines: List<String>, artifact: String): String? {
        val found = lines.firstOrNull { l -> l.startsWith(artifact) }
        return found?.replace(Regex("^\\S+\\s+"), "")?.replace(Regex("\\s+.+$"), "")
    }

    private fun fileList(dependency: Dependency): List<String> {
        val uri = dependency.group.replace('.', '/')
        val depUrl = "${url}/${uri}/${dependency.artifact}/${dependency.version}"
        try {
            val doc = Jsoup.connect(depUrl).get()
            val lines = doc.text().split("\n")
            return lines
        } catch (e: Exception) {
            return emptyList()
        }
    }

}