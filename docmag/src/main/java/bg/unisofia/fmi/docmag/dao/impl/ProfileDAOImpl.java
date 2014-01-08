package bg.unisofia.fmi.docmag.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import bg.unisofia.fmi.docmag.dao.ProfileDAO;
import bg.unisofia.fmi.docmag.dao.UserDAO;
import bg.unisofia.fmi.docmag.domain.impl.profile.Profile;
import bg.unisofia.fmi.docmag.domain.impl.user.User;

@Repository
public class ProfileDAOImpl implements ProfileDAO {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	UserDAO userDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Profile> T getUserProfile(String username) {
		User user = userDao.getUserByUsername(username);
		return (T) user.getProfile();
	}

	@Override
	public void updateUserProfile(Profile profile, String username) {
		User user = userDao.getUserByUsername(username);
		user.setProfile(profile);
		mongoTemplate.save(user);
	}
	

}
