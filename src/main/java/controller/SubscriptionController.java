package controller;

import enums.ApiResp;
import middleware.AuthMiddleware;
import middleware.LoggingMiddleware;
import model.SubscriptionModel;
import service.SubscriptionService;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
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
        String description = String.format("%s Subscribing to %s", subscriber_id, creator_id);
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
        String description = String.format("%s Checking Status Subscription to %s", subscriber_id, creator_id);
        LoggingMiddleware loggingMiddleware = new LoggingMiddleware(mc, description, "/checkstatus");
        return subscriptionService.checkStatus(creator_id, subscriber_id);
    }

    @WebMethod
    public String rejectSubscription(@WebParam(name = "creator_id") int creator_id, @WebParam(name = "subscriber_id") int subscriber_id){
        MessageContext mc = wsContext.getMessageContext();
        AuthMiddleware authMiddleware = new AuthMiddleware(mc);
        if(!authMiddleware.authenticate()){
            return ApiResp.NOT_AUTHENTICATED;
        }
        String description = String.format("Admin rejecting subscription from %s to %s", subscriber_id, creator_id);
        LoggingMiddleware loggingMiddleware = new LoggingMiddleware(mc, description, "/reject");
        return subscriptionService.rejectSubscription(creator_id, subscriber_id);
    }

    @WebMethod
    public String acceptSubscription(@WebParam(name = "creator_id") int creator_id, @WebParam(name = "subscriber_id") int subscriber_id){
        MessageContext mc = wsContext.getMessageContext();
        AuthMiddleware authMiddleware = new AuthMiddleware(mc);
        if(!authMiddleware.authenticate()){
            return ApiResp.NOT_AUTHENTICATED;
        }
        String description = String.format("Admin accepting subscription from %s to %s", subscriber_id, creator_id);
        LoggingMiddleware loggingMiddleware = new LoggingMiddleware(mc, description, "/accept");
        return subscriptionService.acceptSubscription(creator_id, subscriber_id);
    }

    @WebMethod
    public List<SubscriptionModel> fetchPendingSubscription(){
        // TODO ini harus dibikin wrapper lagi buat responsenya kayknya soalnya bingung gabisa return object? udah aing buat di response model blom di test
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

        String description = String.format("Fetching subscription where subscriber id is %s", subscriber_id);
        LoggingMiddleware loggingMiddleware = new LoggingMiddleware(mc, description, "/fetchSubscription");
        return lsm;
    }

//    @WebMethod
//    public ResponseModel fetchPendingSubscription(){
//        // TODO ini harus dibikin wrapper lagi buat responsenya kayknya soalnya bingung gabisa return object? udah aing buat di response model blom di test
//        MessageContext mc = wsContext.getMessageContext();
//        ResponseModel rm = new ResponseModel();
//        AuthMiddleware authMiddleware = new AuthMiddleware(mc);
//        if(!authMiddleware.authenticate()){
//            rm.put("status", ApiResp.NOT_AUTHENTICATED);
//            return rm;
//        }
//        rm.put("status", ApiResp.AUTHENTICATED);
//        List<SubscriptionModel> lsm = subscriptionService.fetchPendingSubscription();
//        String description = String.format("Fetching all pending subscription");
//        LoggingMiddleware loggingMiddleware = new LoggingMiddleware(mc, description, "/fetch");
//        rm.put("data", lsm);
//        return rm;
//    }
}
