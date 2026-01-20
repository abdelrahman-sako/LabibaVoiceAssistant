import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

}

android {
    namespace = "ai.labiba.voiceassistantclient"
    compileSdk = 34

    defaultConfig {
        applicationId = "ai.labiba.voiceassistantclient"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "${gradleLocalProperties(rootDir).getProperty("BASE_URL")}")
        buildConfigField("String", "VOICE_BASE_URL", "${gradleLocalProperties(rootDir).getProperty("VOICE_BASE_URL")}")
        buildConfigField("String", "MESSAGING_PATH", "${gradleLocalProperties(rootDir).getProperty("MESSAGING_PATH")}")
        buildConfigField("String", "VOICE_PATH", "${gradleLocalProperties(rootDir).getProperty("VOICE_PATH")}")
        buildConfigField("String", "BOT_ID", "${gradleLocalProperties(rootDir).getProperty("BOT_ID")}")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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

    android.buildFeatures.buildConfig = true
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


    implementation (project(":LabibaVoiceAssistant"))


}