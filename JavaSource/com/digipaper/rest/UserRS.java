package com.digipaper.rest;

import com.digipaper.framework.generics.GenericRS;
import com.digipaper.framework.generics.GenericService;
import com.digipaper.models.Users;
import com.digipaper.services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.math.BigInteger;
import java.security.MessageDigest;

@Path("/User")
public class UserRS extends GenericRS<Users> {

    @Inject
    UserService userService;

    @Override
    protected GenericService<Users> genericService() {
        return userService;
    }
    /*
    @Override
    public ResponseObject<Users> saveUser(String jsonString) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject req = new JsonParser().parse(jsonString).getAsJsonObject();

        Users model = gson.fromJson(req.get("data"), type);
        String password = model.getPassword();
        password = isValidMD5(password) ? password : ToHashMD5(password);
        model.setPassword(password);

        req.remove("data");
        req.add("data", gson.toJsonTree(model));

        return super.saveUser(jsonString);
    }

    @Override
    public ResponseObject<Users> saveUser(RequestObject<Users> model) throws Exception {
        String password = model.getData().getPassword();
        password = isValidMD5(password) ? password : ToHashMD5(password);
        model.getData().setPassword(password);

        return super.saveUser(model);
    }*/

    public String ToHashMD5(String value) throws Exception {
        MessageDigest mdEncypt = MessageDigest.getInstance("MD5");
        mdEncypt.update(value.getBytes(), 0, value.length());
        String md5 = new BigInteger(1, mdEncypt.digest()).toString(16);
        return md5;
    }

    public boolean isValidMD5(String value) {
        return value.matches("[a-fA-F0-9]{32}");
    }
}