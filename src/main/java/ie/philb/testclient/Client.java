/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.philb.testclient;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author philb
 */
public class Client {

    public static void main(String[] args) throws Exception {

        // System.setProperty("javax.net.debug", "all");

        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };

        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, trustAllCerts, null);

        SSLSocketFactory ssf = sslcontext.getSocketFactory();
        SSLSocket s = (SSLSocket) ssf.createSocket("192.168.0.10", 1234);
        s.startHandshake();

        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();
        os.write("hello".getBytes());

        int i;
        StringBuilder sb = new StringBuilder();

        while ((i = is.read()) != -1) {
            char c = (char) i;
            sb.append(c);
        }

        close(is);
        close(os);
        close(s);

        System.out.println("Got -->" + sb + "<--");
    }

    private static void close(Socket s) {
        if (s == null) {
            return;
        }
        try {
            s.close();
        } catch (Exception ex) {
        }
    }

    private static  void close(OutputStream os) {
        if (os == null) {
            return;
        }
        try {
            os.close();
        } catch (Exception ex) {
        }
    }

    private static void close(InputStream is) {
        if (is == null) {
            return;
        }
        try {
            is.close();
        } catch (Exception ex) {
        }
    }
}
