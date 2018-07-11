#!/bin/sh

set -ex

gradle shadowJar

$GRAALVM_HOME/bin/native-image -cp ./build/libs/kb.jar -H:Name=kb -H:Class=kb.MainKt -H:+ReportUnsupportedElementsAtRuntime -H:IncludeResources='./src/main/resources/*' -H:IncludeResources='./src/main/resources/templates/*'
