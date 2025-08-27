import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.safe.args)
}

android {
    namespace = "sq.mayv.data.remote"
    compileSdk = 36

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "IMAGE_BASE_URL", "\"https://image.tmdb.org/t/p\"")
        buildConfigField("String", "TMDB_BASE_URL", "\"https://api.themoviedb.org/3\"")
        buildConfigField("String", "API_KEY", "\"${getLocalProperty(key = "API_KEY")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":data:model"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.android.compiler)

    // Retrofit
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.retrofit2.kotlin.serialization)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.retrofit2)
}

fun getLocalProperty(key: String): String {
    val localFile = project.rootProject.file("local.properties")
    if (localFile.exists()) {
        val properties = Properties()
        localFile.inputStream().use { properties.load(it) }
        return properties.getProperty(key, "")
    }
    return ""
}