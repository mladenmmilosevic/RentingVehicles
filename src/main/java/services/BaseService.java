package services;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseService {

   @PersistenceContext(unitName = "renting")
   protected EntityManager entityManager;

   public Serializable get(Class<?> c, Object id) {
      return (Serializable) entityManager.find(c, id);
   }

   public Serializable create(Serializable t) {
      Objects.requireNonNull(t);
      entityManager.persist(t);
      entityManager.flush();
      entityManager.refresh(t);
      return t;
   }

   public void delete(Serializable t) {
      Objects.requireNonNull(t);
      entityManager.remove(entityManager.merge(t));
   }

   public Serializable update(Serializable t) {
      Objects.requireNonNull(t);
      return this.entityManager.merge(t);
   }

}
