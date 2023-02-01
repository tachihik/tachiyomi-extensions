package eu.kanade.tachiyomi.extension.all.mangaforfree

import eu.kanade.tachiyomi.multisrc.madara.Madara
import eu.kanade.tachiyomi.network.interceptor.rateLimit
import eu.kanade.tachiyomi.source.Source
import eu.kanade.tachiyomi.source.SourceFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class MangaForFreeFactory : SourceFactory {
    override fun createSources(): List<Source> = listOf(
        MangaForFreeEN(),
        MangaForFreeKO(),
        MangaForFreeALL(),
    )
}
class MangaForFreeEN : MangaForFree("MangaForFree.net", "https://mangaforfree.com", "en") {
    override fun chapterListSelector() = "li.wp-manga-chapter:not(:contains(Raw))"
}
class MangaForFreeKO : MangaForFree("MangaForFree.net", "https://mangaforfree.com", "ko") {
    override fun chapterListSelector() = "li.wp-manga-chapter:contains(Raw)"
}
class MangaForFreeALL : MangaForFree("MangaForFree.net", "https://mangaforfree.com", "all")

abstract class MangaForFree(
    override val name: String,
    override val baseUrl: String,
    lang: String
) : Madara(name, baseUrl, lang) {

    override val client: OkHttpClient = super.client.newBuilder()
        .rateLimit(1, 1, TimeUnit.SECONDS)
        .build()
}
