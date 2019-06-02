package pl.sidor.UserAuth.repository;

import models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);

    void deleteUserById(Integer id);

    User findByEmailAndPassword(String email, String password);
}
