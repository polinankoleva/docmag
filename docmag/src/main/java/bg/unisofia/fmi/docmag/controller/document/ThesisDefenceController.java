package bg.unisofia.fmi.docmag.controller.document;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import bg.unisofia.fmi.docmag.service.ThesisDefenceService;

@Controller
@RequestMapping("/thesisdefence")
public class ThesisDefenceController {
	
	@Autowired
	ThesisDefenceService thesisDefenceService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Object> getAllThesisDefences() {
		return thesisDefenceService.getThesisDefences();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void addThesisDefence(@RequestParam Date date, @RequestParam List<ObjectId> commissionParticipantIds) {
		System.out.println();
		thesisDefenceService.insertThesisDefence(date, commissionParticipantIds);
	}
	
	@RequestMapping(value = "/{thesisDefenceId}", method = RequestMethod.PUT)
	public @ResponseBody Map<String, String> updateThesisDefence(@PathVariable ObjectId thesisDefenceId, @RequestParam(required = false) Date date, @RequestParam(required = false) List<ObjectId> commissionParticipantIds) {
		return thesisDefenceService.updateThesisDefence(thesisDefenceId, date, commissionParticipantIds);
	}
	
	@RequestMapping(value = "/{thesisDefenceId}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, String> deleteThesisDefence(@PathVariable ObjectId thesisDefenceId) {
		return thesisDefenceService.deteteThesisDefence(thesisDefenceId);
	}
	
}
