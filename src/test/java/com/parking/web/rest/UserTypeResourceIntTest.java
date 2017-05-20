package com.parking.web.rest;

import com.parking.Ionic2SampleApp;

import com.parking.domain.UserType;
import com.parking.repository.UserTypeRepository;
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
 * Test class for the UserTypeResource REST controller.
 *
 * @see UserTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ionic2SampleApp.class)
public class UserTypeResourceIntTest {

    private static final String DEFAULT_USER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_USER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_USER_DESCRPTION = "AAAAAAAAAA";
    private static final String UPDATED_USER_DESCRPTION = "BBBBBBBBBB";

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
    private UserTypeRepository userTypeRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserTypeMockMvc;

    private UserType userType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UserTypeResource userTypeResource = new UserTypeResource(userTypeRepository);
        this.restUserTypeMockMvc = MockMvcBuilders.standaloneSetup(userTypeResource)
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
    public static UserType createEntity(EntityManager em) {
        UserType userType = new UserType()
            .userType(DEFAULT_USER_TYPE)
            .userDescrption(DEFAULT_USER_DESCRPTION)
            .addDatetime(DEFAULT_ADD_DATETIME)
            .addUserId(DEFAULT_ADD_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .activation(DEFAULT_ACTIVATION);
        return userType;
    }

    @Before
    public void initTest() {
        userType = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserType() throws Exception {
        int databaseSizeBeforeCreate = userTypeRepository.findAll().size();

        // Create the UserType
        restUserTypeMockMvc.perform(post("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userType)))
            .andExpect(status().isCreated());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeCreate + 1);
        UserType testUserType = userTypeList.get(userTypeList.size() - 1);
        assertThat(testUserType.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testUserType.getUserDescrption()).isEqualTo(DEFAULT_USER_DESCRPTION);
        assertThat(testUserType.getAddDatetime()).isEqualTo(DEFAULT_ADD_DATETIME);
        assertThat(testUserType.getAddUserId()).isEqualTo(DEFAULT_ADD_USER_ID);
        assertThat(testUserType.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
        assertThat(testUserType.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testUserType.isActivation()).isEqualTo(DEFAULT_ACTIVATION);
    }

    @Test
    @Transactional
    public void createUserTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userTypeRepository.findAll().size();

        // Create the UserType with an existing ID
        userType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserTypeMockMvc.perform(post("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userType)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserTypes() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        // Get all the userTypeList
        restUserTypeMockMvc.perform(get("/api/user-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userType.getId().intValue())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].userDescrption").value(hasItem(DEFAULT_USER_DESCRPTION.toString())))
            .andExpect(jsonPath("$.[*].addDatetime").value(hasItem(sameInstant(DEFAULT_ADD_DATETIME))))
            .andExpect(jsonPath("$.[*].addUserId").value(hasItem(DEFAULT_ADD_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].activation").value(hasItem(DEFAULT_ACTIVATION.booleanValue())));
    }

    @Test
    @Transactional
    public void getUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        // Get the userType
        restUserTypeMockMvc.perform(get("/api/user-types/{id}", userType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userType.getId().intValue()))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()))
            .andExpect(jsonPath("$.userDescrption").value(DEFAULT_USER_DESCRPTION.toString()))
            .andExpect(jsonPath("$.addDatetime").value(sameInstant(DEFAULT_ADD_DATETIME)))
            .andExpect(jsonPath("$.addUserId").value(DEFAULT_ADD_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.activation").value(DEFAULT_ACTIVATION.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserType() throws Exception {
        // Get the userType
        restUserTypeMockMvc.perform(get("/api/user-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);
        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();

        // Update the userType
        UserType updatedUserType = userTypeRepository.findOne(userType.getId());
        updatedUserType
            .userType(UPDATED_USER_TYPE)
            .userDescrption(UPDATED_USER_DESCRPTION)
            .addDatetime(UPDATED_ADD_DATETIME)
            .addUserId(UPDATED_ADD_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .activation(UPDATED_ACTIVATION);

        restUserTypeMockMvc.perform(put("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserType)))
            .andExpect(status().isOk());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
        UserType testUserType = userTypeList.get(userTypeList.size() - 1);
        assertThat(testUserType.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testUserType.getUserDescrption()).isEqualTo(UPDATED_USER_DESCRPTION);
        assertThat(testUserType.getAddDatetime()).isEqualTo(UPDATED_ADD_DATETIME);
        assertThat(testUserType.getAddUserId()).isEqualTo(UPDATED_ADD_USER_ID);
        assertThat(testUserType.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
        assertThat(testUserType.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testUserType.isActivation()).isEqualTo(UPDATED_ACTIVATION);
    }

    @Test
    @Transactional
    public void updateNonExistingUserType() throws Exception {
        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();

        // Create the UserType

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserTypeMockMvc.perform(put("/api/user-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userType)))
            .andExpect(status().isCreated());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);
        int databaseSizeBeforeDelete = userTypeRepository.findAll().size();

        // Get the userType
        restUserTypeMockMvc.perform(delete("/api/user-types/{id}", userType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserType.class);
    }
}
