package objects;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.geometry.Pos;
import objects.search.Train;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Post {
    public Train[] value;
    public String error = null;
    public String data = null;
    public String captcha = null;

    public void sendPost(String url, String urlParameters) {

        try {
            Gson gson = new Gson();
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

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            System.out.println(in.readLine());



 /*           Post testPost  = new Post();
            testPost.value = new Train();
            String test = gson.toJson(testPost);
            System.out.println(test);*/
            TestClass postObj = gson.fromJson(in.readLine(), TestClass.class);
   //         System.out.println(postObj.value);
           // this.value = postObj.value;
           /* this.captcha = postObj.captcha;
            this.data = postObj.data;
            this.error = postObj.error;*/
        } catch (Exception e) {
            System.out.println("Помилка методу POST");
        }

    }


    /*public Post(String url) {
        try {

        }catch (Exception e) {
            System.out.println("Помилка методу POST");
        }
    }*/
}
