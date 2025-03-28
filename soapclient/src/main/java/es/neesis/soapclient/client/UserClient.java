package es.neesis.soapclient.client;

import es.neesis.soapclient.ws.user.GetUserRequest;
import es.neesis.soapclient.ws.user.GetUserResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

public class UserClient extends WebServiceGatewaySupport {

    public GetUserResponse getUser(int id) {
        GetUserRequest request = new GetUserRequest();
        request.setId(id);

        return (GetUserResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }
}
