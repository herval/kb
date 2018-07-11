package kb

import com.xenomachina.argparser.InvalidArgumentException
import com.xenomachina.argparser.MissingRequiredPositionalArgumentException
import kb.command.*
import kb.config.ProjectConfig
import kb.config.ProjectFileName
import kb.config.YamlParser

// the kb starts here
fun main(args: Array<String>) {
    val params = ArgsParser(args)
    try {
        when (params.command) {
            Command.Init -> {
                InitProject(params).run()
                syncProjectFile(params)
            }
            Command.Help -> Help(params).print()
            else -> {
                val projectConfig = syncProjectFile(params)

                when (params.command) {
                    Command.Build -> BuildApp(projectConfig, params).run()
                    Command.Deps -> DependenciesCmd(projectConfig, params).run()
                    Command.Run -> RunApp(projectConfig).run()
                    Command.Test -> TODO()
                }
            }
        }
    } catch (e: IllegalStateException) {
        println(e.message)
    } catch (e: MissingRequiredPositionalArgumentException) {
        println(e.message)
    } catch (e: InvalidArgumentException) {
        println(e.message)
    }
}

fun syncProjectFile(params: ArgsParser): ProjectConfig {
    // resync build file for those
    val projectConfig = ProjectConfig.parseWithOverrides(
            params,
            ProjectFileName,
            YamlParser()
    )
    GenerateBuildFiles(projectConfig).run()
    return projectConfig
}
