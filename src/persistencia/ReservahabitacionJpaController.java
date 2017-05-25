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
import modelo.Cliente;
import modelo.Habitacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Reservahabitacion;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Usuario
 */
public class ReservahabitacionJpaController implements Serializable {

    public ReservahabitacionJpaController(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("ArcadiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reservahabitacion reservahabitacion) {
        if (reservahabitacion.getHabitacionList() == null) {
            reservahabitacion.setHabitacionList(new ArrayList<Habitacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idcliente = reservahabitacion.getIdcliente();
            if (idcliente != null) {
                idcliente = em.getReference(idcliente.getClass(), idcliente.getIdcliente());
                reservahabitacion.setIdcliente(idcliente);
            }
            List<Habitacion> attachedHabitacionList = new ArrayList<Habitacion>();
            for (Habitacion habitacionListHabitacionToAttach : reservahabitacion.getHabitacionList()) {
                habitacionListHabitacionToAttach = em.getReference(habitacionListHabitacionToAttach.getClass(), habitacionListHabitacionToAttach.getIdhabitacion());
                attachedHabitacionList.add(habitacionListHabitacionToAttach);
            }
            reservahabitacion.setHabitacionList(attachedHabitacionList);
            em.persist(reservahabitacion);
            if (idcliente != null) {
                idcliente.getReservahabitacionList().add(reservahabitacion);
                idcliente = em.merge(idcliente);
            }
            for (Habitacion habitacionListHabitacion : reservahabitacion.getHabitacionList()) {
                habitacionListHabitacion.getReservahabitacionList().add(reservahabitacion);
                habitacionListHabitacion = em.merge(habitacionListHabitacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reservahabitacion reservahabitacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservahabitacion persistentReservahabitacion = em.find(Reservahabitacion.class, reservahabitacion.getIdreservahabitacion());
            Cliente idclienteOld = persistentReservahabitacion.getIdcliente();
            Cliente idclienteNew = reservahabitacion.getIdcliente();
            List<Habitacion> habitacionListOld = persistentReservahabitacion.getHabitacionList();
            List<Habitacion> habitacionListNew = reservahabitacion.getHabitacionList();
            if (idclienteNew != null) {
                idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getIdcliente());
                reservahabitacion.setIdcliente(idclienteNew);
            }
            List<Habitacion> attachedHabitacionListNew = new ArrayList<Habitacion>();
            for (Habitacion habitacionListNewHabitacionToAttach : habitacionListNew) {
                habitacionListNewHabitacionToAttach = em.getReference(habitacionListNewHabitacionToAttach.getClass(), habitacionListNewHabitacionToAttach.getIdhabitacion());
                attachedHabitacionListNew.add(habitacionListNewHabitacionToAttach);
            }
            habitacionListNew = attachedHabitacionListNew;
            reservahabitacion.setHabitacionList(habitacionListNew);
            reservahabitacion = em.merge(reservahabitacion);
            if (idclienteOld != null && !idclienteOld.equals(idclienteNew)) {
                idclienteOld.getReservahabitacionList().remove(reservahabitacion);
                idclienteOld = em.merge(idclienteOld);
            }
            if (idclienteNew != null && !idclienteNew.equals(idclienteOld)) {
                idclienteNew.getReservahabitacionList().add(reservahabitacion);
                idclienteNew = em.merge(idclienteNew);
            }
            for (Habitacion habitacionListOldHabitacion : habitacionListOld) {
                if (!habitacionListNew.contains(habitacionListOldHabitacion)) {
                    habitacionListOldHabitacion.getReservahabitacionList().remove(reservahabitacion);
                    habitacionListOldHabitacion = em.merge(habitacionListOldHabitacion);
                }
            }
            for (Habitacion habitacionListNewHabitacion : habitacionListNew) {
                if (!habitacionListOld.contains(habitacionListNewHabitacion)) {
                    habitacionListNewHabitacion.getReservahabitacionList().add(reservahabitacion);
                    habitacionListNewHabitacion = em.merge(habitacionListNewHabitacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservahabitacion.getIdreservahabitacion();
                if (findReservahabitacion(id) == null) {
                    throw new NonexistentEntityException("The reservahabitacion with id " + id + " no longer exists.");
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
            Reservahabitacion reservahabitacion;
            try {
                reservahabitacion = em.getReference(Reservahabitacion.class, id);
                reservahabitacion.getIdreservahabitacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservahabitacion with id " + id + " no longer exists.", enfe);
            }
            Cliente idcliente = reservahabitacion.getIdcliente();
            if (idcliente != null) {
                idcliente.getReservahabitacionList().remove(reservahabitacion);
                idcliente = em.merge(idcliente);
            }
            List<Habitacion> habitacionList = reservahabitacion.getHabitacionList();
            for (Habitacion habitacionListHabitacion : habitacionList) {
                habitacionListHabitacion.getReservahabitacionList().remove(reservahabitacion);
                habitacionListHabitacion = em.merge(habitacionListHabitacion);
            }
            em.remove(reservahabitacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reservahabitacion> findReservahabitacionEntities() {
        return findReservahabitacionEntities(true, -1, -1);
    }

    public List<Reservahabitacion> findReservahabitacionEntities(int maxResults, int firstResult) {
        return findReservahabitacionEntities(false, maxResults, firstResult);
    }

    private List<Reservahabitacion> findReservahabitacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reservahabitacion.class));
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

    public Reservahabitacion findReservahabitacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reservahabitacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservahabitacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reservahabitacion> rt = cq.from(Reservahabitacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
