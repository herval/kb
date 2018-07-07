package kb.config

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.nio.file.Files
import java.nio.file.Path

class YamlParser {
    private val mapper = ObjectMapper(YAMLFactory())

    fun <T> load(inputFile: Path, clazz: Class<T>): T {
        return Files.newBufferedReader(inputFile).use {
            mapper.readValue(it, clazz)
        }
    }

    fun <T> save(outputPath: Path, obj: T) {

    }

    init {
        mapper.registerModule(KotlinModule())
        // TODO support empty config
        mapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true)
    }
}