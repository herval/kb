package kb

import com.xenomachina.argparser.InvalidArgumentException
import kb.command.Help
import kb.command.InitProject
import kb.config.ProjectConfig
import kb.config.ProjectFileName

// the kb starts here
fun main(args: Array<String>) {
    val params = ArgsParser(args)
    val projectConfig by lazy {
        ProjectConfig.parseOrDefault(params, ProjectFileName)
    }

    try {
        when (params.command) {
            Command.Init ->
                InitProject(params.projectPath, params.languages).run()
            Command.Build ->
                println("Build")
            Command.Deps ->
                println("Deps")
            Command.Help ->
                Help(params).print()
            Command.Run ->
                println("Run")
            Command.Test ->
                Help(params).print()
        }
    } catch (e: InvalidArgumentException) {
        println(e.message)
    }
}