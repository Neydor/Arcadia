/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Hospedaje;
import persistencia.HospedajeJpaController;

/**
 *
 * @author Usuario
 */
public class HospedajeLogica {
    private HospedajeJpaController hospedajeDAO = new HospedajeJpaController();
    
    public List<Hospedaje> consultarTodos() throws Exception{
        return hospedajeDAO.findHospedajeEntities();
    }   
    
    public void registrarCuenta(Hospedaje hospedaje) throws Exception{
    
    }
}
