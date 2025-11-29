val keystoreProperties = Properties()
val keyPropsFile = file("key.properties")

if (keyPropsFile.exists()) {
    keystoreProperties.load(FileInputStream(keyPropsFile))
}

android {
    signingConfigs {
        create("release") {
            if (keyPropsFile.exists()) {
                storeFile = file(keystoreProperties["storeFile"] as String)
                storePassword = keystoreProperties["storePassword"] as String
                keyAlias = keystoreProperties["keyAlias"] as String
                keyPassword = keystoreProperties["keyPassword"] as String
            } else {
                // 无签名 APK
                enableV1Signing = false
                enableV2Signing = false
            }
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
        }
    }
}
