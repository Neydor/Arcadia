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
import modelo.Consumo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Productocafeteria;
import persistencia.exceptions.IllegalOrphanException;
import persistencia.exceptions.NonexistentEntityException;
import persistencia.exceptions.PreexistingEntityException;

/**
 *
 * @author Usuario
 */
public class ProductocafeteriaJpaController implements Serializable {

    public ProductocafeteriaJpaController() {
        this.emf = Persistence.createEntityManagerFactory("ArcadiaPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Productocafeteria productocafeteria) throws PreexistingEntityException, Exception {
        if (productocafeteria.getConsumoList() == null) {
            productocafeteria.setConsumoList(new ArrayList<Consumo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Consumo> attachedConsumoList = new ArrayList<Consumo>();
            for (Consumo consumoListConsumoToAttach : productocafeteria.getConsumoList()) {
                consumoListConsumoToAttach = em.getReference(consumoListConsumoToAttach.getClass(), consumoListConsumoToAttach.getConsumoPK());
                attachedConsumoList.add(consumoListConsumoToAttach);
            }
            productocafeteria.setConsumoList(attachedConsumoList);
            em.persist(productocafeteria);
            for (Consumo consumoListConsumo : productocafeteria.getConsumoList()) {
                Productocafeteria oldProductocafeteriaOfConsumoListConsumo = consumoListConsumo.getProductocafeteria();
                consumoListConsumo.setProductocafeteria(productocafeteria);
                consumoListConsumo = em.merge(consumoListConsumo);
                if (oldProductocafeteriaOfConsumoListConsumo != null) {
                    oldProductocafeteriaOfConsumoListConsumo.getConsumoList().remove(consumoListConsumo);
                    oldProductocafeteriaOfConsumoListConsumo = em.merge(oldProductocafeteriaOfConsumoListConsumo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProductocafeteria(productocafeteria.getIdproductocafeteria()) != null) {
                throw new PreexistingEntityException("Productocafeteria " + productocafeteria + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Productocafeteria productocafeteria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Productocafeteria persistentProductocafeteria = em.find(Productocafeteria.class, productocafeteria.getIdproductocafeteria());
            List<Consumo> consumoListOld = persistentProductocafeteria.getConsumoList();
            List<Consumo> consumoListNew = productocafeteria.getConsumoList();
            List<String> illegalOrphanMessages = null;
            for (Consumo consumoListOldConsumo : consumoListOld) {
                if (!consumoListNew.contains(consumoListOldConsumo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Consumo " + consumoListOldConsumo + " since its productocafeteria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Consumo> attachedConsumoListNew = new ArrayList<Consumo>();
            for (Consumo consumoListNewConsumoToAttach : consumoListNew) {
                consumoListNewConsumoToAttach = em.getReference(consumoListNewConsumoToAttach.getClass(), consumoListNewConsumoToAttach.getConsumoPK());
                attachedConsumoListNew.add(consumoListNewConsumoToAttach);
            }
            consumoListNew = attachedConsumoListNew;
            productocafeteria.setConsumoList(consumoListNew);
            productocafeteria = em.merge(productocafeteria);
            for (Consumo consumoListNewConsumo : consumoListNew) {
                if (!consumoListOld.contains(consumoListNewConsumo)) {
                    Productocafeteria oldProductocafeteriaOfConsumoListNewConsumo = consumoListNewConsumo.getProductocafeteria();
                    consumoListNewConsumo.setProductocafeteria(productocafeteria);
                    consumoListNewConsumo = em.merge(consumoListNewConsumo);
                    if (oldProductocafeteriaOfConsumoListNewConsumo != null && !oldProductocafeteriaOfConsumoListNewConsumo.equals(productocafeteria)) {
                        oldProductocafeteriaOfConsumoListNewConsumo.getConsumoList().remove(consumoListNewConsumo);
                        oldProductocafeteriaOfConsumoListNewConsumo = em.merge(oldProductocafeteriaOfConsumoListNewConsumo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = productocafeteria.getIdproductocafeteria();
                if (findProductocafeteria(id) == null) {
                    throw new NonexistentEntityException("The productocafeteria with id " + id + " no longer exists.");
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
            Productocafeteria productocafeteria;
            try {
                productocafeteria = em.getReference(Productocafeteria.class, id);
                productocafeteria.getIdproductocafeteria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productocafeteria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Consumo> consumoListOrphanCheck = productocafeteria.getConsumoList();
            for (Consumo consumoListOrphanCheckConsumo : consumoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Productocafeteria (" + productocafeteria + ") cannot be destroyed since the Consumo " + consumoListOrphanCheckConsumo + " in its consumoList field has a non-nullable productocafeteria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(productocafeteria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Productocafeteria> findProductocafeteriaEntities() {
        return findProductocafeteriaEntities(true, -1, -1);
    }

    public List<Productocafeteria> findProductocafeteriaEntities(int maxResults, int firstResult) {
        return findProductocafeteriaEntities(false, maxResults, firstResult);
    }

    private List<Productocafeteria> findProductocafeteriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Productocafeteria.class));
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

    public Productocafeteria findProductocafeteria(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Productocafeteria.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductocafeteriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Productocafeteria> rt = cq.from(Productocafeteria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
