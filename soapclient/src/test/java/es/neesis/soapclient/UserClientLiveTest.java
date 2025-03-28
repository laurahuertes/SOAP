package es.neesis.soapclient;

import es.neesis.soapclient.client.UserClient;
import es.neesis.soapclient.config.UserClientConfig;
import es.neesis.soapclient.ws.user.GetUserResponse;
import es.neesis.soapserver.ws.user.GetAuthenticateResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = UserClientConfig.class, loader = AnnotationConfigContextLoader.class)
public class UserClientLiveTest {

    @Autowired
    private UserClient userClient;

    @Test
    public void whenSendRequest_thenRecieveValidResponse() throws Exception {
        GetUserResponse testUser = userClient.getUser(1);
        try {
            GetAuthenticateResponse testAuthenticate = userClient.authenticateUser("user1", "password");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(testUser.getUser().getUsername(), "user1");
    }

}
