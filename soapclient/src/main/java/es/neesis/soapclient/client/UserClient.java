package es.neesis.soapclient.client;

import es.neesis.soapclient.ws.user.GetUserRequest;
import es.neesis.soapclient.ws.user.GetUserResponse;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UserClient extends WebServiceGatewaySupport {

    public GetUserResponse getUser(int id) {
        GetUserRequest request = new GetUserRequest();
        request.setId(id);

        return (GetUserResponse) getWebServiceTemplate().marshalSendAndReceive(request);
    }

    public AuthenticateResponse authenticateUser(String username, String password) {
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));

        AuthenticateRequest request = new AuthenticateReuqest();
        request.setUsername(username);
        request.setPassword(encodedPassword);

        AuthenticateResponse response = (GetUserResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        if ("OK".equals(response.getStatus())) {
            System.out.println("Login correcto. Usuario: " + response.getUser().getUsername());
            callNumbersToWordsService(response.getUser().getEmail());
        } else {
            System.out.println("Error: " + response.getMessage());
        }

        return response;
    }

}
