/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Reservasalon;
import persistencia.ReservasalonJpaController;

/**
 *
 * @author Usuario
 */
public class ReservasalonLogica {
    private ReservasalonJpaController reservasalonDAO = new ReservasalonJpaController();
    
    public List<Reservasalon> consultarTodos() throws Exception{
        return reservasalonDAO.findReservasalonEntities();
    }   
    
    public void registrarCuenta(Reservasalon reservasalon) throws Exception{
    
    }
}
