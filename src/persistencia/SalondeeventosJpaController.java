/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Reservasalon;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Salondeeventos;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Usuario
 */
public class SalondeeventosJpaController implements Serializable {

    public SalondeeventosJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ArcadiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Salondeeventos salondeeventos) {
        if (salondeeventos.getReservasalonList() == null) {
            salondeeventos.setReservasalonList(new ArrayList<Reservasalon>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Reservasalon> attachedReservasalonList = new ArrayList<Reservasalon>();
            for (Reservasalon reservasalonListReservasalonToAttach : salondeeventos.getReservasalonList()) {
                reservasalonListReservasalonToAttach = em.getReference(reservasalonListReservasalonToAttach.getClass(), reservasalonListReservasalonToAttach.getIdreservasalon());
                attachedReservasalonList.add(reservasalonListReservasalonToAttach);
            }
            salondeeventos.setReservasalonList(attachedReservasalonList);
            em.persist(salondeeventos);
            for (Reservasalon reservasalonListReservasalon : salondeeventos.getReservasalonList()) {
                reservasalonListReservasalon.getSalondeeventosList().add(salondeeventos);
                reservasalonListReservasalon = em.merge(reservasalonListReservasalon);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Salondeeventos salondeeventos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Salondeeventos persistentSalondeeventos = em.find(Salondeeventos.class, salondeeventos.getIdsalon());
            List<Reservasalon> reservasalonListOld = persistentSalondeeventos.getReservasalonList();
            List<Reservasalon> reservasalonListNew = salondeeventos.getReservasalonList();
            List<Reservasalon> attachedReservasalonListNew = new ArrayList<Reservasalon>();
            for (Reservasalon reservasalonListNewReservasalonToAttach : reservasalonListNew) {
                reservasalonListNewReservasalonToAttach = em.getReference(reservasalonListNewReservasalonToAttach.getClass(), reservasalonListNewReservasalonToAttach.getIdreservasalon());
                attachedReservasalonListNew.add(reservasalonListNewReservasalonToAttach);
            }
            reservasalonListNew = attachedReservasalonListNew;
            salondeeventos.setReservasalonList(reservasalonListNew);
            salondeeventos = em.merge(salondeeventos);
            for (Reservasalon reservasalonListOldReservasalon : reservasalonListOld) {
                if (!reservasalonListNew.contains(reservasalonListOldReservasalon)) {
                    reservasalonListOldReservasalon.getSalondeeventosList().remove(salondeeventos);
                    reservasalonListOldReservasalon = em.merge(reservasalonListOldReservasalon);
                }
            }
            for (Reservasalon reservasalonListNewReservasalon : reservasalonListNew) {
                if (!reservasalonListOld.contains(reservasalonListNewReservasalon)) {
                    reservasalonListNewReservasalon.getSalondeeventosList().add(salondeeventos);
                    reservasalonListNewReservasalon = em.merge(reservasalonListNewReservasalon);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = salondeeventos.getIdsalon();
                if (findSalondeeventos(id) == null) {
                    throw new NonexistentEntityException("The salondeeventos with id " + id + " no longer exists.");
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
            Salondeeventos salondeeventos;
            try {
                salondeeventos = em.getReference(Salondeeventos.class, id);
                salondeeventos.getIdsalon();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The salondeeventos with id " + id + " no longer exists.", enfe);
            }
            List<Reservasalon> reservasalonList = salondeeventos.getReservasalonList();
            for (Reservasalon reservasalonListReservasalon : reservasalonList) {
                reservasalonListReservasalon.getSalondeeventosList().remove(salondeeventos);
                reservasalonListReservasalon = em.merge(reservasalonListReservasalon);
            }
            em.remove(salondeeventos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Salondeeventos> findSalondeeventosEntities() {
        return findSalondeeventosEntities(true, -1, -1);
    }

    public List<Salondeeventos> findSalondeeventosEntities(int maxResults, int firstResult) {
        return findSalondeeventosEntities(false, maxResults, firstResult);
    }

    private List<Salondeeventos> findSalondeeventosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Salondeeventos.class));
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

    public Salondeeventos findSalondeeventos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Salondeeventos.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalondeeventosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Salondeeventos> rt = cq.from(Salondeeventos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
