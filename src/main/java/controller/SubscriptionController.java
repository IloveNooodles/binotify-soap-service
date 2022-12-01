package controller;

import enums.ApiResp;
import middleware.AuthMiddleware;
import middleware.LoggingMiddleware;
import model.SubscriptionModel;
import service.SubscriptionService;
import util.Request;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public class SubscriptionController {
    private final SubscriptionService subscriptionService = new SubscriptionService();
    @Resource
    private WebServiceContext wsContext;

    @WebMethod
    public String subscribe(@WebParam(name = "creator_id") int creator_id, @WebParam(name = "subscriber_id") int subscriber_id){
        MessageContext mc = wsContext.getMessageContext();
        AuthMiddleware authMiddleware = new AuthMiddleware(mc);
        if(!authMiddleware.authenticate()){
            return ApiResp.NOT_AUTHENTICATED;
        }
        String description = String.format("Subcriber_id %s is subscribing to creator_id %s", subscriber_id, creator_id);
        LoggingMiddleware loggingMiddleware = new LoggingMiddleware(mc, description, "/subscribe");
        return subscriptionService.subscribe(creator_id, subscriber_id);
    }

    @WebMethod
    public String checkStatus(@WebParam(name = "creator_id") int creator_id, @WebParam(name = "subscriber_id") int subscriber_id) {
        MessageContext mc = wsContext.getMessageContext();
        AuthMiddleware authMiddleware = new AuthMiddleware(mc);
        if(!authMiddleware.authenticate()){
            return ApiResp.NOT_AUTHENTICATED;
        }
        String description = String.format("Subscriber_id %s is checking status subscription to creator_id %s", subscriber_id, creator_id);
        LoggingMiddleware loggingMiddleware = new LoggingMiddleware(mc, description, "/checkstatus");
        return subscriptionService.checkStatus(creator_id, subscriber_id);
    }

    @WebMethod
    public String rejectSubscription(@WebParam(name = "creator_id") int creator_id, @WebParam(name = "subscriber_id") int subscriber_id) throws IOException {
        MessageContext mc = wsContext.getMessageContext();
        AuthMiddleware authMiddleware = new AuthMiddleware(mc);
        if(!authMiddleware.authenticate()){
            return ApiResp.NOT_AUTHENTICATED;
        }
        String description = String.format("Admin rejecting subscription from subscriber_id %s to creator_id %s", subscriber_id, creator_id);
        LoggingMiddleware loggingMiddleware = new LoggingMiddleware(mc, description, "/reject");

        Request r = new Request("http://localhost:8001/update");
        r.setParams(creator_id, subscriber_id, "ACCEPTED");

        try {
            String response = r.responseMapping(r.send().toString());

            if(!response.equals(ApiResp.REJECT_SUBSCRIPTION)){
                return response;
            }

            return subscriptionService.rejectSubscription(creator_id, subscriber_id);

        } catch (IOException e){
            e.printStackTrace();
        }
        return ApiResp.INTERNAL_SERVER_ERROR;
    }

    @WebMethod
    public String acceptSubscription(@WebParam(name = "creator_id") int creator_id, @WebParam(name = "subscriber_id") int subscriber_id){
        MessageContext mc = wsContext.getMessageContext();
        AuthMiddleware authMiddleware = new AuthMiddleware(mc);

        if(!authMiddleware.authenticate()){
            return ApiResp.NOT_AUTHENTICATED;
        }

        String description = String.format("Admin accepting subscription from subscriber_id %s to creator_id %s", subscriber_id, creator_id);
        LoggingMiddleware loggingMiddleware = new LoggingMiddleware(mc, description, "/accept");

        Request r = new Request("http://localhost:8001/update");
        r.setParams(creator_id, subscriber_id, "REJECTED");

        try {
            String response = r.responseMapping(r.send().toString());

            if(!response.equals(ApiResp.REJECT_SUBSCRIPTION)){
                return response;
            }

            return subscriptionService.acceptSubscription(creator_id, subscriber_id);

        } catch (IOException e){
            e.printStackTrace();
        }
        return ApiResp.INTERNAL_SERVER_ERROR;

    }

    @WebMethod
    public List<SubscriptionModel> fetchPendingSubscription(){
        MessageContext mc = wsContext.getMessageContext();
        List<SubscriptionModel> lsm = subscriptionService.fetchPendingSubscription();
        AuthMiddleware authMiddleware = new AuthMiddleware(mc);
        if(!authMiddleware.authenticate()){
            return new ArrayList<>();
        }

        String description = String.format("Fetching all pending subscription");
        LoggingMiddleware loggingMiddleware = new LoggingMiddleware(mc, description, "/fetchPendingSubscription");
        return lsm;
    }

    @WebMethod
    public List<SubscriptionModel> getAcceptedSubscriptionBySubcriptionId(@WebParam(name = "subscriber_id") int subscriber_id){
        MessageContext mc = wsContext.getMessageContext();
        List<SubscriptionModel> lsm = subscriptionService.getAcceptedSubscriptionBySubcriptionId(subscriber_id);
        AuthMiddleware authMiddleware = new AuthMiddleware(mc);
        if(!authMiddleware.authenticate()){
            return new ArrayList<>();
        }

        String description = String.format("subscriber_id %s is fetching subscription", subscriber_id);
        LoggingMiddleware loggingMiddleware = new LoggingMiddleware(mc, description, "/fetchSubscription");
        return lsm;
    }
}
