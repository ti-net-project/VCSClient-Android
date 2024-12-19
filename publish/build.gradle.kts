plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("maven-publish")
}

object PublicSdkConfig{
    const val versionName = "0.3.9"
}

android {
    namespace = "com.tinet.vcslib"
    compileSdk = 33

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    publishing {
        singleVariant("release")
    }
}

dependencies {
    api(
        fileTree(
            mapOf(
                "include" to "*.jar",
                "dir" to "libs"
            )
        )
    )

    implementation("androidx.core:core-ktx:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.24")
    implementation("io.agora:agora-rtm:2.2.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.cloudcare.ft.mobile.sdk.tracker.agent:ft-sdk:1.5.0")
    implementation("com.cloudcare.ft.mobile.sdk.tracker.agent:ft-native:1.1.0")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.ti-net-project"
            artifactId = "VCSClient-Android"
            version = PublicSdkConfig.versionName

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}

val version = PublicSdkConfig.versionName
val sdkFile = "VCSClientLib_${PublicSdkConfig.versionName}_release.zip"
val zipPackagesPath = File("${rootDir.absolutePath}/zip_packages/$sdkFile")

task<Delete>("deleteExpiredFiles"){
    dependsOn("clean")

    delete = setOf(
        "${projectDir.path}/libs",
        "${projectDir.path}/src/main/jinLibs",
        "${projectDir.path}/src/main/res",
        "${projectDir.path}/src/main/AndroidManifest.xml",
        "${projectDir.path}/proguard-rules.pro",
        "${projectDir.path}/consumer-rules.pro"
    )

    doLast {
        println("start to copy libs")

        copy {
            from(zipTree(zipPackagesPath))
            include("arm*/**","x86*/**")
            into("${project.projectDir}/src/main/jniLibs")
        }

        copy{
            from(zipTree(zipPackagesPath))
            include("*.jar")
            into("${project.projectDir}/libs")
        }

        copy{
            from(zipTree(zipPackagesPath))
            include("include/**")
            into("${project.projectDir}/src/main/jniLibs")
        }

        copy{
            from(zipTree(zipPackagesPath))
            include("res/**")
            into("${project.projectDir}/src/main")
        }

        copy{
            from(zipTree(zipPackagesPath))
            include("AndroidManifest.xml")
            into("${project.projectDir}/src/main")
        }

        copy{
            from(zipTree(zipPackagesPath))
            include("proguard-rules.pro")
            into("${project.projectDir}")
        }

        copy{
            from(zipTree(zipPackagesPath))
            include("consumer-rules.pro")
            into("${project.projectDir}")
        }
    }
}

tasks.named("preBuild"){
    dependsOn("deleteExpiredFiles")
}