# Movie App

## Overview
A simple movies app that fetches movies data using [TheMovieDB API](https://developers.themoviedb.org/3/movies)


<p align="center">
  <img src="https://github.com/DresdRam/movie-app/blob/main/screenshots/overview.gif" alt="animated" width="370"/>
</p>


## UI
The UI design of this project is inspired by the [Cinemax Movie Apps UI Kit on Figma](https://www.figma.com/design/wIvEM76AxAkiAsZbx7lQHp/Cinemax---Movie-Apps-UI-Kit--Community-?node-id=5-2&p=f&t=aKAMwsxzHPOESMuM-0).

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

For simplicity in this task, I moved my API key from the local.properties file directly into the build config.
I’m aware that the recommended and more secure approach is to keep API keys in the local.properties file (or use other secure storage mechanisms), and I would normally follow that practice in a production environment.


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
