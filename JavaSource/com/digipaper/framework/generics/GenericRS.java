package com.digipaper.framework.generics;

import com.digipaper.framework.rest.RequestObject;
import com.digipaper.framework.rest.ResponseList;
import com.digipaper.framework.rest.ResponseObject;
import com.digipaper.models.Users;
import com.google.gson.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class GenericRS<T extends GenericModel> {

    protected abstract GenericService<T> genericService();

    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseList<T> getRecords() throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ResponseList<T> response = new ResponseList<>();
        response.setAction("view");

        JsonElement element = gson.toJsonTree(genericService().GetAll("name"));
        JsonArray array = element.getAsJsonArray();

        Iterator<JsonElement> jsonElementIterator = array.iterator();

        while(jsonElementIterator.hasNext()) {

            JsonElement el = jsonElementIterator.next();
            if(el.isJsonObject()) {

                Set<Map.Entry<String, JsonElement>> entries = el.getAsJsonObject().entrySet();
                for (Map.Entry<String, JsonElement> entry: entries) {
                    if(entry.getValue().isJsonObject()) {
                        JsonObject childObject = entry.getValue().getAsJsonObject();
                        Set<Map.Entry<String, JsonElement>> entriesChild = el.getAsJsonObject().entrySet();
                        for (Map.Entry<String, JsonElement> entryChild: entriesChild) {
                            if(!"id".equals(entryChild.getKey()) && !"name".equals(entryChild.getKey()) && !"code".equals(entryChild.getKey()))
                                childObject.remove(entryChild.getKey());
                        }
                    }
                }
            }
        }
        response.setDatas(gson.fromJson(element, List.class));

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
