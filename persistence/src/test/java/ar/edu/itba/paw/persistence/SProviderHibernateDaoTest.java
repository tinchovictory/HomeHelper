package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.daos.SProviderDao;
import ar.edu.itba.paw.interfaces.daos.UserDao;
import ar.edu.itba.paw.model.SProvider;
import ar.edu.itba.paw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.validation.constraints.AssertTrue;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static junit.framework.TestCase.*;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class SProviderHibernateDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private DataSource ds;
    private JdbcTemplate jdbcTemplate;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SProviderDao sProviderDao;



    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void createTest() {
        int count = JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "serviceProviders");
        final Optional<SProvider> sProvider = sProviderDao.create(Const.USER_ID,Const.VALID_DESCRIPTION);
        em.flush();
        assertNotNull(sProvider);
        assertTrue(sProvider.isPresent());
        assertEquals(Const.USER_ID, sProvider.get().getUser().getId());
        assertEquals(count+1,JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "serviceProviders"));
    }

    @Test
    public void getServiceProvidersTest() {

        final Set<SProvider> sProviders = sProviderDao.getServiceProviders();
        assertNotNull(sProviders);
        assertTrue(containsSpId(sProviders,Const.SPROVIDER_ID) );
        assertTrue(containsSpId(sProviders,Const.SPROVIDER_ID) );
        assertEquals(3,sProviders.size());
        assertFalse(containsSpId(sProviders,Const.USER_ID));
    }

    @Test
    public void getServiceProviderWithUserIdTest() {

        final Optional<SProvider> sProvider = sProviderDao.getServiceProviderWithUserId(Const.SPROVIDER_ID);
        assertNotNull(sProvider);
        assertTrue(sProvider.isPresent());
        assertEquals(Const.SPROVIDER_ID, sProvider.get().getUser().getId());
    }

    @Test
    public void updateDescriptionOfServiceProvider(){
        int count = JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "serviceProviders");

        sProviderDao.updateDescriptionOfServiceProvider(Const.SPROVIDER_ID,Const.VALID_DESCRIPTION);

        sProviderDao.updateDescriptionOfServiceProvider(Const.INVALIDAD_USER_ID,Const.VALID_DESCRIPTION);

        try {

            sProviderDao.updateDescriptionOfServiceProvider(Const.SPROVIDER_ID, Const.INVALID_DESCRIPTION);
        }catch (Exception e){
            assertEquals(count,JdbcTestUtils.countRowsInTable(this.jdbcTemplate, "serviceProviders"));
        }

    }

    @Test
    public void getServiceProviderAptitudesTest(){
        Optional<SProvider> sProviderOp = sProviderDao.getServiceProviderWithUserId(Const.SPROVIDER_ID);
        assertTrue(sProviderOp.isPresent());

       assertEquals(2,sProviderOp.get().getAptitudes().size());



    }

    public boolean containsSpId(Set<SProvider> list, int id){
        for (SProvider s : list){
            if(s.getUser().getId() == id){
                return true;
            }
        }
        return false;
    }
}
