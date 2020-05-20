package io.medhanie.beilul.test;

import io.medhanie.beilul.entity.AdminZone;
import io.medhanie.beilul.entity.City;
import io.medhanie.beilul.entity.Country;
import io.medhanie.beilul.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntityTest{

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AccessLogRepository accessLogRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AdminZoneRepository adminZoneRepository;

    @Autowired
    private ApproverRepository approverRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Order(1)
    void testCountry() throws Exception {

        Country country = new Country();
        country.setCountryIso2("US");
        country.setCountryIso3("USA");
        country.setCountryName("United States");

        this.countryRepository.save(country);

        Country countryDb= this.countryRepository.findById("USA").get();
        assertThat(countryDb.getCountryIso2()).isEqualTo("US");
        assertThat(countryDb.getCountryName()).isEqualTo("United States");
    }


    @Test
    @Order(2)
    void testAdminZone() throws Exception {

        AdminZone adminZone = new AdminZone();
        adminZone.setAdminZoneName("California");

        Country country = this.countryRepository.getOne("USA");
        adminZone.setCountry(country);

        this.adminZoneRepository.save(adminZone);

        AdminZone zone= this.adminZoneRepository.findById(Short.valueOf("1")).get();
        assertThat(zone.getCountry().getCountryIso3()).isEqualTo("USA");
        assertThat(zone.getAdminZoneName()).isEqualTo("California");
    }

    @Test
    @Order(3)
    void testCity() throws Exception {

        City city = new City();
        city.setCityName("Oakland");
        BigDecimal lat = BigDecimal.valueOf(37.81D);
        BigDecimal log = BigDecimal.valueOf(-32.27D);
        city.setLatitude(lat);
        city.setLongitude(log);


        AdminZone zone= this.adminZoneRepository.findById(Short.valueOf("1")).get();
        city.setAdminZoneId(zone);

        this.cityRepository.save(city);

        City cityDb= this.cityRepository.findById(Short.valueOf("1")).get();
        assertThat(cityDb.getCityName()).isEqualTo("Oakland");
        assertThat(cityDb.getLatitude()).isEqualTo(lat);
        assertThat(cityDb.getLongitude()).isEqualTo(log);
    }


}
