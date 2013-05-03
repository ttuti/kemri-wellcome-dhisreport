package org.kemri.wellcome.dhisreport.api;

import org.kemri.wellcome.dhisreport.api.model.HttpDhis2Server;

public interface DhisServerService {
	
	public void addDhisServer(HttpDhis2Server server);
	public void updateDhisServer(HttpDhis2Server server);
	public HttpDhis2Server getDhisServer();

}
