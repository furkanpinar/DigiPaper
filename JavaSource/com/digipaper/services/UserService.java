package com.digipaper.services;

import com.digipaper.framework.generics.GenericService;
import com.digipaper.models.Users;

import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;

@Stateless
@Dependent
public class UserService extends GenericService<Users> {
}
