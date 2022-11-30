package middleware;

import io.github.cdimascio.dotenv.Dotenv;

import javax.xml.ws.handler.MessageContext;
import java.util.List;
import java.util.Map;

public class AuthMiddleware {
    private static String REST_API_KEY;
    private static String PHP_API_KEY;
    private MessageContext mc;

    public AuthMiddleware(MessageContext mc){
        this.mc = mc;
        try {
            AuthMiddleware.REST_API_KEY = Dotenv.load().get("REST_API_KEY");
            AuthMiddleware.PHP_API_KEY = Dotenv.load().get("PHP_API_KEY");
        } catch (Exception e){
            AuthMiddleware.REST_API_KEY = System.getenv("REST_API_KEY");
            AuthMiddleware.PHP_API_KEY = System.getenv("PHP_API_KEY");
        }
    }

    public boolean authenticate(){
        Map<String, Object> requestHeader = (Map) this.mc.get(this.mc.HTTP_REQUEST_HEADERS);
        String apiKey = null;
        try {
            apiKey = ((List<String>) requestHeader.get("x-api-key")).get(0);
        } catch (Exception e){
            e.printStackTrace();
        }

        if(apiKey == null) {
            return false;
        }

        if(apiKey.equals(REST_API_KEY)){
            return true;
        }

        if(apiKey.equals(PHP_API_KEY)){
            return true;
        }

        return false;
    }
}
