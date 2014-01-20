package bg.unisofia.fmi.docmag.controller.document;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import bg.unisofia.fmi.docmag.service.UserService;


@Controller
@RequestMapping("/graduationthesis")
public class GraduationThesisController {

	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/{userId}/upload", method = RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void uploadGraduationThesis(@PathVariable ObjectId userId, @RequestParam(value = "file") MultipartFile file) {
		try {
			userService.uploadGraduationThesisForUser(userId, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/{userId}/download", method = RequestMethod.GET)
	public HttpEntity<byte[]> downloadGraduationThesis(@PathVariable ObjectId userId){
		return userService.downloadGraduationThesisForUser(userId);
	}
}
