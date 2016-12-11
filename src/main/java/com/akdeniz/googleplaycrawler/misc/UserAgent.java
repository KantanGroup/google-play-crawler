package com.akdeniz.googleplaycrawler.misc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @david.jackson
 *
 * This class represents the Android's user agent which contains the model's information that determine's compatibility
 * with the Google Play Store API. It seems that a particular user agent does not need to match with the device's
 * Google Service Framework ID (androidid). As such, I provided the user agent of my test Android device.
 *
 * If the UserAgent is not updated after awhile, the Google Service API will return this message:
 *      “This app is incompatible with your device list.”
 *
 * See reference:
 *      http://blog.onyxbits.de/how-to-get-the-google-play-user-agent-for-a-given-device-140/
 */
public class UserAgent {

    private String versionName;
    private String versionCode;
    private String sdk;
    private String device;
    private String hardware;
    private String product;
    private String build;

    public UserAgent(){
        versionName = "5.5.12";
        versionCode = "80351200";
        sdk = "22";
        device = "zeroflteatt";
        hardware = "samsungexynos7420";
        product = "zeroflteatt";
        build = "LMY47X:user";
    }

    public UserAgent(String versionName, String versionCode, String sdk, String device, String hardware, String product,
                     String build) {
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.sdk = sdk;
        this.device = device;
        this.hardware = hardware;
        this.product = product;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getSdk() {
        return sdk;
    }

    public String getDevice() {
        return device;
    }

    public String getHardware() {
        return hardware;
    }

    public String getProduct() {
        return product;
    }

    public String getBuild() {
        return build;
    }

    public String toString() {
        return "Android-Finsky/" + getVersionName() + " (versionCode=" + getVersionCode() + ",sdk=" + getSdk() + ",device=" + getDevice() + ",hardware=" + getHardware() + ",product=" + getProduct() + ",build=" + getBuild() + ")";
    }
}
