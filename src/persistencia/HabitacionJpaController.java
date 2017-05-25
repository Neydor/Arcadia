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
import modelo.Tipohabitacion;
import modelo.Reservahabitacion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Hospedaje;
import modelo.Comodidadasociada;
import modelo.Habitacion;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author Usuario
 */
public class HabitacionJpaController implements Serializable {

    public HabitacionJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ArcadiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Habitacion habitacion) throws PreexistingEntityException, Exception {
        if (habitacion.getReservahabitacionList() == null) {
            habitacion.setReservahabitacionList(new ArrayList<Reservahabitacion>());
        }
        if (habitacion.getHospedajeList() == null) {
            habitacion.setHospedajeList(new ArrayList<Hospedaje>());
        }
        if (habitacion.getComodidadasociadaList() == null) {
            habitacion.setComodidadasociadaList(new ArrayList<Comodidadasociada>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tipohabitacion nombretipohabitacion = habitacion.getNombretipohabitacion();
            if (nombretipohabitacion != null) {
                nombretipohabitacion = em.getReference(nombretipohabitacion.getClass(), nombretipohabitacion.getNombretipohabitacion());
                habitacion.setNombretipohabitacion(nombretipohabitacion);
            }
            List<Reservahabitacion> attachedReservahabitacionList = new ArrayList<Reservahabitacion>();
            for (Reservahabitacion reservahabitacionListReservahabitacionToAttach : habitacion.getReservahabitacionList()) {
                reservahabitacionListReservahabitacionToAttach = em.getReference(reservahabitacionListReservahabitacionToAttach.getClass(), reservahabitacionListReservahabitacionToAttach.getIdreservahabitacion());
                attachedReservahabitacionList.add(reservahabitacionListReservahabitacionToAttach);
            }
            habitacion.setReservahabitacionList(attachedReservahabitacionList);
            List<Hospedaje> attachedHospedajeList = new ArrayList<Hospedaje>();
            for (Hospedaje hospedajeListHospedajeToAttach : habitacion.getHospedajeList()) {
                hospedajeListHospedajeToAttach = em.getReference(hospedajeListHospedajeToAttach.getClass(), hospedajeListHospedajeToAttach.getIdhospedaje());
                attachedHospedajeList.add(hospedajeListHospedajeToAttach);
            }
            habitacion.setHospedajeList(attachedHospedajeList);
            List<Comodidadasociada> attachedComodidadasociadaList = new ArrayList<Comodidadasociada>();
            for (Comodidadasociada comodidadasociadaListComodidadasociadaToAttach : habitacion.getComodidadasociadaList()) {
                comodidadasociadaListComodidadasociadaToAttach = em.getReference(comodidadasociadaListComodidadasociadaToAttach.getClass(), comodidadasociadaListComodidadasociadaToAttach.getIdcomodidad());
                attachedComodidadasociadaList.add(comodidadasociadaListComodidadasociadaToAttach);
            }
            habitacion.setComodidadasociadaList(attachedComodidadasociadaList);
            em.persist(habitacion);
            if (nombretipohabitacion != null) {
                nombretipohabitacion.getHabitacionList().add(habitacion);
                nombretipohabitacion = em.merge(nombretipohabitacion);
            }
            for (Reservahabitacion reservahabitacionListReservahabitacion : habitacion.getReservahabitacionList()) {
                reservahabitacionListReservahabitacion.getHabitacionList().add(habitacion);
                reservahabitacionListReservahabitacion = em.merge(reservahabitacionListReservahabitacion);
            }
            for (Hospedaje hospedajeListHospedaje : habitacion.getHospedajeList()) {
                hospedajeListHospedaje.getHabitacionList().add(habitacion);
                hospedajeListHospedaje = em.merge(hospedajeListHospedaje);
            }
            for (Comodidadasociada comodidadasociadaListComodidadasociada : habitacion.getComodidadasociadaList()) {
                comodidadasociadaListComodidadasociada.getHabitacionList().add(habitacion);
                comodidadasociadaListComodidadasociada = em.merge(comodidadasociadaListComodidadasociada);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHabitacion(habitacion.getIdhabitacion()) != null) {
                throw new PreexistingEntityException("Habitacion " + habitacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Habitacion habitacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Habitacion persistentHabitacion = em.find(Habitacion.class, habitacion.getIdhabitacion());
            Tipohabitacion nombretipohabitacionOld = persistentHabitacion.getNombretipohabitacion();
            Tipohabitacion nombretipohabitacionNew = habitacion.getNombretipohabitacion();
            List<Reservahabitacion> reservahabitacionListOld = persistentHabitacion.getReservahabitacionList();
            List<Reservahabitacion> reservahabitacionListNew = habitacion.getReservahabitacionList();
            List<Hospedaje> hospedajeListOld = persistentHabitacion.getHospedajeList();
            List<Hospedaje> hospedajeListNew = habitacion.getHospedajeList();
            List<Comodidadasociada> comodidadasociadaListOld = persistentHabitacion.getComodidadasociadaList();
            List<Comodidadasociada> comodidadasociadaListNew = habitacion.getComodidadasociadaList();
            if (nombretipohabitacionNew != null) {
                nombretipohabitacionNew = em.getReference(nombretipohabitacionNew.getClass(), nombretipohabitacionNew.getNombretipohabitacion());
                habitacion.setNombretipohabitacion(nombretipohabitacionNew);
            }
            List<Reservahabitacion> attachedReservahabitacionListNew = new ArrayList<Reservahabitacion>();
            for (Reservahabitacion reservahabitacionListNewReservahabitacionToAttach : reservahabitacionListNew) {
                reservahabitacionListNewReservahabitacionToAttach = em.getReference(reservahabitacionListNewReservahabitacionToAttach.getClass(), reservahabitacionListNewReservahabitacionToAttach.getIdreservahabitacion());
                attachedReservahabitacionListNew.add(reservahabitacionListNewReservahabitacionToAttach);
            }
            reservahabitacionListNew = attachedReservahabitacionListNew;
            habitacion.setReservahabitacionList(reservahabitacionListNew);
            List<Hospedaje> attachedHospedajeListNew = new ArrayList<Hospedaje>();
            for (Hospedaje hospedajeListNewHospedajeToAttach : hospedajeListNew) {
                hospedajeListNewHospedajeToAttach = em.getReference(hospedajeListNewHospedajeToAttach.getClass(), hospedajeListNewHospedajeToAttach.getIdhospedaje());
                attachedHospedajeListNew.add(hospedajeListNewHospedajeToAttach);
            }
            hospedajeListNew = attachedHospedajeListNew;
            habitacion.setHospedajeList(hospedajeListNew);
            List<Comodidadasociada> attachedComodidadasociadaListNew = new ArrayList<Comodidadasociada>();
            for (Comodidadasociada comodidadasociadaListNewComodidadasociadaToAttach : comodidadasociadaListNew) {
                comodidadasociadaListNewComodidadasociadaToAttach = em.getReference(comodidadasociadaListNewComodidadasociadaToAttach.getClass(), comodidadasociadaListNewComodidadasociadaToAttach.getIdcomodidad());
                attachedComodidadasociadaListNew.add(comodidadasociadaListNewComodidadasociadaToAttach);
            }
            comodidadasociadaListNew = attachedComodidadasociadaListNew;
            habitacion.setComodidadasociadaList(comodidadasociadaListNew);
            habitacion = em.merge(habitacion);
            if (nombretipohabitacionOld != null && !nombretipohabitacionOld.equals(nombretipohabitacionNew)) {
                nombretipohabitacionOld.getHabitacionList().remove(habitacion);
                nombretipohabitacionOld = em.merge(nombretipohabitacionOld);
            }
            if (nombretipohabitacionNew != null && !nombretipohabitacionNew.equals(nombretipohabitacionOld)) {
                nombretipohabitacionNew.getHabitacionList().add(habitacion);
                nombretipohabitacionNew = em.merge(nombretipohabitacionNew);
            }
            for (Reservahabitacion reservahabitacionListOldReservahabitacion : reservahabitacionListOld) {
                if (!reservahabitacionListNew.contains(reservahabitacionListOldReservahabitacion)) {
                    reservahabitacionListOldReservahabitacion.getHabitacionList().remove(habitacion);
                    reservahabitacionListOldReservahabitacion = em.merge(reservahabitacionListOldReservahabitacion);
                }
            }
            for (Reservahabitacion reservahabitacionListNewReservahabitacion : reservahabitacionListNew) {
                if (!reservahabitacionListOld.contains(reservahabitacionListNewReservahabitacion)) {
                    reservahabitacionListNewReservahabitacion.getHabitacionList().add(habitacion);
                    reservahabitacionListNewReservahabitacion = em.merge(reservahabitacionListNewReservahabitacion);
                }
            }
            for (Hospedaje hospedajeListOldHospedaje : hospedajeListOld) {
                if (!hospedajeListNew.contains(hospedajeListOldHospedaje)) {
                    hospedajeListOldHospedaje.getHabitacionList().remove(habitacion);
                    hospedajeListOldHospedaje = em.merge(hospedajeListOldHospedaje);
                }
            }
            for (Hospedaje hospedajeListNewHospedaje : hospedajeListNew) {
                if (!hospedajeListOld.contains(hospedajeListNewHospedaje)) {
                    hospedajeListNewHospedaje.getHabitacionList().add(habitacion);
                    hospedajeListNewHospedaje = em.merge(hospedajeListNewHospedaje);
                }
            }
            for (Comodidadasociada comodidadasociadaListOldComodidadasociada : comodidadasociadaListOld) {
                if (!comodidadasociadaListNew.contains(comodidadasociadaListOldComodidadasociada)) {
                    comodidadasociadaListOldComodidadasociada.getHabitacionList().remove(habitacion);
                    comodidadasociadaListOldComodidadasociada = em.merge(comodidadasociadaListOldComodidadasociada);
                }
            }
            for (Comodidadasociada comodidadasociadaListNewComodidadasociada : comodidadasociadaListNew) {
                if (!comodidadasociadaListOld.contains(comodidadasociadaListNewComodidadasociada)) {
                    comodidadasociadaListNewComodidadasociada.getHabitacionList().add(habitacion);
                    comodidadasociadaListNewComodidadasociada = em.merge(comodidadasociadaListNewComodidadasociada);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = habitacion.getIdhabitacion();
                if (findHabitacion(id) == null) {
                    throw new NonexistentEntityException("The habitacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Habitacion habitacion;
            try {
                habitacion = em.getReference(Habitacion.class, id);
                habitacion.getIdhabitacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The habitacion with id " + id + " no longer exists.", enfe);
            }
            Tipohabitacion nombretipohabitacion = habitacion.getNombretipohabitacion();
            if (nombretipohabitacion != null) {
                nombretipohabitacion.getHabitacionList().remove(habitacion);
                nombretipohabitacion = em.merge(nombretipohabitacion);
            }
            List<Reservahabitacion> reservahabitacionList = habitacion.getReservahabitacionList();
            for (Reservahabitacion reservahabitacionListReservahabitacion : reservahabitacionList) {
                reservahabitacionListReservahabitacion.getHabitacionList().remove(habitacion);
                reservahabitacionListReservahabitacion = em.merge(reservahabitacionListReservahabitacion);
            }
            List<Hospedaje> hospedajeList = habitacion.getHospedajeList();
            for (Hospedaje hospedajeListHospedaje : hospedajeList) {
                hospedajeListHospedaje.getHabitacionList().remove(habitacion);
                hospedajeListHospedaje = em.merge(hospedajeListHospedaje);
            }
            List<Comodidadasociada> comodidadasociadaList = habitacion.getComodidadasociadaList();
            for (Comodidadasociada comodidadasociadaListComodidadasociada : comodidadasociadaList) {
                comodidadasociadaListComodidadasociada.getHabitacionList().remove(habitacion);
                comodidadasociadaListComodidadasociada = em.merge(comodidadasociadaListComodidadasociada);
            }
            em.remove(habitacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Habitacion> findHabitacionEntities() {
        return findHabitacionEntities(true, -1, -1);
    }

    public List<Habitacion> findHabitacionEntities(int maxResults, int firstResult) {
        return findHabitacionEntities(false, maxResults, firstResult);
    }

    private List<Habitacion> findHabitacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Habitacion.class));
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

    public Habitacion findHabitacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Habitacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getHabitacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Habitacion> rt = cq.from(Habitacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
