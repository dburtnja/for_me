package objects;

import com.google.gson.Gson;
import objects.ExeptionObj.ExceptionObj;
import objects.ticket.Ticket;

import java.lang.reflect.Type;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Post {
    public Object sendPost(String url, String urlParameters, Type type, Ticket ticket) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);
            String cookie = con.getHeaderField(4).substring(0, con.getHeaderField(4).length() - 8);
            System.out.println(con.getHeaderField(4));
            ticket.cookieStore.setText("document.cookie = \"" + cookie + "\";");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String str = in.readLine();
            if (type != null) {
                Gson gson = new Gson();
                try {
                    return gson.fromJson(str, type);
                } catch (Exception e) {
                    return gson.fromJson(str, ExceptionObj.class);
                }
            } else
                return str;
        } catch (Exception e) {
            System.out.println("Помилка методу POST за посиланням: " + url);
            return null;
        }
    }
}
