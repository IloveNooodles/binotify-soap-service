package service;

import enums.ApiResp;
import enums.Status;
import model.SubscriptionModel;
import repository.SubscriptionRepository;

import java.sql.SQLException;
import java.util.List;

public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository = new SubscriptionRepository();

    public String subscribe(int creator_id, int subscriber_id){
        // TODO check kalo creator id atau subscriber id nya gaada
        if(creator_id < 0){
            return ApiResp.INVALID_CREATOR_ID;
        }

        if(subscriber_id < 0){
            return ApiResp.INVALID_SUBSCRIBER_ID;
        }

        try {

            SubscriptionModel sm = subscriptionRepository.getSubscriptionById(creator_id, subscriber_id);
            if(sm != null){
                return ApiResp.ALREADY_SUBSCRIBE;
            }

            subscriptionRepository.subscribe(creator_id, subscriber_id);
            return ApiResp.SUBSCRIPTION_SUCCESSFUL;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ApiResp.INTERNAL_SERVER_ERROR;
    }


    public String checkStatus(int creator_id, int subscriber_id){

        if(creator_id < 0){
            return ApiResp.INVALID_CREATOR_ID;
        }

        if(subscriber_id < 0){
            return ApiResp.INVALID_SUBSCRIBER_ID;
        }

        try {
            //TODO ini polling belom kebayang apa ja yang mau di hit
            SubscriptionModel sm = subscriptionRepository.getSubscriptionById(creator_id, subscriber_id);
            if(sm == null){
                return ApiResp.SUBSCRIPTION_NOT_FOUND;
            }
            return sm.getStatus().toString();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ApiResp.INTERNAL_SERVER_ERROR;
    }

    public String rejectSubscription(int creator_id, int subscriber_id){

        if(creator_id < 0){
            return ApiResp.INVALID_CREATOR_ID;
        }

        if(subscriber_id < 0){
            return ApiResp.INVALID_SUBSCRIBER_ID;
        }

        try {
            SubscriptionModel sm = subscriptionRepository.getSubscriptionById(creator_id, subscriber_id);
            if(sm == null){
                return ApiResp.SUBSCRIPTION_NOT_FOUND;
            }

            // Already Rejected and Accepted
            if(sm.getStatus() == Status.REJECTED){
                return ApiResp.ALREADY_REJECTED;
            }

            if(sm.getStatus() == Status.ACCEPTED){
                return ApiResp.ALREADY_ACCEPTED;
            }

            subscriptionRepository.updateSubscription(creator_id, subscriber_id, Status.REJECTED);
            return ApiResp.REJECT_SUBSCRIPTION;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ApiResp.INTERNAL_SERVER_ERROR;
    }

    public String acceptSubscription(int creator_id, int subscriber_id){

        if(creator_id < 0){
            return ApiResp.INVALID_CREATOR_ID;
        }

        if(subscriber_id < 0){
            return ApiResp.INVALID_SUBSCRIBER_ID;
        }

        try {
            SubscriptionModel sm = subscriptionRepository.getSubscriptionById(creator_id, subscriber_id);

            if(sm == null){
                return ApiResp.SUBSCRIPTION_NOT_FOUND;
            }

            // Already Rejected and Accepted
            if(sm.getStatus() == Status.REJECTED){
                return ApiResp.ALREADY_REJECTED;
            }

            if(sm.getStatus() == Status.ACCEPTED){
                return ApiResp.ALREADY_ACCEPTED;
            }

            subscriptionRepository.updateSubscription(creator_id, subscriber_id, Status.ACCEPTED);
            return ApiResp.ACCEPT_SUBSCRIPTION;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ApiResp.INTERNAL_SERVER_ERROR;
    }

    public List<SubscriptionModel> fetchPendingSubscription(){
        try {
            List<SubscriptionModel> result = subscriptionRepository.getSubscriptionByStatus(Status.PENDING);
            return result;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public List<SubscriptionModel> getAcceptedSubscriptionBySubcriptionId(int subscription_id){
        try {
            List<SubscriptionModel> result = subscriptionRepository.getAcceptedSubscriptionBySubcriptionId(subscription_id);
            return result;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
