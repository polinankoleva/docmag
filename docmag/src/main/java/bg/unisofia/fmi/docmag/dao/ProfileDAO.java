package bg.unisofia.fmi.docmag.dao;

import org.bson.types.ObjectId;

import bg.unisofia.fmi.docmag.domain.impl.profile.Profile;

public interface ProfileDAO {
	
	public <T extends Profile> T getUserProfile(ObjectId userId);
	public void updateUserProfile(Profile profile, ObjectId userId);
	
}
