package com.github.group5.public_transport_project.test;

import com.github.group5.public_transport_project.model.User;
import com.github.group5.public_transport_project.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService service;

    @BeforeEach
    public void setUp() {
        service = new UserService();
    }

    @Test
    public void testRegisterAndLogin() {
// Registrering
        assertTrue(service.register("bilal", "riz", "bilal@mail.com"));

// Login med korrekt passord
        User user = service.login("bilal", "pwd");
        assertNotNull(user);
        assertEquals("bilal", user.getUsername());

// Login med feil passord
        assertNull(service.login("bilal", "feil"));

// Login med ukjent bruker
        assertNull(service.login("ukjent", "passord"));
    }

    @Test
    public void testDuplicateRegistration() {
        service.register("Usman", "Usman", "Usman@gmail.comw");
// Registrering med samme brukernavn skal feile
        assertFalse(service.register("Usman", "ny", "Usman@gmail.com"));
    }
}


