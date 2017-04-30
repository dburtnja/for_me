package objects;


import com.google.gson.Gson;

import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.runtime.*;
import jdk.nashorn.internal.runtime.regexp.joni.ast.Node;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Post {
    public Train[] value;
    public String error;
    public String data;
    public String captcha;

    class Train {
        public String num;
        public int model;
        public int category;
        public String travel_time;
        public int allow_stud;
        public int allow_transportation;
        public int allow_booking;
        public FromTill from;
        public String reserve_error;
        public FromTill till;
        public PlaceType[] types;

        class PlaceType {
            public String id;
            public String letter;
            public int places;
            public String title;
        }

        class FromTill {
            public int date;
            public String src_date;
            public String station;
        }
    }

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


            //{"value":[{"num":"146Ш","model":0,"category":0,"travel_time":"9:40","from":{"station":"Ізмаїл"},"error":null,"data":null,"captcha":null}
            String testClasStr = "{\"value\":[{\"num\":\"146Ш\",\"model\":0,\"category\":0,\"travel_time\":\"9:40\",\"from\":{\"station\":\"Ізмаїл\"},\"error\":null,\"data\":null,\"captcha\":null}";
            System.out.println(testClasStr);

            final JSONParser parser=new JSONParser(new Source("<json>", testClasStr),new Context.ThrowErrorManager());
            Node node;
            try {
                node = (Node) parser.parse();
            }
            catch (  final ParserException e) {
                throw ECMAErrors.syntaxError(e,"invalid.json",e.getMessage());
            }
            final ScriptObject global = Context.getGlobal();

            Post postObj = gson.fromJson(testClasStr, Post.class);
            System.out.println(gson.toJson(postObj));
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
