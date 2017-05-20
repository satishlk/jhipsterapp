package com.parking.web.rest;

import com.parking.Ionic2SampleApp;

import com.parking.domain.RateCard;
import com.parking.repository.RateCardRepository;
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
 * Test class for the RateCardResource REST controller.
 *
 * @see RateCardResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Ionic2SampleApp.class)
public class RateCardResourceIntTest {

    private static final Double DEFAULT_RATE_PER_HOUR = 1D;
    private static final Double UPDATED_RATE_PER_HOUR = 2D;

    private static final Double DEFAULT_EXTRA_HOUR = 1D;
    private static final Double UPDATED_EXTRA_HOUR = 2D;

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

    private static final Boolean DEFAULT_ACTIVATION = false;
    private static final Boolean UPDATED_ACTIVATION = true;

    @Autowired
    private RateCardRepository rateCardRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRateCardMockMvc;

    private RateCard rateCard;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RateCardResource rateCardResource = new RateCardResource(rateCardRepository);
        this.restRateCardMockMvc = MockMvcBuilders.standaloneSetup(rateCardResource)
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
    public static RateCard createEntity(EntityManager em) {
        RateCard rateCard = new RateCard()
            .ratePerHour(DEFAULT_RATE_PER_HOUR)
            .extraHour(DEFAULT_EXTRA_HOUR)
            .vendorId(DEFAULT_VENDOR_ID)
            .addDatetime(DEFAULT_ADD_DATETIME)
            .addUserId(DEFAULT_ADD_USER_ID)
            .updateDateTime(DEFAULT_UPDATE_DATE_TIME)
            .updateUserId(DEFAULT_UPDATE_USER_ID)
            .activation(DEFAULT_ACTIVATION);
        return rateCard;
    }

    @Before
    public void initTest() {
        rateCard = createEntity(em);
    }

    @Test
    @Transactional
    public void createRateCard() throws Exception {
        int databaseSizeBeforeCreate = rateCardRepository.findAll().size();

        // Create the RateCard
        restRateCardMockMvc.perform(post("/api/rate-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateCard)))
            .andExpect(status().isCreated());

        // Validate the RateCard in the database
        List<RateCard> rateCardList = rateCardRepository.findAll();
        assertThat(rateCardList).hasSize(databaseSizeBeforeCreate + 1);
        RateCard testRateCard = rateCardList.get(rateCardList.size() - 1);
        assertThat(testRateCard.getRatePerHour()).isEqualTo(DEFAULT_RATE_PER_HOUR);
        assertThat(testRateCard.getExtraHour()).isEqualTo(DEFAULT_EXTRA_HOUR);
        assertThat(testRateCard.getVendorId()).isEqualTo(DEFAULT_VENDOR_ID);
        assertThat(testRateCard.getAddDatetime()).isEqualTo(DEFAULT_ADD_DATETIME);
        assertThat(testRateCard.getAddUserId()).isEqualTo(DEFAULT_ADD_USER_ID);
        assertThat(testRateCard.getUpdateDateTime()).isEqualTo(DEFAULT_UPDATE_DATE_TIME);
        assertThat(testRateCard.getUpdateUserId()).isEqualTo(DEFAULT_UPDATE_USER_ID);
        assertThat(testRateCard.isActivation()).isEqualTo(DEFAULT_ACTIVATION);
    }

    @Test
    @Transactional
    public void createRateCardWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rateCardRepository.findAll().size();

        // Create the RateCard with an existing ID
        rateCard.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRateCardMockMvc.perform(post("/api/rate-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateCard)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<RateCard> rateCardList = rateCardRepository.findAll();
        assertThat(rateCardList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRateCards() throws Exception {
        // Initialize the database
        rateCardRepository.saveAndFlush(rateCard);

        // Get all the rateCardList
        restRateCardMockMvc.perform(get("/api/rate-cards?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rateCard.getId().intValue())))
            .andExpect(jsonPath("$.[*].ratePerHour").value(hasItem(DEFAULT_RATE_PER_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].extraHour").value(hasItem(DEFAULT_EXTRA_HOUR.doubleValue())))
            .andExpect(jsonPath("$.[*].vendorId").value(hasItem(DEFAULT_VENDOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].addDatetime").value(hasItem(sameInstant(DEFAULT_ADD_DATETIME))))
            .andExpect(jsonPath("$.[*].addUserId").value(hasItem(DEFAULT_ADD_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].updateDateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_DATE_TIME))))
            .andExpect(jsonPath("$.[*].updateUserId").value(hasItem(DEFAULT_UPDATE_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].activation").value(hasItem(DEFAULT_ACTIVATION.booleanValue())));
    }

    @Test
    @Transactional
    public void getRateCard() throws Exception {
        // Initialize the database
        rateCardRepository.saveAndFlush(rateCard);

        // Get the rateCard
        restRateCardMockMvc.perform(get("/api/rate-cards/{id}", rateCard.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rateCard.getId().intValue()))
            .andExpect(jsonPath("$.ratePerHour").value(DEFAULT_RATE_PER_HOUR.doubleValue()))
            .andExpect(jsonPath("$.extraHour").value(DEFAULT_EXTRA_HOUR.doubleValue()))
            .andExpect(jsonPath("$.vendorId").value(DEFAULT_VENDOR_ID.intValue()))
            .andExpect(jsonPath("$.addDatetime").value(sameInstant(DEFAULT_ADD_DATETIME)))
            .andExpect(jsonPath("$.addUserId").value(DEFAULT_ADD_USER_ID.toString()))
            .andExpect(jsonPath("$.updateDateTime").value(sameInstant(DEFAULT_UPDATE_DATE_TIME)))
            .andExpect(jsonPath("$.updateUserId").value(DEFAULT_UPDATE_USER_ID.toString()))
            .andExpect(jsonPath("$.activation").value(DEFAULT_ACTIVATION.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRateCard() throws Exception {
        // Get the rateCard
        restRateCardMockMvc.perform(get("/api/rate-cards/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRateCard() throws Exception {
        // Initialize the database
        rateCardRepository.saveAndFlush(rateCard);
        int databaseSizeBeforeUpdate = rateCardRepository.findAll().size();

        // Update the rateCard
        RateCard updatedRateCard = rateCardRepository.findOne(rateCard.getId());
        updatedRateCard
            .ratePerHour(UPDATED_RATE_PER_HOUR)
            .extraHour(UPDATED_EXTRA_HOUR)
            .vendorId(UPDATED_VENDOR_ID)
            .addDatetime(UPDATED_ADD_DATETIME)
            .addUserId(UPDATED_ADD_USER_ID)
            .updateDateTime(UPDATED_UPDATE_DATE_TIME)
            .updateUserId(UPDATED_UPDATE_USER_ID)
            .activation(UPDATED_ACTIVATION);

        restRateCardMockMvc.perform(put("/api/rate-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRateCard)))
            .andExpect(status().isOk());

        // Validate the RateCard in the database
        List<RateCard> rateCardList = rateCardRepository.findAll();
        assertThat(rateCardList).hasSize(databaseSizeBeforeUpdate);
        RateCard testRateCard = rateCardList.get(rateCardList.size() - 1);
        assertThat(testRateCard.getRatePerHour()).isEqualTo(UPDATED_RATE_PER_HOUR);
        assertThat(testRateCard.getExtraHour()).isEqualTo(UPDATED_EXTRA_HOUR);
        assertThat(testRateCard.getVendorId()).isEqualTo(UPDATED_VENDOR_ID);
        assertThat(testRateCard.getAddDatetime()).isEqualTo(UPDATED_ADD_DATETIME);
        assertThat(testRateCard.getAddUserId()).isEqualTo(UPDATED_ADD_USER_ID);
        assertThat(testRateCard.getUpdateDateTime()).isEqualTo(UPDATED_UPDATE_DATE_TIME);
        assertThat(testRateCard.getUpdateUserId()).isEqualTo(UPDATED_UPDATE_USER_ID);
        assertThat(testRateCard.isActivation()).isEqualTo(UPDATED_ACTIVATION);
    }

    @Test
    @Transactional
    public void updateNonExistingRateCard() throws Exception {
        int databaseSizeBeforeUpdate = rateCardRepository.findAll().size();

        // Create the RateCard

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRateCardMockMvc.perform(put("/api/rate-cards")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rateCard)))
            .andExpect(status().isCreated());

        // Validate the RateCard in the database
        List<RateCard> rateCardList = rateCardRepository.findAll();
        assertThat(rateCardList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRateCard() throws Exception {
        // Initialize the database
        rateCardRepository.saveAndFlush(rateCard);
        int databaseSizeBeforeDelete = rateCardRepository.findAll().size();

        // Get the rateCard
        restRateCardMockMvc.perform(delete("/api/rate-cards/{id}", rateCard.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RateCard> rateCardList = rateCardRepository.findAll();
        assertThat(rateCardList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RateCard.class);
    }
}
