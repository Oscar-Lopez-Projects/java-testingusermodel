package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.Role;
import com.lambdaschool.usermodel.models.User;
import com.lambdaschool.usermodel.models.UserRoles;
import com.lambdaschool.usermodel.models.Useremail;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        //mock is fake data
        //stubs is fake methods
        MockitoAnnotations.initMocks(this);
        List<User> myList = userService.findAll();
      for (User u : myList)
      {
          System.out.println(u.getUserid() + "" + u.getUsername());
        }
    }



    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findUserById() {
        assertEquals("test cinnamon", userService.findUserById(7).getUsername());
    }
    @Test(expected = EntityNotFoundException.class)
    public void AB_findUserByIdNotFound()
    {
        assertEquals("test cinnamon", userService.findUserById(100).getUsername());
    }

    @Test
    public void findByNameContaining() {
        assertEquals(1,userService.findByNameContaining("cin").size());
    }

    @Test
    public void findAll() {
        assertEquals(5, userService.findAll().size());
    }

    @Test
    public void Edelete() {
        userService.delete(7);
        assertEquals(4, userService.findAll().size());
    }
    @Test(expected = EntityNotFoundException.class)
    public void EA_deleteNotFound()
    {
        userService.delete(100);
        assertEquals(4, userService.findAll().size());
    }

    @Test
    public void DfindByName() {
        assertEquals("test cinnamon", userService.findByName("test cinnamon").getUsername());
        assertEquals("test barnbarn", userService.findByName("test barnbarn").getUsername());
    }

    @Test(expected = EntityNotFoundException.class)
    public void DA_findByNameNotFound()
    {
        assertEquals("test cinnamon", userService.findByName("test frank").getUsername());
        assertEquals("test barnbarn", userService.findByName("test elle").getUsername());
    }


    @Test
    public void Fsave() {
        String user2username = "test lopez";
        User u2 = new User(user2username,
                "password",
                "oscar@school.com");

        // create user
        Role r1 = new Role("banana");
        r1.setRoleid(1); // has to match id that's already in our system
        Role r2 = new Role("apple");
        r2.setRoleid(2);
        u2.getRoles().add(new UserRoles(u2, r1));
        u2.getRoles().add(new UserRoles(u2, r2));

        u2.getUseremails().add(new Useremail(u2, "oscar@school.com"));

        // call save to get it to work
        User addUser = userService.save(u2);
        assertNotNull(addUser); //
        assertEquals(user2username, addUser.getUsername());
    }

    @Test
    public void FAsaveput()
    {
        // create user obj
        String user2username = "Test lopez";
        User u2 = new User(user2username,
                "password",
                "oscar@school.com");
        u2.setUserid(15);

        // create user
        Role r1 = new Role("banana");
        r1.setRoleid(1);
        Role r2 = new Role("apple");
        r2.setRoleid(2);
        u2.getRoles().add(new UserRoles(u2, r1));
        u2.getRoles().add(new UserRoles(u2, r2));

        u2.getUseremails().add(new Useremail(u2, "oscar@school.com"));
    }


    @Test
    public void update() {
        String userName = "Test Lopez";

        User u2 = new User(userName,
                "password",
                "oscar@school.com");
        u2.setUserid(4);
        u2.getUseremails()
                .add(new Useremail(u2,
                        "cin@email.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "ja@email.local"));
        u2.getUseremails()
                .add(new Useremail(u2,
                        "lo@email.local"));
        userService.update(u2, u2.getUserid());
        User addUser = userService.save(u2);
        assertNotNull(addUser);
        assertEquals(userName.toLowerCase(), addUser.getUsername());
    }

    @Test
    public void deleteAll() {
        userService.deleteAll();
        assertEquals(0, userService.findAll().size());
    }
}