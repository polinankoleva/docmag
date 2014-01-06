package bg.unisofia.fmi.docmag.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.unisofia.fmi.docmag.dao.DocumentDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.document.Document;;

@Service
public class DocumentService {
	@Autowired
	DocumentDAO documentDao;
	
	@Autowired
	UserDAO userDao;
	
	public List<Document> getUserDocuments(String username) {
		User user = userDao.getUserByUsername(username);
		return documentDao.getAllDocumentsForUser(user);
	}

}
