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
			query.addCriteria(where("cat").gt(Integer.valueOf(filter)));
		if (pageNumber != null && pageSize != null) {
			query.limit(Integer.valueOf(pageSize));
			query.skip(Integer.valueOf(pageSize) * Integer.valueOf(pageNumber));
		}
		return mongoOperations.find(query, News.class, COLLECTION_NAME);
	}

	public void updateNews(News newsToUpdate) throws Exception {
		try {
			mongoOperations.save(newsToUpdate);
		} catch (Exception e) {
			logger.error("Could not updaet news." + e.getMessage());
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
