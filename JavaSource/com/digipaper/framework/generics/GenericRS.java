package com.digipaper.framework.generics;

import com.digipaper.framework.enums.ClauseType;
import com.digipaper.framework.query.QueryService;
import com.digipaper.framework.rest.ResponseList;
import com.digipaper.models.Category;
import com.google.gson.*;

import javax.inject.Inject;
import javax.ws.rs.*;
import com.digipaper.framework.query.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class GenericRS<T extends GenericModel> {

    protected abstract GenericService<T> genericService();

    @Inject
    QueryService<T> queryService;

    protected Class<T> type;

    @SuppressWarnings("unchecked")
    public GenericRS(){
        type = (Class<T>) getTypeArguments(GenericRS.class, getClass()).get(0);
    }

    public static Class<?> getClass(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        } else {
            return null;
        }
    }

    public static <T> List<Class<?>> getTypeArguments(Class<T> baseClass, Class<? extends T> childClass) {
        Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
        Type type = childClass;
        while (!getClass(type).equals(baseClass)) {
            if (type instanceof Class) {
                type = ((Class<?>) type).getGenericSuperclass();
            } else {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Class<?> rawType = (Class<?>) parameterizedType.getRawType();

                if (!rawType.equals(baseClass)) {
                    type = rawType.getGenericSuperclass();
                }
            }
        }

        Type[] actualTypeArguments;
        if (type instanceof Class) {
            actualTypeArguments = ((Class<?>) type).getTypeParameters();
        } else {
            actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
        }
        List<Class<?>> typeArgumentsAsClasses = new ArrayList<>();

        for (Type baseType : actualTypeArguments) {
            while (resolvedTypes.containsKey(baseType)) {
                baseType = resolvedTypes.get(baseType);
            }
            typeArgumentsAsClasses.add(getClass(baseType));
        }
        return typeArgumentsAsClasses;
    }


    @GET
    @Path("/getAll")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseList<T> getRecords() throws Exception {

        System.out.println("URL : /rest/" + type.getSimpleName() + "/getAll");

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

        String requestString = gson.toJson(element);
        System.out.println(requestString);

        return response;
    }

    @GET
    @Path("/getAutoCompleteValues")
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseList<T> getAutoCompleteValues() throws Exception {

        System.out.println("URL : /rest/" + type.getSimpleName() + "/getAutoCompleteValues/");

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        ResponseList<T> response = new ResponseList<>();
        response.setAction("view");

        List<QueryParam> where = new ArrayList<>();
        where.add(new QueryParam("isActive", true, ClauseType.EQUAL));

        JsonElement element = gson.toJsonTree(genericService().GetAutoCompleteData(where, "name", "", null), List.class);
        JsonArray array = element.getAsJsonArray();

        Iterator<JsonElement> jsonElementIterator = array.iterator();

        while(jsonElementIterator.hasNext()) {

            JsonElement el = jsonElementIterator.next();
            if(el.isJsonObject()) {
                JsonObject object = el.getAsJsonObject();
                Set<Map.Entry<String, JsonElement>> entries = object.entrySet();
                for (Map.Entry<String, JsonElement> entry: entries) {
                    if(!"id".equals(entry.getKey()) && !"name".equals(entry.getKey())) {
                        object.remove(entry.getKey());
                        System.out.println("DELETE : " + entry.getKey());
                    } else {
                        System.out.println(entry.getKey());
                    }
                }
            }
        }
        response.setDatas(gson.fromJson(element, List.class));

        String requestString = gson.toJson(element);
        System.out.println(requestString);

        return response;
    }

    public static JsonElement clearNullValues(JsonElement element) {
        if(element.isJsonObject()) {
            Set<Map.Entry<String, JsonElement>> entries = element.getAsJsonObject().entrySet();
            for (Map.Entry<String, JsonElement> entry: entries) {
                if(entry.getValue().isJsonObject()) {
                    JsonObject childObject = entry.getValue().getAsJsonObject();
                    Set<Map.Entry<String, JsonElement>> entriesChild = element.getAsJsonObject().entrySet();
                    for (Map.Entry<String, JsonElement> entryChild: entriesChild) {
                        if(!"id".equals(entryChild.getKey()) && !"name".equals(entryChild.getKey()) && !"code".equals(entryChild.getKey()))
                            childObject.remove(entryChild.getKey());
                    }
                }
            }
        }
        return element;
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecord(@PathParam("id") Integer id) throws Exception{

        System.out.println("URL : " + type.getName() + "/get/" + id);

        JsonObject response = new JsonObject();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        response.add("action", gson.toJsonTree("view"));

        JsonElement element = gson.toJsonTree(genericService().GetById(id));

        clearNullValues(element);

        response.add("data", element);

        System.out.println(gson.toJson(response));

        return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/save")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response saveUser(String request) throws Exception{
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        JsonObject req = new JsonParser().parse(request).getAsJsonObject();

        System.out.println(gson.toJson(req));

        T model = gson.fromJson(req.get("data"), type);

        genericService().Save(model);

        JsonObject response = new JsonObject();

        response.add("action", gson.toJsonTree("view"));

        JsonElement element = gson.toJsonTree(genericService().GetById(model.getId()));

        clearNullValues(element);

        response.add("data", element);

        System.out.println(gson.toJson(response));

        return Response.ok(response.toString(), MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(String request) throws Exception{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonObject req = new JsonParser().parse(request).getAsJsonObject();

        System.out.println(gson.toJson(req));

        T model = gson.fromJson(req.get("data"), type);

        genericService().Delete(model);
    }
}
