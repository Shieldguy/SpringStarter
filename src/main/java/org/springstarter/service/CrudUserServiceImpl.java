/**
 * 
 */
package org.springstarter.service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.mariadb.jdbc.internal.common.QueryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springstarter.dao.DaoUserRoleService;
import org.springstarter.domain.Roles;
import org.springstarter.domain.Users;
import org.springstarter.repository.RolesRepository;
import org.springstarter.repository.UsersRepository;

/**
 * <p>
 * @FileName    : UserService.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 19.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@Service
public class CrudUserServiceImpl implements CrudUserService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CrudUserServiceImpl.class);
	
	private DaoUserRoleService		daoUserRoleService;
	private UsersRepository	usersRepository;
	private	RolesRepository	rolesRepository;
	
	/**
	 * @param daoService the daoService to set
	 */
	@Autowired
	public void setDaoService(DaoUserRoleService daoUserRoleService) {
		this.daoUserRoleService = daoUserRoleService;
LOGGER.info("[DEBUG] daoUserRoleService ({}) was assigned at CrudUserServiceImpl", daoUserRoleService);
	}
	
	/**
	 * @param usersRepository the usersRepository to set
	 */
	@Autowired
	public void setUsersRepository(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
LOGGER.info("[DEBUG] usersRepository ({}) was assigned at CrudUserServiceImpl", usersRepository);
	}

	/**
	 * @param rolesRepository the rolesRepository to set
	 */
	@Autowired
	public void setRolesRepository(RolesRepository rolesRepository) {
		this.rolesRepository = rolesRepository;
LOGGER.info("[DEBUG] rolesRepository ({}) was assigned at CrudUserServiceImpl", rolesRepository);
	}

	/**
	 * <p>
	 * check input values
	 * </p>
	 * @param map
	 * @return
	 */
	public String checkValues(Map<String, String> map) {
		StringBuffer buffer = new StringBuffer();
		
		if(!map.containsKey("username") || map.get("username") == null || map.get("username").length() <= 0)
			buffer.append("username");
		if(!map.containsKey("password") || map.get("password") == null || map.get("password").length() <= 0)
			buffer.append((buffer.length() > 0)? ",password" : "password");
		if(!map.containsKey("firstname") || map.get("firstname") == null || map.get("firstname").length() <= 0)
			buffer.append((buffer.length() > 0)? ",firstname" : "firstname");
		if(!map.containsKey("secondname") || map.get("secondname") == null || map.get("secondname").length() <= 0)
			buffer.append((buffer.length() > 0)? ",secondname" : "secondname");
		
		return buffer.toString();
	}
	
	/* (non-Javadoc)
	 * @see org.springstarter.service.CrudService#add(java.util.Map)
	 */
	@Override
	public Map<String, String> add(Map<String, String> map) {
		Map<String, String> retMap = map;
		String errField = checkValues(map);
		if(errField.isEmpty()) { 
			Roles role = rolesRepository.findByRoleNameEquals("GUEST");
			List<Roles> roles = new ArrayList<>();
			roles.add(role);
			
			Users user = new Users(map.get("username"), map.get("password"), map.get("firstname"), 
					map.get("secondname"), roles);
			
			// Password Encode
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setConfirmCode(UUID.randomUUID().toString());
			retMap.put("confirmcode", ""+user.getConfirmCode());
			user.setRegDate(Timestamp.valueOf(LocalDateTime.now()));
			user.setModDate(Timestamp.valueOf(LocalDateTime.now()));
			
			user = usersRepository.save(user);
			retMap.put("id", ""+user.getId());
			
			LOGGER.info("User ({}) adding service processed successfully.", user.getUserEmail());
		} else {
			retMap.put("errorField", errField);
			LOGGER.warn("Required field value for adding doen't specified.");
		}
		
		return retMap;
	}

	/* (non-Javadoc)
	 * @see org.springstarter.service.CrudService#get(java.lang.String)
	 */
	@Override
	public Map<String, String> get(String keyID) {
		Map<String, String> retMap = new HashMap<>();
		
		Users user = usersRepository.findByUserEmailEquals(keyID);
		if(user == null) {
			LOGGER.debug("Request user ({}) not founded.", keyID);
			retMap.put("error_message", "Request user not exist.");
		} else {
			LOGGER.debug("Request user ({}) founded.", user.getUserEmail());
			retMap.put("id", ""+user.getId());
			retMap.put("username", user.getUserEmail());
			retMap.put("firstname", user.getFirstName());
			retMap.put("secondname", user.getSecondName());
			if(!user.isConfirm())
				retMap.put("confirmcode", user.getConfirmCode());
		}
		
		return retMap;
	}

	/* (non-Javadoc)
	 * @see org.springstarter.service.CrudService#modify(java.lang.String, java.util.Map)
	 */
	@Override
	public Map<String, String> modify(String keyID, Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springstarter.service.CrudService#delete(java.lang.String)
	 */
	@Override
	public Map<String, String> delete(String keyID) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.springstarter.service.CrudUserService#confirmUser(java.lang.String, java.lang.String)
	 */
	@Transactional
	@Override
	public Map<String, String> confirmUser(String keyID, String confirmCode) {
		Map<String, String> retMap = new HashMap<>();
		
		Users user = usersRepository.findByUserEmailAndConfirmCodeEquals(keyID, confirmCode);
		if(user == null) {
			LOGGER.info("Request user ({}) not founded.", keyID);
			retMap.put("error_message", "Request user not exist.");
		} else {
			Roles role = rolesRepository.findByRoleNameEquals("USER");
			List<Roles> roles = new ArrayList<>();
			roles.add(role);
			LOGGER.info("[DEBUG] Request user ({}/{}/{}) founded.", user.getUserEmail(), role.getRoleName(), role.getId());
			
			user.setConfirmCode("");
			user.setConfirm(true);
			user.setRoles(roles);
			user.setModDate(Timestamp.valueOf(LocalDateTime.now()));

			daoUserRoleService.deleteUserRoles(user.getId());
			daoUserRoleService.insertUserRoles(user.getId(), role.getId());
			usersRepository.save(user);
			
			retMap.put("id", ""+user.getId());
			retMap.put("username", user.getUserEmail());
			retMap.put("firstname", user.getFirstName());
			retMap.put("secondname", user.getSecondName());
		}
		
		return retMap;
	}

}
