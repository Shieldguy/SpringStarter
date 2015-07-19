package org.springstarter.dao;

public interface DaoUserRoleService {

	public abstract int insertUserRoles(long userid, long roleid);

	public abstract int deleteUserRoles(long userid);

}