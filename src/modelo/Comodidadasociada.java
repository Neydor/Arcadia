/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "comodidadasociada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comodidadasociada.findAll", query = "SELECT c FROM Comodidadasociada c")
    , @NamedQuery(name = "Comodidadasociada.findByIdcomodidad", query = "SELECT c FROM Comodidadasociada c WHERE c.idcomodidad = :idcomodidad")
    , @NamedQuery(name = "Comodidadasociada.findByNombre", query = "SELECT c FROM Comodidadasociada c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Comodidadasociada.findByPorcentajeincremento", query = "SELECT c FROM Comodidadasociada c WHERE c.porcentajeincremento = :porcentajeincremento")})
public class Comodidadasociada implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcomodidad")
    private Integer idcomodidad;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "porcentajeincremento")
    private BigDecimal porcentajeincremento;
    @JoinTable(name = "comodidadhabitacion", joinColumns = {
        @JoinColumn(name = "idcomodidad", referencedColumnName = "idcomodidad")}, inverseJoinColumns = {
        @JoinColumn(name = "idhabitacion", referencedColumnName = "idhabitacion")})
    @ManyToMany
    private List<Habitacion> habitacionList;

    public Comodidadasociada() {
    }

    public Comodidadasociada(Integer idcomodidad) {
        this.idcomodidad = idcomodidad;
    }

    public Comodidadasociada(Integer idcomodidad, String nombre, BigDecimal porcentajeincremento) {
        this.idcomodidad = idcomodidad;
        this.nombre = nombre;
        this.porcentajeincremento = porcentajeincremento;
    }

    public Integer getIdcomodidad() {
        return idcomodidad;
    }

    public void setIdcomodidad(Integer idcomodidad) {
        this.idcomodidad = idcomodidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPorcentajeincremento() {
        return porcentajeincremento;
    }

    public void setPorcentajeincremento(BigDecimal porcentajeincremento) {
        this.porcentajeincremento = porcentajeincremento;
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
        hash += (idcomodidad != null ? idcomodidad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comodidadasociada)) {
            return false;
        }
        Comodidadasociada other = (Comodidadasociada) object;
        if ((this.idcomodidad == null && other.idcomodidad != null) || (this.idcomodidad != null && !this.idcomodidad.equals(other.idcomodidad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Comodidadasociada[ idcomodidad=" + idcomodidad + " ]";
    }
    
}
