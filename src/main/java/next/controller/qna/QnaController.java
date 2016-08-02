package next.controller.qna;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

@Controller
@RequestMapping("/qna")
public class QnaController {
	private static final Logger log = LoggerFactory.getLogger(QnaController.class);

	private QuestionDao questionDao = QuestionDao.getInstance();
	private AnswerDao answerDao = AnswerDao.getInstance();
	
	@RequestMapping(value = "/{questionId}", method = RequestMethod.GET)
	public String home(HttpSession session, @PathVariable long questionId, Model model) throws Exception {
		log.debug("questionId == {}", questionId);
		if (!UserSessionUtils.isLogined(session)) {
			return "/user/login";
		}
		
        Question question = questionDao.findById(questionId);
        List<Answer> answers = answerDao.findAllByQuestionId(questionId);

        model.addAttribute("question", question);
        model.addAttribute("answers", answers);
        
		return "/qna/show";
	}

}
