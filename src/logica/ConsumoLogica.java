/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Consumo;
import persistencia.ConsumoJpaController;

/**
 *
 * @author Usuario
 */
public class ConsumoLogica {
    private ConsumoJpaController consumoDAO = new ConsumoJpaController();
    
    public List<Consumo> consultarTodos() throws Exception{
        return consumoDAO.findConsumoEntities();
    }   
    
    public void registrarCuenta(Consumo consumo) throws Exception{
    
    }
}
