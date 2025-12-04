package io.github.diegoms.rest;

import io.github.diegoms.quarkusSocial.domain.model.Post;
import io.github.diegoms.quarkusSocial.domain.model.User;
import io.github.diegoms.quarkusSocial.domain.repository.Postrepository;
import io.github.diegoms.quarkusSocial.domain.repository.UserRepository;
import io.github.diegoms.rest.dto.CreatePostrequest;
import io.github.diegoms.rest.dto.PostResponse;
import io.quarkus.panache.common.Sort;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("users/{id}/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PostResource {

    private final UserRepository userRepository;
    private final Postrepository postrepository;

    @Inject
    public PostResource(UserRepository userrepository, Postrepository postrepository) {
        this.userRepository= userrepository;
        this.postrepository = postrepository;
    }
    @Transactional
    @POST
    public Response savePost(@PathParam("id") long userId, CreatePostrequest request) {
        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Post post = new Post();
        post.setText(request.getText());
        post.setUser(user);

        postrepository.persist(post);

        return Response.status(201).build();
    }

    @GET
    public Response listPosts(@PathParam("id") long userId) {

        User user = userRepository.findById(userId);
        if(user == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Post> listaPostagens = postrepository.find("user", Sort.by("dateTime",Sort.Direction.Descending) ,user).list();
        List<PostResponse> postResponses = listaPostagens.stream()
                .map(post -> new PostResponse().fromEntity(post))
                .toList();

        return Response.ok(postResponses).build();
    }

}
