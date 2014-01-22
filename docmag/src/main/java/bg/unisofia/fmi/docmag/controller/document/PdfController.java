package bg.unisofia.fmi.docmag.controller.document;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.markdown4j.Markdown4jProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.service.DocumentService;
import bg.unisofia.fmi.docmag.service.UserService;

@Controller
@RequestMapping("/pdf")
public class PdfController {

	private static final String DATE_PATTERN = "dd.MM.YYYY";
	@Autowired
	UserService usrService;
	@Autowired
	DocumentService docService;

	@RequestMapping(value = "/thesisproposal/{userId}", method = RequestMethod.GET)
	public String printThesisProposal(
			@PathVariable("userId") ObjectId userId,
			@RequestParam(value = "markdown", required = false, defaultValue = "false") boolean markdown,
			ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Student student = usrService.getUserById(userId);
			if (student == null) {
				response.setStatus(404);
				return null;
			}

			ThesisProposal doc = docService.getThesisProposalForUser(userId);
			if (doc == null) {
				response.setStatus(404);
				return null;
			}

			List<Teacher> consultants = usrService.getConsultantsForThesis(doc);
			List<Teacher> leaders = usrService.getScientificLeadersForThesis(doc);
			// populate the model
			if (markdown)
				model.put("markdown", new Markdown4jProcessor());
			model.put("dateFormat", new SimpleDateFormat(DATE_PATTERN));
			model.put("student", student);
			model.put("document", doc);
			model.put("scientificLeaders", leaders);
			model.put("consultants", consultants);
			response.setStatus(200);
			return "thesis_proposal";
		} catch (Exception ex) {
			response.setStatus(500);
			return null;
		}
	}
}
