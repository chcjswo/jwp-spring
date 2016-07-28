package next.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import next.dao.UserDao;
import next.model.User;

@Controller
@RequestMapping("/users")
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	
	private UserDao userDao = UserDao.getInstance();

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String form() throws Exception {
		return "user/form";
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public String create(User user) throws Exception {
		log.debug("user == {}", user);
		userDao.insert(user);
		return "redirect:/";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginForm() throws Exception {
		return "user/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(User user, Model model) throws Exception {
		String userId = user.getUserId();
        String password = user.getPassword();
        User loginUser = userDao.findByUserId(userId);
        
        log.debug("userId == {}", userId);
        log.debug("password == {}", password);
        
        if (loginUser == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        
        if (loginUser.matchPassword(password)) {
            //HttpSession session = request.getSession();
            model.addAttribute("user", user);
            return "redirect:/";
        } else {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }
	}

}
