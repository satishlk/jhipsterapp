package com.parking.web.rest;

import com.parking.Ionic2SampleApp;

import com.parking.domain.Users;
import com.parking.repository.UsersRepository;
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
 * Test class for the UsersResource REST controller.
 *
 * @see UsersResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ionic2SampleApp.class)
public class UsersResourceIntTest {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final String DEFAULT_FULL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FULL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_NO = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NO = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Integer DEFAULT_PINCODE = 1;
    private static final Integer UPDATED_PINCODE = 2;

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

    private static final Long DEFAULT_PARENT = 1L;
    private static final Long UPDATED_PARENT = 2L;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUsersMockMvc;

    private Users users;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        UsersResource usersResource = new UsersResource(usersRepository);
        this.restUsersMockMvc = MockMvcBuilders.standaloneSetup(usersResource)
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
    public static Users createEntity(EntityManager em) {
        Users users = new Users()
            .userId(DEFAULT_USER_ID)
            .fullName(DEFAULT_FULL_NAME)
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD)
            .email(DEFAULT_EMAIL)
            .contactNo(DEFAULT_CONTACT_NO)
            .address(DEFAULT_ADDRESS)
            .pincode(DEFAULT_PINCODE)
            .activation(DEFAULT_ACTIVATION)
            .vendorId(DEFAULT_VENDOR_ID)
            .addDatetime(DEFAULT_ADD_DATETIME)
            .addUserId(DEFAULT_ADD_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .parent(DEFAULT_PARENT);
        return users;
    }

    @Before
    public void initTest() {
        users = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsers() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();

        // Create the Users
        restUsersMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(users)))
            .andExpect(status().isCreated());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate + 1);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUsers.getFullName()).isEqualTo(DEFAULT_FULL_NAME);
        assertThat(testUsers.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testUsers.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testUsers.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUsers.getContactNo()).isEqualTo(DEFAULT_CONTACT_NO);
        assertThat(testUsers.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testUsers.getPincode()).isEqualTo(DEFAULT_PINCODE);
        assertThat(testUsers.isActivation()).isEqualTo(DEFAULT_ACTIVATION);
        assertThat(testUsers.getVendorId()).isEqualTo(DEFAULT_VENDOR_ID);
        assertThat(testUsers.getAddDatetime()).isEqualTo(DEFAULT_ADD_DATETIME);
        assertThat(testUsers.getAddUserId()).isEqualTo(DEFAULT_ADD_USER_ID);
        assertThat(testUsers.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
        assertThat(testUsers.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testUsers.getParent()).isEqualTo(DEFAULT_PARENT);
    }

    @Test
    @Transactional
    public void createUsersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usersRepository.findAll().size();

        // Create the Users with an existing ID
        users.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsersMockMvc.perform(post("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(users)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get all the usersList
        restUsersMockMvc.perform(get("/api/users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(users.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].fullName").value(hasItem(DEFAULT_FULL_NAME.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].contactNo").value(hasItem(DEFAULT_CONTACT_NO.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].pincode").value(hasItem(DEFAULT_PINCODE)))
            .andExpect(jsonPath("$.[*].activation").value(hasItem(DEFAULT_ACTIVATION.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorId").value(hasItem(DEFAULT_VENDOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].addDatetime").value(hasItem(sameInstant(DEFAULT_ADD_DATETIME))))
            .andExpect(jsonPath("$.[*].addUserId").value(hasItem(DEFAULT_ADD_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].parent").value(hasItem(DEFAULT_PARENT.intValue())));
    }

    @Test
    @Transactional
    public void getUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);

        // Get the users
        restUsersMockMvc.perform(get("/api/users/{id}", users.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(users.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.fullName").value(DEFAULT_FULL_NAME.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.contactNo").value(DEFAULT_CONTACT_NO.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.pincode").value(DEFAULT_PINCODE))
            .andExpect(jsonPath("$.activation").value(DEFAULT_ACTIVATION.booleanValue()))
            .andExpect(jsonPath("$.vendorId").value(DEFAULT_VENDOR_ID.intValue()))
            .andExpect(jsonPath("$.addDatetime").value(sameInstant(DEFAULT_ADD_DATETIME)))
            .andExpect(jsonPath("$.addUserId").value(DEFAULT_ADD_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.parent").value(DEFAULT_PARENT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUsers() throws Exception {
        // Get the users
        restUsersMockMvc.perform(get("/api/users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Update the users
        Users updatedUsers = usersRepository.findOne(users.getId());
        updatedUsers
            .userId(UPDATED_USER_ID)
            .fullName(UPDATED_FULL_NAME)
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD)
            .email(UPDATED_EMAIL)
            .contactNo(UPDATED_CONTACT_NO)
            .address(UPDATED_ADDRESS)
            .pincode(UPDATED_PINCODE)
            .activation(UPDATED_ACTIVATION)
            .vendorId(UPDATED_VENDOR_ID)
            .addDatetime(UPDATED_ADD_DATETIME)
            .addUserId(UPDATED_ADD_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .parent(UPDATED_PARENT);

        restUsersMockMvc.perform(put("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsers)))
            .andExpect(status().isOk());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate);
        Users testUsers = usersList.get(usersList.size() - 1);
        assertThat(testUsers.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUsers.getFullName()).isEqualTo(UPDATED_FULL_NAME);
        assertThat(testUsers.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testUsers.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testUsers.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUsers.getContactNo()).isEqualTo(UPDATED_CONTACT_NO);
        assertThat(testUsers.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testUsers.getPincode()).isEqualTo(UPDATED_PINCODE);
        assertThat(testUsers.isActivation()).isEqualTo(UPDATED_ACTIVATION);
        assertThat(testUsers.getVendorId()).isEqualTo(UPDATED_VENDOR_ID);
        assertThat(testUsers.getAddDatetime()).isEqualTo(UPDATED_ADD_DATETIME);
        assertThat(testUsers.getAddUserId()).isEqualTo(UPDATED_ADD_USER_ID);
        assertThat(testUsers.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
        assertThat(testUsers.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testUsers.getParent()).isEqualTo(UPDATED_PARENT);
    }

    @Test
    @Transactional
    public void updateNonExistingUsers() throws Exception {
        int databaseSizeBeforeUpdate = usersRepository.findAll().size();

        // Create the Users

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUsersMockMvc.perform(put("/api/users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(users)))
            .andExpect(status().isCreated());

        // Validate the Users in the database
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUsers() throws Exception {
        // Initialize the database
        usersRepository.saveAndFlush(users);
        int databaseSizeBeforeDelete = usersRepository.findAll().size();

        // Get the users
        restUsersMockMvc.perform(delete("/api/users/{id}", users.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Users> usersList = usersRepository.findAll();
        assertThat(usersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Users.class);
    }
}
