package io.github.diegoms.rest;

import io.github.diegoms.quarkusSocial.domain.model.Follower;
import io.github.diegoms.quarkusSocial.domain.model.User;
import io.github.diegoms.quarkusSocial.domain.repository.FollowerRepository;
import io.github.diegoms.quarkusSocial.domain.repository.UserRepository;
import io.github.diegoms.rest.dto.FollowerRequest;
import io.github.diegoms.rest.dto.FollowerPerUserResponse;
import io.github.diegoms.rest.dto.FollowerResponse;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.annotations.Query;

import java.util.stream.Collectors;

@Path("/users/{userId}/followers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class FollowerResource {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;

    @Inject
    public FollowerResource(FollowerRepository followerRepository, UserRepository userRepository) {
        this.followerRepository = followerRepository;
        this.userRepository = userRepository;
    }

    @PUT
    @Transactional
    public Response followerUser(@PathParam("userId") long userId, FollowerRequest followerRequest) {

        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        User follower = userRepository.findById(followerRequest.getFollowerId());

        if (userId==(followerRequest.getFollowerId())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("You can't follow yourself").build();
        }

        if (!followerRepository.isFollowing(userId, followerRequest.getFollowerId())) {

            var entity=new Follower();
            entity.setUser(user);
            entity.setFollower(follower);
            followerRepository.persist(entity);

            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.status(Response.Status.NOT_MODIFIED).build();
    }
    @GET
    public Response listFollowers(@PathParam("userId") long userId) {


        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

      var list  =followerRepository.findByUser(userId);
      FollowerPerUserResponse response = new FollowerPerUserResponse();
      response.setFollowerCount(list.size());
       var followerList = list.stream()
               .map(FollowerResponse::new)
               .collect(Collectors.toList());

         response.setContent(followerList);
        return Response.ok(response).build();
        }

        @DELETE
        @Transactional
        public Response unfollowUser(@PathParam("userId") long userId, @QueryParam("followerId") long followerId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();

        }
        boolean deleted = followerRepository.deleteByFollowerAndUser(followerId, userId);
        if (deleted) {
            return Response.status(Response.Status.OK).build();
        } else {
            return Response.status(Response.Status.NOT_MODIFIED).build();
        }
    }

}
