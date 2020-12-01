package com.ibm.adro.learningspringboot.resource;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ibm.adro.learningspringboot.model.User;
import com.ibm.adro.learningspringboot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
@RequestMapping(
    path = "/api/v1/users"
)
public class UserResource {
    
    private UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
        method = RequestMethod.GET, 
        produces = MediaType.APPLICATION_JSON
    )
    public List<User> fetchUsers(@QueryParam("gender") String gender) {
        return userService.getAllUsers(Optional.ofNullable(gender)); 
    } 

    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON,  
        path = "{userUid}"
    )
    public ResponseEntity<?> fetchUser(@PathVariable("userUid") UUID userUid) {
        return userService.getUser(userUid).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorMessage("The User " + userUid + " was not found")));
    }

    @RequestMapping(
        method = RequestMethod.POST, 
        consumes = MediaType.APPLICATION_JSON, 
        produces = MediaType.APPLICATION_JSON
    )
    public ResponseEntity<Integer> insertNewUser(@RequestBody User user) {
        int result = userService.insertUser(user); 

        if(result == 1) {
            return ResponseEntity.ok().build();
        } 

        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(
        method = RequestMethod.PUT, 
        consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.APPLICATION_JSON
    )
    public ResponseEntity<Integer> updateUser(@RequestBody User user) {
        int result = userService.updateUser(user); 

        if(result == 1) {
            return ResponseEntity.ok().build();
        } 

        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON,
        path = "{userUid}"
    )
    public ResponseEntity<Integer> deleteUser(@PathVariable UUID userUid) {
        int result = userService.removeUser(userUid); 

        if(result == 1) {
            return ResponseEntity.ok().build();
        } 

        return ResponseEntity.badRequest().build();
    }

    @Data
    @AllArgsConstructor
    class ErrorMessage {
        String ErrorMessage;
    }

}
