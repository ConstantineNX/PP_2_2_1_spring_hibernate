package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

   private final SessionFactory sessionFactory;

   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user, Car car) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
   TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("SELECT u FROM User u LEFT JOIN FETCH u.car", User.class);
      return query.getResultList();
   }

   @Override
   public User findByCar(String model, int series) {
      return sessionFactory.getCurrentSession()
              .createQuery("SELECT u FROM User u JOIN FETCH u.car c WHERE c.model = :model AND c.series = :series", User.class)
              .setParameter("model", model)
              .setParameter("series", series)
              .getSingleResult();
   }

}
