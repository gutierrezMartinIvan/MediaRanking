package ar.com.mediaranking.models.repository;

import ar.com.mediaranking.models.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {


}
