package bg.unisofia.fmi.docmag.controller.document;

import java.text.SimpleDateFormat;
import java.util.List;

import org.bson.types.ObjectId;
import org.markdown4j.Markdown4jProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.service.DocumentService;
import bg.unisofia.fmi.docmag.service.UserService;

@Controller
@RequestMapping("/documents/pdf")
public class PdfController {

	private static final String DATE_PATTERN = "dd.MM.YYYY";
	@Autowired UserService     usrService;
	@Autowired DocumentService docService;

	@RequestMapping(value  = "/thesisproposal", method = RequestMethod.GET)
	public String printThesisProposal(@RequestHeader("User-Id") ObjectId userId, @RequestParam(value = "markdown", required = false,
			defaultValue = "false") boolean markdown, ModelMap model) {
		User student  = usrService.getUserById(userId);
		// TODO: add error handling if the user is not a student
		ThesisProposal document = docService.getThesisProposalForUser(userId);
		// TODO: add error handling if the document doesn't exist
		List<Teacher> consultants  = usrService.getConsultantsForThesis(document);
		List<Teacher> scientificLeaders =
				usrService.getScientificLeadersForThesis(document);
		// populate the model
		if (markdown) model.put("markdown", new Markdown4jProcessor());
		model.put("dateFormat", new SimpleDateFormat(DATE_PATTERN));
		model.put("student", student);
		model.put("document", document);
		model.put("scientificLeaders", scientificLeaders);
		model.put("consultants", consultants);
		return "thesis_proposal";
	}
}
