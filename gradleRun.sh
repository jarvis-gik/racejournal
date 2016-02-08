#!/usr/bin/env bash
echo Build Shadow Jar...
gradle shadowJar
echo Remove existing protobuf file
rm -f RaceDirectoryProtoBufData
echo Run racejournal.jar...
java -jar build/libs/racejournal-all.jar
