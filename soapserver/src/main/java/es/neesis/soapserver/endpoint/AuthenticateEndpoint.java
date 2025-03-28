package es.neesis.soapserver.endpoint;

import es.neesis.soapserver.repository.FakeUserDB;
import es.neesis.soapserver.ws.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class AuthenticateEndpoint {
    private static final String NAMESPACE_URI = "http://www.neesis.es/soapserver/ws/user";

    private final FakeUserDB userRepository;

    @Autowired
    public AuthenticateEndpoint(FakeUserDB userRepository) {
        this.userRepository = userRepository;
    }


    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAuthenticateRequest")
    @ResponsePayload
    public GetAuthenticateResponse getUser(@RequestPayload GetAuthenticateRequest request) {
        GetAuthenticateResponse response = new GetAuthenticateResponse();
        Boolean username = userRepository.getUserByUsername(request.getUsername());

        if(!username){
            response.setCodeResponse("KO, El usuario introducido es incorrecto");

        } else{
            response.setCodeResponse("OK");
        }

        User usuario = userRepository.getUserByPassword(request.getUsername(), request.getPassword());

        if(usuario == null){
            response.setCodeResponse("KO, La contraseña introducida es incorrecta");
        } else{
            response.setCodeResponse("OK");
        }

        response.setUser(usuario);
        return response;
    }
}
