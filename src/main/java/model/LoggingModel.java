package model;

import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class LoggingModel {
    private @NonNull int id;
    private @NonNull String description;
    private @NonNull String IP;
    private @NonNull String endpoint;
    private @NonNull Date timestamp;

    public LoggingModel(){
        this.id = 0;
        this.description = "Default Value";
        this.IP = "localhost";
        this.endpoint = "Subscription";
        this.timestamp = new Date();
    }

    public LoggingModel(int id, String description, String IP, String endpoint, Date date){
        this.id = id;
        this.description = description;
        this.IP = IP;
        this.endpoint = endpoint;
        this.timestamp = date;
    }
}
