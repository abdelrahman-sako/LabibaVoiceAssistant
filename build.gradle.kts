plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("maven-publish")
}

android {
    namespace = "ai.labiba.labibavoiceassistant"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        //add proguard roles to aar file
        consumerProguardFiles("proguard-rules.pro")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }



    publishing {
        singleVariant("release") {
            withSourcesJar()
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

publishing{
    publications {
        register<MavenPublication>("release") {
            groupId = "ai.labiba"
            artifactId = "labibavoiceassistant"
            version = "1.0"

            afterEvaluate {
                from(components["release"])
            }
        }

        repositories {
            maven {
                name = "labibaVoiceAssistantRepo"
                url = uri("${project.buildDir}/repo")
            }
        }

    }
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

    //Room
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation 'com.squareup.retrofit2:converter-scalars:2.9.0'//to be able to receive the response as string
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.11")

    //Gson
    implementation ("com.google.code.gson:gson:2.10")

}