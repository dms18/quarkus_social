package io.github.diegoms.quarkusSocial.domain.repository;

import io.github.diegoms.quarkusSocial.domain.model.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

}
