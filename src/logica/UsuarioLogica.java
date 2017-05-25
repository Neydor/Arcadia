/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.List;
import modelo.Usuario;
import persistencia.UsuarioJpaController;

/**
 *
 * @author Usuario
 */
public class UsuarioLogica {

    private UsuarioJpaController usuarioDAO = new UsuarioJpaController();

    public List<Usuario> consultarTodos() throws Exception {
        return usuarioDAO.findUsuarioEntities();
    }

    public Usuario consultarUsuario(Usuario usuario) throws Exception {
        return usuarioDAO.findUsuario(usuario.getIdusuario());

    }
}
