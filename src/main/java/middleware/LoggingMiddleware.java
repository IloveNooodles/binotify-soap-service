package middleware;

import com.sun.net.httpserver.HttpExchange;
import model.LoggingModel;
import service.LoggingService;

import javax.xml.ws.handler.MessageContext;
import java.net.InetSocketAddress;

public class LoggingMiddleware {
    private static final LoggingService loggingService = new LoggingService();

    public LoggingMiddleware(MessageContext mc, String description, String endpoint){
        HttpExchange httpExchange = (HttpExchange)mc.get("com.sun.xml.internal.ws.http.exchange");
        InetSocketAddress remoteAddress = httpExchange.getRemoteAddress();
        String IP = remoteAddress.getAddress().toString().substring(1);

        LoggingModel lm = new LoggingModel();
        lm.setIP(IP);
        lm.setDescription(description);
        lm.setEndpoint(endpoint);

        loggingService.addLoging(lm);
    }
}
