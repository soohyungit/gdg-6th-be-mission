package gdg.hongik.mission.repository;

import gdg.hongik.mission.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {


    @PersistenceContext
    private EntityManager em;

    public Product findById(Long id){
        return em.find(Product.class, id);
    }

    public List<Product> findAll(){
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public Product findByName(String name){
        List<Product> result = em.createQuery(
            "SELECT p FROM Product p WHERE p.name = :name", Product.class
        ).setParameter("name", name).getResultList();

        return result.isEmpty() ? null : result.get(0);
    }

    public void save(Product product){
        em.persist(product);
    }

    public void deleteById(Long id){
        Product product = em.find(Product.class, id);
        if(product != null ){
            em.remove(product);
        }
    }
}
