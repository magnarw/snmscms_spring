package no.snms.repository;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import no.snms.dao.Jumma;
import no.snms.dao.News;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;

public class JummaRepository {

	
	@Autowired
	private MongoOperations mongoOperations;
    public static final String COLLECTION_NAME = "jumma";
     
    public List<Jumma> listJumma() {
    	return mongoOperations.findAll(Jumma.class, COLLECTION_NAME);
    }
    
    public void updateJumma(List <Jumma> jummas){
    
    	for(Jumma jummaToRemove : jummas){
    		mongoOperations.save(jummaToRemove);
    	}
    	
    }

	public void deleteJumma(Jumma jumma) {
		mongoOperations.remove(jumma);
		
	}
	
	
}
