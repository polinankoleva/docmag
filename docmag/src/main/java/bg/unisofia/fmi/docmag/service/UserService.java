package bg.unisofia.fmi.docmag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.User;

@Service
public final class UserService {

	@Autowired
	UserDAO userDao;
	
	public void createUser(User user){
		userDao.createUser(user);
	}
	
	public User getUserByUsername(String username){
		return userDao.getUserByUsername(username);
	}
}
