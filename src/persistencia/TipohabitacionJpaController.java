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
import modelo.Tipohabitacion;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author Usuario
 */
public class TipohabitacionJpaController implements Serializable {

    public TipohabitacionJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ArcadiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tipohabitacion tipohabitacion) throws PreexistingEntityException, Exception {
        if (tipohabitacion.getHabitacionList() == null) {
            tipohabitacion.setHabitacionList(new ArrayList<Habitacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Habitacion> attachedHabitacionList = new ArrayList<Habitacion>();
            for (Habitacion habitacionListHabitacionToAttach : tipohabitacion.getHabitacionList()) {
                habitacionListHabitacionToAttach = em.getReference(habitacionListHabitacionToAttach.getClass(), habitacionListHabitacionToAttach.getIdhabitacion());
                attachedHabitacionList.add(habitacionListHabitacionToAttach);
            }
            tipohabitacion.setHabitacionList(attachedHabitacionList);
            em.persist(tipohabitacion);
            for (Habitacion habitacionListHabitacion : tipohabitacion.getHabitacionList()) {
                Tipohabitacion oldNombretipohabitacionOfHabitacionListHabitacion = habitacionListHabitacion.getNombretipohabitacion();
                habitacionListHabitacion.setNombretipohabitacion(tipohabitacion);
                habitacionListHabitacion = em.merge(habitacionListHabitacion);
                if (oldNombretipohabitacionOfHabitacionListHabitacion != null) {
                    oldNombretipohabitacionOfHabitacionListHabitacion.getHabitacionList().remove(habitacionListHabitacion);
                    oldNombretipohabitacionOfHabitacionListHabitacion = em.merge(oldNombretipohabitacionOfHabitacionListHabitacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTipohabitacion(tipohabitacion.getNombretipohabitacion()) != null) {
                throw new PreexistingEntityException("Tipohabitacion " + tipohabitacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tipohabitacion tipohabitacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipohabitacion persistentTipohabitacion = em.find(Tipohabitacion.class, tipohabitacion.getNombretipohabitacion());
            List<Habitacion> habitacionListOld = persistentTipohabitacion.getHabitacionList();
            List<Habitacion> habitacionListNew = tipohabitacion.getHabitacionList();
            List<String> illegalOrphanMessages = null;
            for (Habitacion habitacionListOldHabitacion : habitacionListOld) {
                if (!habitacionListNew.contains(habitacionListOldHabitacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Habitacion " + habitacionListOldHabitacion + " since its nombretipohabitacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Habitacion> attachedHabitacionListNew = new ArrayList<Habitacion>();
            for (Habitacion habitacionListNewHabitacionToAttach : habitacionListNew) {
                habitacionListNewHabitacionToAttach = em.getReference(habitacionListNewHabitacionToAttach.getClass(), habitacionListNewHabitacionToAttach.getIdhabitacion());
                attachedHabitacionListNew.add(habitacionListNewHabitacionToAttach);
            }
            habitacionListNew = attachedHabitacionListNew;
            tipohabitacion.setHabitacionList(habitacionListNew);
            tipohabitacion = em.merge(tipohabitacion);
            for (Habitacion habitacionListNewHabitacion : habitacionListNew) {
                if (!habitacionListOld.contains(habitacionListNewHabitacion)) {
                    Tipohabitacion oldNombretipohabitacionOfHabitacionListNewHabitacion = habitacionListNewHabitacion.getNombretipohabitacion();
                    habitacionListNewHabitacion.setNombretipohabitacion(tipohabitacion);
                    habitacionListNewHabitacion = em.merge(habitacionListNewHabitacion);
                    if (oldNombretipohabitacionOfHabitacionListNewHabitacion != null && !oldNombretipohabitacionOfHabitacionListNewHabitacion.equals(tipohabitacion)) {
                        oldNombretipohabitacionOfHabitacionListNewHabitacion.getHabitacionList().remove(habitacionListNewHabitacion);
                        oldNombretipohabitacionOfHabitacionListNewHabitacion = em.merge(oldNombretipohabitacionOfHabitacionListNewHabitacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = tipohabitacion.getNombretipohabitacion();
                if (findTipohabitacion(id) == null) {
                    throw new NonexistentEntityException("The tipohabitacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipohabitacion tipohabitacion;
            try {
                tipohabitacion = em.getReference(Tipohabitacion.class, id);
                tipohabitacion.getNombretipohabitacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipohabitacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Habitacion> habitacionListOrphanCheck = tipohabitacion.getHabitacionList();
            for (Habitacion habitacionListOrphanCheckHabitacion : habitacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tipohabitacion (" + tipohabitacion + ") cannot be destroyed since the Habitacion " + habitacionListOrphanCheckHabitacion + " in its habitacionList field has a non-nullable nombretipohabitacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipohabitacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tipohabitacion> findTipohabitacionEntities() {
        return findTipohabitacionEntities(true, -1, -1);
    }

    public List<Tipohabitacion> findTipohabitacionEntities(int maxResults, int firstResult) {
        return findTipohabitacionEntities(false, maxResults, firstResult);
    }

    private List<Tipohabitacion> findTipohabitacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tipohabitacion.class));
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

    public Tipohabitacion findTipohabitacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tipohabitacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipohabitacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tipohabitacion> rt = cq.from(Tipohabitacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
