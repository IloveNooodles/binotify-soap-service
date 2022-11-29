package model;

import enums.Status;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
public class Subscription {
    private @NonNull int creator_id;
    private @NonNull int subscriber_id;
    private @NonNull Status status;

    public Subscription(){
        this.creator_id = 0;
        this.subscriber_id = 0;
        this.status = Status.PENDING;
    }
}
