package service;

import javax.jws.*;

@WebService(endpointInterface = "service.Service")
public class ServiceImpl implements Service {

    @Override
    public String helloworld() {
        return "Hello World";
    }

    @Override
    public String hi(String fullName) {
        return "Hello " + fullName;
    }
}