package com.charity.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.charity.dao.BranchRepository;
import com.charity.dao.RoleRepository;
import com.charity.dao.UserRepository;
import com.charity.entities.Branch;
import com.charity.entities.City;
import com.charity.entities.Country;
import com.charity.entities.Emarah;
import com.charity.entities.Location;
import com.charity.entities.Region;
import com.charity.entities.Role;
import com.charity.entities.User;
import com.charity.entities.UserRole;
import com.charity.enums.GenderTypeEnum;
import com.charity.enums.RoleTypeEnum;
import com.charity.enums.UserTypeEnum;
import com.charity.util.Encryptor;
import com.charity.util.GeneralUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CurrentUserDetailsService userDetailsService;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RoleRepository roleRepository;

	public List<User> findAllDelegates(boolean loadDetails) {
		List<User> list = userRepository.findByTypeOrderByUserOrder(UserTypeEnum.DELEGATE);
		if (loadDetails) {
			for (User user : list) {
				initializeDelegateData(user);
			}
		}
		return list;
	}

	public List<User> findAllBenefactors() {
		List<User> list = userRepository.findByTypeOrderByDateCreatedDesc(UserTypeEnum.BENEFACTOR);
		for (User user : list) {
			initializeBenefactorData(user);
		}
		return list;
	}

	public List<User> findAllDelegatesInBranch(Long branchId, boolean loadDetails) {
		List<User> list = userRepository.findByTypeAndBranchIdOrderByUserOrder(UserTypeEnum.DELEGATE, branchId);
		if (loadDetails) {
			for (User user : list) {
				initializeDelegateData(user);
			}
		}
		return list;
	}

	public List<User> findAllEmployeesInBranch(Long branchId) {
		List<UserTypeEnum> types = new ArrayList<UserTypeEnum>();
		types.add(UserTypeEnum.EMPLOYEE);
		List<User> list = userRepository.findByBranchIdAndTypeIn(branchId, types);
		for (User user : list) {
			initializeEmployeeData(user);
		}
		return list;
	}

	public List<User> findAllEmployees() {
		List<UserTypeEnum> types = new ArrayList<UserTypeEnum>();
		types.add(UserTypeEnum.EMPLOYEE);
		types.add(UserTypeEnum.ADMIN);
		List<User> list = userRepository.findByTypeIn(types);
		for (User user : list) {
			initializeEmployeeData(user);
		}
		return list;
	}

	public User loadUserData(User user) {
		Optional<User> result = userRepository.findById(user.getId());
		if (result.isPresent()) {
			user = result.get();
		}
		boolean isDelegate = user.getType() == UserTypeEnum.DELEGATE;
		if (isDelegate)
			initializeDelegateData(user);
		else
			initializeEmployeeData(user);
		return user;
	}

	public User findById(Long id) {
		Optional<User> result = userRepository.findById(id);
		if (result.isPresent()) {
			return result.get();
		} else
			return null;
	}

	private void initializeBenefactorData(User user) {
		if (user == null)
			return;
		Hibernate.initialize(user.getCity());
		Hibernate.initialize(user.getRegion());
		Hibernate.initialize(user.getLocation());
		Hibernate.initialize(user.getCreator());

	}

	private void initializeDelegateData(User user) {
		if (user == null)
			return;
		Hibernate.initialize(user.getEmarah());
		Hibernate.initialize(user.getCity());
		Hibernate.initialize(user.getBranch());
		Hibernate.initialize(user.getRegion());
		Hibernate.initialize(user.getLocation());
		Hibernate.initialize(user.getUserDetails());
		Hibernate.initialize(user.getCustody());
		Hibernate.initialize(user.getRoles());
		Hibernate.initialize(user.getLastModifier());
		if (user.getUserDetails() == null) {
			user.setUserDetails(new com.charity.entities.UserDetails());
		}
		if (StringUtils.isNotBlank(user.getAttribute1())) {
			user.setPasswordDisplay(Encryptor.decrypt(user.getAttribute1()));
		}
	}

	private void initializeEmployeeData(User user) {
		if (user == null)
			return;
		Hibernate.initialize(user.getEmarah());
		Hibernate.initialize(user.getCity());
		Hibernate.initialize(user.getBranch());
		Hibernate.initialize(user.getRegion());
		Hibernate.initialize(user.getLocation());
		Hibernate.initialize(user.getUserDetails());
		Hibernate.initialize(user.getCustody());
		Hibernate.initialize(user.getRoles());
		Hibernate.initialize(user.getLastModifier());
		if (user.getUserDetails() == null) {
			user.setUserDetails(new com.charity.entities.UserDetails());
		}
		if (StringUtils.isNotBlank(user.getAttribute1())) {
			user.setPasswordDisplay(Encryptor.decrypt(user.getAttribute1()));
		}
	}

	public User findByUserNameIgnoreCase(String username) {
		User user = userRepository.findByUserNameIgnoreCase(username);
		if (user != null) {
			Hibernate.initialize(user.getRoles());
		}
		return user;
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public User createNewBenefactor(String name, String mobileNumber, GenderTypeEnum genderType, Date birthDate,
			User delegate, User adminUser) {

		log.info("########### createNewBenefactor,name: " + name + ",mobileNumber: " + mobileNumber + ",creator: "
				+ delegate + ",adminUser: " + adminUser);

		if (StringUtils.isBlank(name) || StringUtils.isBlank(mobileNumber)) {
			log.info("########## unable to createNewBenefactor either name or mobileNumber are empty #########");
			return null;
		}

		if (delegate == null || delegate.getId() == null || delegate.getId() <= 0) {
			log.info("########## unable to createNewBenefactor delegate is empty #########");
			return null;
		}

		/*
		 * List<User> usersList = userRepository
		 * .findByTypeAndEnabledTrueAndDisplayNameAndMobileNumber(UserTypeEnum.
		 * BENEFACTOR, name, mobileNumber); if (usersList.size() > 0) { return
		 * usersList.get(0); }
		 */

		mobileNumber = mobileNumber.trim();
		name = name.trim();
		if (mobileNumber.startsWith("971")) {
			mobileNumber = mobileNumber.substring(3);
		}

		Branch branch = branchRepository.findById(delegate.getBranchId()).get();

		User user = new User(UserTypeEnum.BENEFACTOR);
		user.setAccountNonLocked(false); // make account locked to disable user from login
		user.setGenderType(genderType);
		if (birthDate != null)
			user.setBirthDate(birthDate);
		user.setUserName(GeneralUtils.generateRandomUserName());
		user.setMobileNumber(mobileNumber);
		if (user.getCityId() != null && user.getCityId() > 0) {
			user.setCity(new City(user.getCityId()));
		}
		user.setDisplayName(name);

		// set country/city/region/location to same data of delegate
		user.setCountry(new Country(delegate.getCountryId()));
		user.setEmarah(new Emarah(delegate.getEmarahId()));
		user.setCity(new City(delegate.getCityId()));
		user.setRegion(new Region(delegate.getRegionId()));
		user.setLocation(new Location(delegate.getLocationId()));

		user.setCreator(delegate);
		user.setLastModifier(delegate);
		user.setDateLastModified(new Date());
		user.setBranch(branch);
		user.setCreatorAdmin(adminUser);
		Role role = roleRepository.findById(RoleTypeEnum.ROLE_BENEFACTOR.getId()).get();
		user.getRoles().add(new UserRole(user, role, branch));
		userRepository.save(user);
		user.setDisplayNameAutoComplete(user.getMobileNumber());

		return user;
	}

	public boolean isAuthenticated(String userName, String password) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, password, userDetails.getAuthorities());
		Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		return authentication.isAuthenticated();
	}

	public User authenticate(String userName, String password) {
		boolean authenticated = isAuthenticated(userName, password);
		log.info("###### isAuthenticated: " + authenticated + ",userName: " + userName + ",password: " + password);
		if (isAuthenticated(userName, password)) {
			return userRepository.findByUserNameIgnoreCase(userName);
		} else
			return null;
	}

	public boolean isUserHasRole(RoleTypeEnum roleTypeEnum, User user) {
		for (UserRole userRole : user.getRoles()) {
			if (userRole.getRole().getName().equalsIgnoreCase(roleTypeEnum.getName()))
				return true;
		}
		return false;
	}

}
