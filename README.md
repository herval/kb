# kb
## An opinionated zero-config compiler for Kotlin & Java

kb follows the design philosophy behind compiler toolchains from modern languages such as Rust, Swift and Golang. Java (and other JVM languages) have always been plagued by bulky, complex and multi-stage compiling processes.

Behind the scenes, kb essentially build and maintains a set of Gradle configurations for you.


## Project structure conventions

Every kb project requires code to be put on specific folder structures:

`project.yaml` - the (optional) config file where you define project dependencies, plugins, etc
`src/main/<java, kotlin, resources>` - the folders where source must be located. Everything under resources will automatically be bundled.
`src/test/<java, kotlin, resources>` - the folders where tests must be located. Everything under resources will automatically be included on test runs.

## Commands

### `kb build`
Build a binary containing all sources on your project, and puts it (along with all dependency jars) on an `out` folder.

Arguments:
  `--jar` - build a Jar. kb will locate the main class automatically - if there's more than one, you need to specify which one you want to use by using the `--main-class` param or via the `project.yaml` file.
  `--main-class` - specify the main class of the project. Optional if you have a single main class, or if you're building a library


### `kb run`
Compiles and runs the main class of the project.

Arguments:
  `--binary`
  `--main-class`
  `--app-args`
  `--jvm-args`


### `kb deps`
Manages dependencies. The following commands are available:

  `list` - list all current project dependencies
  `add` - add a dependency. 
  `upgrade <group>:<project>` - upgrade a dependency to the latest available release


### `kb test`
Run all tests on the test path. The following commands are available:

  `--target` - run a single target. The format can be either a package (in which case all tests in that package will be ran - eg `com.foo`), a class file (`com.foo.BarTest`) or a specific test (`com.foo.BarTest:methodName`).


## The project.yaml file

This is how you specify project dependencies, override default behaviors, include plugins, etc. The structure of a fully-loaded `project.yaml` file looks like the following:

```
repositories:
  - "http://myserver/repo" 

dependencies:
  - "org.apache.commons:commons-collections4:4.1"
  - "org.apache.commons:commons-math3:4.0"
  - "../other-project"

main-class: "foo.bar.Main"

jvm-args: "-server -Xms2G"

app-args: "-foo=true -bar=false"

output-name: "custom-jar-name"
```

## Specifying dependencies
The format of dependencies follow Gradle's convention of `<group>:<project>:<version>`

You can also specify _source dependencies_ by pointing to folders that either follow the kb structure or contain a `project.yaml` file.

## Submodules
There are no submodules. You can use source dependencies to have multiple projects in the same folder structure.