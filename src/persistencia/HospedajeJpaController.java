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
import modelo.Consumo;
import modelo.Hospedaje;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Usuario
 */
public class HospedajeJpaController implements Serializable {

    public HospedajeJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ArcadiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Hospedaje hospedaje) {
        if (hospedaje.getHabitacionList() == null) {
            hospedaje.setHabitacionList(new ArrayList<Habitacion>());
        }
        if (hospedaje.getConsumoList() == null) {
            hospedaje.setConsumoList(new ArrayList<Consumo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idcliente = hospedaje.getIdcliente();
            if (idcliente != null) {
                idcliente = em.getReference(idcliente.getClass(), idcliente.getIdcliente());
                hospedaje.setIdcliente(idcliente);
            }
            List<Habitacion> attachedHabitacionList = new ArrayList<Habitacion>();
            for (Habitacion habitacionListHabitacionToAttach : hospedaje.getHabitacionList()) {
                habitacionListHabitacionToAttach = em.getReference(habitacionListHabitacionToAttach.getClass(), habitacionListHabitacionToAttach.getIdhabitacion());
                attachedHabitacionList.add(habitacionListHabitacionToAttach);
            }
            hospedaje.setHabitacionList(attachedHabitacionList);
            List<Consumo> attachedConsumoList = new ArrayList<Consumo>();
            for (Consumo consumoListConsumoToAttach : hospedaje.getConsumoList()) {
                consumoListConsumoToAttach = em.getReference(consumoListConsumoToAttach.getClass(), consumoListConsumoToAttach.getConsumoPK());
                attachedConsumoList.add(consumoListConsumoToAttach);
            }
            hospedaje.setConsumoList(attachedConsumoList);
            em.persist(hospedaje);
            if (idcliente != null) {
                idcliente.getHospedajeList().add(hospedaje);
                idcliente = em.merge(idcliente);
            }
            for (Habitacion habitacionListHabitacion : hospedaje.getHabitacionList()) {
                habitacionListHabitacion.getHospedajeList().add(hospedaje);
                habitacionListHabitacion = em.merge(habitacionListHabitacion);
            }
            for (Consumo consumoListConsumo : hospedaje.getConsumoList()) {
                Hospedaje oldHospedajeOfConsumoListConsumo = consumoListConsumo.getHospedaje();
                consumoListConsumo.setHospedaje(hospedaje);
                consumoListConsumo = em.merge(consumoListConsumo);
                if (oldHospedajeOfConsumoListConsumo != null) {
                    oldHospedajeOfConsumoListConsumo.getConsumoList().remove(consumoListConsumo);
                    oldHospedajeOfConsumoListConsumo = em.merge(oldHospedajeOfConsumoListConsumo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Hospedaje hospedaje) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hospedaje persistentHospedaje = em.find(Hospedaje.class, hospedaje.getIdhospedaje());
            Cliente idclienteOld = persistentHospedaje.getIdcliente();
            Cliente idclienteNew = hospedaje.getIdcliente();
            List<Habitacion> habitacionListOld = persistentHospedaje.getHabitacionList();
            List<Habitacion> habitacionListNew = hospedaje.getHabitacionList();
            List<Consumo> consumoListOld = persistentHospedaje.getConsumoList();
            List<Consumo> consumoListNew = hospedaje.getConsumoList();
            List<String> illegalOrphanMessages = null;
            for (Consumo consumoListOldConsumo : consumoListOld) {
                if (!consumoListNew.contains(consumoListOldConsumo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Consumo " + consumoListOldConsumo + " since its hospedaje field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idclienteNew != null) {
                idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getIdcliente());
                hospedaje.setIdcliente(idclienteNew);
            }
            List<Habitacion> attachedHabitacionListNew = new ArrayList<Habitacion>();
            for (Habitacion habitacionListNewHabitacionToAttach : habitacionListNew) {
                habitacionListNewHabitacionToAttach = em.getReference(habitacionListNewHabitacionToAttach.getClass(), habitacionListNewHabitacionToAttach.getIdhabitacion());
                attachedHabitacionListNew.add(habitacionListNewHabitacionToAttach);
            }
            habitacionListNew = attachedHabitacionListNew;
            hospedaje.setHabitacionList(habitacionListNew);
            List<Consumo> attachedConsumoListNew = new ArrayList<Consumo>();
            for (Consumo consumoListNewConsumoToAttach : consumoListNew) {
                consumoListNewConsumoToAttach = em.getReference(consumoListNewConsumoToAttach.getClass(), consumoListNewConsumoToAttach.getConsumoPK());
                attachedConsumoListNew.add(consumoListNewConsumoToAttach);
            }
            consumoListNew = attachedConsumoListNew;
            hospedaje.setConsumoList(consumoListNew);
            hospedaje = em.merge(hospedaje);
            if (idclienteOld != null && !idclienteOld.equals(idclienteNew)) {
                idclienteOld.getHospedajeList().remove(hospedaje);
                idclienteOld = em.merge(idclienteOld);
            }
            if (idclienteNew != null && !idclienteNew.equals(idclienteOld)) {
                idclienteNew.getHospedajeList().add(hospedaje);
                idclienteNew = em.merge(idclienteNew);
            }
            for (Habitacion habitacionListOldHabitacion : habitacionListOld) {
                if (!habitacionListNew.contains(habitacionListOldHabitacion)) {
                    habitacionListOldHabitacion.getHospedajeList().remove(hospedaje);
                    habitacionListOldHabitacion = em.merge(habitacionListOldHabitacion);
                }
            }
            for (Habitacion habitacionListNewHabitacion : habitacionListNew) {
                if (!habitacionListOld.contains(habitacionListNewHabitacion)) {
                    habitacionListNewHabitacion.getHospedajeList().add(hospedaje);
                    habitacionListNewHabitacion = em.merge(habitacionListNewHabitacion);
                }
            }
            for (Consumo consumoListNewConsumo : consumoListNew) {
                if (!consumoListOld.contains(consumoListNewConsumo)) {
                    Hospedaje oldHospedajeOfConsumoListNewConsumo = consumoListNewConsumo.getHospedaje();
                    consumoListNewConsumo.setHospedaje(hospedaje);
                    consumoListNewConsumo = em.merge(consumoListNewConsumo);
                    if (oldHospedajeOfConsumoListNewConsumo != null && !oldHospedajeOfConsumoListNewConsumo.equals(hospedaje)) {
                        oldHospedajeOfConsumoListNewConsumo.getConsumoList().remove(consumoListNewConsumo);
                        oldHospedajeOfConsumoListNewConsumo = em.merge(oldHospedajeOfConsumoListNewConsumo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = hospedaje.getIdhospedaje();
                if (findHospedaje(id) == null) {
                    throw new NonexistentEntityException("The hospedaje with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hospedaje hospedaje;
            try {
                hospedaje = em.getReference(Hospedaje.class, id);
                hospedaje.getIdhospedaje();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hospedaje with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Consumo> consumoListOrphanCheck = hospedaje.getConsumoList();
            for (Consumo consumoListOrphanCheckConsumo : consumoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Hospedaje (" + hospedaje + ") cannot be destroyed since the Consumo " + consumoListOrphanCheckConsumo + " in its consumoList field has a non-nullable hospedaje field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Cliente idcliente = hospedaje.getIdcliente();
            if (idcliente != null) {
                idcliente.getHospedajeList().remove(hospedaje);
                idcliente = em.merge(idcliente);
            }
            List<Habitacion> habitacionList = hospedaje.getHabitacionList();
            for (Habitacion habitacionListHabitacion : habitacionList) {
                habitacionListHabitacion.getHospedajeList().remove(hospedaje);
                habitacionListHabitacion = em.merge(habitacionListHabitacion);
            }
            em.remove(hospedaje);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Hospedaje> findHospedajeEntities() {
        return findHospedajeEntities(true, -1, -1);
    }

    public List<Hospedaje> findHospedajeEntities(int maxResults, int firstResult) {
        return findHospedajeEntities(false, maxResults, firstResult);
    }

    private List<Hospedaje> findHospedajeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Hospedaje.class));
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

    public Hospedaje findHospedaje(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Hospedaje.class, id);
        } finally {
            em.close();
        }
    }

    public int getHospedajeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Hospedaje> rt = cq.from(Hospedaje.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
