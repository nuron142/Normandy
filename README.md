# Sunil Kumar

The app contains a list screen and detail screen. It follows MVVM with Clean Architecture and the whole app is written in Kotlin

The Project consists of three modules:
app - Android module which contains Activities
data - Android library module which has implementation for room Database and Retrofit
domain - Java library which contains models and Use cases

Libraries
===============
Android Architecture Components,
Dagger 2,
RxJava 2,
OkHttp,
Glide,
Junit,
Mockito,
Espresso

Building
==========
The app can be build using "./gradlew assembleDebug" command.
Use "./gradlew test" to run the unit tests
