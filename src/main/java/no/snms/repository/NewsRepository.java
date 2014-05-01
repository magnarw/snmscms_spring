package no.snms.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.snms.dao.News;
import no.snms.rest.AdminJsonService;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
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
		
		/*
		 *   "sort": [['pri','desc'], ['createdDate','-1']]
		 * 
		 */
		query.with(new Sort(Sort.Direction.DESC, "pri"));
		query.with(new Sort(Sort.Direction.DESC, "createdDate"));
		logger.debug("Querying with news with pagSize=" + pageSize +", pageNumber=" + pageNumber +", filter=" + filter);
		if (filter != null && Integer.valueOf(filter)!=0) {
			query.addCriteria(where("cat").is(Integer.valueOf(filter)));
			logger.debug("Querying with news with filter=" + filter);
		}
		if (pageNumber != null && pageSize != null) {
			query.limit(Integer.valueOf(pageSize)+1);
			query.skip(Integer.valueOf(pageSize) * Integer.valueOf(pageNumber));
		}
		List<News> newsFormMongo = mongoOperations.find(query, News.class, COLLECTION_NAME);
		if (pageNumber != null && pageSize != null) {
			boolean hasMoreElements = (newsFormMongo!=null && newsFormMongo.size()>0 && newsFormMongo.size()==Integer.valueOf(pageSize)+1);
			for(News newsItem : newsFormMongo){
				newsItem.setHasMoreElements(hasMoreElements);
				newsItem.setNextPage(Integer.valueOf(pageNumber)+1);
			}
			if(hasMoreElements){
				newsFormMongo.remove(newsFormMongo.size()-1);
			}
		}
		return newsFormMongo;
	}

	public void updateNews(News newsToUpdate) throws Exception {
		try {
			if(newsToUpdate.getPri()!=null && (newsToUpdate.getPri()==1 || newsToUpdate.getPri()==2)){
				Query query = new Query();
				query.addCriteria(Criteria.where("pri").is(newsToUpdate.getPri()));
				List<News> news = mongoOperations.find(query, News.class);
				for(News n : news){
					n.setPri(null);
					mongoOperations.save(n);
					logger.error("Change pri of news");
				}	
			
			}
			newsToUpdate.setCreatedDate(new Date(System.currentTimeMillis()));
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
