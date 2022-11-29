package model;

import enums.Status;
import lombok.Data;
import lombok.NonNull;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "Subscription")
public class SubscriptionModel {
    private @NonNull int creator_id;
    private @NonNull int subscriber_id;
    private @NonNull Status status;

    public SubscriptionModel(){
        this.creator_id = 0;
        this.subscriber_id = 0;
        this.status = Status.PENDING;
    }

    public SubscriptionModel(int creator_id, int subscriber_id, Status status){
        this.creator_id = this.creator_id;
        this.subscriber_id = this.subscriber_id;
        this.status = this.status;
    }
}
