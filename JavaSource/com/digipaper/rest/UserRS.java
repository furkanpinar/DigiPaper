package com.digipaper.rest;

import com.digipaper.framework.generics.GenericRS;
import com.digipaper.framework.generics.GenericService;
import com.digipaper.framework.rest.RequestObject;
import com.digipaper.framework.rest.ResponseList;
import com.digipaper.framework.rest.ResponseObject;
import com.digipaper.models.Users;
import com.digipaper.services.UserService;

import javax.inject.Inject;
import javax.jws.WebParam;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/User")
public class UserRS extends GenericRS<Users> {

    @Inject
    UserService userService;

    @Override
    protected GenericService<Users> genericService() {
        return userService;
    }
}