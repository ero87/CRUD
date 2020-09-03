package am.test.crud.repository;

import am.test.crud.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
//
//    @Modifying
//    @Query(value = "Update users usr SET usr.name=:name, usr.surname=:suranem, usr.email=:email, usr.password=:password WHERE usr.id=:userId")
//    Optional<UserEntity> updateMUser(UserEntity userEntity);

    Optional<UserEntity> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

}
