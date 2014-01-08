package bg.unisofia.fmi.docmag.dao;

import bg.unisofia.fmi.docmag.domain.impl.profile.Profile;

public interface ProfileDAO {
	
	public <T extends Profile> T getUserProfile(String username);
	public void updateUserProfile(Profile profile, String username);
	
}
