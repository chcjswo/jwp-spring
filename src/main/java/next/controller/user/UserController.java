package next.controller.user;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
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
	public String login(User user, Model model, HttpSession session) throws Exception {
		String userId = user.getUserId();
        String password = user.getPassword();
        User loginUser = userDao.findByUserId(userId);
        
        log.debug("userId == {}", userId);
        log.debug("password == {}", password);
        
        if (loginUser == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        
        if (loginUser.matchPassword(password)) {
        	session.setAttribute("user", user);
            model.addAttribute("user", user);
            return "redirect:/";
        } else {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public String edit(@PathVariable String userId, Model model, HttpSession session) throws Exception {
        log.debug("userId == {}", userId);
        User user = userDao.findByUserId(userId);

    	if (!UserSessionUtils.isSameUser(session, user)) {
        	throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        model.addAttribute("user", user);
    	
		return "user/updateForm";
	}
	
	@RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
	public String update(User user, @PathVariable String userId, HttpSession session) throws Exception {
		log.debug("update");
		
		User userInfo = userDao.findByUserId(userId);
		
        if (!UserSessionUtils.isSameUser(session, userInfo)) {
        	throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        
        User updateUser = new User(
        		userId,
        		user.getPassword(),
        		user.getName(),
        		user.getEmail());
        log.debug("Update User : {}", updateUser);
        user.update(updateUser);
        return "redirect:/";
	}

}
