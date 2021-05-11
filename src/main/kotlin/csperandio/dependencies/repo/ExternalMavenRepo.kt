package csperandio.dependencies.repo

import csperandio.dependencies.dependencies.Dependency
import org.jsoup.Jsoup

class ExternalMavenRepo(val url: String) : MavenRepo {
    override fun date(dependency: Dependency): String? {
        val lines = versionList(dependency)
        return versionDate(lines, dependency.version)
    }

    private fun versionDate(lines: List<String>, version: String): String? {
        val marker = "${version}/"
        val found = lines.filter { l -> l.startsWith(marker) }
        if (found.isEmpty()) {
            return null
        }

        val s = found[0].replace(Regex("${marker} +"), "")
            .replace(Regex(" .+$"), "")

        return s
    }

    private fun versionList(dependency: Dependency): List<String> {
        val uri = dependency.group.replace('.', '/')
        val depUrl = "${url}/${uri}/${dependency.artefact}"
        val doc = Jsoup.connect(depUrl).get()
        val lines = doc.text().split("\n")
        return lines
    }

}