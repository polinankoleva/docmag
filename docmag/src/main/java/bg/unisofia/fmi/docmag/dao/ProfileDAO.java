package bg.unisofia.fmi.docmag.dao;

import bg.unisofia.fmi.docmag.domain.impl.profile.Profile;

public interface ProfileDAO {
	
	public Profile getUserProfile(String username);
	public boolean updateUserProfile(Profile profile);
	
}
