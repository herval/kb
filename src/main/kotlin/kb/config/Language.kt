package kb.config


enum class Language {
    java,
    kotlin
}

fun sourcePaths(languages: List<Language>): List<String> {
    return languages.map { "src/main/${it.name.toLowerCase()}" }
}

fun testPaths(languages: List<Language>): List<String> {
    return languages.map { "src/test/${it.name.toLowerCase()}" }
}

val DefaultLanguages = listOf(
        Language.java,
        Language.kotlin
)