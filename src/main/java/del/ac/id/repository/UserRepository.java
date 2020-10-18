package del.ac.id.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import del.ac.id.model.User;

public interface UserRepository extends MongoRepository<User, String>{

	User findByEmail(String email);
}
