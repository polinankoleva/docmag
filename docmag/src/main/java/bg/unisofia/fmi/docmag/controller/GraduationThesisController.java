package bg.unisofia.fmi.docmag.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/graduationthesis")
public class GraduationThesisController {

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody String uploadGraduationThesis(@RequestParam MultipartFile file) {
		String response = "File is test uploaded successufully.Name:" + file.getOriginalFilename();
		System.out.println(response);
		return response;
	}
}
