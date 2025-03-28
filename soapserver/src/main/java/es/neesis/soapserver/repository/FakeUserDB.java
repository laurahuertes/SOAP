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
        user1.setPassword("password");
        user1.setEmail("user@mail.com");
        Address address1 = new Address();
        address1.setDireccion("Calle 1");
        address1.setCiudad("Ciudad 1");
        address1.setPais("Pais 1");
        user1.setAddress(address1);
        user1.setDateExpirationPass("2025-04-01");
        user1.setDateLastLoggin("2025-03-28");

        dbUsers.put(user1.getId(), user1);
    }

    public User getUser(int id) {
        return dbUsers.get(id);
    }

    public Boolean getUserByUsername(String username){

        for (Map.Entry<Integer, User> entry : dbUsers.entrySet()) {
            User usuario = entry.getValue(); // Obtener el objeto User
            if (usuario.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User getUserByPassword(String username, String password){

        for (Map.Entry<Integer, User> entry : dbUsers.entrySet()) {
            User usuario = entry.getValue();
            if (usuario.getUsername().equals(password) && getUserByUsername(username)) {
                return usuario;
            }
        }
        return null;



    }
}
