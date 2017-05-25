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
import modelo.Habitacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Comodidadasociada;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Usuario
 */
public class ComodidadasociadaJpaController implements Serializable {

    public ComodidadasociadaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ArcadiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comodidadasociada comodidadasociada) {
        if (comodidadasociada.getHabitacionList() == null) {
            comodidadasociada.setHabitacionList(new ArrayList<Habitacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Habitacion> attachedHabitacionList = new ArrayList<Habitacion>();
            for (Habitacion habitacionListHabitacionToAttach : comodidadasociada.getHabitacionList()) {
                habitacionListHabitacionToAttach = em.getReference(habitacionListHabitacionToAttach.getClass(), habitacionListHabitacionToAttach.getIdhabitacion());
                attachedHabitacionList.add(habitacionListHabitacionToAttach);
            }
            comodidadasociada.setHabitacionList(attachedHabitacionList);
            em.persist(comodidadasociada);
            for (Habitacion habitacionListHabitacion : comodidadasociada.getHabitacionList()) {
                habitacionListHabitacion.getComodidadasociadaList().add(comodidadasociada);
                habitacionListHabitacion = em.merge(habitacionListHabitacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comodidadasociada comodidadasociada) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comodidadasociada persistentComodidadasociada = em.find(Comodidadasociada.class, comodidadasociada.getIdcomodidad());
            List<Habitacion> habitacionListOld = persistentComodidadasociada.getHabitacionList();
            List<Habitacion> habitacionListNew = comodidadasociada.getHabitacionList();
            List<Habitacion> attachedHabitacionListNew = new ArrayList<Habitacion>();
            for (Habitacion habitacionListNewHabitacionToAttach : habitacionListNew) {
                habitacionListNewHabitacionToAttach = em.getReference(habitacionListNewHabitacionToAttach.getClass(), habitacionListNewHabitacionToAttach.getIdhabitacion());
                attachedHabitacionListNew.add(habitacionListNewHabitacionToAttach);
            }
            habitacionListNew = attachedHabitacionListNew;
            comodidadasociada.setHabitacionList(habitacionListNew);
            comodidadasociada = em.merge(comodidadasociada);
            for (Habitacion habitacionListOldHabitacion : habitacionListOld) {
                if (!habitacionListNew.contains(habitacionListOldHabitacion)) {
                    habitacionListOldHabitacion.getComodidadasociadaList().remove(comodidadasociada);
                    habitacionListOldHabitacion = em.merge(habitacionListOldHabitacion);
                }
            }
            for (Habitacion habitacionListNewHabitacion : habitacionListNew) {
                if (!habitacionListOld.contains(habitacionListNewHabitacion)) {
                    habitacionListNewHabitacion.getComodidadasociadaList().add(comodidadasociada);
                    habitacionListNewHabitacion = em.merge(habitacionListNewHabitacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comodidadasociada.getIdcomodidad();
                if (findComodidadasociada(id) == null) {
                    throw new NonexistentEntityException("The comodidadasociada with id " + id + " no longer exists.");
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
            Comodidadasociada comodidadasociada;
            try {
                comodidadasociada = em.getReference(Comodidadasociada.class, id);
                comodidadasociada.getIdcomodidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comodidadasociada with id " + id + " no longer exists.", enfe);
            }
            List<Habitacion> habitacionList = comodidadasociada.getHabitacionList();
            for (Habitacion habitacionListHabitacion : habitacionList) {
                habitacionListHabitacion.getComodidadasociadaList().remove(comodidadasociada);
                habitacionListHabitacion = em.merge(habitacionListHabitacion);
            }
            em.remove(comodidadasociada);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comodidadasociada> findComodidadasociadaEntities() {
        return findComodidadasociadaEntities(true, -1, -1);
    }

    public List<Comodidadasociada> findComodidadasociadaEntities(int maxResults, int firstResult) {
        return findComodidadasociadaEntities(false, maxResults, firstResult);
    }

    private List<Comodidadasociada> findComodidadasociadaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comodidadasociada.class));
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

    public Comodidadasociada findComodidadasociada(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comodidadasociada.class, id);
        } finally {
            em.close();
        }
    }

    public int getComodidadasociadaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comodidadasociada> rt = cq.from(Comodidadasociada.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
