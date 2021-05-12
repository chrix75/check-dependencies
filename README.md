# check-dependencies

Give you the date of all your dependency versions in a Maven project.

## Description

This program read all pom files of your project and provide the date for every declared dependency.
Thus, you know if your project is outdated.


## Build
Go inside the project directory and execute `mvn clean install`.

That command will build a standalone jar.

## Usage
Once you have built the jar file, you can use it with the command:

`java -jar check-dependencies-1.0-jar-with-dependencies.jar [PROJECT_FOLDER] [OUTPUT_FILE]`

where:
- PROJECT_FOLDER is your project directory in which the root pom file is.
- OUTPUT_FILE is the report file. This file uses the CSV format.

### Example
You have a Maven project `/home/foobar/myproject` and you want the report as `/home/foobar/myproject_report.csv`, you execute the command:

`java -jar check-dependencies-1.0-jar-with-dependencies.jar /home/foobar/myproject /home/foobar/myproject_report.csv`

The result will be like:
```shell
dependency,version,date
org.jsoup:jsoup,1.13.1,2020-03-01
org.junit.jupiter:junit-jupiter,5.7.1,2021-02-04
org.jetbrains.kotlin:kotlin-test,1.5.0,2021-04-26
```

## Notes
- All pom files in a project are processed
- Work only with Maven
- Only dependencies in Maven Central are managed (you can upgrade that by coding ;) )
- Versions have been set by properties are not managed (not yet)


