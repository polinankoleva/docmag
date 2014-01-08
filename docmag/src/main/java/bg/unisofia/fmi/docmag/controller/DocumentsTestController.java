package bg.unisofia.fmi.docmag.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import bg.unisofia.fmi.docmag.domain.impl.document.Document;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal;
import bg.unisofia.fmi.docmag.domain.impl.document.ThesisProposal.ThesisProposalStatus;
import bg.unisofia.fmi.docmag.service.DocumentService;

@Controller
@RequestMapping("/documents")
public class DocumentsTestController {

	@Autowired
	DocumentService documentService;

	@RequestMapping(value = "/{username}/thesisProposal/edit", method = RequestMethod.GET)
	public @ResponseBody
	String editThesisProposal(@PathVariable String username) {
		ThesisProposal thesis = documentService.getThesisProposalForUserWithUsername(username);
		documentService.editThesisProposal(thesis);
		
		return "Edited";
	}
	
	@RequestMapping(value = "/{username}", method = RequestMethod.GET)
	public @ResponseBody
	String getAll(@PathVariable String username) {
		List<Document> documents = documentService.getUserDocuments(username);
		
		String response = "";
		if (documents != null) {
			for (Document document : documents) {
				response += document.toString();
			}
		}
		
		return response;
	}
	
	@RequestMapping(value = "/{username}/thesisProposal", method = RequestMethod.GET)
	public @ResponseBody
	String getThesisProposal(@PathVariable String username) {
		ThesisProposal thesis = documentService.getThesisProposalForUserWithUsername(username);
		String response = "";
		if (thesis != null) {
			response += thesis.toString();
		}
		return response;
	}
	
	@RequestMapping(value = "/{username}/thesisProposal/status", method = RequestMethod.GET)
	public @ResponseBody
	String getThesisProposalStatus(@PathVariable String username) {
		ThesisProposalStatus status = documentService.getThesisProposalStatusForUserWithUsername(username);
		return status.toString();
	}

}
