plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id ("dagger.hilt.android.plugin")
    id ("kotlin-kapt")
}

android {
    namespace = "ai.labiba.labibavoiceassistant"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    android.viewBinding.enable= true
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    //by viewModels
    implementation ("androidx.fragment:fragment-ktx:1.6.2")

    
    //Coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")

    //Coroutine Lifecycle Scopes
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")

    //Coil
    implementation("io.coil-kt:coil:2.4.0")
    implementation("io.coil-kt:coil-gif:2.2.2")

    //Dagger - Hilt
    implementation ("com.google.dagger:hilt-android:2.48.1")
    kapt ("com.google.dagger:hilt-android-compiler:2.48.1")

    //Room
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation 'com.squareup.retrofit2:converter-scalars:2.9.0'//to be able to receive the response as string
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.1")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.1")

    //Gson
    implementation ("com.google.code.gson:gson:2.10")

}