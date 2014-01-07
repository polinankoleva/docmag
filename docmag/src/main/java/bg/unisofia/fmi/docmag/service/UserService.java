package bg.unisofia.fmi.docmag.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.user.PHDStudent;
import bg.unisofia.fmi.docmag.domain.impl.user.Teacher;
import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

@Service
public final class UserService {

	@Autowired
	UserDAO userDao;
	
	public void createUser(User user){
		userDao.createUser(user);
	}
	
	public User getUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}
	
	public List<Teacher> getScientificLeadersPHDStudentWithUsername(String username) {
		User user = getUserByUsername(username);
		if (user.getType() == UserType.PHD) {
			return userDao.getScientificLeadersForPHDStudent((PHDStudent)user);
		}
		else {
			return null;
		}
	}

}
