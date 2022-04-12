#!/bin/bash

WD=target
JAR=learn-kotlin-1.0-SNAPSHOT.jar
MC=tuman.learn.kotlin.App

cd "$WD" && java -cp "$JAR" $MC $*
