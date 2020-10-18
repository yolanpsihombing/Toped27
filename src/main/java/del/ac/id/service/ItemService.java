package del.ac.id.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import del.ac.id.repository.ItemRepository;

@Service
public class ItemService {

	@Autowired ItemRepository itemRepository;
	
}
