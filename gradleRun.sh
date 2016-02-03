#!/usr/bin/env bash
echo Build Shadow Jar...
gradle shadowJar
echo Remove existing protobuf file
rm -f RaceDirectoryProtoBufData
echo Run belllap.jar...
java -jar build/libs/belllap-all.jar
