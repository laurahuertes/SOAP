package es.neesis.soapclient.client;

import es.neesis.soapclient.ws.user.GetUserRequest;
import es.neesis.soapclient.ws.user.GetUserResponse;
import es.neesis.soapclient.ws.user.User;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

@Component
public class UserClient extends WebServiceGatewaySupport {

    public void callNumbersToWordsService(String email) throws Exception {
        int asciiSum = email.chars().sum();
        System.out.println("Suma de ASCII del email: " + asciiSum);

        // Consumir el servicio público NumberConversion
        URL wsdlUrl = new URL("https://www.dataaccess.com/webservicesserver/NumberConversion.wso?WSDL");
        QName qname = new QName("http://www.dataaccess.com/webservicesserver/", "NumberConversion");
        Service service = Service.create(wsdlUrl, qname);

        NumberConversionSoapType soap = service.getPort(NumberConversionSoapType.class);
        String result = soap.numberToWords(asciiSum);

        System.out.println("Número en palabras: " + result);
    }

    public GetUserResponse getUser(int i) {
        GetUserRequest request = new GetUserRequest();
        request.setId(i);

        GetUserResponse response = (GetUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws", request);

        return response;
    }


    // Interfaz del servicio SOAP externo
    @WebService(targetNamespace = "http://www.dataaccess.com/webservicesserver/", name = "NumberConversionSoapType")
    public interface NumberConversionSoapType {
        @WebMethod(operationName = "NumberToWords")
        String numberToWords(@WebParam(name = "ubiNum") int ubiNum);
    }

    public GetAuthenticateResponse authenticateUser(String username, String password) {
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes(StandardCharsets.UTF_8));

        GetAuthenticateRequest request = new GetAuthenticateRequest();
        request.setUsername(username);
        request.setPassword(encodedPassword);

        GetAuthenticateResponse response = (GetUserResponse) getWebServiceTemplate().marshalSendAndReceive(request);

        if ("OK".equals(response.getStatus())) {
            System.out.println("Login correcto. Usuario: " + response.getUser().getUsername());
            callNumbersToWordsService(response.getUser().getEmail());
        } else {
            System.out.println("Error: " + response.getMessage());
        }

        return response;
    }

}
