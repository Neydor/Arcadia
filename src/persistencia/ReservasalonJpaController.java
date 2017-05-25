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
import modelo.Salondeeventos;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Reservasalon;
import persistencia.exceptions.NonexistentEntityException;

/**
 *
 * @author Usuario
 */
public class ReservasalonJpaController implements Serializable {

    public ReservasalonJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ArcadiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Reservasalon reservasalon) {
        if (reservasalon.getSalondeeventosList() == null) {
            reservasalon.setSalondeeventosList(new ArrayList<Salondeeventos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente idcliente = reservasalon.getIdcliente();
            if (idcliente != null) {
                idcliente = em.getReference(idcliente.getClass(), idcliente.getIdcliente());
                reservasalon.setIdcliente(idcliente);
            }
            List<Salondeeventos> attachedSalondeeventosList = new ArrayList<Salondeeventos>();
            for (Salondeeventos salondeeventosListSalondeeventosToAttach : reservasalon.getSalondeeventosList()) {
                salondeeventosListSalondeeventosToAttach = em.getReference(salondeeventosListSalondeeventosToAttach.getClass(), salondeeventosListSalondeeventosToAttach.getIdsalon());
                attachedSalondeeventosList.add(salondeeventosListSalondeeventosToAttach);
            }
            reservasalon.setSalondeeventosList(attachedSalondeeventosList);
            em.persist(reservasalon);
            if (idcliente != null) {
                idcliente.getReservasalonList().add(reservasalon);
                idcliente = em.merge(idcliente);
            }
            for (Salondeeventos salondeeventosListSalondeeventos : reservasalon.getSalondeeventosList()) {
                salondeeventosListSalondeeventos.getReservasalonList().add(reservasalon);
                salondeeventosListSalondeeventos = em.merge(salondeeventosListSalondeeventos);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Reservasalon reservasalon) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Reservasalon persistentReservasalon = em.find(Reservasalon.class, reservasalon.getIdreservasalon());
            Cliente idclienteOld = persistentReservasalon.getIdcliente();
            Cliente idclienteNew = reservasalon.getIdcliente();
            List<Salondeeventos> salondeeventosListOld = persistentReservasalon.getSalondeeventosList();
            List<Salondeeventos> salondeeventosListNew = reservasalon.getSalondeeventosList();
            if (idclienteNew != null) {
                idclienteNew = em.getReference(idclienteNew.getClass(), idclienteNew.getIdcliente());
                reservasalon.setIdcliente(idclienteNew);
            }
            List<Salondeeventos> attachedSalondeeventosListNew = new ArrayList<Salondeeventos>();
            for (Salondeeventos salondeeventosListNewSalondeeventosToAttach : salondeeventosListNew) {
                salondeeventosListNewSalondeeventosToAttach = em.getReference(salondeeventosListNewSalondeeventosToAttach.getClass(), salondeeventosListNewSalondeeventosToAttach.getIdsalon());
                attachedSalondeeventosListNew.add(salondeeventosListNewSalondeeventosToAttach);
            }
            salondeeventosListNew = attachedSalondeeventosListNew;
            reservasalon.setSalondeeventosList(salondeeventosListNew);
            reservasalon = em.merge(reservasalon);
            if (idclienteOld != null && !idclienteOld.equals(idclienteNew)) {
                idclienteOld.getReservasalonList().remove(reservasalon);
                idclienteOld = em.merge(idclienteOld);
            }
            if (idclienteNew != null && !idclienteNew.equals(idclienteOld)) {
                idclienteNew.getReservasalonList().add(reservasalon);
                idclienteNew = em.merge(idclienteNew);
            }
            for (Salondeeventos salondeeventosListOldSalondeeventos : salondeeventosListOld) {
                if (!salondeeventosListNew.contains(salondeeventosListOldSalondeeventos)) {
                    salondeeventosListOldSalondeeventos.getReservasalonList().remove(reservasalon);
                    salondeeventosListOldSalondeeventos = em.merge(salondeeventosListOldSalondeeventos);
                }
            }
            for (Salondeeventos salondeeventosListNewSalondeeventos : salondeeventosListNew) {
                if (!salondeeventosListOld.contains(salondeeventosListNewSalondeeventos)) {
                    salondeeventosListNewSalondeeventos.getReservasalonList().add(reservasalon);
                    salondeeventosListNewSalondeeventos = em.merge(salondeeventosListNewSalondeeventos);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = reservasalon.getIdreservasalon();
                if (findReservasalon(id) == null) {
                    throw new NonexistentEntityException("The reservasalon with id " + id + " no longer exists.");
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
            Reservasalon reservasalon;
            try {
                reservasalon = em.getReference(Reservasalon.class, id);
                reservasalon.getIdreservasalon();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The reservasalon with id " + id + " no longer exists.", enfe);
            }
            Cliente idcliente = reservasalon.getIdcliente();
            if (idcliente != null) {
                idcliente.getReservasalonList().remove(reservasalon);
                idcliente = em.merge(idcliente);
            }
            List<Salondeeventos> salondeeventosList = reservasalon.getSalondeeventosList();
            for (Salondeeventos salondeeventosListSalondeeventos : salondeeventosList) {
                salondeeventosListSalondeeventos.getReservasalonList().remove(reservasalon);
                salondeeventosListSalondeeventos = em.merge(salondeeventosListSalondeeventos);
            }
            em.remove(reservasalon);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Reservasalon> findReservasalonEntities() {
        return findReservasalonEntities(true, -1, -1);
    }

    public List<Reservasalon> findReservasalonEntities(int maxResults, int firstResult) {
        return findReservasalonEntities(false, maxResults, firstResult);
    }

    private List<Reservasalon> findReservasalonEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Reservasalon.class));
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

    public Reservasalon findReservasalon(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reservasalon.class, id);
        } finally {
            em.close();
        }
    }

    public int getReservasalonCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Reservasalon> rt = cq.from(Reservasalon.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
