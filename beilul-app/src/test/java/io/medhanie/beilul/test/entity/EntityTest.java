package io.medhanie.beilul.test.entity;

import io.medhanie.beilul.entity.*;
import io.medhanie.beilul.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntityTest {

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
    @DisplayName("Test Entity: Country")
    void testCountry() throws Exception {

        Country country = new Country();
        country.setCountryIso2("US");
        country.setCountryIso3("USA");
        country.setCountryName("United States");

        this.countryRepository.save(country);

        Country countryDb = this.countryRepository.findById("USA").get();
        assertThat(countryDb.getCountryIso2()).isEqualTo("US");
        assertThat(countryDb.getCountryName()).isEqualTo("United States");
    }


    @Test
    @Order(2)
    @DisplayName("Test Entity: Admin Zone")
    void testAdminZone() throws Exception {

        AdminZone adminZone = new AdminZone();
        adminZone.setAdminZoneName("California");

        Country country = this.countryRepository.getOne("USA");
        adminZone.setCountry(country);

        this.adminZoneRepository.save(adminZone);

        AdminZone zone = this.adminZoneRepository.findById(Short.valueOf("1")).get();
        assertThat(zone.getCountry().getCountryIso3()).isEqualTo("USA");
        assertThat(zone.getAdminZoneName()).isEqualTo("California");
    }

    @Test
    @Order(3)
    @DisplayName("Test Entity: City")
    void testCity() throws Exception {

        City city = new City();
        city.setCityName("Oakland");
        BigDecimal lat = BigDecimal.valueOf(37.81D);
        BigDecimal log = BigDecimal.valueOf(-32.27D);
        city.setLatitude(lat);
        city.setLongitude(log);


        AdminZone zone = this.adminZoneRepository.findById(Short.valueOf("1")).get();
        city.setAdminZoneId(zone);

        this.cityRepository.save(city);

        City cityDb = this.cityRepository.findById(Short.valueOf("1")).get();
        assertThat(cityDb.getCityName()).isEqualTo("Oakland");
        assertThat(cityDb.getLatitude()).isEqualTo(lat);
        assertThat(cityDb.getLongitude()).isEqualTo(log);
    }

    @Test
    @Order(4)
    @DisplayName("Test Entity: Address")
    void testAddress() throws Exception {

        Address address = new Address();
        address.setAddress("Grand Ave");
        address.setCityName("Oakland");
        address.setDistrict("California");
        address.setCountry(countryRepository.getOne("USA"));

        this.addressRepository.save(address);

        Address add = this.addressRepository.findById(Integer.valueOf("1")).get();
        assertThat(add.getCityName()).isEqualTo("Oakland");
        assertThat(add.getDistrict()).isEqualTo("California");
        assertThat(add.getAddress()).isEqualTo("Grand Ave");
    }

    @Test
    @Order(5)
    @DisplayName("Test Entity: Role")
    void testRole() throws Exception {

        Role role1 = new Role();
        role1.setName("ADMIN");

        Role role2 = new Role();
        role2.setName("USER");

        this.roleRepository.saveAll(Arrays.asList(role1, role2));


        List<Role> roles = this.roleRepository.findAll();
        assertThat(roles.size()).isEqualTo(2);
        assertThat(roles.get(0).getName()).isEqualTo("ADMIN");
        assertThat(roles.get(1).getName()).isEqualTo("USER");
    }

    @Test
    @Order(6)
    @DisplayName("Test Entity: Language")
    void testLanguage() throws Exception {

        Language lan1 = new Language();
        lan1.setName("English");
        lan1.setNativeName("english");

        Language lan2 = new Language();
        lan2.setName("Xosa");
        lan2.setNativeName("xosa");

        this.languageRepository.saveAll(Arrays.asList(lan1, lan2));


        List<Language> lans = this.languageRepository.findAll();
        assertThat(lans.size()).isEqualTo(2);
        assertThat(lans.get(0).getName()).isEqualTo("English");
        assertThat(lans.get(0).getNativeName()).isEqualTo("english");
        assertThat(lans.get(1).getName()).isEqualTo("Xosa");
        assertThat(lans.get(1).getNativeName()).isEqualTo("xosa");
    }

    @Test
    @Order(7)
    @DisplayName("Test Entity: Category")
    void testCategory() throws Exception {

        Category cat1 = new Category();
        cat1.setDescription("Technology");

        Category cat2 = new Category();
        cat2.setDescription("Science");

        Category cat3 = new Category();
        cat3.setDescription("Politics");

        this.categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3));


        List<Category> cats = this.categoryRepository.findAll();
        assertThat(cats.size()).isEqualTo(3);
        assertThat(cats.get(0).getDescription()).isEqualTo("Technology");
        assertThat(cats.get(1).getDescription()).isEqualTo("Science");
        assertThat(cats.get(2).getDescription()).isEqualTo("Politics");
    }

    @Test
    @Order(8)
    @DisplayName("Test Entity: Membership")
    void testMembership() throws Exception {
        Member mem1 = new Member();
        mem1.setActive(Boolean.FALSE);
        mem1.setAddress(this.addressRepository.getOne(Integer.valueOf("1")));
        mem1.setEmail("mem@mem.com");
        mem1.setFirstName("John");
        mem1.setLastName("Doe");
        mem1.setPassword("xyxyxyxy");
        mem1.setUsername("mem1");
        mem1.setRoles(this.roleRepository.findAll());

        Member mem2 = new Member();
        mem2.setActive(Boolean.FALSE);
        mem2.setAddress(this.addressRepository.getOne(Integer.valueOf("1")));
        mem2.setEmail("mem2@mem.com");
        mem2.setFirstName("Sus");
        mem2.setLastName("Mus");
        mem2.setPassword("uouououo");
        mem2.setUsername("sus");
        Role role = this.roleRepository.findAll().get(0);
        mem2.setRoles(Arrays.asList(role));

        this.memberRepository.saveAll(Arrays.asList(mem1, mem2));


        List<Member> mems = this.memberRepository.findAll();
        assertThat(mems.size()).isEqualTo(2);
        assertThat(mems.get(0).getEmail()).isEqualTo("mem@mem.com");
        assertThat(mems.get(0).getRoles().size()).isEqualTo(2);
        assertThat(mems.get(1).getPassword()).isEqualTo("uouououo");
        assertThat(mems.get(1).getRoles().size()).isEqualTo(1);

    }

    @Test
    @Order(9)
    @DisplayName("Test Entity: Content")
    void testContent() throws Exception {
        Content con1 = new Content();
        con1.setBody("<html><body><h1>Title</h1><p>lorem ipsum</p></body></html>");
        con1.setTitle("Lorem");
        con1.setSummary("<html><body><h1>Title</h1><p>summary</p></body></html>");
        con1.setLanguages(this.languageRepository.findAll());
        con1.setCategories(this.categoryRepository.findAll());
        con1.setCreatedBy(this.memberRepository.findById(Integer.valueOf("1")).get());

        this.contentRepository.save(con1);

        Content con = this.contentRepository.findById(Integer.valueOf("1")).get();
        assertThat(con1.getBody()).isEqualTo("<html><body><h1>Title</h1><p>lorem ipsum</p></body></html>");
        assertThat(con1.getCategories().size()).isEqualTo(3);
        assertThat(con1.getLanguages().size()).isEqualTo(2);
        assertThat(con1.getSummary()).isEqualTo("<html><body><h1>Title</h1><p>summary</p></body></html>");
        assertThat(con1.getTitle()).isEqualTo("Lorem");

    }

    @Test
    @Order(10)
    @DisplayName("Test Entity: Access Log")
    void testAccessLog() throws Exception {
        AccessLog accessLog = new AccessLog();
        accessLog.setContentId(1);
        accessLog.setDbAccessTime(OffsetDateTime.now());
        accessLog.setApiAccessTime(OffsetDateTime.now());
        accessLog.setMemberId(1);
        accessLog.setResponseSize("60K");
        accessLog.setRequestSize("10K");
        accessLog.setRequestMethod("GET");
        accessLog.setResponseStatus(Short.valueOf("200"));

        this.accessLogRepository.save(accessLog);

        AccessLog acc = this.accessLogRepository.findById(Integer.valueOf("1")).get();
        assertThat(acc.getRequestMethod()).isEqualTo("GET");
        assertThat(acc.getResponseSize()).isEqualTo("60K");
        assertThat(acc.getRequestSize()).isEqualTo("10K");
    }

    @Test
    @Order(11)
    @DisplayName("Test Entity: Comment")
    void testComment() throws Exception {
        Comment comment = new Comment();
        comment.setComment("Superb!");
        comment.setContentId(1);
        comment.setCreatedBy(this.memberRepository.findById(Integer.valueOf("1")).get());

        this.commentRepository.save(comment);

        Comment comment2 = new Comment();
        comment2.setComment("Absolutely Superb!");
        comment2.setContentId(Integer.valueOf("2"));
        comment2.setCreatedBy(this.memberRepository.findById(Integer.valueOf("2")).get());
        Comment comm = this.commentRepository.findById(Integer.valueOf("1")).get();

        assertThat(comm.getComment()).isEqualTo("Superb!");
        assertThat(comm.getContentId()).isEqualTo(1);
        assertThat(comm.getCreatedBy()).isNotNull();

        this.commentRepository.save(comment2);

        Comment comm2 = this.commentRepository.findById(Integer.valueOf("2")).get();
        assertThat(comm2.getComment()).isEqualTo("Absolutely Superb!");
    }

    @Test
    @Order(12)
    @DisplayName("Test Entity: Approver")
    void testApprover() throws Exception {
        Approver approver = new Approver();
        approver.setApproved(Boolean.TRUE);
        approver.setContentId(1);
        approver.setReason("Approved.");
        approver.setApprovedBy(this.memberRepository.getOne(1));

        this.approverRepository.save(approver);

        Approver app = this.approverRepository.findById(1).get();
        assertThat(app.isApproved()).isEqualTo(Boolean.TRUE);
        assertThat(app.getReason()).isEqualTo("Approved.");
    }


}
