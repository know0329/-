plugins {
    id ("org.jetbrains.kotlin.jvm")
    id ("kotlin-kapt")
}

dependencies {

    // dager hilt
    implementation(com.d208.buildsrc.DaggerHilt.DAGGER_HILT_JAVAX)

    // Retrofit
    implementation (com.d208.buildsrc.Retrofit.RETROFIT)
    implementation (com.d208.buildsrc.Retrofit.CONVERTER_GSON)
    implementation (com.d208.buildsrc.Retrofit.CONVERTER_JAXB)

    //okHttp
    implementation (com.d208.buildsrc.OkHttp.OKHTTP)
    implementation (com.d208.buildsrc.OkHttp.LOGGING_INTERCEPTOR)
}