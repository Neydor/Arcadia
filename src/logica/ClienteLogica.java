/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Cliente;
import persistencia.ClienteJpaController;

/**
 *
 * @author Usuario
 */
public class ClienteLogica {
    private ClienteJpaController clienteDAO = new ClienteJpaController();
    
    public List<Cliente> consultarTodos() throws Exception{
        return clienteDAO.findClienteEntities();
    }   
    
    public void registrarCuenta(Cliente cliente) throws Exception{
    
    }
}
