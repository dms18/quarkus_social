package io.github.diegoms.rest;

import io.github.diegoms.quarkusSocial.domain.model.User;
import io.github.diegoms.quarkusSocial.domain.repository.UserRepository;
import io.github.diegoms.rest.dto.CreateUserRequest;
import io.github.diegoms.rest.dto.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResorce  {

    private UserRepository repository;
    private final Validator validador;

    @Inject
    public UserResorce(UserRepository repository, Validator validador) {
        this.repository = repository;
        this.validador = validador;
    }


@POST
@Transactional
    public Response createUser(CreateUserRequest userRequest){

    Set<ConstraintViolation<CreateUserRequest>> violations = validador.validate(userRequest);

    if(!violations.isEmpty()){
       return  ResponseError.createFromValidation(violations)
               .withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);

    }

    User user = new User();
    user.setName(userRequest.getName());
    user.setAge(userRequest.getAge());

    repository.persist(user);

    return Response.status(Response.Status.CREATED.getStatusCode()).
            entity(user).build();
}

@GET
    public Response listAllUsers() {

    PanacheQuery<User> todosUsusarios = repository.findAll();

    return Response.ok(todosUsusarios.list()).build();
}


@DELETE
@Path("{id}")
@Transactional
public Response deletarUser(@PathParam("id") Long id){
    User user = repository.findById(id);

    if (user!= null) {
        repository.delete(user);
        return Response.noContent().build();
    }else {
        return Response.status(Response.Status.NOT_FOUND).build();
    }


}

@Path("{id}")
@PUT
@Transactional
public Response updateUser(@PathParam("id") Long id, CreateUserRequest userData) {
    User user = repository.findById(id);
    if (user!= null) {
        user.setName(userData.getName());
        user.setAge(userData.getAge());
        return Response.noContent().build();
    }else {
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
}
