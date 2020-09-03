package am.test.crud.service;

import am.test.crud.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsersService {

    UserEntity add(UserEntity userEntity);

    Optional<UserEntity> getUser(Integer userId) throws Exception;

    List<UserEntity> getAllFromRedis();

    List<UserEntity> getAllFromLoadingCache();

    //    @Modifying
//    @Query(value = "Update users usr SET usr.name=:name, usr.surname=:suranem, usr.email=:email, usr.password=:password WHERE usr.id=:userId")
    Optional<UserEntity> updateUser(Integer userId, UserEntity userEntity);
}
