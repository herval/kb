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

    val subcommand by parser.positional(
            help = "Command to get help for",
            transform = {
                commands[this] ?: throw InvalidArgumentException("Unknown command: ${this}. Type kb help for options.")
            }
    ).default(Command.Help)

    val appArgs by parser.storing(
            "--app-args"
    ).default("")

    val jvmArgs by parser.storing(
            "--jvm-args"
    ).default("")

    val list by parser.flagging(
            "--list"
    ).default(false)

    val mainClass by parser.storing(
            "--main-class"
    ).default("")

    val buildJar by parser.flagging(
            "--jar"
    ).default(true)

    val buildFatJar by parser.flagging(
            "--fatjar"
    ).default(false)

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
