package del.ac.id.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import del.ac.id.model.Item;
import del.ac.id.model.User;
import del.ac.id.repository.ItemRepository;
import del.ac.id.service.CustomUserDetailsService;

@Controller
public class AuthController {

	@Autowired
	private CustomUserDetailsService userService;
	
	@Autowired
	private ItemRepository itemRepository;
	
	public ModelAndView login() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("login");
		return mv;
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.GET)
	public ModelAndView signup() {
		ModelAndView mv = new ModelAndView();
		User user = new User();
		mv.addObject("user", user);
		mv.setViewName("signup");
		
		return mv;
	}
	
	@RequestMapping(value="/signup", method=RequestMethod.POST)
		public ModelAndView createNewUser(User user, BindingResult bindingResult) {
			ModelAndView mv = new ModelAndView();
			User userExist = userService.findUserByEmail(user.getEmail());
			if(userExist != null) {
				bindingResult.rejectValue("email", "There is already a user registered with the username provided");
			}
			if(bindingResult.hasErrors()) {
				mv.setViewName("signup");
			}else {
				userService.saveUser(user);
				mv.addObject("successMessage", "User has been registered successfully");
				mv.addObject("user", new User());
				mv.setViewName("login");
			}
			
		return mv;
	}
	
	@RequestMapping(value="/dashboard", method=RequestMethod.GET)
	public ModelAndView dashboard() {
		List<Item> items = itemRepository.findAll();
		ModelAndView mv = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findUserByEmail(auth.getName());
		mv.addObject("currentUser", user);
		mv.addObject("items",items);
		mv.addObject("fullName", "Welcome" + user.getFullname());
		mv.addObject("adminMessage", "Content available Only for users with role admin");
		mv.setViewName("dashboard");
		
		return mv;
	}
	
	@RequestMapping(value={"/", "/home"}, method=RequestMethod.GET)
	public ModelAndView home() {
		List<Item> items = itemRepository.findAll();
		ModelAndView mv = new ModelAndView();
		mv.addObject("items",items);
		mv.setViewName("home");
		return mv;
	}
}
