plugins {
    kotlin("jvm") version "1.3.21"
    java
}

repositories {
    jcenter()
}

dependencies {
    testCompile("junit:junit:4.+")
}

tasks.test {
    testLogging {
        events("PASSED", "FAILED", "SKIPPED", "STANDARD_ERROR", "STANDARD_OUT")
    }
}