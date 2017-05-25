/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import logica.UsuarioLogica;
import modelo.Usuario;

/**
 *
 * @author cristian
 */
public class VentanaLoggin extends javax.swing.JFrame {

    public void ingresar(String inUser, String inPass) {
        try {
            UsuarioLogica user = new UsuarioLogica();
            Usuario u = new Usuario();
            u.setIdusuario(inUser);
            System.out.println(u.getIdusuario());
            System.out.println(inUser);
            System.out.println(user.consultarUsuario(u));
            if (!inUser.isEmpty() && !inPass.isEmpty() && user.consultarUsuario(u) != null) {
                if (user.consultarUsuario(u).getIdusuario().equals(inUser) && user.consultarUsuario(u).getClave().equals(inPass)) {
                    try {
                        if (user.consultarUsuario(u).getCargo().equals("Administrador")) {
                            Admin admin = new Admin();
                            admin.setVisible(true);
                            dispose();
                        } else if (user.consultarUsuario(u).getCargo().equals("Recepcionista")) {
                            Recepcionista r = new Recepcionista();
                            r.setVisible(true);
                            dispose();
                        } else if (user.consultarUsuario(u).getCargo().equals("Encargado")) {
                            Encargado e = new Encargado();
                            e.setVisible(true);
                            dispose();
                        } else {
                            JOptionPane.showMessageDialog(this, "Datos incorrectos. Intentelo de nuevo.");
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Debe llenar todos los campos. Intentelo de nuevo.");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o password invalidos. Intentelo de nuevo.");
                }
            } else {
                if (user.consultarUsuario(u) == null && !inUser.isEmpty() && !inPass.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Usuario o password invalidos. Intentelo de nuevo.");
                } else if (inUser.isEmpty() || inPass.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Debe llenar todos los campos. Intentelo de nuevo.");
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(VentanaLoggin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates new form Ventana
     */
    public VentanaLoggin() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        inUsuario = new javax.swing.JTextField();
        botonLogin = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        inPassword = new javax.swing.JPasswordField();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("LOG-IN");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Usuario:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Contraseña:");

        inUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inUsuarioActionPerformed(evt);
            }
        });
        inUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                inUsuarioKeyTyped(evt);
            }
        });

        botonLogin.setText("Ingresar");
        botonLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonLoginActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/LogoHotel2.png"))); // NOI18N

        inPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inPasswordActionPerformed(evt);
            }
        });

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Capa 0.png"))); // NOI18N

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Capa 1.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jLabel4)
                .addContainerGap(113, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(218, 218, 218))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botonLogin)
                        .addGap(219, 219, 219))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel5)
                                .addGap(5, 5, 5))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(32, 32, 32)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inUsuario)
                            .addComponent(inPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(103, 103, 103))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(inUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(inPassword)
                    .addComponent(jLabel3)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(29, 29, 29)
                .addComponent(botonLogin)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inUsuarioActionPerformed

    private void botonLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonLoginActionPerformed
        ingresar(inUsuario.getText(), inPassword.getText());
        inUsuario.setText("");
        inPassword.setText("");
    }//GEN-LAST:event_botonLoginActionPerformed

    private void inPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_inPasswordActionPerformed

    private void inUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inUsuarioKeyTyped
        char c = evt.getKeyChar();
        if (inUsuario.getText().length() >= 10) {
            evt.consume();
        }
        if (!Character.isDigit(c)) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_inUsuarioKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(VentanaLoggin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaLoggin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaLoggin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaLoggin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaLoggin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonLogin;
    private javax.swing.JPasswordField inPassword;
    private javax.swing.JTextField inUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables
}
