#!/usr/bin/env bash
rm -f RaceDirectoryProtoBufData
protoc -I=./src/main/proto --java_out=./src/main/java ./src/main/proto/racedirectory.proto
mvn clean
mvn jetty:run