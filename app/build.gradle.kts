plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.my_friend_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.my_friend_app"
        minSdk = 24
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

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.navigation:navigation-fragment:2.7.7")
    implementation("androidx.navigation:navigation-ui:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

// круглое изображение
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    //передача  фото, картинки и работа с ней:
    implementation ("com.github.bumptech.glide:glide:4.16.0")

    //Возможность преобразовать любой объект в необходимый формат:
    implementation ("androidx.multidex:multidex:2.0.1")


    // выбор фото из галереи и ипользование:
    implementation ("com.karumi:dexter:6.2.3")
    // New version
    implementation ("androidx.multidex:multidex:2.0.1")
//Room
    implementation ("androidx.room:room-runtime:2.6.1")
    annotationProcessor ("androidx.room:room-compiler:2.6.1")
    //подключение к room database
    implementation ("android.arch.persistence.room:runtime:1.1.1")
    annotationProcessor ("android.arch.persistence.room:compiler:1.1.1")
    //расширение возможностей фрагмента:
    implementation ("androidx.fragment:fragment:1.6.2")

}