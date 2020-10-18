package del.ac.id.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import del.ac.id.model.Item;
import del.ac.id.model.Store;
import del.ac.id.repository.StoreRepository;

@Controller
public class StoreController {

	@Autowired StoreRepository storeRepository;
	@Autowired MongoTemplate mongoTemplate;
	
	@RequestMapping("/admin/store")
	public ModelAndView storeIndex() {
		List<Store> store = storeRepository.findAll();
		ModelAndView mv = new ModelAndView("store");
		mv.addObject("stores", store);
		return mv;
	}
	
	@GetMapping("/admin/store/show/item/{store_name}")
	public ModelAndView itemStore(@PathVariable(name="store_name") String store_name) {
		Query query = new Query(Criteria.where("store_name").is(store_name));
		List<Item> item = mongoTemplate.find(query, Item.class);
		ModelAndView mv = new ModelAndView("store_item");
		mv.addObject("items",item);
		return mv;
	}
}
