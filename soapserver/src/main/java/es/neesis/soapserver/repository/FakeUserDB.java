package es.neesis.soapserver.repository;

import es.neesis.soapserver.ws.user.Address;
import es.neesis.soapserver.ws.user.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FakeUserDB {

    private static final Map<Integer, User> dbUsers = new HashMap<>();

    @PostConstruct
    public void initData() {
        User user1 = new User();
        user1.setId(1);
        user1.setUsername("user1");
        user1.setEmail("user@mail.com");
        Address address1 = new Address();
        address1.setDireccion("Calle 1");
        address1.setCiudad("Ciudad 1");
        address1.setPais("Pais 1");
        user1.setAddress(address1);

        dbUsers.put(user1.getId(), user1);
    }

    public User getUser(int id) {
        return dbUsers.get(id);
    }
}
