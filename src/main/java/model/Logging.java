package model;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Logging {
    private @NonNull int id;
    private @NonNull String description;
    private @NonNull String IP;
    private @NonNull String endpoint;
    private @NonNull Date timestamp;

    public Logging(){
        this.id = 0;
        this.description = "Default Value";
        this.IP = "localhost";
        this.endpoint = "Subscription";
        this.timestamp = new Date();
    }

    public Logging(int id, String description, String IP, String endpoint, Date date){
        this.id = id;
        this.description = description;
        this.IP = IP;
        this.endpoint = endpoint;
        this.timestamp = date;
    }
}
