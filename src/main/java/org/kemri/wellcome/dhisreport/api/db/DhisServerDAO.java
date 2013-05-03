package org.kemri.wellcome.dhisreport.api.db;

import org.kemri.wellcome.dhisreport.api.model.HttpDhis2Server;

public interface DhisServerDAO extends GenericDAO<HttpDhis2Server> {
	
	public void addDhisServer(HttpDhis2Server server);
	public void updateDhisServer(HttpDhis2Server server);
	public HttpDhis2Server getDhisServer();

}
