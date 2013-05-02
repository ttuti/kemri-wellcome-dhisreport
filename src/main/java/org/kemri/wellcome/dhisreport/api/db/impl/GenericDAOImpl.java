package org.kemri.wellcome.dhisreport.api.db.impl;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.jdbc.Work;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

import org.kemri.wellcome.dhisreport.api.db.GenericDAO;
import org.kemri.wellcome.dhisreport.api.model.PersistentEntity;

/**
 * This class is a generic Data Access Object which provides the basic common
 * data access methods for all extract entities. It uses generics to
 * parameterize the type of domain object we are working with.
 * <p>
 * A domain class instance variable is also declared to allow the domain object
 * implementation type to be injected from an IoC Container such as Spring.
 * 
 */
public class GenericDAOImpl<E extends PersistentEntity> implements
		GenericDAO<E> {

	public Logger logger = Logger.getLogger(GenericDAOImpl.class);

	private SQLExceptionTranslator jdbcExceptionTranslator;

	private SQLExceptionTranslator defaultJdbcExceptionTranslator;

	// Domain implementation class.
	private Class<E> domainClass;

	@SuppressWarnings("unused")
	private String restrictions;

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * This method should be called on initialization of the DAO to set the
	 * persistent Class from the generic E parameter, if the class was not set
	 * explicitly.
	 */
	@SuppressWarnings("unchecked")
	@PostConstruct
	public void setUpDao() {

		if (domainClass == null) {

			domainClass = (Class<E>) ((ParameterizedType) getClass()
					.getGenericSuperclass()).getActualTypeArguments()[0];
		}
	}

	@SuppressWarnings("unchecked")
	public final E findById(final Long id) {

		StringBuffer sb = new StringBuffer();
		sb.append("Attempting to find HibernateEntity ");
		sb.append(domainClass.getSimpleName());
		sb.append("by id ");
		sb.append(id);

		/*
		 * logger.debug(sb.toString()); return (E)
		 * entityManager.find(domainClass, id);
		 */

		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("SELECT i FROM "
				+ domainClass.getName() + " i WHERE i.id = " + id);
		return query.list().size() > 0 ? (E) query.list().get(0):null;

	}

	public final boolean exists(final Long id) {

		return findById(id) != null;
	}

	@SuppressWarnings("unchecked")
	public List<E> findAll() {
		Session session = (Session) getEntityManager().getDelegate();
		Query query = session.createQuery("SELECT i FROM "
				+ domainClass.getName() + " i ORDER BY i.id DESC");
		return query.list();
	}

	/**
	 * We just return the same instance as passed in here, so no need to
	 * reassign to the return value after call.
	 */
	public E saveOrUpdate(final E entity) {

		// entityManager.persist(entity);
		// use Hibernate Session #saveOrPersist as the JPA
		// persist method does not work with detached objects
		// and client code is expecting the Hibernate saveOrUpdate behaviour

		Session session = (Session) getEntityManager().getDelegate();
		session.saveOrUpdate(entity);
		flush();
		return entity;
	}

	/**
	 * Delete the entity passed in from the database.
	 * 
	 * @param entity
	 */
	public void delete(E entity) {

		entityManager.remove(entity);
		flush();
	}

	public void flush() {
		entityManager.flush();
	}

	public void clear() {

		entityManager.clear();
	}

	/**
	 * @see org.hibernate.Session.doWork(org.hibernate.jdbc.Work)
	 * @param work
	 *            Work instance
	 */
	protected void doWork(final Work work) {

		((Session) entityManager.getDelegate()).doWork(work);
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	protected List<E> findByCriteria(final Criterion... criterion) {

		return findByCriteria(false, criterion);
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<E> findByCriteria(final boolean cacheQuery,
			final Criterion... criterion) {
		clear();
		return (List) execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria crit = session.createCriteria(domainClass);

				for (Criterion c : criterion) {
					crit.add(c);
				}

				crit.setCacheable(cacheQuery);
				return crit.list();
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<E> findByExample(final E exampleInstance,
			final String[] excludeProperty) {
		clear();
		return (List) execute(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {

				Criteria criteria = session.createCriteria(domainClass);
				Example example = Example.create(exampleInstance);

				for (String exclude : excludeProperty) {

					example.excludeProperty(exclude);
				}

				criteria.add(example);

				return criteria.list();
			}
		});
	}

	/**
	 * @return the domainClass
	 */
	public Class<E> getDomainClass() {

		return domainClass;
	}

	/**
	 * @param domainClass
	 *            the domainClass to set
	 */
	public void setDomainClass(Class<E> domainClass) {

		this.domainClass = domainClass;
	}

	@SuppressWarnings("rawtypes")
	public Object execute(final HibernateCallback callback) {
		clear();
		final Session session = (Session) entityManager.getDelegate();

		try {
			return callback.doInHibernate(session);
		} catch (HibernateException e) {

			throw SessionFactoryUtils.convertHibernateAccessException(e);
		} catch (SQLException e) {

			throw convertJdbcAccessException(session, e);
		}
	}

	@SuppressWarnings("rawtypes")
	public List executeFind(final HibernateCallback callback) {
		clear();
		Object result = execute(callback);

		if (result != null && !(result instanceof List)) {

			throw new InvalidDataAccessApiUsageException(
					"Result object returned from HibernateCallback "
							+ "isn't a List: [" + result + "]");
		}

		return (List) result;
	}

	/**
	 * Convert the given SQLException to an appropriate exception from the
	 * <code>org.springframework.dao</code> hierarchy. Can be overridden in
	 * subclasses.
	 * <p>
	 * Note that a direct SQLException can just occur when callback code
	 * performs direct JDBC access via <code>Session.connection()</code>.
	 * 
	 * @param ex
	 *            the SQLException
	 * @return the corresponding DataAccessException instance
	 * @see #setJdbcExceptionTranslator
	 * @see org.hibernate.Session#connection()
	 */
	protected DataAccessException convertJdbcAccessException(Session session,
			SQLException ex) {

		SQLExceptionTranslator translator = getJdbcExceptionTranslator();

		if (translator == null) {

			translator = getDefaultJdbcExceptionTranslator(session);
		}

		return translator.translate("Hibernate-related JDBC operation", null,
				ex);
	}

	/**
	 * Obtain a default SQLExceptionTranslator, lazily creating it if necessary.
	 * <p>
	 * Creates a default
	 * {@link org.springframework.jdbc.support. SQLErrorCodeSQLExceptionTranslator}
	 * for the SessionFactory's underlying DataSource.
	 */
	protected synchronized SQLExceptionTranslator getDefaultJdbcExceptionTranslator(
			Session session) {

		if (this.defaultJdbcExceptionTranslator == null) {

			this.defaultJdbcExceptionTranslator = SessionFactoryUtils
					.newJdbcExceptionTranslator(session.getSessionFactory());
		}

		return this.defaultJdbcExceptionTranslator;
	}

	/**
	 * Set the JDBC exception translator for this instance.
	 * <p>
	 * Applied to any SQLException root cause of a Hibernate JDBCException,
	 * overriding Hibernate's default SQLException translation ( which is based
	 * on Hibernate's Dialect for a specific target database ).
	 * 
	 * @param jdbcExceptionTranslator
	 *            the exception translator
	 * @see java.sql.SQLException
	 * @see org.hibernate.JDBCException
	 * @see org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator
	 * @see org.springframework.jdbc.support.SQLStateSQLExceptionTranslator
	 */
	public void setJdbcExceptionTranslator(
			SQLExceptionTranslator jdbcExceptionTranslator) {

		this.jdbcExceptionTranslator = jdbcExceptionTranslator;
	}

	/**
	 * Return the JDBC exception translator for this instance, if any.
	 */
	public SQLExceptionTranslator getJdbcExceptionTranslator() {

		return this.jdbcExceptionTranslator;
	}

	/**
	 * Returns the EntityManager for direct reference from subclasses.
	 * 
	 * @return EntityManager
	 */
	protected EntityManager getEntityManager() {
		
		return entityManager;
	}

}
