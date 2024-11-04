/*  plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.ulpsoft.inmobiliarialaboratorio3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ulpsoft.inmobiliarialaboratorio3"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.legacy.support.v4)
    implementation(libs.activity)
    implementation(libs.play.services.maps)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.scalars)
    implementation(libs.glide)
    implementation(libs.annotations)
    implementation(libs.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

 */


plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.ulpsoft.inmobiliarialaboratorio3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ulpsoft.inmobiliarialaboratorio3"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Dependencias esenciales para el proyecto
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.legacy.support.v4)
    implementation(libs.activity)
    implementation(libs.play.services.maps)
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.converter.scalars)

    // Glide para cargar imágenes
    implementation(libs.glide)
    implementation(libs.protolite.well.known.types)


    // Dependencias de pruebas
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
