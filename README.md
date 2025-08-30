# Movie App

## Overview
A simple movies app that fetches movies data using [TheMovieDB API](https://developers.themoviedb.org/3/movies)


<p align="center">
  <img src="https://github.com/DresdRam/movie-app/blob/main/screenshots/overview.gif" alt="animated" width="370"/>
</p>


## To improve/add
- [X] Unit test cases
- [X] Ui test cases
- [X] Paging


## Clean Architecture
The app is designed with multi layer MVVM architecture for better control over individual modules. Which makes the project open for scalability, and flexible to change, maintain and test.

## Configuration

1. Login into [TheMovieDB](https://www.themoviedb.org/) for getting API_KEY and ACCESS_TOKEN
2. Add `API_KEY="Your_Api_Key"` to `local.properties` file.

```kotlin
  defaultConfig {
        buildConfigField("String", "API_KEY", "\"${getLocalProperty(key = "API_KEY")}\"")
    }
 ```

## Notice

Due to being a simple task, i pushed a commit to move my Api key from local.properties file directly to build configs.



## Features

- Jetpack Compose
- Compose Navigation
- Material Design 3
- Themes
- Flows
- Retrofit2
- Room DB
- Hilt
- Coroutines
- Coil
- Modularization
