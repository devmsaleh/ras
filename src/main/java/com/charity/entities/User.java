package com.charity.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.charity.enums.BranchPermissionTypeEnum;
import com.charity.enums.ContractTypeEnum;
import com.charity.enums.GenderTypeEnum;
import com.charity.enums.UserTypeEnum;
import com.charity.util.Constants;
import com.charity.util.Encryptor;
import com.charity.util.GeneralUtils;

@Entity
public class User extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -5309130510929562607L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	private Long id;
	@Column(nullable = false, unique = true)
	private String userName;//
	private String email;
	private String mobileNumber;
	private String address;
	@Column(nullable = false)
	private String password;
	private boolean enabled = true;
	private boolean accountNonExpired = true;
	private boolean accountNonLocked = true;
	private boolean credentialsNonExpired = true;
	private String displayName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dateCreated = new Date();
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateLastModified;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserRole> roles = new HashSet<UserRole>();
	private String token;

	@Enumerated(EnumType.STRING)
	private UserTypeEnum type = UserTypeEnum.DELEGATE;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastLoginDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date lastActionDate;

	@Enumerated(EnumType.STRING)
	private ContractTypeEnum contractType = ContractTypeEnum.PERMANENT;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Temporal(TemporalType.DATE)
	private Date expiryDate;

	@Temporal(TemporalType.DATE)
	private Date birthDate;

	@Enumerated(EnumType.STRING)
	private GenderTypeEnum genderType = GenderTypeEnum.MALE;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Attachment photo;

	@ManyToOne(fetch = FetchType.LAZY)
	private User creator;

	@ManyToOne(fetch = FetchType.LAZY)
	private User lastModifier;

	@Column(name = "last_modifier_id", insertable = false, updatable = false)
	private Long lastModifierId;

	@Transient
	private String lastModifierDisplayName;

	@Column(name = "country_id", insertable = false, updatable = false)
	private Long countryId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Country country;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Emarah emarah;

	@Column(name = "emarah_id", insertable = false, updatable = false)
	private Long emarahId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private City city;

	@Column(name = "city_id", insertable = false, updatable = false)
	private Long cityId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Region region;

	@Column(name = "region_id", insertable = false, updatable = false)
	private Long regionId;

	@ManyToOne(fetch = FetchType.LAZY)
	private Location location;

	@Column(name = "location_id", insertable = false, updatable = false)
	private Long locationId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Branch branch;

	@Column(name = "branch_id", insertable = false, updatable = false)
	private Long branchId;

	@Column(columnDefinition = "integer default 0")
	private Integer userOrder;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private UserDetails userDetails;

	private BigDecimal maxLimit;

	private String attribute1;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Custody custody;

	@Enumerated(EnumType.STRING)
	private BranchPermissionTypeEnum branchPermissionType;

	@Transient
	private String passwordDisplay;

	@Transient
	private String typeDisplay;

	@Transient
	private String displayNameAutoComplete;

	@Transient
	private boolean admin;

	@Transient
	private boolean charityBox;

	@Transient
	private boolean showCollectors;

	@Transient
	private String idEncrypted;

	private String notes;

	@ManyToOne(fetch = FetchType.LAZY)
	private User creatorAdmin;

	@Transient
	private int age;

	@Transient
	private String dateCreatedStr;

	public User(UserTypeEnum userTypeEnum) {
		this.type = userTypeEnum;
		this.passwordDisplay = Encryptor.generateRandomNumber(5);
		this.attribute1 = Encryptor.encrypt(this.passwordDisplay);
		this.password = new BCryptPasswordEncoder().encode(this.passwordDisplay);
		if (userTypeEnum == UserTypeEnum.BENEFACTOR) {
			// this.userName = "ben" + Encryptor.generateRandomNumber(3);
		}
		this.userDetails = new UserDetails();
		this.contractType = null;
	}

	public User(Long id) {
		this.id = id;
	}

	public User(Long id, String displayName) {
		this.id = id;
		this.displayName = displayName;
	}

	public User() {
		this.attribute1 = Encryptor.generateRandomNumber(5);
		this.passwordDisplay = attribute1;
		this.userName = "delegate" + Encryptor.generateRandomNumber(3);
		this.userDetails = new UserDetails();
		this.custody = new Custody();
		this.country = new Country(Constants.UAE_COUNTRY_ID);
		this.startDate = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public GenderTypeEnum getGenderType() {
		return genderType;
	}

	public void setGenderType(GenderTypeEnum genderType) {
		this.genderType = genderType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateLastModified() {
		return dateLastModified;
	}

	public void setDateLastModified(Date dateLastModified) {
		this.dateLastModified = dateLastModified;
	}

	public Set<UserRole> getRoles() {
		return roles;
	}

	public void setRoles(Set<UserRole> roles) {
		this.roles = roles;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public UserTypeEnum getType() {
		return type;
	}

	public void setType(UserTypeEnum type) {
		this.type = type;
	}

	public ContractTypeEnum getContractType() {
		return contractType;
	}

	public void setContractType(ContractTypeEnum contractType) {
		this.contractType = contractType;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Attachment getPhoto() {
		return photo;
	}

	public void setPhoto(Attachment photo) {
		this.photo = photo;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public User getLastModifier() {
		return lastModifier;
	}

	public void setLastModifier(User lastModifier) {
		this.lastModifier = lastModifier;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Emarah getEmarah() {
		return emarah;
	}

	public void setEmarah(Emarah emarah) {
		this.emarah = emarah;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Integer getUserOrder() {
		return userOrder;
	}

	public void setUserOrder(Integer userOrder) {
		this.userOrder = userOrder;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public String getAttribute1() {
		return attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getPasswordDisplay() {
		return passwordDisplay;
	}

	public void setPasswordDisplay(String passwordDisplay) {
		this.passwordDisplay = passwordDisplay;
	}

	public BigDecimal getMaxLimit() {
		return maxLimit;
	}

	public void setMaxLimit(BigDecimal maxLimit) {
		this.maxLimit = maxLimit;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Custody getCustody() {
		return custody;
	}

	public void setCustody(Custody custody) {
		this.custody = custody;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getTypeDisplay() {
		if (type != null) {
			if (type == UserTypeEnum.EMPLOYEE) {
				typeDisplay = "موظف";
			} else if (type == UserTypeEnum.ADMIN) {
				typeDisplay = "مدير النظام";
			}
		}
		return typeDisplay;
	}

	public void setTypeDisplay(String typeDisplay) {
		this.typeDisplay = typeDisplay;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getDisplayNameAutoComplete() {
		return displayNameAutoComplete;
	}

	public void setDisplayNameAutoComplete(String displayNameAutoComplete) {
		this.displayNameAutoComplete = displayNameAutoComplete;
	}

	public BranchPermissionTypeEnum getBranchPermissionType() {
		return branchPermissionType;
	}

	public void setBranchPermissionType(BranchPermissionTypeEnum branchPermissionType) {
		this.branchPermissionType = branchPermissionType;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isCharityBox() {
		return charityBox;
	}

	public void setCharityBox(boolean charityBox) {
		this.charityBox = charityBox;
	}

	public boolean isShowCollectors() {
		return showCollectors;
	}

	public void setShowCollectors(boolean showCollectors) {
		this.showCollectors = showCollectors;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Date getLastActionDate() {
		return lastActionDate;
	}

	public void setLastActionDate(Date lastActionDate) {
		this.lastActionDate = lastActionDate;
	}

	public Long getEmarahId() {
		return emarahId;
	}

	public void setEmarahId(Long emarahId) {
		this.emarahId = emarahId;
	}

	public String getIdEncrypted() {
		if (id != null)
			return Encryptor.encrypt(id.toString());
		else
			return "";
	}

	public void setIdEncrypted(String idEncrypted) {
		this.idEncrypted = idEncrypted;
	}

	public Long getLastModifierId() {
		return lastModifierId;
	}

	public void setLastModifierId(Long lastModifierId) {
		this.lastModifierId = lastModifierId;
	}

	public String getLastModifierDisplayName() {
		return lastModifierDisplayName;
	}

	public void setLastModifierDisplayName(String lastModifierDisplayName) {
		this.lastModifierDisplayName = lastModifierDisplayName;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public User getCreatorAdmin() {
		return creatorAdmin;
	}

	public void setCreatorAdmin(User creatorAdmin) {
		this.creatorAdmin = creatorAdmin;
	}

	public int getAge() {
		if (birthDate != null) {
			age = GeneralUtils.getAge(birthDate);
		}
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDateCreatedStr() {
		dateCreatedStr = GeneralUtils.formatDateTime(dateCreated);
		return dateCreatedStr;
	}

	public void setDateCreatedStr(String dateCreatedStr) {
		this.dateCreatedStr = dateCreatedStr;
	}

}
