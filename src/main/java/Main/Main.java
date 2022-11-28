package Main;

import ws.ServiceImpl;

import javax.jws.*;
import javax.xml.ws.*;

public class Main {
    public static void main(String[] args){
        try {
            Endpoint.publish("http://localhost:4789/ws/service", new ServiceImpl());
            System.out.println("Done");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
