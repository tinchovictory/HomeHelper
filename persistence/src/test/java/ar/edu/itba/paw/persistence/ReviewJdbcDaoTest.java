package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.interfaces.daos.ReviewDao;
import ar.edu.itba.paw.interfaces.daos.SProviderDao;
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

import static org.junit.Assert.assertEquals;

@Sql("classpath:schema.sql")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
public class ReviewJdbcDaoTest extends AbstractTransactionalJUnit4SpringContextTests {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    ReviewDao reviewDao;

    @Autowired
    private DataSource ds;

    private JdbcTemplate jdbcTemplate;
    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void getReviewsOfAptitudeTest(){

        assertEquals(2,reviewDao.getReviewsOfAptitude(Const.VALID_APTITUDE_ID).size());

        assertEquals(1,reviewDao.getReviewsOfAptitude(Const.VALID_APTITUDE2_ID).size());

        assertEquals(0,reviewDao.getReviewsOfAptitude(Const.INVALID_APTITUDE_ID).size());
    }
}
