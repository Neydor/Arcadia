/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "salondeeventos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Salondeeventos.findAll", query = "SELECT s FROM Salondeeventos s")
    , @NamedQuery(name = "Salondeeventos.findByIdsalon", query = "SELECT s FROM Salondeeventos s WHERE s.idsalon = :idsalon")
    , @NamedQuery(name = "Salondeeventos.findByCapacidadmax", query = "SELECT s FROM Salondeeventos s WHERE s.capacidadmax = :capacidadmax")
    , @NamedQuery(name = "Salondeeventos.findByPrecio", query = "SELECT s FROM Salondeeventos s WHERE s.precio = :precio")})
public class Salondeeventos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsalon")
    private Integer idsalon;
    @Basic(optional = false)
    @Column(name = "capacidadmax")
    private int capacidadmax;
    @Basic(optional = false)
    @Column(name = "precio")
    private double precio;
    @ManyToMany(mappedBy = "salondeeventosList")
    private List<Reservasalon> reservasalonList;

    public Salondeeventos() {
    }

    public Salondeeventos(Integer idsalon) {
        this.idsalon = idsalon;
    }

    public Salondeeventos(Integer idsalon, int capacidadmax, double precio) {
        this.idsalon = idsalon;
        this.capacidadmax = capacidadmax;
        this.precio = precio;
    }

    public Integer getIdsalon() {
        return idsalon;
    }

    public void setIdsalon(Integer idsalon) {
        this.idsalon = idsalon;
    }

    public int getCapacidadmax() {
        return capacidadmax;
    }

    public void setCapacidadmax(int capacidadmax) {
        this.capacidadmax = capacidadmax;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @XmlTransient
    public List<Reservasalon> getReservasalonList() {
        return reservasalonList;
    }

    public void setReservasalonList(List<Reservasalon> reservasalonList) {
        this.reservasalonList = reservasalonList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsalon != null ? idsalon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Salondeeventos)) {
            return false;
        }
        Salondeeventos other = (Salondeeventos) object;
        if ((this.idsalon == null && other.idsalon != null) || (this.idsalon != null && !this.idsalon.equals(other.idsalon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Salondeeventos[ idsalon=" + idsalon + " ]";
    }
    
}
