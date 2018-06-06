/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.mycompany.appcredito.jpacontroller;

import co.mycompany.appcredito.jpacontroller.exceptions.NonexistentEntityException;
import co.mycompany.appcredito.jpacontroller.exceptions.PreexistingEntityException;
import co.mycompany.appcredito.model.Creditos;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author yypv2
 */
public class CreditosJpaController implements Serializable {

    public CreditosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Creditos creditos) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(creditos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCreditos(creditos.getNumcredito()) != null) {
                throw new PreexistingEntityException("Creditos " + creditos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Creditos creditos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            creditos = em.merge(creditos);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = creditos.getNumcredito();
                if (findCreditos(id) == null) {
                    throw new NonexistentEntityException("The creditos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Creditos creditos;
            try {
                creditos = em.getReference(Creditos.class, id);
                creditos.getNumcredito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The creditos with id " + id + " no longer exists.", enfe);
            }
            em.remove(creditos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Creditos> findCreditosEntities() {
        return findCreditosEntities(true, -1, -1);
    }

    public List<Creditos> findCreditosEntities(int maxResults, int firstResult) {
        return findCreditosEntities(false, maxResults, firstResult);
    }

    private List<Creditos> findCreditosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Creditos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Creditos findCreditos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Creditos.class, id);
        } finally {
            em.close();
        }
    }

    public int getCreditosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Creditos> rt = cq.from(Creditos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
