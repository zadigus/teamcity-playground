import jetbrains.buildServer.configs.kotlin.v2019_2.*
import jetbrains.buildServer.configs.kotlin.v2019_2.buildSteps.script
import jetbrains.buildServer.configs.kotlin.v2019_2.projectFeatures.buildReportTab
import jetbrains.buildServer.configs.kotlin.v2019_2.triggers.vcs

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2021.2"

project {

    buildType(Build)
    buildType(OtherBuild)

    features {
        buildReportTab {
            id = "PROJECT_EXT_3"
            title = "test"
            startPage = "test.zip!index.html"
        }
    }
}

object Build : BuildType({
    name = "Build"

    artifactRules = """
        tests_integration/pickles-report => pickles-report.zip
        my-other-path => other-path.zip
    """.trimIndent()

    params {
        password("env.MY_SECRET_VALUE", "credentialsJSON:faf2d7c8-3565-452a-8cfe-a7a55a4f0f4c", display = ParameterDisplay.HIDDEN)
    }

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            scriptContent = "./hello-world.sh"
        }
    }

    triggers {
        vcs {
        }
    }
})

object OtherBuild : BuildType({
    name = "Other Build"

    vcs {
        root(DslContext.settingsRoot)
    }

    steps {
        script {
            scriptContent = "./hello-world.sh"
        }
    }

    triggers {
        vcs {
        }
    }
})
