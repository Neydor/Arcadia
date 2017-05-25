/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Tipohabitacion;
import persistencia.TipohabitacionJpaController;

/**
 *
 * @author Usuario
 */
public class TipohabitacionLogica {

    private TipohabitacionJpaController tipohabitacionDAO = new TipohabitacionJpaController();

    public List<Tipohabitacion> consultarTodos() throws Exception {
        return tipohabitacionDAO.findTipohabitacionEntities();
    }

    public void modificarTipoHAbitacion(Tipohabitacion tipohabitacion) throws Exception {
        tipohabitacionDAO.edit(tipohabitacion);
    }

    public void registrarTipoHabitacion(Tipohabitacion tipo) throws Exception {
        tipohabitacionDAO.create(tipo);
    }

    public Tipohabitacion consultarTipoHabitacion(Tipohabitacion tipo) throws Exception {
        return tipohabitacionDAO.findTipohabitacion(tipo.getNombretipohabitacion());
    }
}
