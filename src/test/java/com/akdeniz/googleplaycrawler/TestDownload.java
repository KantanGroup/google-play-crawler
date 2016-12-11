package com.akdeniz.googleplaycrawler;

import com.akdeniz.googleplaycrawler.misc.UserAgent;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DefaultHttpClient;
import org.testng.annotations.BeforeClass;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * This test unit downloads a single Android app to the user's "~/Downloads/" directory. Be sure to fill out the
 * "login.conf" parameters accordingly.
 */
public class TestDownload {

    public static void main(String[] args) throws Exception
    {
        // https://play.google.com/store/apps/details?id=com.nianticlabs.pokemongo&hl=en
        String packageName = "com.nianticlabs.pokemongo";
        String downloadPath = System.getProperty("user.home") + "/Downloads/";
        String loginConfFile = "./src/test/resources/login.conf";
        String userAgentConfFile = "./src/test/resources/user_agent.conf";
        GooglePlayAPI service = getService(loginConfFile, userAgentConfFile);
        download(service, packageName, downloadPath);
    }

    private static void download(GooglePlayAPI service, String packageName, String downloadPath) throws Exception {
        service.setLocalization("en-US");
        service.login();

        System.out.println("get details...");
        GooglePlay.DetailsResponse details = service.details(packageName);
        GooglePlay.AppDetails appDetails = details.getDocV2().getDetails().getAppDetails();
        GooglePlay.Offer offer = details.getDocV2().getOffer(0);

        System.out.println("\n" + appDetails.toString() + "\n");

        int versionCode = appDetails.getVersionCode();
        long installationSize = appDetails.getInstallationSize();
        int offerType = offer.getOfferType();

        System.out.println("Downloading..." + appDetails.getPackageName() + " : " + installationSize + " bytes");
        InputStream downloadStream = service.download(appDetails.getPackageName(), versionCode, offerType);

        System.out.println("write file...");
        FileOutputStream outputStream = new FileOutputStream(downloadPath + packageName);

        byte buffer[] = new byte[1024];
        for (int k; (k = downloadStream.read(buffer)) != -1;) {
            outputStream.write(buffer, 0, k);
        }
        downloadStream.close();
        outputStream.close();
        System.out.println("Downloaded! " + packageName);
    }

    @BeforeClass
    private static GooglePlayAPI getService(String loginConfFile, String userAgentConfFile) throws Exception {

        String configFile = "./src/test/resources/login.conf";

        Properties configProperties = new Properties();
        configProperties.load(new FileInputStream(configFile));

        String email = configProperties.getProperty("email");
        String password = configProperties.getProperty("password");
        String androidId = configProperties.getProperty("androidid");
        String host = configProperties.getProperty("host");
        String port = configProperties.getProperty("port");

        String versionName = configProperties.getProperty("versionName");
        String versionCode = configProperties.getProperty("versionCode");
        String sdk = configProperties.getProperty("sdk");
        String device = configProperties.getProperty("device");
        String hardware = configProperties.getProperty("hardware");
        String product = configProperties.getProperty("product");
        String build = configProperties.getProperty("build");
        UserAgent userAgent = new UserAgent(versionName, versionCode, sdk, device, hardware, product, build);

        GooglePlayAPI service = new GooglePlayAPI(email, password, androidId, userAgent);

        if (host != null && port != null) {
            service.setClient(getProxiedHttpClient(host, Integer.valueOf(port)));
        }

        return service;
    }

    private static HttpClient getProxiedHttpClient(String host, Integer port) throws Exception {
        HttpClient client = new DefaultHttpClient(GooglePlayAPI.getConnectionManager());
        client.getConnectionManager().getSchemeRegistry().register(Utils.getMockedScheme());
        HttpHost proxy = new HttpHost(host, port);
        client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        return client;
    }

}
