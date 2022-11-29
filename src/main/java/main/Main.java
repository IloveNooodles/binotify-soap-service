package main;

import controller.SubscriptionController;
import javax.xml.ws.*;


public class Main {
    public static void main(String[] args){
        try {
            Endpoint.publish("http://localhost:9000/api/subscription", new SubscriptionController());
            System.out.println("Server created at localhost:9000");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
