package com.github.group5.public_transport_project.test;

import com.github.group5.public_transport_project.model.User;
import com.github.group5.public_transport_project.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService service;

    @BeforeEach //Opprett ny før hver test
    public void setUp() {
        service = new UserService();
    }

    @Test
    public void testRegisterAndLogin() { //Registrering
        assertTrue(service.register("bilal", "riz", "bilal@mail.com"));

        User user = service.login("bilal", "riz"); //Loggin med riktig passord
        assertNotNull(user);
        assertEquals("bilal", user.getUsername());

        assertNull(service.login("bilal", "feil")); //Login med feil passord

        assertNull(service.login("ukjent", "passord")); //Ukjent bruker
    }

    @Test
    public void testDuplicateRegistration() {
        service.register("Usman", "Usman", "Usman@gmail.comw");

        assertFalse(service.register("Usman", "ny", "Usman@gmail.com")); //Samme brukernavn feiler
    }
}


