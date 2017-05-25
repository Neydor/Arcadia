/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Usuario
 */
@Embeddable
public class ConsumoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "idhospedaje")
    private int idhospedaje;
    @Basic(optional = false)
    @Column(name = "idproductocafeteria")
    private String idproductocafeteria;

    public ConsumoPK() {
    }

    public ConsumoPK(int idhospedaje, String idproductocafeteria) {
        this.idhospedaje = idhospedaje;
        this.idproductocafeteria = idproductocafeteria;
    }

    public int getIdhospedaje() {
        return idhospedaje;
    }

    public void setIdhospedaje(int idhospedaje) {
        this.idhospedaje = idhospedaje;
    }

    public String getIdproductocafeteria() {
        return idproductocafeteria;
    }

    public void setIdproductocafeteria(String idproductocafeteria) {
        this.idproductocafeteria = idproductocafeteria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idhospedaje;
        hash += (idproductocafeteria != null ? idproductocafeteria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsumoPK)) {
            return false;
        }
        ConsumoPK other = (ConsumoPK) object;
        if (this.idhospedaje != other.idhospedaje) {
            return false;
        }
        if ((this.idproductocafeteria == null && other.idproductocafeteria != null) || (this.idproductocafeteria != null && !this.idproductocafeteria.equals(other.idproductocafeteria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ConsumoPK[ idhospedaje=" + idhospedaje + ", idproductocafeteria=" + idproductocafeteria + " ]";
    }
    
}
