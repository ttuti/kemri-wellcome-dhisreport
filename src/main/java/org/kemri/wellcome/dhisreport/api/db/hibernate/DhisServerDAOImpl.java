package org.kemri.wellcome.dhisreport.api.db.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.kemri.wellcome.dhisreport.api.db.DhisServerDAO;
import org.kemri.wellcome.dhisreport.api.db.impl.GenericDAOImpl;
import org.kemri.wellcome.dhisreport.api.model.HttpDhis2Server;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Repository(value = "serverDAO")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class DhisServerDAOImpl extends GenericDAOImpl<HttpDhis2Server> implements
		DhisServerDAO {
	
	private static final Logger logger = Logger.getLogger(DhisServerDAOImpl.class);

	@Override
	public void addDhisServer(HttpDhis2Server server) {
		saveOrUpdate(server);
	}

	@Override
	public void updateDhisServer(HttpDhis2Server server) {
		saveOrUpdate(server);
	}

	@Override
	public HttpDhis2Server getDhisServer() {
		HttpDhis2Server server = null;
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("SELECT i FROM "
				+ getDomainClass().getName()+ " i ");
		if(query.list().size() > 0){
			logger.error("\n List size:"+query.list().size()+"\n");
			server = (HttpDhis2Server)query.list().get(0);
			return server;
		}		
		return server;
	}

}
