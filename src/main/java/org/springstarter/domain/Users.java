/**
 * 
 */
package org.springstarter.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * <p>
 * User domain class.
 * </p>
 * @FileName    : Users.java
 * @Project     : SpringStarter
 * @Date        : 2015. 6. 19.
 * @Version     : 1.0
 * @Author      : Simon W.J. Kim (shieldguy@gmail.com)
 */
@Entity
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long	id;
	
	@NotEmpty
	@Column(name = "userid", unique = true, nullable = false)
	private String	userEmail;	// email address
	
	@JsonIgnore
	@NotEmpty
	@Column(nullable = false)
	private String	password;
	
	//@Transient
	//private String	passwordConfirm;
	
	@NotEmpty
	@Column(nullable = false)
	private String	firstName;
	
	@NotEmpty
	@Column(nullable = false)
	private String	secondName;
	
	@JsonIgnore
	private boolean	isConfirm = false;			// user email confirm
	
	@JsonIgnore
	private boolean	isLocked = false;			// cause password wrong
	
	@JsonIgnore
	private boolean	isEnabled = true;			// cause user request disable
	
	@JsonIgnore
	private String	confirmCode;
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL) 
	@JoinTable(name = "USERS_ROLES", 
			joinColumns = @JoinColumn(name = "USERS_ID", referencedColumnName = "ID"), 
			inverseJoinColumns = @JoinColumn(name = "ROLES_ID", referencedColumnName = "ID"))
	private List<Roles>	roles;
	
	@CreatedDate
	private Timestamp	regDate;
	
	@JsonIgnore
	@LastModifiedDate
	private Timestamp	modDate;

	public Users() {
	}
	
	public Users(String userEmail, String password, String firstName,
			String secondName, List<Roles> roles) {
		super();
		this.userEmail = userEmail;
		this.password = password;
		this.firstName = firstName;
		this.secondName = secondName;
		this.roles = roles;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 * @return this
	 */
	public Users setId(long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail the userEmail to set
	 * @return this
	 */
	public Users setUserEmail(String userEmail) {
		this.userEmail = userEmail;
		return this;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 * @return this
	 */
	public Users setPassword(String password) {
		this.password = password;
		return this;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 * @return this
	 */
	public Users setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * @return the secondName
	 */
	public String getSecondName() {
		return secondName;
	}

	/**
	 * @param secondName the secondName to set
	 * @return this
	 */
	public Users setSecondName(String secondName) {
		this.secondName = secondName;
		return this;
	}

	/**
	 * @return the isConfirm
	 */
	public boolean isConfirm() {
		return isConfirm;
	}

	/**
	 * @param isConfirm the isConfirm to set
	 */
	public void setConfirm(boolean isConfirm) {
		this.isConfirm = isConfirm;
	}

	/**
	 * @return the isLocked
	 */
	public boolean isLocked() {
		return isLocked;
	}

	/**
	 * @param isLocked the isLocked to set
	 */
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * @return the confirmCode
	 */
	public String getConfirmCode() {
		return confirmCode;
	}

	/**
	 * @param confirmCode the confirmCode to set
	 */
	public void setConfirmCode(String confirmCode) {
		this.confirmCode = confirmCode;
	}

	/**
	 * @return the roles
	 */
	public List<Roles> getRoles() {
		return roles;
	}

	/**
	 * @param role the role to set
	 * @return this
	 */
	public Users setRoles(List<Roles> role) {
		this.roles = roles;
		return this;
	}

	/**
	 * @return the isEnabled
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * @param isEnabled the isEnabled to set
	 * @return this
	 */
	public Users setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
		return this;
	}

	
	/**
	 * @return the regDate
	 */
	public Timestamp getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the modDate
	 */
	public Timestamp getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(Timestamp modDate) {
		this.modDate = modDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Users [userEmail=" + userEmail + ", firstName=" + firstName
				+ ", secondName=" + secondName + ", roles=" + roles + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((secondName == null) ? 0 : secondName.hashCode());
		result = prime * result
				+ ((userEmail == null) ? 0 : userEmail.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (secondName == null) {
			if (other.secondName != null)
				return false;
		} else if (!secondName.equals(other.secondName))
			return false;
		if (userEmail == null) {
			if (other.userEmail != null)
				return false;
		} else if (!userEmail.equals(other.userEmail))
			return false;
		return true;
	}

}
