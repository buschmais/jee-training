package com.buschmais.training.jee.adapters.primary.rest;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

@Path("hello-world")
public class HelloWorldResource {

    @GET
    public String helloWorld(@QueryParam("name") @DefaultValue("World") String name) {
        return "Hello " + name;
    }

}
