kotlinc main.kt -include-runtime -d out.jar
if [ $? == 0 ]; then
    java -jar out.jar
fi
