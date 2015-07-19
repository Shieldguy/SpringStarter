/**
 * 
 */
package org.springstarter.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springstarter.domain.Roles;
import org.springstarter.domain.Users;

/**
 * <p>
 * Implement database process for native sql query
 * </p>
 * @FileName    : DaoServiceImpl.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 22.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@Repository
public class DaoUserRoleServiceImpl implements DaoUserRoleService {
	
	protected EntityManager entityManager;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DaoUserRoleServiceImpl.class);

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
LOGGER.info("[DEBUG] entityManager ({}) setted at DaoUserServiceImpl class.", entityManager);
    }

	/*
	 * (non-Javadoc)
	 * @see org.springstarter.dao.DaoUserRoleService#insertUserRoles(java.lang.Long, java.lang.Long)
	 */
	@Override
	public int insertUserRoles(long userid, long roleid) {
		EntityTransaction et = null;
		int	nResult = 0;
		
		Query query = entityManager.createNativeQuery("insert into users_roles (users_id, roles_id) values (?, ?);");
		query.setParameter(1, userid);
		query.setParameter(2, roleid);
		nResult = query.executeUpdate();
		
		return nResult;
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.springstarter.dao.DaoUserRoleService#deleteUserRoles(java.lang.Long)
	 */
	@Override
	public int deleteUserRoles(long userid) {
		EntityTransaction et = null;
		int	nResult = 0;
		
		Query query = entityManager.createNativeQuery("delete from users_roles where users_id = ?;");
		query.setParameter(1, userid);
		nResult = query.executeUpdate();
		
		return nResult;
	}

}
