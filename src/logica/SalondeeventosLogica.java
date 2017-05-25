/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Salondeeventos;
import persistencia.SalondeeventosJpaController;

/**
 *
 * @author Usuario
 */
public class SalondeeventosLogica {

    private SalondeeventosJpaController salondeeventosDAO = new SalondeeventosJpaController();

    public List<Salondeeventos> consultarTodos() throws Exception {
        return salondeeventosDAO.findSalondeeventosEntities();
    }

    public void registrarSalon(int capacidadM, double precio) throws Exception {
        Salondeeventos s = new Salondeeventos();
        s.setIdsalon(salondeeventosDAO.getSalondeeventosCount()+1);
        s.setCapacidadmax(capacidadM);
        s.setPrecio(precio);
        salondeeventosDAO.create(s);
    }
    
    public int generarConsecutivoId(){
        
        return
        (salondeeventosDAO.getSalondeeventosCount()+1);        
 
    }
    
}
