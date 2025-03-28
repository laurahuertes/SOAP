package es.neesis.soapserver.endpoint;

import es.neesis.soapserver.repository.FakeUserDB;
import es.neesis.soapserver.ws.user.GetUserRequest;
import es.neesis.soapserver.ws.user.GetUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UserEndpoint {

    private static final String NAMESPACE_URI = "http://www.neesis.es/soapserver/ws/user";

    private final FakeUserDB userRepository;

    @Autowired
    public UserEndpoint(FakeUserDB userRepository) {
        this.userRepository = userRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserRequest")
    @ResponsePayload
    public GetUserResponse getUser(@RequestPayload GetUserRequest request) {
        GetUserResponse response = new GetUserResponse();
        response.setUser(userRepository.getUser(request.getId()));
        return response;
    }

}
