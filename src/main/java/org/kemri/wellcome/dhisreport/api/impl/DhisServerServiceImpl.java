package org.kemri.wellcome.dhisreport.api.impl;

import org.kemri.wellcome.dhisreport.api.DhisServerService;
import org.kemri.wellcome.dhisreport.api.db.DhisServerDAO;
import org.kemri.wellcome.dhisreport.api.model.HttpDhis2Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

@Service("serverService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class DhisServerServiceImpl implements DhisServerService {
	
	@Autowired
	private DhisServerDAO serverDAO;

	@Override
	public void addDhisServer(HttpDhis2Server server) {
		serverDAO.addDhisServer(server);

	}

	@Override
	public void updateDhisServer(HttpDhis2Server server) {
		serverDAO.updateDhisServer(server);

	}

	@Override
	public HttpDhis2Server getDhisServer() {
		return serverDAO.getDhisServer();
	}

}
