package ws;

import javax.jws.*;

@WebService
public interface Service {

    @WebMethod
    public String helloworld();

    @WebMethod
    public String hi(String full);
}
