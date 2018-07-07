package kb

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.InvalidArgumentException
import com.xenomachina.argparser.default
import kb.config.DefaultLanguages
import kb.config.Language
import java.nio.file.Paths

enum class Command { Init, Run, Build, Test, Deps, Help }

val commands = mapOf(
        "init" to Command.Init,
        "run" to Command.Run,
        "build" to Command.Build,
        "test" to Command.Test,
        "deps" to Command.Deps,
        "help" to Command.Help
)

class ArgsParser(args: Array<String>) {
    // accept 1 regex followed by n filenames
    private val parser = ArgParser(args)

    val command by parser.positional(
            help = "Please specify a command. Type kb help for options.",
            transform = {
                commands[this] ?: throw InvalidArgumentException("Unknown command: ${this}. Type kb help for options.")
            }
    )

    val appArgs by parser.storing(
            "--app-args",
            help = "Args to be supplied to the app (space separated)"
    ).default("")

    val jvmArgs by parser.storing(
            "--jvm-args",
            help = "Args to be supplied to the JVM"
    ).default("")

    val version by parser.storing(
            "--version",
            help = "The application/lib version"
    ).default("0.0.0")

    val mainClass by parser.storing(
            "--main-class",
            help = "Fully qualified name of the main class"
    ).default("")

    val outputName by parser.storing(
            "--output-name",
            help = "Name of the output compiled object"
    ).default("")

    val languages by parser.adding(
            "--language",
            help = "Specify the programming languages used in the project. Supported values: ${Language.values().map { it.name.toLowerCase() }.joinToString(", ")}",
            transform = {
                try {
                    Language.valueOf(this).name
                } catch (e: IllegalArgumentException) {
                    throw InvalidArgumentException("Language not supported: ${this}")
                }
            }
    )

    val rootPath by parser.storing(
            "--root-path", help = "The root path of the project"
    ).default(
            Paths.get("").toAbsolutePath().toString() // current path is the root path by default
    )
}
