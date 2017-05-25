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
import modelo.Hospedaje;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Cliente;
import modelo.Reservasalon;
import modelo.Reservahabitacion;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author Usuario
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ArcadiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) throws PreexistingEntityException, Exception {
        if (cliente.getHospedajeList() == null) {
            cliente.setHospedajeList(new ArrayList<Hospedaje>());
        }
        if (cliente.getReservasalonList() == null) {
            cliente.setReservasalonList(new ArrayList<Reservasalon>());
        }
        if (cliente.getReservahabitacionList() == null) {
            cliente.setReservahabitacionList(new ArrayList<Reservahabitacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Hospedaje> attachedHospedajeList = new ArrayList<Hospedaje>();
            for (Hospedaje hospedajeListHospedajeToAttach : cliente.getHospedajeList()) {
                hospedajeListHospedajeToAttach = em.getReference(hospedajeListHospedajeToAttach.getClass(), hospedajeListHospedajeToAttach.getIdhospedaje());
                attachedHospedajeList.add(hospedajeListHospedajeToAttach);
            }
            cliente.setHospedajeList(attachedHospedajeList);
            List<Reservasalon> attachedReservasalonList = new ArrayList<Reservasalon>();
            for (Reservasalon reservasalonListReservasalonToAttach : cliente.getReservasalonList()) {
                reservasalonListReservasalonToAttach = em.getReference(reservasalonListReservasalonToAttach.getClass(), reservasalonListReservasalonToAttach.getIdreservasalon());
                attachedReservasalonList.add(reservasalonListReservasalonToAttach);
            }
            cliente.setReservasalonList(attachedReservasalonList);
            List<Reservahabitacion> attachedReservahabitacionList = new ArrayList<Reservahabitacion>();
            for (Reservahabitacion reservahabitacionListReservahabitacionToAttach : cliente.getReservahabitacionList()) {
                reservahabitacionListReservahabitacionToAttach = em.getReference(reservahabitacionListReservahabitacionToAttach.getClass(), reservahabitacionListReservahabitacionToAttach.getIdreservahabitacion());
                attachedReservahabitacionList.add(reservahabitacionListReservahabitacionToAttach);
            }
            cliente.setReservahabitacionList(attachedReservahabitacionList);
            em.persist(cliente);
            for (Hospedaje hospedajeListHospedaje : cliente.getHospedajeList()) {
                Cliente oldIdclienteOfHospedajeListHospedaje = hospedajeListHospedaje.getIdcliente();
                hospedajeListHospedaje.setIdcliente(cliente);
                hospedajeListHospedaje = em.merge(hospedajeListHospedaje);
                if (oldIdclienteOfHospedajeListHospedaje != null) {
                    oldIdclienteOfHospedajeListHospedaje.getHospedajeList().remove(hospedajeListHospedaje);
                    oldIdclienteOfHospedajeListHospedaje = em.merge(oldIdclienteOfHospedajeListHospedaje);
                }
            }
            for (Reservasalon reservasalonListReservasalon : cliente.getReservasalonList()) {
                Cliente oldIdclienteOfReservasalonListReservasalon = reservasalonListReservasalon.getIdcliente();
                reservasalonListReservasalon.setIdcliente(cliente);
                reservasalonListReservasalon = em.merge(reservasalonListReservasalon);
                if (oldIdclienteOfReservasalonListReservasalon != null) {
                    oldIdclienteOfReservasalonListReservasalon.getReservasalonList().remove(reservasalonListReservasalon);
                    oldIdclienteOfReservasalonListReservasalon = em.merge(oldIdclienteOfReservasalonListReservasalon);
                }
            }
            for (Reservahabitacion reservahabitacionListReservahabitacion : cliente.getReservahabitacionList()) {
                Cliente oldIdclienteOfReservahabitacionListReservahabitacion = reservahabitacionListReservahabitacion.getIdcliente();
                reservahabitacionListReservahabitacion.setIdcliente(cliente);
                reservahabitacionListReservahabitacion = em.merge(reservahabitacionListReservahabitacion);
                if (oldIdclienteOfReservahabitacionListReservahabitacion != null) {
                    oldIdclienteOfReservahabitacionListReservahabitacion.getReservahabitacionList().remove(reservahabitacionListReservahabitacion);
                    oldIdclienteOfReservahabitacionListReservahabitacion = em.merge(oldIdclienteOfReservahabitacionListReservahabitacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCliente(cliente.getIdcliente()) != null) {
                throw new PreexistingEntityException("Cliente " + cliente + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getIdcliente());
            List<Hospedaje> hospedajeListOld = persistentCliente.getHospedajeList();
            List<Hospedaje> hospedajeListNew = cliente.getHospedajeList();
            List<Reservasalon> reservasalonListOld = persistentCliente.getReservasalonList();
            List<Reservasalon> reservasalonListNew = cliente.getReservasalonList();
            List<Reservahabitacion> reservahabitacionListOld = persistentCliente.getReservahabitacionList();
            List<Reservahabitacion> reservahabitacionListNew = cliente.getReservahabitacionList();
            List<String> illegalOrphanMessages = null;
            for (Hospedaje hospedajeListOldHospedaje : hospedajeListOld) {
                if (!hospedajeListNew.contains(hospedajeListOldHospedaje)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Hospedaje " + hospedajeListOldHospedaje + " since its idcliente field is not nullable.");
                }
            }
            for (Reservasalon reservasalonListOldReservasalon : reservasalonListOld) {
                if (!reservasalonListNew.contains(reservasalonListOldReservasalon)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservasalon " + reservasalonListOldReservasalon + " since its idcliente field is not nullable.");
                }
            }
            for (Reservahabitacion reservahabitacionListOldReservahabitacion : reservahabitacionListOld) {
                if (!reservahabitacionListNew.contains(reservahabitacionListOldReservahabitacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Reservahabitacion " + reservahabitacionListOldReservahabitacion + " since its idcliente field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Hospedaje> attachedHospedajeListNew = new ArrayList<Hospedaje>();
            for (Hospedaje hospedajeListNewHospedajeToAttach : hospedajeListNew) {
                hospedajeListNewHospedajeToAttach = em.getReference(hospedajeListNewHospedajeToAttach.getClass(), hospedajeListNewHospedajeToAttach.getIdhospedaje());
                attachedHospedajeListNew.add(hospedajeListNewHospedajeToAttach);
            }
            hospedajeListNew = attachedHospedajeListNew;
            cliente.setHospedajeList(hospedajeListNew);
            List<Reservasalon> attachedReservasalonListNew = new ArrayList<Reservasalon>();
            for (Reservasalon reservasalonListNewReservasalonToAttach : reservasalonListNew) {
                reservasalonListNewReservasalonToAttach = em.getReference(reservasalonListNewReservasalonToAttach.getClass(), reservasalonListNewReservasalonToAttach.getIdreservasalon());
                attachedReservasalonListNew.add(reservasalonListNewReservasalonToAttach);
            }
            reservasalonListNew = attachedReservasalonListNew;
            cliente.setReservasalonList(reservasalonListNew);
            List<Reservahabitacion> attachedReservahabitacionListNew = new ArrayList<Reservahabitacion>();
            for (Reservahabitacion reservahabitacionListNewReservahabitacionToAttach : reservahabitacionListNew) {
                reservahabitacionListNewReservahabitacionToAttach = em.getReference(reservahabitacionListNewReservahabitacionToAttach.getClass(), reservahabitacionListNewReservahabitacionToAttach.getIdreservahabitacion());
                attachedReservahabitacionListNew.add(reservahabitacionListNewReservahabitacionToAttach);
            }
            reservahabitacionListNew = attachedReservahabitacionListNew;
            cliente.setReservahabitacionList(reservahabitacionListNew);
            cliente = em.merge(cliente);
            for (Hospedaje hospedajeListNewHospedaje : hospedajeListNew) {
                if (!hospedajeListOld.contains(hospedajeListNewHospedaje)) {
                    Cliente oldIdclienteOfHospedajeListNewHospedaje = hospedajeListNewHospedaje.getIdcliente();
                    hospedajeListNewHospedaje.setIdcliente(cliente);
                    hospedajeListNewHospedaje = em.merge(hospedajeListNewHospedaje);
                    if (oldIdclienteOfHospedajeListNewHospedaje != null && !oldIdclienteOfHospedajeListNewHospedaje.equals(cliente)) {
                        oldIdclienteOfHospedajeListNewHospedaje.getHospedajeList().remove(hospedajeListNewHospedaje);
                        oldIdclienteOfHospedajeListNewHospedaje = em.merge(oldIdclienteOfHospedajeListNewHospedaje);
                    }
                }
            }
            for (Reservasalon reservasalonListNewReservasalon : reservasalonListNew) {
                if (!reservasalonListOld.contains(reservasalonListNewReservasalon)) {
                    Cliente oldIdclienteOfReservasalonListNewReservasalon = reservasalonListNewReservasalon.getIdcliente();
                    reservasalonListNewReservasalon.setIdcliente(cliente);
                    reservasalonListNewReservasalon = em.merge(reservasalonListNewReservasalon);
                    if (oldIdclienteOfReservasalonListNewReservasalon != null && !oldIdclienteOfReservasalonListNewReservasalon.equals(cliente)) {
                        oldIdclienteOfReservasalonListNewReservasalon.getReservasalonList().remove(reservasalonListNewReservasalon);
                        oldIdclienteOfReservasalonListNewReservasalon = em.merge(oldIdclienteOfReservasalonListNewReservasalon);
                    }
                }
            }
            for (Reservahabitacion reservahabitacionListNewReservahabitacion : reservahabitacionListNew) {
                if (!reservahabitacionListOld.contains(reservahabitacionListNewReservahabitacion)) {
                    Cliente oldIdclienteOfReservahabitacionListNewReservahabitacion = reservahabitacionListNewReservahabitacion.getIdcliente();
                    reservahabitacionListNewReservahabitacion.setIdcliente(cliente);
                    reservahabitacionListNewReservahabitacion = em.merge(reservahabitacionListNewReservahabitacion);
                    if (oldIdclienteOfReservahabitacionListNewReservahabitacion != null && !oldIdclienteOfReservahabitacionListNewReservahabitacion.equals(cliente)) {
                        oldIdclienteOfReservahabitacionListNewReservahabitacion.getReservahabitacionList().remove(reservahabitacionListNewReservahabitacion);
                        oldIdclienteOfReservahabitacionListNewReservahabitacion = em.merge(oldIdclienteOfReservahabitacionListNewReservahabitacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = cliente.getIdcliente();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getIdcliente();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Hospedaje> hospedajeListOrphanCheck = cliente.getHospedajeList();
            for (Hospedaje hospedajeListOrphanCheckHospedaje : hospedajeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Hospedaje " + hospedajeListOrphanCheckHospedaje + " in its hospedajeList field has a non-nullable idcliente field.");
            }
            List<Reservasalon> reservasalonListOrphanCheck = cliente.getReservasalonList();
            for (Reservasalon reservasalonListOrphanCheckReservasalon : reservasalonListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Reservasalon " + reservasalonListOrphanCheckReservasalon + " in its reservasalonList field has a non-nullable idcliente field.");
            }
            List<Reservahabitacion> reservahabitacionListOrphanCheck = cliente.getReservahabitacionList();
            for (Reservahabitacion reservahabitacionListOrphanCheckReservahabitacion : reservahabitacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cliente (" + cliente + ") cannot be destroyed since the Reservahabitacion " + reservahabitacionListOrphanCheckReservahabitacion + " in its reservahabitacionList field has a non-nullable idcliente field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
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

    public Cliente findCliente(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
