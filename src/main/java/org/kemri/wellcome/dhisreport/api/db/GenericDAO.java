package org.kemri.wellcome.dhisreport.api.db;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.kemri.wellcome.dhisreport.api.model.PersistentEntity;



/**
 * This interface is a generic Data Access Object which defines the 
 * basic common data access methods for entites in the ISIS domain.
 * It uses generics to parameterize the type of domain object we are 
 * working with.
 * 
 */
public interface GenericDAO<E extends PersistentEntity > 
{

    E findById(final Long id);

    boolean exists(final Long id);

    List<E> findAll();

    List<E> findByExample( final E exampleInstance,
            			   final String[] excludeProperty );

	/**
	 * Save the entity to the database. 
	 * The saved entity will be returned - this could be important, as it could 
	 * potentially be a different instance, with fields updated after the save
	 * such as id or version number. Check the implementation.
	 * 
	 * @param entity
	 */
    E saveOrUpdate(E entity);
	 
	/**
	 * Delete the entity passed in from the database.
	 * 
	 * @param entity
	 */
	void delete(E entity );
    
	/**
	 * Flush, (commit), all changes to persistent objects. NOTE: This will
	 * flush changes to ALL persistent objects not just not just those of this
	 * particular entity. 
	 */
    void flush();

    void clear();
    
    /**
     * Used to execute a callback action in the context of the Hibernate Session.
     * If JPA is being used, the implementation will delegate to the Hibernate Session
     * wrapped by the EntityManager.
     * <p>
     * This interface method exists to simplify the Hibernate -> JPA migration.
     * 
     * @param callback HibernateCallback
     * @return Object returned by passed HibernateCallback
     */
    @SuppressWarnings("rawtypes")
    Object execute(HibernateCallback callback);
    
    /**
     * Used to execute a callback action in the context of the Hibernate Session
     * that returns a List of items.
     * If JPA is being used, the implementation will delegate to the Hibernate Session
     * wrapped by the EntityManager.
     * <p>
     * This interface method exists to simplify the Hibernate -> JPA migration.
     * 
     * @param callback HibernateCallback
     * @return List returned by passed HibernateCallback
     */
    @SuppressWarnings("rawtypes")
	List executeFind(HibernateCallback callback);
    
}
