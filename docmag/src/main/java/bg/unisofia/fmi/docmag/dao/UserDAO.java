package bg.unisofia.fmi.docmag.dao;

import bg.unisofia.fmi.docmag.domain.User;

public interface UserDAO {

	public void  createUser(User user);

	public User getUserByUsername(String username);
}
