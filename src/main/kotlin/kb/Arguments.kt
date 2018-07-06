package kb

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException
import com.xenomachina.argparser.default
import java.nio.file.Paths

enum class Command { Init, Run, Build, Test, Deps, Help }

val commands = mapOf(
        "init" to Command.Init,
        "run" to Command.Run,
        "test" to Command.Test,
        "deps" to Command.Deps,
        "help" to Command.Help
)

enum class Language {
    Java, Kotlin
}

val DefaultLanguages = listOf(Language.Java, Language.Kotlin)

class ArgsParser(args: Array<String>) {
    // accept 1 regex followed by n filenames
    private val parser = ArgParser(args)

    val command by parser.positional(
            help = "Please specify a command. Type kb help for options.",
            transform = {
                commands[this] ?: throw InvalidArgumentException("Unknown command: ${this}. Type kb help for options.")
            }
    )

    val languages by parser.adding(
            "--language",
            help = "Specify the programming languages used in the project. Supported values: ${Language.values().map { it.name.toLowerCase() }.joinToString(", ")}",
            transform = {
                try {
                    Language.valueOf(this.capitalize())
                } catch (e: IllegalArgumentException) {
                    throw InvalidArgumentException("Language not supported: ${this}")
                }
            }
    ).default(DefaultLanguages)

    val projectPath by parser.storing(
            "--root-path", help = "The root path of the project"
    ).default(
            Paths.get("").toAbsolutePath().toString() // current path is the root path by default
    )
}
