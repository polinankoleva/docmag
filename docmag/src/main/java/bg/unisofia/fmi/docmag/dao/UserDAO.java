package bg.unisofia.fmi.docmag.dao;

import java.util.List;

import bg.unisofia.fmi.docmag.domain.impl.user.User;
import bg.unisofia.fmi.docmag.domain.impl.user.User.UserType;

public interface UserDAO {

	public void  createUser(User user);

	public User getUserByUsername(String username);
	public List<User> getAllUsersOfType(UserType type);
	
}
