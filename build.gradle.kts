// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.serialization)
        classpath("com.google.gms:google-services:4.3.15")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
        maven("https://maven.google.com")
        jcenter()
    }
}

plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.google.devtools.ksp) apply false
}