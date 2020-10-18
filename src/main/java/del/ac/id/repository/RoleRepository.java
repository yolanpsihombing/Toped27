package del.ac.id.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import del.ac.id.model.Role;

public interface RoleRepository extends MongoRepository<Role, String>{
	
	Role findByRole(String role);
}
