/**
 *  Copyright 2012 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of DHIS2 Reporting module.
 *
 *  DHIS2 Reporting module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  DHIS2 Reporting module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with DHIS2 Reporting module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package org.kemri.wellcome.dhisreport.api.db.hibernate;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hisp.dhis.dxf2.importsummary.ImportConflict;
import org.hisp.dhis.dxf2.importsummary.ImportCount;
import org.hisp.dhis.dxf2.importsummary.ImportSummary;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingException;
import org.kemri.wellcome.dhisreport.api.db.DHIS2ReportingDAO;
import org.kemri.wellcome.dhisreport.api.model.DataElement;
import org.kemri.wellcome.dhisreport.api.model.DataValueTemplate;
import org.kemri.wellcome.dhisreport.api.model.Disaggregation;
import org.kemri.wellcome.dhisreport.api.model.Identifiable;
import org.kemri.wellcome.dhisreport.api.model.Location;
import org.kemri.wellcome.dhisreport.api.model.ReportDefinition;
import org.kemri.wellcome.dhisreport.api.model.Role;
import org.kemri.wellcome.dhisreport.api.model.User;
import org.kemri.wellcome.dhisreport.api.utils.Period;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * It is a default implementation of {@link DHIS2ReportingDAO}.
 */

@Repository(value = "dao")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class HibernateDHIS2ReportingDAO implements DHIS2ReportingDAO {
	protected final Logger log = Logger
			.getLogger(HibernateDHIS2ReportingDAO.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public DataElement getDataElement(Integer id) {
		Session session = entityManager.unwrap(Session.class);
		return (DataElement) session.get(DataElement.class, id);
	}
	
	@Override
	public ImportSummary getImportSummary(Integer id) {
		Session session = entityManager.unwrap(Session.class);
		return (ImportSummary) session.get(ImportSummary.class, id);
	}

	@Override
	public DataElement saveDataElement(DataElement de) {
		return (DataElement) saveObject(de);
	}

	@Override
	public void deleteDataElement(DataElement de) {
		Session session = entityManager.unwrap(Session.class);
		session.delete(de);
	}

	@Override
	public Disaggregation getDisaggregation(Integer id) {
		Session session = entityManager.unwrap(Session.class);
		return (Disaggregation) session.get(Disaggregation.class, id);
	}

	@Override
	public Disaggregation saveDisaggregation(Disaggregation disagg) {
		return (Disaggregation) saveObject(disagg);
	}

	@Override
	public ReportDefinition getReportDefinition(Integer id) {
		Session session = entityManager.unwrap(Session.class);
		return (ReportDefinition) session.get(ReportDefinition.class, id);
	}

	@Override
	public ReportDefinition saveReportDefinition(ReportDefinition rd) {
		return (ReportDefinition) saveObject(rd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<DataElement> getAllDataElements() {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("from DataElement order by name asc");
		return (List<DataElement>) query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Disaggregation> getAllDisaggregations() {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("from Disaggregation");
		return (List<Disaggregation>) query.list();
	}

	@Override
	public void deleteDisaggregation(Disaggregation disagg) {
		Session session = entityManager.unwrap(Session.class);
		session.delete(disagg);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ReportDefinition> getAllReportDefinitions() {
		Session session = entityManager.unwrap(Session.class);
		Query query = session
				.createQuery("from ReportDefinition order by name asc");
		return (List<ReportDefinition>) query.list();
	}

	@Override
	public void deleteReportDefinition(ReportDefinition rd) {
		Session session = entityManager.unwrap(Session.class);
		session.delete(rd);
	}

	@Override
	public String evaluateDataValueTemplate(DataValueTemplate dvt,
			Period period, Location location) throws DHIS2ReportingException {
		String queryString = dvt.getQuery();
		if (queryString == null || queryString.isEmpty()) {
			log.error("Empty query for " + dvt.getDataelement().getName()
					+ " : " + dvt.getDisaggregation().getName());
			return "No results returned for query";
		}

		if (dvt.potentialUpdateDelete()) {
			throw new DHIS2ReportingException(
					"Attempt to execute potential update/delete query for "
							+ dvt.getDataelement().getName() + " : "
							+ dvt.getDisaggregation().getName());
		}

		Session session = entityManager.unwrap(Session.class);
		Query query = session.createSQLQuery(queryString);
		query.setParameter("locationName", location.getName());
		query.setParameter("startPeriod", period.getStart());
		query.setParameter("endPeriod", period.getEnd());
		
		Object object= query.uniqueResult();		
		if(object !=null)
			return query.uniqueResult().toString();
		
	    return "No results returned for query";
	}

	// --------------------------------------------------------------------------------------------------------------
	// Generic methods for DHIS2 identifiable objects
	// --------------------------------------------------------------------------------------------------------------
	public Identifiable getObjectByUid(String uid, Class<?> clazz) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(clazz);
		criteria.add(Restrictions.eq("uid", uid));
		return (Identifiable) criteria.uniqueResult();
	}
	
	public DataValueTemplate getDataValueTemplate(ReportDefinition reportDefinition,DataElement dataelement,Disaggregation disaggregation) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(DataValueTemplate.class);
		criteria.add(Restrictions.eq("reportDefinition", reportDefinition));
		criteria.add(Restrictions.eq("dataelement", dataelement));
		criteria.add(Restrictions.eq("disaggregation", disaggregation));
		return (DataValueTemplate) criteria.uniqueResult();
	}
	
	public Identifiable getObjectByName(String name, Class<?> clazz) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(clazz);
		criteria.add(Restrictions.eq("name", name));
		return (Identifiable) criteria.uniqueResult();
	}

	public Identifiable saveObject(Identifiable object) {
		Session session = entityManager.unwrap(Session.class);

		// force merge if uid already exists
		Identifiable existingObject = getObjectByUid(object.getUid(),
				object.getClass());		
		
		// force merge if name already exists but replace uid
		Identifiable existingObject2 = getObjectByName(object.getName(),
				object.getClass());
		
		String uid= object.getUid();			
		if (existingObject != null) {
			session.evict(existingObject);
			object.setId(existingObject.getId());
			session.load(object, object.getId());
		}else if(existingObject == null && existingObject2 !=null){
			session.evict(existingObject2);
			object.setId(existingObject2.getId());
			session.load(object, object.getId());
		}
		object.setUid(uid);		
		session.saveOrUpdate(object);
		return object;
	}

	@Override
	public DataElement getDataElementByUid(String uid) {
		return (DataElement) getObjectByUid(uid, DataElement.class);
	}

	@Override
	public Disaggregation getDisaggregationByUid(String uid) {
		return (Disaggregation) getObjectByUid(uid, Disaggregation.class);
	}

	@Override
	public ReportDefinition getReportDefinitionByUid(String uid) {
		return (ReportDefinition) getObjectByUid(uid, ReportDefinition.class);
	}

	@Override
	public DataValueTemplate getDataValueTemplate(Integer id) {
		Session session = entityManager.unwrap(Session.class);
		return (DataValueTemplate) session.get(DataValueTemplate.class, id);
	}

	@Override
	public DataValueTemplate saveDataValueTemplate(DataValueTemplate dvt) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(dvt);
		return dvt;
	}

	@Override
	public Location getLocationByOU_Code(String OU_Code) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Location.class);
		criteria.add(Restrictions.like("name", "%" + OU_Code + "%"));
		return (Location) criteria.uniqueResult();
	}

	@Override
	public void deleteDataValueTemplate(DataValueTemplate dvt) {
		Session session = entityManager.unwrap(Session.class);
		session.delete(dvt);

	}

	@Override
	public Location getLocation(Integer id) {
		Session session = entityManager.unwrap(Session.class);
		return (Location) session.get(Location.class, id);
	}

	@Override
	public Location saveLocation(Location ln) {
		return (Location) saveObject(ln);
	}

	@Override
	public void deleteLocation(Location ln) {
		Session session = entityManager.unwrap(Session.class);
		session.delete(ln);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Location> getAllLocations() {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("from Location");
		return (List<Location>) query.list();
	}

	@Override
	public ImportSummary saveImportSummary(ImportSummary im) {
		return (ImportSummary) saveObject(im);
	}

	@Override
	public ImportCount saveImportCount(ImportCount ic) {
		return (ImportCount) saveObject(ic);
	}

	@Override
	public ImportConflict saveImportConflict(ImportConflict icf) {
		return (ImportConflict) saveObject(icf);
	}

	@Override
	public User getUser(Integer id) {
		Session session = entityManager.unwrap(Session.class);
		return (User) session.get(User.class, id);
	}

	@Override
	public User getUserByUsername(String username) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.like("username", "%" + username + "%"));
		return (User) criteria.uniqueResult();
	}

	@Override
	public User getUserByEmail(String email) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.like("email", "%" + email + "%"));
		return (User) criteria.uniqueResult();
	}

	@Override
	public Role getRole(Integer id) {
		Session session = entityManager.unwrap(Session.class);
		return (Role) session.get(Role.class, id);
	}

	@Override
	public Role getRoleByName(String roleName) {
		Session session = entityManager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Role.class);
		criteria.add(Restrictions.like("roleName", "%" + roleName + "%"));
		return (Role) criteria.uniqueResult();
	}

	@Override
	public User saveUser(User user) {
		return (User) saveObject(user);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Role> getAllRoles() {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("from Role");
		return (List<Role>) query.list();
	}
}
