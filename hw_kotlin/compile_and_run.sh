#!/bin/bash

kotlinc main.kt -include-runtime -d out.jar
if [ $? == 0 ]; then
    java -ea -jar out.jar
fi

