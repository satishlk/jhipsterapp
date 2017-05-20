package com.parking.web.rest;

import com.parking.Ionic2SampleApp;

import com.parking.domain.Vehicles;
import com.parking.repository.VehiclesRepository;
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
 * Test class for the VehiclesResource REST controller.
 *
 * @see VehiclesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ionic2SampleApp.class)
public class VehiclesResourceIntTest {

    private static final Long DEFAULT_VEHICLE_ID = 1L;
    private static final Long UPDATED_VEHICLE_ID = 2L;

    private static final String DEFAULT_VEHICLES_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLES_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VEHICLES_DESCRTION = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLES_DESCRTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ADD_DATETIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ADD_DATETIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_ADD_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_ADD_USER_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_UPDATE_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPDATE_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_UPDATE_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_UPDATE_USER_ID = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVATION = false;
    private static final Boolean UPDATED_ACTIVATION = true;

    @Autowired
    private VehiclesRepository vehiclesRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehiclesMockMvc;

    private Vehicles vehicles;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VehiclesResource vehiclesResource = new VehiclesResource(vehiclesRepository);
        this.restVehiclesMockMvc = MockMvcBuilders.standaloneSetup(vehiclesResource)
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
    public static Vehicles createEntity(EntityManager em) {
        Vehicles vehicles = new Vehicles()
            .vehicleId(DEFAULT_VEHICLE_ID)
            .vehiclesType(DEFAULT_VEHICLES_TYPE)
            .vehiclesDescrtion(DEFAULT_VEHICLES_DESCRTION)
            .addDatetime(DEFAULT_ADD_DATETIME)
            .addUserId(DEFAULT_ADD_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .activation(DEFAULT_ACTIVATION);
        return vehicles;
    }

    @Before
    public void initTest() {
        vehicles = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicles() throws Exception {
        int databaseSizeBeforeCreate = vehiclesRepository.findAll().size();

        // Create the Vehicles
        restVehiclesMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicles)))
            .andExpect(status().isCreated());

        // Validate the Vehicles in the database
        List<Vehicles> vehiclesList = vehiclesRepository.findAll();
        assertThat(vehiclesList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicles testVehicles = vehiclesList.get(vehiclesList.size() - 1);
        assertThat(testVehicles.getVehicleId()).isEqualTo(DEFAULT_VEHICLE_ID);
        assertThat(testVehicles.getVehiclesType()).isEqualTo(DEFAULT_VEHICLES_TYPE);
        assertThat(testVehicles.getVehiclesDescrtion()).isEqualTo(DEFAULT_VEHICLES_DESCRTION);
        assertThat(testVehicles.getAddDatetime()).isEqualTo(DEFAULT_ADD_DATETIME);
        assertThat(testVehicles.getAddUserId()).isEqualTo(DEFAULT_ADD_USER_ID);
        assertThat(testVehicles.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
        assertThat(testVehicles.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testVehicles.isActivation()).isEqualTo(DEFAULT_ACTIVATION);
    }

    @Test
    @Transactional
    public void createVehiclesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehiclesRepository.findAll().size();

        // Create the Vehicles with an existing ID
        vehicles.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehiclesMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicles)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Vehicles> vehiclesList = vehiclesRepository.findAll();
        assertThat(vehiclesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehicles() throws Exception {
        // Initialize the database
        vehiclesRepository.saveAndFlush(vehicles);

        // Get all the vehiclesList
        restVehiclesMockMvc.perform(get("/api/vehicles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicles.getId().intValue())))
            .andExpect(jsonPath("$.[*].vehicleId").value(hasItem(DEFAULT_VEHICLE_ID.intValue())))
            .andExpect(jsonPath("$.[*].vehiclesType").value(hasItem(DEFAULT_VEHICLES_TYPE.toString())))
            .andExpect(jsonPath("$.[*].vehiclesDescrtion").value(hasItem(DEFAULT_VEHICLES_DESCRTION.toString())))
            .andExpect(jsonPath("$.[*].addDatetime").value(hasItem(sameInstant(DEFAULT_ADD_DATETIME))))
            .andExpect(jsonPath("$.[*].addUserId").value(hasItem(DEFAULT_ADD_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].activation").value(hasItem(DEFAULT_ACTIVATION.booleanValue())));
    }

    @Test
    @Transactional
    public void getVehicles() throws Exception {
        // Initialize the database
        vehiclesRepository.saveAndFlush(vehicles);

        // Get the vehicles
        restVehiclesMockMvc.perform(get("/api/vehicles/{id}", vehicles.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicles.getId().intValue()))
            .andExpect(jsonPath("$.vehicleId").value(DEFAULT_VEHICLE_ID.intValue()))
            .andExpect(jsonPath("$.vehiclesType").value(DEFAULT_VEHICLES_TYPE.toString()))
            .andExpect(jsonPath("$.vehiclesDescrtion").value(DEFAULT_VEHICLES_DESCRTION.toString()))
            .andExpect(jsonPath("$.addDatetime").value(sameInstant(DEFAULT_ADD_DATETIME)))
            .andExpect(jsonPath("$.addUserId").value(DEFAULT_ADD_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.activation").value(DEFAULT_ACTIVATION.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingVehicles() throws Exception {
        // Get the vehicles
        restVehiclesMockMvc.perform(get("/api/vehicles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicles() throws Exception {
        // Initialize the database
        vehiclesRepository.saveAndFlush(vehicles);
        int databaseSizeBeforeUpdate = vehiclesRepository.findAll().size();

        // Update the vehicles
        Vehicles updatedVehicles = vehiclesRepository.findOne(vehicles.getId());
        updatedVehicles
            .vehicleId(UPDATED_VEHICLE_ID)
            .vehiclesType(UPDATED_VEHICLES_TYPE)
            .vehiclesDescrtion(UPDATED_VEHICLES_DESCRTION)
            .addDatetime(UPDATED_ADD_DATETIME)
            .addUserId(UPDATED_ADD_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .activation(UPDATED_ACTIVATION);

        restVehiclesMockMvc.perform(put("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVehicles)))
            .andExpect(status().isOk());

        // Validate the Vehicles in the database
        List<Vehicles> vehiclesList = vehiclesRepository.findAll();
        assertThat(vehiclesList).hasSize(databaseSizeBeforeUpdate);
        Vehicles testVehicles = vehiclesList.get(vehiclesList.size() - 1);
        assertThat(testVehicles.getVehicleId()).isEqualTo(UPDATED_VEHICLE_ID);
        assertThat(testVehicles.getVehiclesType()).isEqualTo(UPDATED_VEHICLES_TYPE);
        assertThat(testVehicles.getVehiclesDescrtion()).isEqualTo(UPDATED_VEHICLES_DESCRTION);
        assertThat(testVehicles.getAddDatetime()).isEqualTo(UPDATED_ADD_DATETIME);
        assertThat(testVehicles.getAddUserId()).isEqualTo(UPDATED_ADD_USER_ID);
        assertThat(testVehicles.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
        assertThat(testVehicles.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testVehicles.isActivation()).isEqualTo(UPDATED_ACTIVATION);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicles() throws Exception {
        int databaseSizeBeforeUpdate = vehiclesRepository.findAll().size();

        // Create the Vehicles

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehiclesMockMvc.perform(put("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicles)))
            .andExpect(status().isCreated());

        // Validate the Vehicles in the database
        List<Vehicles> vehiclesList = vehiclesRepository.findAll();
        assertThat(vehiclesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehicles() throws Exception {
        // Initialize the database
        vehiclesRepository.saveAndFlush(vehicles);
        int databaseSizeBeforeDelete = vehiclesRepository.findAll().size();

        // Get the vehicles
        restVehiclesMockMvc.perform(delete("/api/vehicles/{id}", vehicles.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vehicles> vehiclesList = vehiclesRepository.findAll();
        assertThat(vehiclesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vehicles.class);
    }
}
