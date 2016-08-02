package next.controller.user;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import next.dao.QuestionDao;
import next.model.Question;

@Controller
@RequestMapping("/api")
public class ApiController {
	private static final Logger log = LoggerFactory.getLogger(ApiController.class);
	
	private QuestionDao questionDao = QuestionDao.getInstance();
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Question form() throws Exception {
		log.debug("ApiController /api/list");
		return new Question(1000, "tony", "title", "contents",
			new Date(), 10);
	}

}
