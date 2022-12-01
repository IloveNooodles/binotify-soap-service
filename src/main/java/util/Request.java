package util;

import enums.ApiResp;
import middleware.AuthMiddleware;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private String url;
    private Map<String, Object> params = new HashMap<>();
    private AuthMiddleware middleware = new AuthMiddleware();
    private String response;
    private byte[] postData;

    public Request(String url){
        this.url = url;
    }

    public String getAttributeResponse(){
        return this.response;
    }

    public void addParam(String key, Object value){
        this.params.put(key, value);
    }

    public void setParams(String creator_id, String subscriber_id, String status){
        this.params.put("creator_id", creator_id);
        this.params.put("subscriber_id", subscriber_id);
        this.params.put("status", status);
    }

    public byte[] getPostData(){
        return this.postData;
    }

    public void setByteParams() throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        try {
            for(Map.Entry<String,Object> param : this.params.entrySet()) {
                if (sb.length() != 0) sb.append('&');
                sb.append(URLEncoder.encode(param.getKey(), "UTF-8"));
                sb.append('=');
                sb.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            }
            byte[] postData = sb.toString().getBytes(StandardCharsets.UTF_8);
            this.postData = postData;
        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }

    }

    public String getStringResponse(HttpURLConnection conn) throws IOException {
        try {
            Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (int c; (c = in.read()) >= 0;)
                sb.append((char)c);
            String response = sb.toString();
            this.response = response;
            return response;
        } catch (IOException e){
            e.printStackTrace();
        }
        return ApiResp.INTERNAL_SERVER_ERROR;
    }

    public Object getResponseJson(String res) throws ParseException {
        JSONParser parser = new JSONParser();
        Object response = parser.parse(res);
        return response;
    }

    public Object send() throws IOException {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(this.url).openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("x-api-key", middleware.PHP_API_KEY);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            this.setByteParams();
            conn.getOutputStream().write(this.getPostData());
            return this.getResponseJson(this.getStringResponse(conn));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args){
        Request r = new Request("http://localhost:8001/subscribed");
        r.addParam("creator_id", 1);
        r.addParam("subscriber_id", 1);
        r.addParam("status", "PENDING");
        try {
            Object response = r.send();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}