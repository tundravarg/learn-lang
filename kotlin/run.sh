#!/bin/bash

WD=target
JAR=learn-kotlin-1.0-SNAPSHOT.jar
MC=tuman.learn.kotlin.AppKt

cd "$WD" && java -cp "$JAR:lib/*" $MC $*
