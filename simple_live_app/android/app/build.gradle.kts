import java.util.Properties
import java.io.FileInputStream

val keyPropsFile = file("key.properties")
val keystoreProperties = Properties()

if (keyPropsFile.exists()) {
    keystoreProperties.load(FileInputStream(keyPropsFile))
}

android {
    namespace = "com.example.simple_live_app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.simple_live_app"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    signingConfigs {
        create("release") {
            if (keyPropsFile.exists()) {
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
            } else {
                // CI 无签名 APK
                enableV1Signing = false
                enableV2Signing = false
            }
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }
}
