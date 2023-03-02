package com.example.proyecto1mertrimestre.io;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectPatos {

    private static final String URL_BASE = "https://random-d.uk/api";

    //en principio mi endpoint va a tener siempre este formato: "num.jpg" siendo num un int
    //mi api tambien tiene archivos .gif pero he decidido que yo solo voy a usar los ".jpg"
    public static String getRequest(String endpoint ) {

        HttpURLConnection http = null;
        String content = null;
        try {
            URL url = new URL( URL_BASE + endpoint );
            http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");

            //comprobamos que homos recibido bien la info
            if( http.getResponseCode() == HttpURLConnection.HTTP_OK ) {

                //recogemos la info en un string
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader( http.getInputStream() ));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
                reader.close();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            //desconectamos la conexion
            if( http != null ) http.disconnect();
        }
        return content;
    }

}
