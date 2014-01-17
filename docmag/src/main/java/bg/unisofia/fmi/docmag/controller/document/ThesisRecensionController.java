package bg.unisofia.fmi.docmag.controller.document;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import bg.unisofia.fmi.docmag.domain.impl.document.ThesisRecension;
import bg.unisofia.fmi.docmag.service.DocumentService;

@Controller
@RequestMapping("/thesisrecension")
public class ThesisRecensionController {
	
	@Autowired
	DocumentService documentService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ThesisRecension getThesisRecension(@RequestHeader("User-Id") ObjectId userId) {
		//return documentService.getThesisRecensionForUser(userId);
		return null;
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void setThesisRecension(@RequestHeader("User-Id") ObjectId userId){
		//must be implement

	}

	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void updateThesisThesisRecension(@RequestHeader("User-Id") ObjectId userId){
		//must be implement
	}

	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteThesisRecension(@RequestHeader("User-Id") ObjectId userId) {
		documentService.deteleThesisRecensionForUser(userId);
	}

}
