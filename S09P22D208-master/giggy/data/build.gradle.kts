import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    namespace = "com.d208.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        jvmTarget = "17"
    }
}

dependencies {
    implementation (project (":domain"))
    implementation("androidx.core:core-ktx:1.8.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Retrofit
    implementation (com.d208.buildsrc.Retrofit.RETROFIT)
    implementation (com.d208.buildsrc.Retrofit.CONVERTER_GSON)
    implementation (com.d208.buildsrc.Retrofit.CONVERTER_JAXB)

    //okHttp
    implementation (com.d208.buildsrc.OkHttp.OKHTTP)
    implementation (com.d208.buildsrc.OkHttp.LOGGING_INTERCEPTOR)

    //coroutines
    implementation (com.d208.buildsrc.Coroutines.COROUTINES)
    implementation (com.d208.buildsrc.Coroutines.COROUTINES_PLAY_SERVICES)

    // dager hilt
    implementation (com.d208.buildsrc.DaggerHilt.DAGGER_HILT)
    kapt (com.d208.buildsrc.DaggerHilt.DAGGER_HILT_COMPILER)
    kapt (com.d208.buildsrc.DaggerHilt.DAGGER_HILT_ANDROIDX_COMPILER)
}