package del.ac.id.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mongodb.client.result.UpdateResult;

import del.ac.id.model.Item;
import del.ac.id.repository.ItemRepository;

@RestController
public class ItemController {

	@Autowired ItemRepository itemRepository;
	@Autowired MongoTemplate mongoTemplate;
	
	
	@RequestMapping("/item")
	public ModelAndView item() {
		List<Item> items = itemRepository.findAll();
		ModelAndView mv = new ModelAndView("item");
		mv.addObject("items", items);
		
		return mv;
	}
	
	@GetMapping("/item/show/admin/{id}")
	public ModelAndView showAdmin(@PathVariable (value="id") String id) {
		Optional<Item> item = itemRepository.findById(id);
		Query query = new Query(Criteria.where("id").is(id));
		List<Item> item2 = mongoTemplate.find(query, Item.class);
		if(item2 != null) {
			Update update = new Update().inc("seen", 1);
			UpdateResult result = mongoTemplate.updateFirst(query, update, Item.class);
		}
		
		item.get().setSeen(item.get().getSeen()+1);
		ModelAndView mv = new ModelAndView("showAdmin");
		mv.addObject("item", item);
		return mv;
	}
	
	@GetMapping("/item/show/{id}")
	public ModelAndView show(@PathVariable (value="id") String id) {
		Optional<Item> item = itemRepository.findById(id);
		Query query = new Query(Criteria.where("id").is(id));
		List<Item> item2 = mongoTemplate.find(query, Item.class);
		if(item2 != null) {
			Update update = new Update().inc("seen", 1);
			UpdateResult result = mongoTemplate.updateFirst(query, update, Item.class);
		}
		
		item.get().setSeen(item.get().getSeen()+1);
		ModelAndView mv = new ModelAndView("show");
		mv.addObject("item", item);
		return mv;
	}
	
	@GetMapping("/item/showFormUpdate/admin/{id}")
	public ModelAndView showFormUpdate(@PathVariable(name="id") String id) {
		Optional<Item> item = itemRepository.findById(id);
		
		ModelAndView mv = new ModelAndView("updateAdmin");
		mv.addObject("item", item);
		return mv;
	}
	
	@GetMapping("/updateItem")
	public ModelAndView updateItem(@RequestParam(name="id") String id, @RequestParam(name="stock") double stock, @RequestParam(name="itemDetail.weight") double weight, @RequestParam(name="itemDetail.condition") String condition, @RequestParam(name="itemDetail.category") String category) {
		Optional<Item> item = itemRepository.findById(id);
		Query query = new Query(Criteria.where("id").is(id));
		List<Item> item2 = mongoTemplate.find(query, Item.class);
		if(item2!=null) {
			Update update = new Update();
			update.set("stock", stock);
			update.set("item_detail.weight", weight);
			update.set("item_detail.condition", condition);
			update.set("item_detail.category", category);
			UpdateResult result = mongoTemplate.updateFirst(query, update, Item.class);
		}
		ModelAndView mv = new ModelAndView("redirect:/dashboard");
		
		return mv;
	}
	
	@GetMapping("/buyItem")
	public ModelAndView buyItem(@RequestParam(name="id") String id, @RequestParam(name="stock") double stock, @RequestParam(name="rating") double rating) {
		Optional<Item> item = itemRepository.findById(id);
		Query query = new Query(Criteria.where("id").is(id));
		List<Item> item2 = mongoTemplate.find(query, Item.class);
		if(item2!=null) {
			Update update = new Update();
			update.inc("stock", -stock);
			update.set("sold", item.get().getSold()+stock);
			update.set("rating", item.get().getRating()+rating/2);
			UpdateResult result = mongoTemplate.updateFirst(query, update, Item.class);
		}
		ModelAndView mv = new ModelAndView("redirect:/");
		
		return mv;
	}
}
