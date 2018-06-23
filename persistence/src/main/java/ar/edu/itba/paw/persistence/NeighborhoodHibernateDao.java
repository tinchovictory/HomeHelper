package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.interfaces.daos.NeighborhoodDao;
import ar.edu.itba.paw.model.Neighborhood;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
@Repository
public class NeighborhoodHibernateDao implements NeighborhoodDao {


    @PersistenceContext
    private EntityManager em;


    @Override
    public int insertNeighborhood(String ngname) {
        final Neighborhood ng = new Neighborhood(ngname);
        em.persist(ng);
        return ng.getNgId();
    }

    @Override
    public List<Neighborhood> getAllNeighborhoods() {
        final TypedQuery<Neighborhood> query = em.createQuery("from Neighborhood", Neighborhood.class);
        return query.getResultList();
    }
}
