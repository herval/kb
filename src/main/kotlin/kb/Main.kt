package kb

import com.xenomachina.argparser.InvalidArgumentException
import com.xenomachina.argparser.MissingRequiredPositionalArgumentException
import kb.command.GenerateBuildFiles
import kb.command.Help
import kb.command.InitProject
import kb.config.ProjectConfig
import kb.config.ProjectFileName
import kb.config.YamlParser

// the kb starts here
fun main(args: Array<String>) {
    val params = ArgsParser(args)
    try {
        when (params.command) {
            Command.Init -> InitProject(params).run()
            Command.Help -> Help(params).print()
            else -> {
                // resync build file for those
                val projectConfig = ProjectConfig.parseWithOverrides(
                        params,
                        ProjectFileName,
                        YamlParser()
                )
                GenerateBuildFiles(projectConfig).run()

                when (params.command) {
                    Command.Build -> TODO()
                    Command.Deps -> TODO()
                    Command.Run -> TODO()
                    Command.Test -> TODO()
                }
            }
        }
    } catch (e: MissingRequiredPositionalArgumentException) {
        println(e.message)
    } catch (e: InvalidArgumentException) {
        println(e.message)
    }
}