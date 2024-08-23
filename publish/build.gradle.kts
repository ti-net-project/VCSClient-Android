plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

android {
    namespace = "com.tinet.publish"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

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

    publishing {
        singleVariant("release")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // kotlin 协程
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

    // kotlin 反射
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")

    //声网RTM SDK
    implementation("io.agora:agora-rtm:2.2.0")

    // retrofit2 gson 转换库
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    testImplementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // json 解析库
    api("com.google.code.gson:gson:2.9.0")
    testImplementation("com.google.code.gson:gson:2.9.0")

    // 网络请求库
    api("com.squareup.okhttp3:okhttp:3.14.9")
    api("com.squareup.retrofit2:retrofit:2.9.0")
    testImplementation("com.squareup.okhttp3:okhttp:3.14.9")
    testImplementation("com.squareup.retrofit2:retrofit:2.9.0")

    //观测云
    implementation("com.cloudcare.ft.mobile.sdk.tracker.agent:ft-sdk:1.5.0")
    implementation("com.cloudcare.ft.mobile.sdk.tracker.agent:ft-native:1.1.0")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.ti-net-project"
            artifactId = "VSClient-Android"
            version = "0.1.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}