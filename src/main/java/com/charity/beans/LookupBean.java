package com.charity.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.charity.dao.AccountantRepository;
import com.charity.dao.BranchRepository;
import com.charity.dao.CityRepository;
import com.charity.dao.EmarahRepository;
import com.charity.dao.LocationRepository;
import com.charity.dao.RegionRepository;
import com.charity.entities.Accountant;
import com.charity.entities.Branch;
import com.charity.entities.City;
import com.charity.entities.Country;
import com.charity.entities.Emarah;
import com.charity.entities.Location;
import com.charity.entities.Region;
import com.charity.enums.RoleTypeEnum;
import com.charity.service.CacheService;
import com.charity.util.Constants;
import com.charity.util.GeneralUtils;

@Component("lookupBean")
@Scope("view")
public class LookupBean implements Serializable {

	private static final long serialVersionUID = 1952444616365727783L;

	private static final Logger log = LoggerFactory.getLogger(LookupBean.class);

	@Autowired
	private CurrentUserBean currentUserBean;

	@Autowired
	private CacheService cacheService;

	@Autowired
	private EmarahRepository emarahRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private BranchRepository branchRepository;

	@Autowired
	private AccountantRepository accountantRepository;

	private List<Emarah> emarahList = new ArrayList<Emarah>();

	private List<Emarah> filteredEmarahList = new ArrayList<Emarah>();

	private List<City> cityList = new ArrayList<City>();

	private List<City> filteredCityList = new ArrayList<City>();

	private List<Region> regionList = new ArrayList<Region>();

	private List<Region> filteredRegionList = new ArrayList<Region>();

	private List<Location> locationList = new ArrayList<Location>();

	private List<Location> filteredLocationList = new ArrayList<Location>();

	private List<Branch> branchList = new ArrayList<Branch>();

	private List<Branch> filteredBranchList = new ArrayList<Branch>();

	private List<Accountant> accountantList = new ArrayList<Accountant>();

	private List<Accountant> filteredAccountantList = new ArrayList<Accountant>();

	private Emarah emarah = new Emarah();

	private City city = new City();

	private Region region = new Region();

	private Location location = new Location();

	private Branch branch = new Branch();

	private Accountant accountant = new Accountant();

	private boolean renderForm;

	private boolean editMode;

	private long emarahDefaultId = Constants.DEFAULT_EMARAH_ID;

	private Emarah emarahDefault;

	@PostConstruct
	public void init() {
		try {
			log.info("######## init LookupBean,url: " + GeneralUtils.getHttpServletRequest().getRequestURI());
			if (currentUserBean.isAdmin() || currentUserBean.hasRole(RoleTypeEnum.ROLE_MANAGE_SETTINGS)) {
				emarahList = emarahRepository.findAllEmarahsOrderByName();
				// if we have default emarah remove it from emarah list and add it as first
				// element
				if (emarahDefaultId > 0) {
					Optional<Emarah> result = emarahRepository.findById(emarahDefaultId);
					if (result.isPresent()) {
						emarahDefault = result.get();
						for (Iterator<Emarah> iterator = emarahList.iterator(); iterator.hasNext();) {
							Emarah emarahLoop = iterator.next();
							if (emarahLoop.getId().longValue() == emarahDefaultId) {
								iterator.remove();
							}
						}
						emarahList.add(0, emarahDefault);
					}
				}
				filteredEmarahList.addAll(emarahList);
				log.info("######### emarahList: " + emarahList.size());
			}
		} catch (Exception e) {
			log.error("Exception in init LookupBean", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public String formatDateTime(Date date) {
		return GeneralUtils.formatDateTime(date);
	}

	public void emarahChanged(Emarah emarah) {
		try {
			this.emarah = emarah;
			this.city = new City();
			this.region = new Region();
			this.location = new Location();
			cityList.clear();
			filteredCityList.clear();
			regionList.clear();
			filteredRegionList.clear();
			locationList.clear();
			filteredLocationList.clear();
			branchList.clear();
			filteredBranchList.clear();
			log.info("######## emarahChanged,id: " + emarah.getId());
			if (emarah != null && emarah.getId() != null) {
				cityList = cityRepository.findByEmarahIdOrderByNameArabic(emarah.getId());
				filteredCityList.addAll(cityList);
				log.info("######## emarahChanged,cityList: " + cityList.size());
			}
			PrimeFaces.current().scrollTo("form:cityTableContainer");
		} catch (Exception e) {
			log.error("Exception in emarahChanged", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void cityChanged(City city) {
		try {
			this.city = city;
			this.region = new Region();
			this.location = new Location();
			regionList.clear();
			filteredRegionList.clear();
			locationList.clear();
			filteredLocationList.clear();
			branchList.clear();
			filteredBranchList.clear();
			log.info("######## cityChanged,id: " + city.getId());
			if (city != null && city.getId() != null) {
				regionList = regionRepository.findByCityIdOrderByNameArabic(city.getId());
				filteredRegionList.addAll(regionList);
				log.info("######## cityChanged,regionList: " + regionList.size());
			}
			PrimeFaces.current().scrollTo("form:regionTableContainer");
		} catch (Exception e) {
			log.error("Exception in cityChanged", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void regionChanged(Region region) {
		try {

			this.region = region;
			locationList.clear();
			filteredLocationList.clear();

			log.info("######## regionChanged,id: " + region.getId());

			if (region != null && region.getId() != null) {
				locationList = locationRepository.findByRegionIdOrderByNameArabic(region.getId());
				filteredLocationList.addAll(locationList);
				log.info("######## regionChanged,locationList: " + locationList.size());
			}
			PrimeFaces.current().scrollTo("form:locationTableContainer");
		} catch (Exception e) {
			log.error("Exception in regionChanged", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void addNewEmarahButtonClicked() {
		emarah = new Emarah();
		emarah.setCreator(currentUserBean.getUser());
		emarah.setCountry(new Country(Constants.UAE_COUNTRY_ID));
	}

	public void addNewCityButtonClicked() {
		city = new City();
		city.setCreator(currentUserBean.getUser());
		city.setEmarah(emarah);
	}

	public void addNewRegionButtonClicked() {
		region = new Region();
		region.setCreator(currentUserBean.getUser());
		region.setCity(city);
	}

	public void addNewLocationButtonClicked() {
		location = new Location();
		location.setCreator(currentUserBean.getUser());
		location.setRegion(region);
	}

	public void createOrUpdateEmarah() {

		if (emarah.getId() == null || emarah.getId() <= 0) {
			createNewEmarah();
		} else {
			updateEmarah();
		}

		emarah = new Emarah();
		emarah.setCreator(currentUserBean.getUser());
		emarah.setCountry(new Country(Constants.UAE_COUNTRY_ID));

	}

	private void createNewEmarah() {
		try {
			log.info("######## createNewEmarah,emarah: " + emarah);
			emarah.setDateLastModified(new Date());
			emarah.setLastModifier(currentUserBean.getUser());
			emarahRepository.save(emarah);
			emarahList.add(emarah);
			filteredEmarahList.add(emarah);
			GeneralUtils.showDialogInfo("تم اضافة الإمارة بنجاح");
		} catch (Exception e) {
			log.error("Exception in createNewEmarah", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private void updateEmarah() {
		try {
			log.info("######## updateEmarah,emarah: " + emarah);
			emarah.setLastModifier(currentUserBean.getUser());
			emarah.setDateLastModified(new Date());
			emarahRepository.save(emarah);
			GeneralUtils.showDialogInfo("تم تحديث الإمارة بنجاح");
		} catch (Exception e) {
			log.error("Exception in updateEmarah", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void createOrUpdateCity() {

		if (city.getId() == null || city.getId() <= 0) {
			createNewCity();
		} else {
			updateCity();
		}

		city = new City();
		city.setCreator(currentUserBean.getUser());
		city.setEmarah(emarah);

	}

	private void createNewCity() {
		try {
			log.info("######## createNewCity,emarah: " + emarah);
			city.setDateLastModified(new Date());
			city.setLastModifier(currentUserBean.getUser());
			cityRepository.save(city);
			cityList.add(city);
			filteredCityList.add(city);
			GeneralUtils.showDialogInfo("تم اضافة المدينة بنجاح");
		} catch (Exception e) {
			log.error("Exception in createNewCity", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private void updateCity() {
		try {
			log.info("######## updateCity,city: " + city);
			city.setLastModifier(currentUserBean.getUser());
			city.setDateLastModified(new Date());
			cityRepository.save(city);
			GeneralUtils.showDialogInfo("تم تحديث المدينة بنجاح");
		} catch (Exception e) {
			log.error("Exception in updateCity", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void createOrUpdateRegion() {

		if (region.getId() == null || region.getId() <= 0) {
			createNewRegion();
		} else {
			updateRegion();
		}

		region = new Region();
		region.setCreator(currentUserBean.getUser());
		region.setCity(city);
	}

	private void createNewRegion() {
		try {
			log.info("######## createNewRegion,region: " + region);
			region.setDateLastModified(new Date());
			region.setLastModifier(currentUserBean.getUser());
			regionRepository.save(region);
			regionList.add(region);
			filteredRegionList.add(region);
			GeneralUtils.showDialogInfo("تم اضافة المنطقة بنجاح");
		} catch (Exception e) {
			log.error("Exception in createNewRegion", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private void updateRegion() {
		try {
			log.info("######## updateRegion,region: " + region);
			region.setLastModifier(currentUserBean.getUser());
			region.setDateLastModified(new Date());
			regionRepository.save(region);
			GeneralUtils.showDialogInfo("تم تحديث المنطقة بنجاح");
		} catch (Exception e) {
			log.error("Exception in updateRegion", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public void createOrUpdateLocation() {

		if (location.getId() == null || location.getId() <= 0) {
			createNewLocation();
		} else {
			updateLocation();
		}

		location = new Location();
		location.setCreator(currentUserBean.getUser());
		location.setRegion(region);

	}

	private void createNewLocation() {
		try {
			log.info("######## createNewLocation,location: " + location);
			location.setDateLastModified(new Date());
			location.setLastModifier(currentUserBean.getUser());
			locationRepository.save(location);
			locationList.add(location);
			filteredLocationList.add(location);
			GeneralUtils.showDialogInfo("تم اضافة الموقع بنجاح");
		} catch (Exception e) {
			log.error("Exception in createNewLocation", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	private void updateLocation() {
		try {
			log.info("######## updateLocation,location: " + location);
			location.setLastModifier(currentUserBean.getUser());
			location.setDateLastModified(new Date());
			locationRepository.save(location);
			GeneralUtils.showDialogInfo("تم تحديث الموقع بنجاح");
		} catch (Exception e) {
			log.error("Exception in updateLocation", e);
			GeneralUtils.showDialogError("حدث خطأ فى النظام...يرجى المحاولة مرة أخرى" + "<BR/>"
					+ " واذا استمرت المشكلة يرجى الاتصال بالدعم الفني");
		}
	}

	public List<Emarah> getEmarahList() {
		return emarahList;
	}

	public void setEmarahList(List<Emarah> emarahList) {
		this.emarahList = emarahList;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

	public List<Region> getRegionList() {
		return regionList;
	}

	public void setRegionList(List<Region> regionList) {
		this.regionList = regionList;
	}

	public List<Location> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<Location> locationList) {
		this.locationList = locationList;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}

	public List<Accountant> getAccountantList() {
		return accountantList;
	}

	public void setAccountantList(List<Accountant> accountantList) {
		this.accountantList = accountantList;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
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

	public Accountant getAccountant() {
		return accountant;
	}

	public void setAccountant(Accountant accountant) {
		this.accountant = accountant;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public List<City> getFilteredCityList() {
		return filteredCityList;
	}

	public void setFilteredCityList(List<City> filteredCityList) {
		this.filteredCityList = filteredCityList;
	}

	public List<Region> getFilteredRegionList() {
		return filteredRegionList;
	}

	public void setFilteredRegionList(List<Region> filteredRegionList) {
		this.filteredRegionList = filteredRegionList;
	}

	public List<Location> getFilteredLocationList() {
		return filteredLocationList;
	}

	public void setFilteredLocationList(List<Location> filteredLocationList) {
		this.filteredLocationList = filteredLocationList;
	}

	public List<Branch> getFilteredBranchList() {
		return filteredBranchList;
	}

	public void setFilteredBranchList(List<Branch> filteredBranchList) {
		this.filteredBranchList = filteredBranchList;
	}

	public List<Accountant> getFilteredAccountantList() {
		return filteredAccountantList;
	}

	public void setFilteredAccountantList(List<Accountant> filteredAccountantList) {
		this.filteredAccountantList = filteredAccountantList;
	}

	public boolean isRenderForm() {
		return renderForm;
	}

	public void setRenderForm(boolean renderForm) {
		this.renderForm = renderForm;
	}

	public boolean isEditMode() {
		return editMode;
	}

	public void setEditMode(boolean editMode) {
		this.editMode = editMode;
	}

	public Emarah getEmarah() {
		return emarah;
	}

	public void setEmarah(Emarah emarah) {
		this.emarah = emarah;
	}

	public List<Emarah> getFilteredEmarahList() {
		return filteredEmarahList;
	}

	public void setFilteredEmarahList(List<Emarah> filteredEmarahList) {
		this.filteredEmarahList = filteredEmarahList;
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
