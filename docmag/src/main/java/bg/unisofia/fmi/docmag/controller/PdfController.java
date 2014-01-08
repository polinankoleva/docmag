package bg.unisofia.fmi.docmag.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.markdown4j.Markdown4jProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.service.DocumentService;
import bg.unisofia.fmi.docmag.service.UserService;

@Controller
@RequestMapping("/documents/pdf")
public class PdfController {

    private static final Markdown4jProcessor
        MD4J        = new Markdown4jProcessor();
    private static final DateFormat
        DATE_FORMAT = new SimpleDateFormat("dd.MM.YYYY");

    @Autowired private UserService     usrService;
    @Autowired private DocumentService docService;

    @RequestMapping(value  = "{username}/thesisproposal",
                    method = RequestMethod.GET)
    public ModelAndView printThesisProposal(
            @PathVariable String username,
            @RequestParam(value        = "markdown",
                          required     = false,
                          defaultValue = "false") boolean markdown) {
        User student = usrService.getUserByUsername(username);
        // TODO: add error handling if the user is not a student
        ThesisProposal document = docService.getThesisProposal(username);
        // TODO: add error handling if the document doesn't exist
        List<User> consultants = usrService.getConsultantsForThesis(document);
        List<Teacher> scientificLeaders =
            usrService.getScientificLeadersForThesis(document);
        // populate the model
        Map<String, Object> model = new HashMap<>();
        if (markdown) model.put("markdown", MD4J);
        model.put("faculty", "ФМИ");
        model.put("dateFormat", DATE_FORMAT);
        model.put("student", student);
        model.put("document", document);
        model.put("scientificLeaders", scientificLeaders);
        model.put("consultants", consultants);
        return new ModelAndView("thesis_proposal.html", model);
    }
}
