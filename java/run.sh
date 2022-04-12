#!/bin/bash

WD=target
JAR=learn-java-1.0-SNAPSHOT.jar
MC=tuman.learn.java.App

cd "$WD" && java -cp "$JAR" $MC $*
