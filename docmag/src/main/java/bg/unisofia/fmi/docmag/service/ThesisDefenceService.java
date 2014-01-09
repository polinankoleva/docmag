package bg.unisofia.fmi.docmag.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.unisofia.fmi.docmag.dao.ThesisDefenceDAO;
import bg.unisofia.fmi.docmag.domain.impl.ThesisDefence;

@Service
public class ThesisDefenceService {

	@Autowired
	ThesisDefenceDAO thesisDefenceDao;
	
	public ThesisDefence getThesisDefenceByUsername(String username){
		return thesisDefenceDao.getThesisDefenceByUsername(username);
	}
	

}
