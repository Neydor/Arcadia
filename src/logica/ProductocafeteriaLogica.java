/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Productocafeteria;
import persistencia.ProductocafeteriaJpaController;

/**
 *
 * @author Usuario
 */
public class ProductocafeteriaLogica {
    private ProductocafeteriaJpaController productocafeteriaDAO = new ProductocafeteriaJpaController();
    
    public List<Productocafeteria> consultarTodos() throws Exception{
        return productocafeteriaDAO.findProductocafeteriaEntities();
    }   
    
    public void registrarCuenta(Productocafeteria productocafeteria) throws Exception{
    
    }
}
