package no.snms.repository;

import java.util.List;

import no.snms.dao.News;
import no.snms.rest.AdminJsonService;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class NewsRepository {
	@Autowired
	private MongoOperations mongoOperations;
	private static final Logger logger = Logger.getLogger(NewsRepository.class);
	public static final String COLLECTION_NAME = "news";

	public List<News> listNews(String pageSize, String pageNumber, String filter) {
		Query query = new Query();
		if (filter != null)
			query.addCriteria(where("cat").is(Integer.valueOf(filter)));
		if (pageNumber != null && pageSize != null) {
			query.limit(Integer.valueOf(pageSize));
			query.skip(Integer.valueOf(pageSize) * Integer.valueOf(pageNumber));
		}
		return mongoOperations.find(query, News.class, COLLECTION_NAME);
	}

	public void updateNews(News newsToUpdate) throws Exception {
		try {
			if(newsToUpdate.getPri()!=null && (newsToUpdate.getPri()==1 || newsToUpdate.getPri()==2)){
				Query query = new Query();
				Update update = new Update();
				update.addToSet("pri", -1);
				query.addCriteria(where("pri").is(newsToUpdate.getPri()));
				mongoOperations.updateMulti(query,update,News.class);
			}
			mongoOperations.save(newsToUpdate);
		} catch (Exception e) {
			logger.error("Could not updaet news." + e.getMessage());
			e.printStackTrace();
			throw e; 
		}
	}

	public void removeNews(News newsToRemove)  throws Exception {
		try {
			mongoOperations.remove(newsToRemove);
		} catch (Exception e) {
			logger.error("Could not remove news." + e.getMessage());
			throw e; 
		}
	}


}
