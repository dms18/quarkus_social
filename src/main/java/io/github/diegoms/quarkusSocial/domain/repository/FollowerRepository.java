package io.github.diegoms.quarkusSocial.domain.repository;

import io.github.diegoms.quarkusSocial.domain.model.Follower;
import io.github.diegoms.rest.dto.FollowerResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import javax.security.auth.login.Configuration;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class FollowerRepository  implements PanacheRepository<Follower> {


    public boolean isFollowing(long userId, long followerId) {
        long count = count("user.id = ?1 and follower.id = ?2", userId, followerId);
        return count > 0? true:false;
    }

    public List<Follower> findByUser(Long userId) {
        PanacheQuery<Follower> followerPanacheQuery = find("user.id", userId);
        return followerPanacheQuery.list();
    }

    public boolean deleteByFollowerAndUser(long followerId, long userId) {
        // procura o relacionamento antes de deletar e retorna se foi removido
        Follower entity = find("follower.id = ?1 and user.id = ?2", followerId, userId).firstResult();
        if (entity == null) {
            return false;
        }
        delete(entity);
        return true;
    }
}
