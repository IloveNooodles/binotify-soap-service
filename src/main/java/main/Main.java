package main;

import service.ServiceImpl;

import javax.xml.ws.*;


public class Main {
    public static void main(String[] args){
        try {
            Endpoint.publish("http://localhost:9000/Service/service", new ServiceImpl());
            System.out.println("Server created at localhost:9000");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
