package no.snms.repository;

import java.util.List;

import no.snms.dao.News;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class NewsRepository  {
	
	@Autowired
	private MongoOperations mongoOperations;
    public static final String COLLECTION_NAME = "news";
     
    public List<News> listNews(String pageSize,String pageNumber, String filter) {
    	Query query = new Query();
    	if(filter!=null)
    		query.addCriteria(where("cat").gt(Integer.valueOf(filter)));
    	if(pageNumber!=null && pageSize!=null){  
    		query.limit(Integer.valueOf(pageSize));
    		query.skip(Integer.valueOf(pageSize)*Integer.valueOf(pageNumber));
    	}
    	return mongoOperations.find(query, News.class, COLLECTION_NAME);
    }
     
//    public void deletePerson(Person person) {
//        mongoTemplate.remove(person, COLLECTION_NAME);
//    }
//     
//    public void updatePerson(Person person) {
//        mongoTemplate.insert(person, COLLECTION_NAME);      
//    }
	
	
}
