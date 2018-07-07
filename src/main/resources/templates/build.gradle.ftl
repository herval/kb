//
// WARNING: THIS FILE IS AUTOGENERATED BY kb. ANY MODIFICATIONS WILL BE IGNORED! 🤷‍♂️
//

<#list languages as language>
    <#if language == 'java'>
apply plugin: 'java'
    </#if>
    <#if language == 'kotlin'>
apply plugin: 'kotlin'
    </#if>
</#list>


buildscript {
    ext.kotlin_version = '1.2.51'

    repositories {
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath 'com.github.jengelman.gradle.plugins:shadow:2.0.4'
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
    }
}

<#list languages as language>
    <#if language == 'kotlin'>
compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

compileTestKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
    </#if>
</#list>


<#if group??>
group = '${group}'
</#if>

<#if version??>
version = '${version}'
</#if>

apply plugin: 'application'

<#if mainClass??>
mainClassName = "${mainClass}"
</#if>

<#if jvmArgs??>
applicationDefaultJvmArgs = [${jvmArgs}]
</#if>

repositories {
    <#list repositories as repository>
        maven { url "${repository}" }
    </#list>
}

sourceSets {
    <#list sourcePaths as path>
        main.java.srcDirs += '${path}'
    </#list>
    <#list testPaths as path>
        test.java.srcDirs += '${path}'
    </#list>
}

dependencies {
    <#list dependencies as dependency>
        compile '${dependency}'
    </#list>

    <#list testDependencies as dependency>
        testCompile '${dependency}'
    </#list>
}


run {
    if (project.hasProperty('args')) {
        args project.args.split('\\s')
    }
}

//
// WARNING: THIS FILE IS AUTOGENERATED BY kb. ANY MODIFICATIONS WILL BE IGNORED! 🤷‍♂️
//
