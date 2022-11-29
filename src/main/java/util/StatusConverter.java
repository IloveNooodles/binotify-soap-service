package util;

import enums.Status;

public class StatusConverter {
    public static Status StringToStatus(String s){
        switch (s){
            case "PENDING":
                return Status.PENDING;
            case "ACCEPTED":
                return  Status.ACCEPTED;
            case "REJECTED":
                return Status.REJECTED;
        }
        return null;
    }

    public static String StatusToString(Status s){
        switch (s){
            case PENDING:
                return "PENDING";
            case ACCEPTED:
                return "ACCEPTED";
            case REJECTED:
                return "REJECTED";
        }
        return null;
    }
}
