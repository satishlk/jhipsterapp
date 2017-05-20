package com.parking.web.rest;

import com.parking.Ionic2SampleApp;

import com.parking.domain.Location;
import com.parking.repository.LocationRepository;
import com.parking.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.parking.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LocationResource REST controller.
 *
 * @see LocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ionic2SampleApp.class)
public class LocationResourceIntTest {

    private static final Long DEFAULT_LOCATION_ID = 1L;
    private static final Long UPDATED_LOCATION_ID = 2L;

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_CONTACT_NO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATION = false;
    private static final Boolean UPDATED_ACTIVATION = true;

    private static final Long DEFAULT_VENDOR_ID = 1L;
    private static final Long UPDATED_VENDOR_ID = 2L;

    private static final ZonedDateTime DEFAULT_ADD_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ADD_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ADD_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ADD_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLocationMockMvc;

    private Location location;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocationResource locationResource = new LocationResource(locationRepository);
        this.restLocationMockMvc = MockMvcBuilders.standaloneSetup(locationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Location createEntity(EntityManager em) {
        Location location = new Location()
            .locationId(DEFAULT_LOCATION_ID)
            .locationName(DEFAULT_LOCATION_NAME)
            .locationAddress(DEFAULT_LOCATION_ADDRESS)
            .locationContactNo(DEFAULT_LOCATION_CONTACT_NO)
            .activation(DEFAULT_ACTIVATION)
            .vendorId(DEFAULT_VENDOR_ID)
            .addDatetime(DEFAULT_ADD_DATETIME)
            .addUserId(DEFAULT_ADD_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID);
        return location;
    }

    @Before
    public void initTest() {
        location = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocation() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // Create the Location
        restLocationMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isCreated());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate + 1);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getLocationId()).isEqualTo(DEFAULT_LOCATION_ID);
        assertThat(testLocation.getLocationName()).isEqualTo(DEFAULT_LOCATION_NAME);
        assertThat(testLocation.getLocationAddress()).isEqualTo(DEFAULT_LOCATION_ADDRESS);
        assertThat(testLocation.getLocationContactNo()).isEqualTo(DEFAULT_LOCATION_CONTACT_NO);
        assertThat(testLocation.isActivation()).isEqualTo(DEFAULT_ACTIVATION);
        assertThat(testLocation.getVendorId()).isEqualTo(DEFAULT_VENDOR_ID);
        assertThat(testLocation.getAddDatetime()).isEqualTo(DEFAULT_ADD_DATETIME);
        assertThat(testLocation.getAddUserId()).isEqualTo(DEFAULT_ADD_USER_ID);
        assertThat(testLocation.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
        assertThat(testLocation.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
    }

    @Test
    @Transactional
    public void createLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = locationRepository.findAll().size();

        // Create the Location with an existing ID
        location.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLocationMockMvc.perform(post("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLocations() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get all the locationList
        restLocationMockMvc.perform(get("/api/locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(location.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationId").value(hasItem(DEFAULT_LOCATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME.toString())))
            .andExpect(jsonPath("$.[*].locationAddress").value(hasItem(DEFAULT_LOCATION_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].locationContactNo").value(hasItem(DEFAULT_LOCATION_CONTACT_NO.toString())))
            .andExpect(jsonPath("$.[*].activation").value(hasItem(DEFAULT_ACTIVATION.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorId").value(hasItem(DEFAULT_VENDOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].addDatetime").value(hasItem(sameInstant(DEFAULT_ADD_DATETIME))))
            .andExpect(jsonPath("$.[*].addUserId").value(hasItem(DEFAULT_ADD_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())));
    }

    @Test
    @Transactional
    public void getLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);

        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", location.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(location.getId().intValue()))
            .andExpect(jsonPath("$.locationId").value(DEFAULT_LOCATION_ID.intValue()))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME.toString()))
            .andExpect(jsonPath("$.locationAddress").value(DEFAULT_LOCATION_ADDRESS.toString()))
            .andExpect(jsonPath("$.locationContactNo").value(DEFAULT_LOCATION_CONTACT_NO.toString()))
            .andExpect(jsonPath("$.activation").value(DEFAULT_ACTIVATION.booleanValue()))
            .andExpect(jsonPath("$.vendorId").value(DEFAULT_VENDOR_ID.intValue()))
            .andExpect(jsonPath("$.addDatetime").value(sameInstant(DEFAULT_ADD_DATETIME)))
            .andExpect(jsonPath("$.addUserId").value(DEFAULT_ADD_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocation() throws Exception {
        // Get the location
        restLocationMockMvc.perform(get("/api/locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Update the location
        Location updatedLocation = locationRepository.findOne(location.getId());
        updatedLocation
            .locationId(UPDATED_LOCATION_ID)
            .locationName(UPDATED_LOCATION_NAME)
            .locationAddress(UPDATED_LOCATION_ADDRESS)
            .locationContactNo(UPDATED_LOCATION_CONTACT_NO)
            .activation(UPDATED_ACTIVATION)
            .vendorId(UPDATED_VENDOR_ID)
            .addDatetime(UPDATED_ADD_DATETIME)
            .addUserId(UPDATED_ADD_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID);

        restLocationMockMvc.perform(put("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLocation)))
            .andExpect(status().isOk());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate);
        Location testLocation = locationList.get(locationList.size() - 1);
        assertThat(testLocation.getLocationId()).isEqualTo(UPDATED_LOCATION_ID);
        assertThat(testLocation.getLocationName()).isEqualTo(UPDATED_LOCATION_NAME);
        assertThat(testLocation.getLocationAddress()).isEqualTo(UPDATED_LOCATION_ADDRESS);
        assertThat(testLocation.getLocationContactNo()).isEqualTo(UPDATED_LOCATION_CONTACT_NO);
        assertThat(testLocation.isActivation()).isEqualTo(UPDATED_ACTIVATION);
        assertThat(testLocation.getVendorId()).isEqualTo(UPDATED_VENDOR_ID);
        assertThat(testLocation.getAddDatetime()).isEqualTo(UPDATED_ADD_DATETIME);
        assertThat(testLocation.getAddUserId()).isEqualTo(UPDATED_ADD_USER_ID);
        assertThat(testLocation.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
        assertThat(testLocation.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingLocation() throws Exception {
        int databaseSizeBeforeUpdate = locationRepository.findAll().size();

        // Create the Location

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLocationMockMvc.perform(put("/api/locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(location)))
            .andExpect(status().isCreated());

        // Validate the Location in the database
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLocation() throws Exception {
        // Initialize the database
        locationRepository.saveAndFlush(location);
        int databaseSizeBeforeDelete = locationRepository.findAll().size();

        // Get the location
        restLocationMockMvc.perform(delete("/api/locations/{id}", location.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Location> locationList = locationRepository.findAll();
        assertThat(locationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Location.class);
    }
}
