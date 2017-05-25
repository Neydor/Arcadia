/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Consumo;
import modelo.ConsumoPK;
import modelo.Hospedaje;
import modelo.Productocafeteria;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author Usuario
 */
public class ConsumoJpaController implements Serializable {

    public ConsumoJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ArcadiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Consumo consumo) throws PreexistingEntityException, Exception {
        if (consumo.getConsumoPK() == null) {
            consumo.setConsumoPK(new ConsumoPK());
        }
        consumo.getConsumoPK().setIdproductocafeteria(consumo.getProductocafeteria().getIdproductocafeteria());
        consumo.getConsumoPK().setIdhospedaje(consumo.getHospedaje().getIdhospedaje());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hospedaje hospedaje = consumo.getHospedaje();
            if (hospedaje != null) {
                hospedaje = em.getReference(hospedaje.getClass(), hospedaje.getIdhospedaje());
                consumo.setHospedaje(hospedaje);
            }
            Productocafeteria productocafeteria = consumo.getProductocafeteria();
            if (productocafeteria != null) {
                productocafeteria = em.getReference(productocafeteria.getClass(), productocafeteria.getIdproductocafeteria());
                consumo.setProductocafeteria(productocafeteria);
            }
            em.persist(consumo);
            if (hospedaje != null) {
                hospedaje.getConsumoList().add(consumo);
                hospedaje = em.merge(hospedaje);
            }
            if (productocafeteria != null) {
                productocafeteria.getConsumoList().add(consumo);
                productocafeteria = em.merge(productocafeteria);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConsumo(consumo.getConsumoPK()) != null) {
                throw new PreexistingEntityException("Consumo " + consumo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Consumo consumo) throws NonexistentEntityException, Exception {
        consumo.getConsumoPK().setIdproductocafeteria(consumo.getProductocafeteria().getIdproductocafeteria());
        consumo.getConsumoPK().setIdhospedaje(consumo.getHospedaje().getIdhospedaje());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Consumo persistentConsumo = em.find(Consumo.class, consumo.getConsumoPK());
            Hospedaje hospedajeOld = persistentConsumo.getHospedaje();
            Hospedaje hospedajeNew = consumo.getHospedaje();
            Productocafeteria productocafeteriaOld = persistentConsumo.getProductocafeteria();
            Productocafeteria productocafeteriaNew = consumo.getProductocafeteria();
            if (hospedajeNew != null) {
                hospedajeNew = em.getReference(hospedajeNew.getClass(), hospedajeNew.getIdhospedaje());
                consumo.setHospedaje(hospedajeNew);
            }
            if (productocafeteriaNew != null) {
                productocafeteriaNew = em.getReference(productocafeteriaNew.getClass(), productocafeteriaNew.getIdproductocafeteria());
                consumo.setProductocafeteria(productocafeteriaNew);
            }
            consumo = em.merge(consumo);
            if (hospedajeOld != null && !hospedajeOld.equals(hospedajeNew)) {
                hospedajeOld.getConsumoList().remove(consumo);
                hospedajeOld = em.merge(hospedajeOld);
            }
            if (hospedajeNew != null && !hospedajeNew.equals(hospedajeOld)) {
                hospedajeNew.getConsumoList().add(consumo);
                hospedajeNew = em.merge(hospedajeNew);
            }
            if (productocafeteriaOld != null && !productocafeteriaOld.equals(productocafeteriaNew)) {
                productocafeteriaOld.getConsumoList().remove(consumo);
                productocafeteriaOld = em.merge(productocafeteriaOld);
            }
            if (productocafeteriaNew != null && !productocafeteriaNew.equals(productocafeteriaOld)) {
                productocafeteriaNew.getConsumoList().add(consumo);
                productocafeteriaNew = em.merge(productocafeteriaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ConsumoPK id = consumo.getConsumoPK();
                if (findConsumo(id) == null) {
                    throw new NonexistentEntityException("The consumo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ConsumoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Consumo consumo;
            try {
                consumo = em.getReference(Consumo.class, id);
                consumo.getConsumoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The consumo with id " + id + " no longer exists.", enfe);
            }
            Hospedaje hospedaje = consumo.getHospedaje();
            if (hospedaje != null) {
                hospedaje.getConsumoList().remove(consumo);
                hospedaje = em.merge(hospedaje);
            }
            Productocafeteria productocafeteria = consumo.getProductocafeteria();
            if (productocafeteria != null) {
                productocafeteria.getConsumoList().remove(consumo);
                productocafeteria = em.merge(productocafeteria);
            }
            em.remove(consumo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Consumo> findConsumoEntities() {
        return findConsumoEntities(true, -1, -1);
    }

    public List<Consumo> findConsumoEntities(int maxResults, int firstResult) {
        return findConsumoEntities(false, maxResults, firstResult);
    }

    private List<Consumo> findConsumoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Consumo.class));
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

    public Consumo findConsumo(ConsumoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Consumo.class, id);
        } finally {
            em.close();
        }
    }

    public int getConsumoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Consumo> rt = cq.from(Consumo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
