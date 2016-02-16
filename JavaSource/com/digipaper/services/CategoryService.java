package com.digipaper.services;

import com.digipaper.framework.generics.GenericService;
import com.digipaper.models.Category;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;

@Dependent
@Stateless
public class CategoryService extends GenericService<Category> {

}
