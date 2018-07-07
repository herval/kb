#!/bin/sh

$GRAALVM_HOME/bin/native-image -cp ./build/libs/kb.jar -H:Name=kb -H:Class=kb.MainKt -H:+ReportUnsupportedElementsAtRuntime
