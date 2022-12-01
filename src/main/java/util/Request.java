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

    public void setParams(int creator_id, int subscriber_id, String status){
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
        try {
            JSONParser parser = new JSONParser();
            Object response = parser.parse(res);
            return response;
        } catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    public String send() throws IOException {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(this.url).openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("x-api-key", middleware.PHP_API_KEY);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            this.setByteParams();
            conn.getOutputStream().write(this.getPostData());
            return this.getStringResponse(conn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String responseMapping(String response){

        if(response.contains(ApiResp.DATA_NOT_COMPLETE)){
            return ApiResp.DATA_NOT_COMPLETE;
        }

        if(response.contains(ApiResp.INVALID_CREATOR_ID)){
            return ApiResp.INVALID_CREATOR_ID;
        }

        if(response.contains(ApiResp.NOT_AUTHENTICATED)){
            return ApiResp.NOT_AUTHENTICATED;
        }

        if(response.contains(ApiResp.INVALID_SUBSCRIBER_ID)){
            return ApiResp.INVALID_SUBSCRIBER_ID;
        }

        if(response.contains(ApiResp.INTERNAL_SERVER_ERROR)){
            return ApiResp.INTERNAL_SERVER_ERROR;
        }

        if(response.contains(ApiResp.SUBSCRIPTION_NOT_FOUND)){
            return ApiResp.SUBSCRIPTION_NOT_FOUND;
        }

        if(response.contains(ApiResp.SUBSCRIPTION_SUCCESSFUL)){
            return ApiResp.SUBSCRIPTION_SUCCESSFUL;
        }

        if(response.contains(ApiResp.SUBSCRIPTION_UPDATE_SUCCESSFUL)){
            return ApiResp.SUBSCRIPTION_UPDATE_SUCCESSFUL;
        }

        if(response.contains(ApiResp.ALREADY_REJECTED)){
            return ApiResp.ALREADY_REJECTED;
        }

        if(response.contains(ApiResp.ALREADY_ACCEPTED)){
            return ApiResp.ALREADY_ACCEPTED;
        }

        if(response.contains(ApiResp.ACCEPT_SUBSCRIPTION)){
            return ApiResp.ACCEPT_SUBSCRIPTION;
        }

        if(response.contains(ApiResp.REJECT_SUBSCRIPTION)){
            return ApiResp.REJECT_SUBSCRIPTION;
        }

        return ApiResp.INTERNAL_SERVER_ERROR;
    }

    public static void main(String[] args){
        Request r = new Request("http://localhost:8001/subscribed/update");
        r.setParams(0, 0, "ACCEPTED");

        try {
            Object response = r.send();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
