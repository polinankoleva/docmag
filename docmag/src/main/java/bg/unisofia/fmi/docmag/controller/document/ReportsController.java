package bg.unisofia.fmi.docmag.controller.document;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bg.unisofia.fmi.docmag.domain.impl.ThesisDefence;
import bg.unisofia.fmi.docmag.domain.impl.user.Student;
import bg.unisofia.fmi.docmag.service.ReportsService;

@Controller
@RequestMapping("/reports")
public class ReportsController {

	@Autowired
	ReportsService reportsService;

	@RequestMapping(value = "/graduated", method = RequestMethod.GET)
	public @ResponseBody List<Student> getStudentsReport(@RequestHeader("User-Id") ObjectId userId,
			@RequestParam(required = true) Long startDate, 
			@RequestParam(required = false) Long endDate, 
			@RequestParam(required = false) ObjectId leaderId, 
			@RequestParam(required = false) ObjectId reviewerId) {
		Date firstDate = (startDate == null) ? new Date() : new Date(startDate);
		return reportsService.reportForStudents(userId, firstDate, new Date(endDate), leaderId, reviewerId);
	}
	
	@RequestMapping(value = "/thesisdefences", method = RequestMethod.GET)
	public @ResponseBody List<ThesisDefence> getThesisDefences(@RequestHeader("User-Id") ObjectId userId,
			@RequestParam Long startDate, @RequestParam(required = false) Long endDate, 
			@RequestParam ObjectId commissionParticipantId) {
		return reportsService.reportForThesisDefences(userId, new Date(startDate), new Date(endDate), commissionParticipantId);
	}
	
	

}
