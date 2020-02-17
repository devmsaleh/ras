package com.charity.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.charity.dao.BranchRepository;
import com.charity.dao.CityRepository;
import com.charity.dao.CountryRepository;
import com.charity.dao.EmarahRepository;
import com.charity.dao.LocationRepository;
import com.charity.dao.RegionRepository;
import com.charity.dao.RoleRepository;
import com.charity.dao.UserRepository;
import com.charity.dto.RoleCategoryDTO;
import com.charity.entities.Branch;
import com.charity.entities.City;
import com.charity.entities.Country;
import com.charity.entities.Custody;
import com.charity.entities.Emarah;
import com.charity.entities.Location;
import com.charity.entities.Region;
import com.charity.entities.Role;
import com.charity.entities.User;
import com.charity.entities.UserRole;
import com.charity.enums.BranchPermissionTypeEnum;
import com.charity.enums.RoleCategoryEnum;
import com.charity.enums.RoleTypeEnum;
import com.charity.enums.UserTypeEnum;
import com.charity.service.UserService;
import com.charity.util.Constants;
import com.charity.util.Encryptor;
import com.charity.util.GeneralUtils;

@Component("userManagementBean")
@Scope("view")
public class UserManagementBean implements Serializable {

	private static final long serialVersionUID = -2170171168619370572L;

	private static final Logger log = LoggerFactory.getLogger(UserManagementBean.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CountryRepository countryRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private CurrentUserBean currentUserBean;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private EmarahRepository emarahRepository;

	private List<User> delegatesList = new ArrayList<User>();

	private List<User> filteredDelegatesList = new ArrayList<User>();

	private List<User> employeesList = new ArrayList<User>();

	private List<User> filteredEmployeesList = new ArrayList<User>();

	private User user = new User();

	private Long selectedRoleId;

	private List<Country> countriesList = new ArrayList<Country>();

	private List<Emarah> emarahList = new ArrayList<Emarah>();

	private List<City> citiesList = new ArrayList<City>();

	private List<Region> regionsList = new ArrayList<Region>();

	private List<Location> locationsList = new ArrayList<Location>();

	private List<Branch> branchesList = new ArrayList<Branch>();

	private Date minDate = DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH);

	private boolean editMode;

	private boolean renderUserTab;

	private int activeTab;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private BranchRepository branchRepository;

	private List<SelectItem> roleCategoriesItemsList = new ArrayList<SelectItem>();

	private List<RoleCategoryDTO> roleCategoriesList = new ArrayList<RoleCategoryDTO>();

	private List<Role> selectedRolesList = new ArrayList<Role>();

	private long emarahDefaultId = Constants.DEFAULT_EMARAH_ID;

	private Emarah emarahDefault;

	@PostConstruct
	public void init() {
		try {
			log.info("######## init UserManagementBean,uri: " + GeneralUtils.getHttpServletRequest().getRequestURI());
			emarahList = emarahRepository.findActiveEmarahs();
			if (GeneralUtils.getHttpServletRequest().getRequestURI().contains("delegates.xhtml")) {
				if (currentUserBean.isAdmin()
						|| currentUserBean.hasRoleForAllBranches(RoleTypeEnum.ROLE_MANAGE_DELEGATES)) {
					delegatesList = userService.findAllDelegates(true);
				} else if (currentUserBean.hasRole(RoleTypeEnum.ROLE_MANAGE_DELEGATES)) {
					delegatesList = userService.findAllDelegatesInBranch(currentUserBean.getUser().getBranchId(), true);
				}
				filteredDelegatesList.addAll(delegatesList);
				user.setUserName("user" + (delegatesList.size() + 1));
			} else if (GeneralUtils.getHttpServletRequest().getRequestURI().contains("employees.xhtml")) {
				loadRoles();
				if (currentUserBean.isAdmin()
						|| currentUserBean.hasRoleForAllBranches(RoleTypeEnum.ROLE_MANAGE_EMPLOYEES)) {
					employeesList = userService.findAllEmployees();
				} else if (currentUserBean.hasRole(RoleTypeEnum.ROLE_MANAGE_EMPLOYEES)) {
					// we should hide admin user from employees
					employeesList = userService.findAllEmployeesInBranch(currentUserBean.getUser().getBranchId());
				}
				filteredEmployeesList.addAll(employeesList);
				user.setUserName("emp" + (employeesList.size() + 1));
				user.setType(UserTypeEnum.EMPLOYEE);
				userTypeChanged();
			}

			countriesList = countryRepository.findAll();
			log.info("######## init UserManagementBean,delegatesList: " + delegatesList.size() + ",emarahList: "
					+ emarahList.size());
			user.setCreator(currentUserBean.getUser());

		} catch (Exception e) {
			log.error("Exception in init UserManagementBean", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void userTypeChanged() {
		try {
			log.info("######## userTypeChanged,type: " + user.getType());
			if (user.getType().equals(UserTypeEnum.ADMIN)) {
				user.setBranchPermissionType(BranchPermissionTypeEnum.ALL_BRANCHES);
			} else if (user.getType().equals(UserTypeEnum.EMPLOYEE)) {
				user.setBranchPermissionType(BranchPermissionTypeEnum.ONE_BRANCH);
				// in case of create new employee,select default employee roles
				if (user.getId() == null && selectedRolesList.size() == 0) {
					for (RoleCategoryDTO roleCategoryDTO : roleCategoriesList) {
						if (roleCategoryDTO.getCategory() == RoleCategoryEnum.EMPLOYEE) {
							selectedRolesList.addAll(roleCategoryDTO.getRolesList());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Exception in init userTypeChanged", e);
		}
	}

	private void loadRoles() {
		List<Role> rolesList = roleRepository.findByHiddenFalse();
		for (Role role : rolesList) {
			log.info("####### processing role: " + role.getName() + ",category: " + role.getCategory());
			RoleCategoryDTO roleCategoryDTO = new RoleCategoryDTO(role.getCategory());
			if (roleCategoriesList.contains(roleCategoryDTO)) {
				log.info("####### category exist: " + role.getCategory());
				roleCategoryDTO = roleCategoriesList.get(roleCategoriesList.indexOf(roleCategoryDTO));
				log.info("####### roles in existing category: " + roleCategoryDTO.getRolesList().size());
				roleCategoryDTO.getRolesList().add(role);
			} else {
				log.info("####### category new: " + role.getCategory());
				roleCategoryDTO.getRolesList().add(role);
				roleCategoriesList.add(roleCategoryDTO);
			}
		}

		for (RoleCategoryDTO roleCategoryDTO : roleCategoriesList) {
			SelectItemGroup group = new SelectItemGroup(roleCategoryDTO.getCategory().getLabel());
			group.setSelectItems(convertRolesToSelectItems(roleCategoryDTO.getRolesList()));
			roleCategoriesItemsList.add(group);
		}

	}

	private SelectItem[] convertRolesToSelectItems(List<Role> rolesList) {
		List<SelectItem> itemsList = new ArrayList<SelectItem>();
		for (Role role : rolesList) {
			itemsList.add(new SelectItem(role, role.getDescription()));
		}
		return itemsList.stream().toArray(SelectItem[]::new);
	}

	public void hideUserTab() {
		log.info("######## hideUserTab ##########");
		renderUserTab = false;
	}

	public void emarahChanged() {
		try {
			log.info("######## emarahChanged,user.getEmarah(): " + user.getEmarah() + ",user.getEmarahId(): "
					+ user.getEmarahId());
			citiesList.clear();
			branchesList.clear();
			regionsList.clear();
			locationsList.clear();
			if (user.getEmarah() != null) {
				if (currentUserBean.isAdmin()) {
					citiesList = cityRepository.findByEmarahIdAndActiveTrueOrderByNameArabic(user.getEmarah().getId());
				} else if (currentUserBean.isEmployee()) {
					City loggedInUserCity = cityRepository.getOne(currentUserBean.getUser().getCityId());
					citiesList.add(loggedInUserCity);
				}
				if (citiesList.size() > 0) {
					user.setCity(citiesList.get(0));
					cityChanged();
				}
			} else {
				log.info("######## countryChanged,user.getEmarah() is null ########");
			}
			log.info("######## UserManagementBean > emarahChanged,citiesList: " + citiesList.size());
		} catch (Exception e) {
			log.error("Exception in UserManagementBean > emarahChanged", e);
		}
	}

	public void cityChanged() {
		try {
			log.info("######## cityChanged,user.getCity(): " + user.getCity());
			branchesList.clear();
			regionsList.clear();
			locationsList.clear();
			if (user.getCity() != null) {
				if (currentUserBean.isAdmin()) {
					branchesList = branchRepository.findByCityIdOrderByNameArabic(user.getCity().getId());
				} else if (currentUserBean.isEmployee()) {
					Branch loggedInUserBranch = branchRepository.getOne(currentUserBean.getUser().getBranchId());
					branchesList.add(loggedInUserBranch);
				}
				if (user.getId() == null
						|| (user.getBranch() != null && user.getBranch().getId() != Constants.ALL_BRANCHES_ID)) {
					branchesList.removeIf(branchLoop -> branchLoop.getId() == Constants.ALL_BRANCHES_ID);
				}
				regionsList = regionRepository.findByCityIdAndActiveTrueOrderByNameArabic(user.getCity().getId());
				if (regionsList.size() > 0) {
					user.setRegion(regionsList.get(0));
					regionChanged();
				}
			} else {
				log.info("######## cityChanged,user.getCity() is null ########");
			}
			log.info("######## UserManagementBean > cityChanged,branchesList: " + branchesList.size() + ",regionsList: "
					+ regionsList + ",locationsList: " + locationsList.size());
		} catch (Exception e) {
			log.error("Exception in UserManagementBean > cityChanged", e);
		}
	}

	public void regionChanged() {
		try {
			log.info("######## regionChanged ##########");
			locationsList.clear();
			if (user.getRegion() != null) {
				locationsList = locationRepository
						.findByRegionIdAndActiveTrueOrderByNameArabic(user.getRegion().getId());
			} else {
				log.info("######## regionChanged,user.getRegion() is null ########");
			}
			log.info("######## UserManagementBean > regionChanged,locationsList: " + locationsList.size());
		} catch (Exception e) {
			log.error("Exception in UserManagementBean > regionChanged", e);
		}
	}

	public void setSelectedUser(User user) {
		boolean isDelegate = user.getType() == UserTypeEnum.DELEGATE;
		boolean isEmployee = user.getType() == UserTypeEnum.EMPLOYEE;
		user = userService.loadUserData(user);
		selectedRolesList = new ArrayList<Role>();
		log.info("######## setSelectedUser,user: " + user.getId());
		log.info("######## setSelectedUser,user.getCustody(): " + user.getCustody());
		log.info("######## setSelectedUser,user.getRoles(): " + user.getRoles().size() + ",selectedRolesList: "
				+ selectedRolesList.size());
		if (isEmployee) {
			log.info("######## processing all userRoles for employee #########");
			for (UserRole userRole : user.getRoles()) {
				log.info("######## processing userRole: " + userRole.getId());
				if (!userRole.getRole().isHidden())
					selectedRolesList.add(userRole.getRole());
			}
			log.info("######## end of processing all userRoles for employee,selectedRolesList: "
					+ selectedRolesList.size());
		}
		if (isDelegate && user.getCustody() == null) {
			user.setCustody(new Custody());
		}
		this.user = user;
		emarahChanged();
		editMode = true;
		renderUserTab = true;
		if (user.getStartDate() != null && user.getStartDate().before(new Date())) {
			this.minDate = user.getStartDate();
		} else {
			minDate = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);
		}
		if (isDelegate) {
			PrimeFaces.current().scrollTo("form:addNewDelegateButton");
		} else {
			PrimeFaces.current().scrollTo("form:addNewEmployeeButton");
		}
	}

	public void addNewEmployeeButtonClicked() {
		log.info("######## addNewEmployeeButtonClicked #########");
		editMode = false;
		renderUserTab = true;
		user = new User();
		if (emarahDefault == null && emarahDefaultId > 0) {
			Optional<Emarah> result = emarahRepository.findById(emarahDefaultId);
			if (result.isPresent()) {
				emarahDefault = result.get();
			}
		}
		if (emarahList.size() > 0) {
			if (emarahDefault != null)
				user.setEmarah(emarahDefault);
			else
				user.setEmarah(emarahList.get(0));
		}
		emarahChanged();
		minDate = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);
		user.setCreator(currentUserBean.getUser());
		user.setType(UserTypeEnum.EMPLOYEE);
		selectedRolesList = new ArrayList<Role>();
		userTypeChanged();
		user.setUserName("emp" + (employeesList.size() + 1));
		PrimeFaces.current().scrollTo("form:addNewEmployeeButton");
		PrimeFaces.current().focus("form:employeeName");
	}

	public void addNewDelegateButtonClicked() {
		editMode = false;
		renderUserTab = true;
		user = new User();
		if (emarahDefault == null && emarahDefaultId > 0) {
			Optional<Emarah> result = emarahRepository.findById(emarahDefaultId);
			if (result.isPresent()) {
				emarahDefault = result.get();
			}
		}
		if (emarahList.size() > 0) {
			if (emarahDefault != null)
				user.setEmarah(emarahDefault);
			else
				user.setEmarah(emarahList.get(0));
		}
		emarahChanged();
		minDate = DateUtils.truncate(new Date(), java.util.Calendar.DAY_OF_MONTH);
		user.setCreator(currentUserBean.getUser());
		user.setUserName("user" + (delegatesList.size() + 1));
		PrimeFaces.current().scrollTo("form:addNewDelegateButton");
		PrimeFaces.current().focus("form:tabs:delegateName");
	}

	@Transactional(rollbackFor = Exception.class)
	public void createOrUpdateUser() {

		boolean isDelegate = user.getType() == UserTypeEnum.DELEGATE;

		if (!isDelegate) {

			if (selectedRolesList.size() == 0) {
				GeneralUtils.addErrorMessage("يرجى اختيار صلاحيات الموظف", null);
				return;
			}

			log.info("####### selectedRolesList: " + selectedRolesList.size());

			for (Role role : selectedRolesList) {
				log.info("####### SELECTED ROLE: " + role.getDescription());
			}
		} else {
			// delegate validation
			if (!isValidDelegate()) {
				return;
			}

		}

		if (!editMode) {
			createNewUser();
		} else {
			updateUser();
		}

		if (!FacesContext.getCurrentInstance().isValidationFailed())
			resetUserData(isDelegate);

	}

	private boolean isValidDelegate() {

		if (user.getCountry() == null) {
			GeneralUtils.addErrorMessage("يرجى اختيار دولة المندوب", null);
			return false;
		}

		if (user.getEmarah() == null) {
			GeneralUtils.addErrorMessage("يرجى اختيار إمارة المندوب", null);
			return false;
		}

		if (user.getCity() == null) {
			GeneralUtils.addErrorMessage("يرجى اختيار مدينة المندوب", null);
			return false;
		}

		if (user.getRegion() == null) {
			GeneralUtils.addErrorMessage("يرجى اختيار منطقة المندوب", null);
			return false;
		}

		if (user.getLocation() == null) {
			GeneralUtils.addErrorMessage("يرجى اختيار مكان المندوب", null);
			return false;
		}

		if (user.getBranch() == null) {
			GeneralUtils.addErrorMessage("يرجى اختيار فرع المندوب", null);
			return false;
		}

		return true;
	}

	private void resetUserData(boolean isDelegate) {

		user = new User();
		selectedRolesList = new ArrayList<Role>();
		user.setCreator(currentUserBean.getUser());
		if (isDelegate) {
			user.setUserName("user" + (delegatesList.size() + 1));
		} else {
			user.setUserName("emp" + (employeesList.size() + 1));
			user.setType(UserTypeEnum.EMPLOYEE);
			userTypeChanged();
		}
		if (emarahDefault != null)
			user.setEmarah(emarahDefault);
		else
			user.setEmarah(emarahList.get(0));
		emarahChanged();
		activeTab = 0;
		renderUserTab = false;

	}

	private void updateUser() {
		try {
			log.info("######## updateUser,user: " + user.getId() + ",type: " + user.getType());

			boolean isDelegate = user.getType() == UserTypeEnum.DELEGATE;
			boolean isEmployee = user.getType() == UserTypeEnum.EMPLOYEE;
			user.setPasswordDisplay(user.getPasswordDisplay().trim());
			user.setLastModifier(currentUserBean.getUser());
			user.setDateLastModified(new Date());

			if (isDelegate) {
				if (user.getCustody().getId() == null) {
					if (isCustodyDataEntered()) {
						log.info("######## NEW CUSTODY WILL BE CREATED FOR USER: " + user.getId());
						user.getCustody().setResponsibleUser(user);
						user.getCustody().setCreator(currentUserBean.getUser());
						user.getCustody().setDateCreated(new Date());
					} else {
						log.info("######## NOOOOOOO CUSTODY WILL BE CREATED FOR USER: " + user.getId());
						user.setCustody(null);
					}
				} else {
					log.info("######## CUSTODY WILL BE UPDATED FOR USER: " + user.getId());
				}
			}

			boolean passwordChanged = isPasswordChanged();
			log.info("######## passwordChanged: " + passwordChanged);
			if (passwordChanged) {
				user.setAttribute1(Encryptor.encrypt(user.getPasswordDisplay()));
				user.setPassword(new BCryptPasswordEncoder().encode(user.getPasswordDisplay()));
			}

			userRepository.save(user);

			if (isEmployee) {
				setEmployeeRoles();
				userRepository.save(user);
				employeesList.clear();
				filteredEmployeesList.clear();

				if (currentUserBean.isAdmin()
						|| currentUserBean.hasRoleForAllBranches(RoleTypeEnum.ROLE_MANAGE_EMPLOYEES)) {
					employeesList = userService.findAllEmployees();
				} else if (currentUserBean.hasRole(RoleTypeEnum.ROLE_MANAGE_EMPLOYEES)) {
					// we should hide admin user from employees
					employeesList = userService.findAllEmployeesInBranch(currentUserBean.getUser().getBranchId());
				}
				filteredEmployeesList.addAll(employeesList);
			} else {
				// reload updated user from db into the dataTable
				log.info("######### update delegates dataTable ##########");
				delegatesList.clear();
				filteredDelegatesList.clear();

				if (currentUserBean.isAdmin()
						|| currentUserBean.hasRoleForAllBranches(RoleTypeEnum.ROLE_MANAGE_DELEGATES)) {
					delegatesList = userService.findAllDelegates(true);
				} else if (currentUserBean.hasRole(RoleTypeEnum.ROLE_MANAGE_DELEGATES)) {
					delegatesList = userService.findAllDelegatesInBranch(currentUserBean.getUser().getBranchId(), true);
				}
				filteredDelegatesList.addAll(delegatesList);
			}

			if (isDelegate)
				GeneralUtils.showDialogInfo("تم تحديث بيانات المندوب بنجاح");
			else
				GeneralUtils.showDialogInfo("تم تحديث بيانات الموظف بنجاح");

			editMode = false;
		} catch (Exception e) {
			log.error("Exception updateUser", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private boolean isPasswordChanged() {
		if (StringUtils.isNotBlank(user.getPasswordDisplay())) {
			String oldPassword = Encryptor.decrypt(user.getAttribute1());
			log.info("######### user old password is: " + oldPassword + ",new password: " + user.getPasswordDisplay());
			return !oldPassword.equals(user.getPasswordDisplay());
		} else
			return false;
	}

	private void createNewUser() {
		try {
			log.info("######## createNewUser,user.getType(): " + user.getType());
			if (userRepository.countByUserNameIgnoreCase(user.getUserName()) > 0) {
				GeneralUtils.addErrorMessage("اسم المستخدم موجود مسبقا", null);
				return;
			}
			boolean isDelegate = user.getType() == UserTypeEnum.DELEGATE;
			if (StringUtils.isNotBlank(user.getPasswordDisplay())) {
				user.setAttribute1(Encryptor.encrypt(user.getPasswordDisplay()));
				user.setPassword(new BCryptPasswordEncoder().encode(user.getPasswordDisplay()));
			}
			if (isDelegate) {
				addRole(user, RoleTypeEnum.ROLE_DELEGATE.getId(), null);
			} else if (user.getType() == UserTypeEnum.ADMIN) {
				addRole(user, RoleTypeEnum.ROLE_ADMIN.getId(), null);
			} else if (user.getType() == UserTypeEnum.EMPLOYEE) {
				setEmployeeRoles();
			}
			user.getUserDetails().setUser(user);

			if (isDelegate) {
				if (isCustodyDataEntered()) {
					log.info("######## NEW CUSTODY WILL BE CREATED FOR THE NEW USER ###########");
					user.getCustody().setResponsibleUser(user);
					user.getCustody().setCreator(currentUserBean.getUser());
					user.getCustody().setDateCreated(new Date());
				} else {
					user.setCustody(null);
				}
			} else {
				user.setCustody(null);
			}

			for (UserRole userRole : user.getRoles()) {
				log.info("####### ROLE: " + userRole.getRole().getId());
			}

			user.setLastModifier(currentUserBean.getUser());
			user.setDateLastModified(new Date());

			userRepository.save(user);

			if (isDelegate) {
				delegatesList.add(user);
				filteredDelegatesList.add(user);
				GeneralUtils.showDialogInfo("تم اضافة المندوب بنجاح");
			} else {
				employeesList.add(user);
				filteredEmployeesList.add(user);
				GeneralUtils.showDialogInfo("تم اضافة الموظف بنجاح");
			}

		} catch (Exception e) {
			log.error("Exception createNewUser", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public String formatDateTime(Date date) {
		return GeneralUtils.formatDateTime(date);
	}

	private void setEmployeeRoles() {
		log.info("######### setEmployeeRoles ##########");
		if (user.getId() != null) {
			// removeAllRoles and flush in case of update
			removeAllRoles(user);
			log.info("######### after removeAllRoles##########");
			user = userRepository.getOne(user.getId());
		}
		Branch branch = null;
		log.info("###### user.getBranchPermissionType(): " + user.getBranchPermissionType());
		if (user.getBranchPermissionType() == BranchPermissionTypeEnum.ONE_BRANCH) {
			log.info("###### user.getBranchPermissionType() is one branch ######");
			branch = user.getBranch();
		}
		addRole(user, RoleTypeEnum.ROLE_EMPLOYEE.getId(), branch);
		for (Role roleLoop : selectedRolesList) {
			addRole(user, roleLoop.getId(), branch);
		}
		log.info("######### setEmployeeRoles finished ##########");
	}

	private void addRole(User user, Long roleId, Branch branch) {
		Role role = roleRepository.getOne(roleId);
		if (branch == null) {
			log.info("####### BRANCH IS NULL WHEN ADDING ROLE ##########");
			branch = branchRepository.getOne(Constants.ALL_BRANCHES_ID);
		} else {
			branch = branchRepository.getOne(branch.getId());
		}
		log.info("###### ADDING ROLE: " + role.getDescription() + ",FOR USER: " + user.getId() + ",BRANCH: "
				+ user.getBranch().getId());
		UserRole userRole = new UserRole(user, role);
		userRole.setCreator(currentUserBean.getUser());
		userRole.setBranch(branch);
		user.getRoles().add(userRole);
		role.getUserRoles().add(userRole);
	}

	private void removeAllRoles(User user) {
		log.info("######## removeAllRoles, user.getRoles(): " + user.getRoles().size() + ",user: " + user);
		for (Iterator<UserRole> iterator = user.getRoles().iterator(); iterator.hasNext();) {
			UserRole userRole = iterator.next();
			log.info("######## removeAllRoles,processing userRole: " + userRole.getRole().getDescription()
					+ ",userRole.getUser(): " + userRole.getUser());
			if (userRole.getUser().getId().equals(user.getId())) {
				log.info("######## removeAllRoles,removing userRole: " + userRole.getId() + ",userRole.getUser(): "
						+ userRole.getUser());
				iterator.remove();
			}
		}
		userRepository.saveAndFlush(user);
		log.info("######## removeAllRoles END, user.getRoles(): " + user.getRoles().size() + ",user: " + user);
	}

	private void removeRole(User user, Role role) {
		for (Iterator<UserRole> iterator = user.getRoles().iterator(); iterator.hasNext();) {
			UserRole userRole = iterator.next();

			if (userRole.getUser().equals(user) && userRole.getRole().equals(role)) {
				iterator.remove();
				userRole.getRole().getUserRoles().remove(userRole);
				userRole.setUser(null);
				userRole.setUser(null);
			}
		}
	}

	public void tabChange() {
		try {
			log.info("######## tabChange,user: " + user);
		} catch (Exception e) {
			log.error("Exception tabChange", e);
		}
	}

	private boolean isCustodyDataEntered() {
		Custody custody = user.getCustody();
		if (StringUtils.isNotBlank(custody.getCardNumber()) || StringUtils.isNotBlank(custody.getDeviceNumber())
				|| StringUtils.isNotBlank(custody.getDeviceType()) || StringUtils.isNotBlank(custody.getSerialNumber())
				|| StringUtils.isNotBlank(custody.getSimNumber())
				|| StringUtils.isNotBlank(custody.getManualReceiptsBookNumber())
				|| StringUtils.isNotBlank(custody.getManualReceiptsBookNumberStart())
				|| StringUtils.isNotBlank(custody.getManualReceiptsBookNumberEnd())
				|| StringUtils.isNotBlank(custody.getElectronicReceiptsBookNumber())
				|| StringUtils.isNotBlank(custody.getElectronicReceiptsBookNumberStart())
				|| StringUtils.isNotBlank(custody.getElectronicReceiptsBookNumberEnd())
				|| StringUtils.isNotBlank(custody.getNotes())) {
			return true;
		} else {
			return false;
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getSelectedRoleId() {
		return selectedRoleId;
	}

	public void setSelectedRoleId(Long selectedRoleId) {
		this.selectedRoleId = selectedRoleId;
	}

	public List<User> getDelegatesList() {
		return delegatesList;
	}

	public void setDelegatesList(List<User> delegatesList) {
		this.delegatesList = delegatesList;
	}

	public List<User> getEmployeesList() {
		return employeesList;
	}

	public void setEmployeesList(List<User> employeesList) {
		this.employeesList = employeesList;
	}

	public List<Country> getCountriesList() {
		return countriesList;
	}

	public void setCountriesList(List<Country> countriesList) {
		this.countriesList = countriesList;
	}

	public List<City> getCitiesList() {
		return citiesList;
	}

	public void setCitiesList(List<City> citiesList) {
		this.citiesList = citiesList;
	}

	public List<Branch> getBranchesList() {
		return branchesList;
	}

	public void setBranchesList(List<Branch> branchesList) {
		this.branchesList = branchesList;
	}

	public List<User> getFilteredDelegatesList() {
		return filteredDelegatesList;
	}

	public void setFilteredDelegatesList(List<User> filteredDelegatesList) {
		this.filteredDelegatesList = filteredDelegatesList;
	}

	public List<User> getFilteredEmployeesList() {
		return filteredEmployeesList;
	}

	public void setFilteredEmployeesList(List<User> filteredEmployeesList) {
		this.filteredEmployeesList = filteredEmployeesList;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public boolean isRenderUserTab() {
		return renderUserTab;
	}

	public void setRenderUserTab(boolean renderUserTab) {
		this.renderUserTab = renderUserTab;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public int getActiveTab() {
		return activeTab;
	}

	public void setActiveTab(int activeTab) {
		this.activeTab = activeTab;
	}

	public List<RoleCategoryDTO> getRoleCategoriesList() {
		return roleCategoriesList;
	}

	public void setRoleCategoriesList(List<RoleCategoryDTO> roleCategoriesList) {
		this.roleCategoriesList = roleCategoriesList;
	}

	public List<SelectItem> getRoleCategoriesItemsList() {
		return roleCategoriesItemsList;
	}

	public void setRoleCategoriesItemsList(List<SelectItem> roleCategoriesItemsList) {
		this.roleCategoriesItemsList = roleCategoriesItemsList;
	}

	public List<Role> getSelectedRolesList() {
		return selectedRolesList;
	}

	public void setSelectedRolesList(List<Role> selectedRolesList) {
		this.selectedRolesList = selectedRolesList;
	}

	public List<Region> getRegionsList() {
		return regionsList;
	}

	public void setRegionsList(List<Region> regionsList) {
		this.regionsList = regionsList;
	}

	public List<Location> getLocationsList() {
		return locationsList;
	}

	public void setLocationsList(List<Location> locationsList) {
		this.locationsList = locationsList;
	}

	public List<Emarah> getEmarahList() {
		return emarahList;
	}

	public void setEmarahList(List<Emarah> emarahList) {
		this.emarahList = emarahList;
	}

	public long getEmarahDefaultId() {
		return emarahDefaultId;
	}

	public void setEmarahDefaultId(long emarahDefaultId) {
		this.emarahDefaultId = emarahDefaultId;
	}

	public Emarah getEmarahDefault() {
		return emarahDefault;
	}

	public void setEmarahDefault(Emarah emarahDefault) {
		this.emarahDefault = emarahDefault;
	}

}
