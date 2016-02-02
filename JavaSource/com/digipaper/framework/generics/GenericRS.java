package com.digipaper.framework.generics;

import com.digipaper.framework.rest.RequestObject;
import com.digipaper.framework.rest.ResponseList;
import com.digipaper.framework.rest.ResponseObject;
import com.digipaper.models.Users;
import com.fasterxml.jackson.core.JsonParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public abstract class GenericRS<T extends GenericModel> {

    protected abstract GenericService<T> genericService();

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseList<T> getRecords() throws Exception {
        ResponseList<T> response = new ResponseList<>();
        response.setAction("view");
        response.setDatas(genericService().GetAll("name"));

        return response;
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseObject<T> getRecord(@PathParam("id") Integer id) throws Exception{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ResponseObject<T> response = new ResponseObject<>();
        response.setAction("view");
        response.setData(genericService().GetById(id));

        String prettyJsonString = gson.toJson(response);
        System.out.println(prettyJsonString);

        return response;
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    public ResponseObject<T> saveUser(RequestObject<Users> model) throws Exception{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestString = gson.toJson(model);
        System.out.println(requestString);

        genericService().Save((T)model.getData());
        ResponseObject<T> response = new ResponseObject<>();
        response.setAction("view");
        response.setData(genericService().GetById(model.getData().getId()));

        String responseString = gson.toJson(response);
        System.out.println(responseString);

        return response;
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(RequestObject<Users> model) throws Exception{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String requestString = gson.toJson(model);
        System.out.println(requestString);

        genericService().Delete((T) model.getData());
    }
}
