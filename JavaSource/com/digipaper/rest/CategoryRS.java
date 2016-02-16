package com.digipaper.rest;

import com.digipaper.framework.generics.GenericRS;
import com.digipaper.framework.generics.GenericService;
import com.digipaper.models.Category;
import com.digipaper.services.CategoryService;

import javax.inject.Inject;
import javax.ws.rs.Path;

@Path("/Category")
public class CategoryRS extends GenericRS<Category> {

    @Inject
    CategoryService categoryService;

    @Override
    protected GenericService<Category> genericService() {
        return categoryService;
    }
}
