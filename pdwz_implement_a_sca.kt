package com.pdwz.analyzer

import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * Scalable Web App Analyzer
 */
object WebAppAnalyzer {

    private val httpClient = OkHttpClient()

    fun analyze(url: String) {
        val request = Request.Builder().url(url).build()
        val response = httpClient.newCall(request).execute()

        val doc = Jsoup.parse(response.body!!.string())

        // Extract page metadata
        val title = doc.title()
        val metaTags = doc.getElementsByTag("meta")

        // Extract CSS and JavaScript files
        val cssFiles = extractResources("link[rel=stylesheet]", doc)
        val jsFiles = extractResources("script[src]", doc)

        // Analyze page performance
        val pageLoadTime = measurePageLoadTime(url)
        val pageSize = measurePageSize(response.body!!.byteStream())

        // Print analysis results
        println("Title: $title")
        println("Meta Tags:")
        metaTags.forEach { println("  ${it.attr("name")} = ${it.attr("content")}") }
        println("CSS Files:")
        cssFiles.forEach { println("  $it") }
        println("JavaScript Files:")
        jsFiles.forEach { println("  $it") }
        println("Page Load Time: $pageLoadTime ms")
        println("Page Size: $pageSize bytes")
    }

    private fun extractResources(selector: String, doc: Document): List<String> {
        val resources = mutableListOf<String>()
        val elements: Elements = doc.select(selector)
        for (element in elements) {
            resources.add(element.attr("href") ?: element.attr("src"))
        }
        return resources
    }

    private fun measurePageLoadTime(url: String): Long {
        // Implement page load time measurement logic here
        return 0
    }

    private fun measurePageSize(inputStream: java.io.InputStream): Long {
        // Implement page size measurement logic here
        return 0
    }
}