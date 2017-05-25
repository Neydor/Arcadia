/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "tipohabitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tipohabitacion.findAll", query = "SELECT t FROM Tipohabitacion t")
    , @NamedQuery(name = "Tipohabitacion.findByNombretipohabitacion", query = "SELECT t FROM Tipohabitacion t WHERE t.nombretipohabitacion = :nombretipohabitacion")
    , @NamedQuery(name = "Tipohabitacion.findByPrecio", query = "SELECT t FROM Tipohabitacion t WHERE t.precio = :precio")})
public class Tipohabitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "nombretipohabitacion")
    private String nombretipohabitacion;
    @Basic(optional = false)
    @Column(name = "precio")
    private double precio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nombretipohabitacion")
    private List<Habitacion> habitacionList;

    public Tipohabitacion() {
    }

    public Tipohabitacion(String nombretipohabitacion) {
        this.nombretipohabitacion = nombretipohabitacion;
    }

    public Tipohabitacion(String nombretipohabitacion, double precio) {
        this.nombretipohabitacion = nombretipohabitacion;
        this.precio = precio;
    }

    public String getNombretipohabitacion() {
        return nombretipohabitacion;
    }

    public void setNombretipohabitacion(String nombretipohabitacion) {
        this.nombretipohabitacion = nombretipohabitacion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @XmlTransient
    public List<Habitacion> getHabitacionList() {
        return habitacionList;
    }

    public void setHabitacionList(List<Habitacion> habitacionList) {
        this.habitacionList = habitacionList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nombretipohabitacion != null ? nombretipohabitacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tipohabitacion)) {
            return false;
        }
        Tipohabitacion other = (Tipohabitacion) object;
        if ((this.nombretipohabitacion == null && other.nombretipohabitacion != null) || (this.nombretipohabitacion != null && !this.nombretipohabitacion.equals(other.nombretipohabitacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Tipohabitacion[ nombretipohabitacion=" + nombretipohabitacion + " ]";
    }
    
}
