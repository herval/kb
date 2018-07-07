package kb.config


enum class Language {
    java,
    kotlin
}

fun sourcePaths(languages: List<String>): List<String> {
    return languages.map { "src/main/${it.toLowerCase()}" }
}

fun testPaths(languages: List<String>): List<String> {
    return languages.map { "src/test/${it.toLowerCase()}" }
}

val DefaultLanguages = listOf(
        Language.java.name,
        Language.kotlin.name
)