package com.digipaper.rest;

import com.digipaper.models.Users;
import com.digipaper.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/User")
public class UserRS {

    @Inject
    UserService userService;

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Users> getUsers() throws Exception {
        return userService.GetAll("name");
    }
}