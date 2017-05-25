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

    public void registrarHabitacion(Habitacion h) throws Exception {
        habitacionDAO.create(h);
    }
    
    public Habitacion consultarHabitacion(Habitacion h) throws Exception{
        return habitacionDAO.findHabitacion(h.getIdhabitacion());
    }
}
