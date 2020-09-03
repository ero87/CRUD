package am.test.crud.service.impl;

import am.test.crud.entity.UserEntity;
import am.test.crud.redis.UserRedis;
import am.test.crud.repository.UserRepository;
import am.test.crud.service.UsersService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Component
public class UserServiceImpl implements UsersService {

    private final UserRedis usersRedis;

    private final UserRepository userRepository;

    private final LoadingCache<Integer, Optional<UserEntity>> usersCache;

    @Autowired
    public UserServiceImpl(UserRedis usersRedis, UserRepository userRepository) {
        this.usersRedis = usersRedis;
        this.userRepository = userRepository;
        this.usersCache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build(new CacheLoader<Integer, Optional<UserEntity>>() {
                    @Override
                    public Optional<UserEntity> load(Integer id) throws Exception {
                        return UserServiceImpl.this.userRepository.findById(id);
                    }
                });
    }

    @Override
    public UserEntity add(UserEntity userEntity) {
        return this.userRepository.save(userEntity);
    }

    @Override
    public Optional<UserEntity> updateUser(Integer userId, UserEntity userEntity) {
        UserEntity newUser = this.userRepository.getOne(userId);
        if (userEntity.getSurname() != null) {
            newUser.setSurname(userEntity.getSurname());
        }
        if (userEntity.getName() != null) {
            newUser.setName(userEntity.getName());
        }
        if (userEntity.getEmail() != null) {
            newUser.setEmail(userEntity.getEmail());
        }
        if (userEntity.getPassword() != null) {
            newUser.setPassword(userEntity.getPassword());
        }
        this.userRepository.save(newUser);
        return Optional.ofNullable(newUser);
    }

    @Override
    public Optional<UserEntity> getUser(Integer userId) {
        UserEntity userEntity = usersRedis.getUser(userId + "");
        if (userEntity == null) {
            userEntity = this.userRepository.findById(userId).orElse(null);
            if (userEntity != null) {
                this.usersRedis.addUser(userId + "", userEntity, 600);
            }
        }
        return Optional.ofNullable(userEntity);
    }

    @Override
    public List<UserEntity> getAllFromRedis() {
        List<UserEntity> userEntities = this.usersRedis.getUsers();
        if (userEntities == null) {
            userEntities = this.getAllFromDB();
            this.usersRedis.addUsers(userEntities, 500);
        }
        return userEntities;
    }

    @Override
    public List<UserEntity> getAllFromLoadingCache() {
        return null;
    }

    private List<UserEntity> getAllFromDB() {
        return this.userRepository.findAll();
    }
}
