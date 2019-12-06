package com.example.demo.model;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.containsString;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    private UserRepository userRepository;
    private User user1;

    private String user = "test_user";
    private String password = "AYck8WkIwhZclxRIklc2VmzQ9K2tqrdH";
    
    @Before
    public void setUp() throws Exception {
        user1 = new User(user+"1", password);
        userRepository.save(user1);
    }

    @Test
	public void shouldFindUserObjectById() throws Exception {
        User user = userRepository.findById(1);
        assertNotNull("User Object should NOT be Null", user);
    }

	@Test
	public void usernameShouldContainUnderscoreSymbol() throws Exception {
        assertEquals(user+"1", user1.getUsername());
        assertThat(user1.getUsername(), containsString("_"));
    }

    @Test
    public void passwordShouldContainNumbersAndCharacters() throws Exception {
        assertTrue(user1.getUsername() + " user's password does not contain numbers " + user1.getPassword(), 
            user1.getPassword().matches(".*[0-9].*"));
    }

    @After
    public void tearDown() throws Exception {
        user1 = null;
        userRepository = null;
        assertNull("user1 Object is NOT Null", user1);
        assertNull("userRepository Object is NOT Null", userRepository);
    }

}
