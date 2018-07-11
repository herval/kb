package kb.command

import kb.ArgsParser
import kb.Command


class Help(val args: ArgsParser) {
    fun print() {
        when (args.subcommand) {
            Command.Build -> buildHelp()
            Command.Init -> initHelp()
            Command.Run -> runHelp()
            Command.Deps -> depsHelp()
            Command.Test -> testHelp()
            else -> defaultHelp()
        }
    }

    private fun buildHelp() {
        println("""
            |Build a binary containing all sources on your project, and puts it (along with all dependency jars) on an `out` folder.
            |
            |Arguments:
            |   --fatjar    build a fatjar. This will include all the dependencies in a single jar file. Ideal for distributing a binary with no external dependencies.
            |   --binary    build a native binary. This requires GraalVM to be installed.
            |   --jar       build a regular jar, with or without a main class (as per the project.yaml config)
            """.trimMargin())
    }

    private fun initHelp() {
        println("""
            |Creates the default folder structure and a project.yaml config file on the current folder.
            |
            | Arguments:
            |   --root-path     initialize the project on the given folder, instead of current path
        """".trimMargin())
    }

    private fun runHelp() {
        println("""
            |Compiles and runs the main class of the project.
            |
            |Arguments:
            |   --app-args      pass down parameters to the app as-is, separated by spaces. Eg.: --app-args "-foo=true -bar=false"
            |   --jvm-args      pass down these arguments to the JVM, separated by spaces. Eg.: --jvm-args "-server -Xms2G"
            """.trimMargin())
    }

    private fun depsHelp() {
        println("""
            |Manages dependencies. The following commands are available:
            |
            |   --list                                       list all current project dependencies (compile and test).
            |   --add <group>:<artifact>:<version?>           add a dependency.
            |   --add-test <group>:<artifact>:<version?>      add a test dependency.
            |   --upgrade <group>:<artifact>:<version?>       upgrade a dependency to the given version.
            |   --upgrade-test <group>:<artifact>:<version?>  upgrade a test dependency.
            |
            |   If <version> is not specified, kb will try to upgrade to the latest available version.
            """.trimMargin())
    }

    private fun testHelp() {
        println("""
            |Run all tests on the test path. The following commands are available:
            |
            |   --target    run a single target.
            |
            |The format can be either a package (in which case all tests in that package will be ran - eg "com.foo"), a class file ("com.foo.BarTest") or a specific test ("com.foo.BarTest:methodName").
            """.trimMargin())
    }

    private fun defaultHelp() {
        println("""
            |kb provides all the tooling to build libraries, "fat jar" binaries, run tests and manage your dependencies.
            |
            |Every kb project requires code to be put on specific folder structures:
            |
            |  project.yaml                           the (optional) config file where you define project dependencies, plugins, etc
            |  src/main/<java, kotlin, resources>     the folders where source must be located. Everything under resources will automatically be bundled.
            |  src/test/<java, kotlin, resources>     the folders where tests must be located. Everything under resources will automatically be included on test runs.
            |
            |Commands:
            |
            |   kb init     Creates the default folder structure
            |   kb build    Build a binary
            |   kb run      Compiles and runs the main class of the project
            |   kb deps     Manages dependencies
            |   kb test     Run tests
            |
            |For detailed explanation of arguments and options, use "kb help <command>".
            """.trimMargin())
    }
}