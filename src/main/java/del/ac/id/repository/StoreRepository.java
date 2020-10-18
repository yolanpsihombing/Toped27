package del.ac.id.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import del.ac.id.model.Store;

@Repository
public interface StoreRepository extends MongoRepository<Store, String>{

}
