/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import modelo.Comodidadasociada;
import persistencia.ComodidadasociadaJpaController;

/**
 *
 * @author Usuario
 */
public class ComodidadasociadaLogica {

    private ComodidadasociadaJpaController comodidadasociadaDAO = new ComodidadasociadaJpaController();

    public List<Comodidadasociada> consultarTodos() throws Exception {
        return comodidadasociadaDAO.findComodidadasociadaEntities();
    }

    public void registrarComodidad(Comodidadasociada comodidad) throws Exception {
        comodidadasociadaDAO.create(comodidad);
    }
    
     public void modificarComodidad(Comodidadasociada comodidad) throws Exception {
        comodidadasociadaDAO.edit(comodidad);
    }

    public Comodidadasociada consultarComodidad(Comodidadasociada comodidad) throws Exception {
        return comodidadasociadaDAO.findComodidadasociada(comodidad.getIdcomodidad());
    }

    public int consecutivoIDComodidad() throws Exception {
        return comodidadasociadaDAO.getComodidadasociadaCount() + 1;
    }

    
}
