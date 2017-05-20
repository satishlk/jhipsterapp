package com.parking.web.rest;

import com.parking.Ionic2SampleApp;

import com.parking.domain.ParkingHistory;
import com.parking.repository.ParkingHistoryRepository;
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
 * Test class for the ParkingHistoryResource REST controller.
 *
 * @see ParkingHistoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ionic2SampleApp.class)
public class ParkingHistoryResourceIntTest {

    private static final Long DEFAULT_PARKING_ID = 1L;
    private static final Long UPDATED_PARKING_ID = 2L;

    private static final String DEFAULT_VEHICLE_NO = "AAAAAAAAAA";
    private static final String UPDATED_VEHICLE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_INTIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INTIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_OUT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_OUT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_IN_USER = "AAAAAAAAAA";
    private static final String UPDATED_IN_USER = "BBBBBBBBBB";

    private static final String DEFAULT_OUT_USER = "AAAAAAAAAA";
    private static final String UPDATED_OUT_USER = "BBBBBBBBBB";

    private static final Long DEFAULT_COST = 1L;
    private static final Long UPDATED_COST = 2L;

    @Autowired
    private ParkingHistoryRepository parkingHistoryRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restParkingHistoryMockMvc;

    private ParkingHistory parkingHistory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ParkingHistoryResource parkingHistoryResource = new ParkingHistoryResource(parkingHistoryRepository);
        this.restParkingHistoryMockMvc = MockMvcBuilders.standaloneSetup(parkingHistoryResource)
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
    public static ParkingHistory createEntity(EntityManager em) {
        ParkingHistory parkingHistory = new ParkingHistory()
            .parkingId(DEFAULT_PARKING_ID)
            .vehicleNo(DEFAULT_VEHICLE_NO)
            .contactNo(DEFAULT_CONTACT_NO)
            .intime(DEFAULT_INTIME)
            .outTime(DEFAULT_OUT_TIME)
            .inUser(DEFAULT_IN_USER)
            .outUser(DEFAULT_OUT_USER)
            .cost(DEFAULT_COST);
        return parkingHistory;
    }

    @Before
    public void initTest() {
        parkingHistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createParkingHistory() throws Exception {
        int databaseSizeBeforeCreate = parkingHistoryRepository.findAll().size();

        // Create the ParkingHistory
        restParkingHistoryMockMvc.perform(post("/api/parking-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parkingHistory)))
            .andExpect(status().isCreated());

        // Validate the ParkingHistory in the database
        List<ParkingHistory> parkingHistoryList = parkingHistoryRepository.findAll();
        assertThat(parkingHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        ParkingHistory testParkingHistory = parkingHistoryList.get(parkingHistoryList.size() - 1);
        assertThat(testParkingHistory.getParkingId()).isEqualTo(DEFAULT_PARKING_ID);
        assertThat(testParkingHistory.getVehicleNo()).isEqualTo(DEFAULT_VEHICLE_NO);
        assertThat(testParkingHistory.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testParkingHistory.getIntime()).isEqualTo(DEFAULT_INTIME);
        assertThat(testParkingHistory.getOutTime()).isEqualTo(DEFAULT_OUT_TIME);
        assertThat(testParkingHistory.getInUser()).isEqualTo(DEFAULT_IN_USER);
        assertThat(testParkingHistory.getOutUser()).isEqualTo(DEFAULT_OUT_USER);
        assertThat(testParkingHistory.getCost()).isEqualTo(DEFAULT_COST);
    }

    @Test
    @Transactional
    public void createParkingHistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = parkingHistoryRepository.findAll().size();

        // Create the ParkingHistory with an existing ID
        parkingHistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restParkingHistoryMockMvc.perform(post("/api/parking-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parkingHistory)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ParkingHistory> parkingHistoryList = parkingHistoryRepository.findAll();
        assertThat(parkingHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllParkingHistories() throws Exception {
        // Initialize the database
        parkingHistoryRepository.saveAndFlush(parkingHistory);

        // Get all the parkingHistoryList
        restParkingHistoryMockMvc.perform(get("/api/parking-histories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(parkingHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].parkingId").value(hasItem(DEFAULT_PARKING_ID.intValue())))
            .andExpect(jsonPath("$.[*].vehicleNo").value(hasItem(DEFAULT_VEHICLE_NO.toString())))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.toString())))
            .andExpect(jsonPath("$.[*].intime").value(hasItem(sameInstant(DEFAULT_INTIME))))
            .andExpect(jsonPath("$.[*].outTime").value(hasItem(sameInstant(DEFAULT_OUT_TIME))))
            .andExpect(jsonPath("$.[*].inUser").value(hasItem(DEFAULT_IN_USER.toString())))
            .andExpect(jsonPath("$.[*].outUser").value(hasItem(DEFAULT_OUT_USER.toString())))
            .andExpect(jsonPath("$.[*].cost").value(hasItem(DEFAULT_COST.intValue())));
    }

    @Test
    @Transactional
    public void getParkingHistory() throws Exception {
        // Initialize the database
        parkingHistoryRepository.saveAndFlush(parkingHistory);

        // Get the parkingHistory
        restParkingHistoryMockMvc.perform(get("/api/parking-histories/{id}", parkingHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(parkingHistory.getId().intValue()))
            .andExpect(jsonPath("$.parkingId").value(DEFAULT_PARKING_ID.intValue()))
            .andExpect(jsonPath("$.vehicleNo").value(DEFAULT_VEHICLE_NO.toString()))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO.toString()))
            .andExpect(jsonPath("$.intime").value(sameInstant(DEFAULT_INTIME)))
            .andExpect(jsonPath("$.outTime").value(sameInstant(DEFAULT_OUT_TIME)))
            .andExpect(jsonPath("$.inUser").value(DEFAULT_IN_USER.toString()))
            .andExpect(jsonPath("$.outUser").value(DEFAULT_OUT_USER.toString()))
            .andExpect(jsonPath("$.cost").value(DEFAULT_COST.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingParkingHistory() throws Exception {
        // Get the parkingHistory
        restParkingHistoryMockMvc.perform(get("/api/parking-histories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParkingHistory() throws Exception {
        // Initialize the database
        parkingHistoryRepository.saveAndFlush(parkingHistory);
        int databaseSizeBeforeUpdate = parkingHistoryRepository.findAll().size();

        // Update the parkingHistory
        ParkingHistory updatedParkingHistory = parkingHistoryRepository.findOne(parkingHistory.getId());
        updatedParkingHistory
            .parkingId(UPDATED_PARKING_ID)
            .vehicleNo(UPDATED_VEHICLE_NO)
            .contactNo(UPDATED_CONTACT_NO)
            .intime(UPDATED_INTIME)
            .outTime(UPDATED_OUT_TIME)
            .inUser(UPDATED_IN_USER)
            .outUser(UPDATED_OUT_USER)
            .cost(UPDATED_COST);

        restParkingHistoryMockMvc.perform(put("/api/parking-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedParkingHistory)))
            .andExpect(status().isOk());

        // Validate the ParkingHistory in the database
        List<ParkingHistory> parkingHistoryList = parkingHistoryRepository.findAll();
        assertThat(parkingHistoryList).hasSize(databaseSizeBeforeUpdate);
        ParkingHistory testParkingHistory = parkingHistoryList.get(parkingHistoryList.size() - 1);
        assertThat(testParkingHistory.getParkingId()).isEqualTo(UPDATED_PARKING_ID);
        assertThat(testParkingHistory.getVehicleNo()).isEqualTo(UPDATED_VEHICLE_NO);
        assertThat(testParkingHistory.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testParkingHistory.getIntime()).isEqualTo(UPDATED_INTIME);
        assertThat(testParkingHistory.getOutTime()).isEqualTo(UPDATED_OUT_TIME);
        assertThat(testParkingHistory.getInUser()).isEqualTo(UPDATED_IN_USER);
        assertThat(testParkingHistory.getOutUser()).isEqualTo(UPDATED_OUT_USER);
        assertThat(testParkingHistory.getCost()).isEqualTo(UPDATED_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingParkingHistory() throws Exception {
        int databaseSizeBeforeUpdate = parkingHistoryRepository.findAll().size();

        // Create the ParkingHistory

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restParkingHistoryMockMvc.perform(put("/api/parking-histories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(parkingHistory)))
            .andExpect(status().isCreated());

        // Validate the ParkingHistory in the database
        List<ParkingHistory> parkingHistoryList = parkingHistoryRepository.findAll();
        assertThat(parkingHistoryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteParkingHistory() throws Exception {
        // Initialize the database
        parkingHistoryRepository.saveAndFlush(parkingHistory);
        int databaseSizeBeforeDelete = parkingHistoryRepository.findAll().size();

        // Get the parkingHistory
        restParkingHistoryMockMvc.perform(delete("/api/parking-histories/{id}", parkingHistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ParkingHistory> parkingHistoryList = parkingHistoryRepository.findAll();
        assertThat(parkingHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ParkingHistory.class);
    }
}
