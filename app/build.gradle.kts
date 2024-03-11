plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.diophantine"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.diophantine"
        minSdk = 23
        targetSdk = 34
        versionCode = 1
        versionName = "1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        viewBinding = true
        compose = true
        buildConfig = true
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.squareup.okhttp3:okhttp:4.6.0")
    implementation("com.squareup.moshi:moshi:1.9.3")
    implementation("com.squareup.okio:okio:2.8.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    modules {
        module("org.jetbrains.kotlin:kotlin-stdlib-jdk7") {
            replacedBy("org.jetbrains.kotlin:kotlin-stdlib", "kotlin-stdlib-jdk7 is now part of kotlin-stdlib")
        }
        module("org.jetbrains.kotlin:kotlin-stdlib-jdk8") {
            replacedBy("org.jetbrains.kotlin:kotlin-stdlib", "kotlin-stdlib-jdk8 is now part of kotlin-stdlib")
        }
    }
}