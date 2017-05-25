/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Habitacion;
import modelo.Tipohabitacion;
import persistencia.HabitacionJpaController;

/**
 *
 * @author Usuario
 */
public class HabitacionLogica {

    private HabitacionJpaController habitacionDAO = new HabitacionJpaController();

    public List<Habitacion> consultarTodos() throws Exception {
        return habitacionDAO.findHabitacionEntities();
    }

    public void registrarHabitacion(String id,Tipohabitacion t) throws Exception {
        Habitacion h = new Habitacion();
        h.setIdhabitacion(id);
        h.setNombretipohabitacion(t);
        habitacionDAO.create(h);
    }
    
    public Habitacion consultarHabitacion(String id) throws Exception{
        return habitacionDAO.findHabitacion(id);
    }
}
