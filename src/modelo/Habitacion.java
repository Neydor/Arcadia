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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "habitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Habitacion.findAll", query = "SELECT h FROM Habitacion h")
    , @NamedQuery(name = "Habitacion.findByIdhabitacion", query = "SELECT h FROM Habitacion h WHERE h.idhabitacion = :idhabitacion")})
public class Habitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idhabitacion")
    private String idhabitacion;
    @JoinTable(name = "habitacionreservada", joinColumns = {
        @JoinColumn(name = "idhabitacion", referencedColumnName = "idhabitacion")}, inverseJoinColumns = {
        @JoinColumn(name = "idreservahabitacion", referencedColumnName = "idreservahabitacion")})
    @ManyToMany
    private List<Reservahabitacion> reservahabitacionList;
    @JoinTable(name = "habitacionhospedaje", joinColumns = {
        @JoinColumn(name = "idhabitacion", referencedColumnName = "idhabitacion")}, inverseJoinColumns = {
        @JoinColumn(name = "idhospedaje", referencedColumnName = "idhospedaje")})
    @ManyToMany
    private List<Hospedaje> hospedajeList;
    @ManyToMany(mappedBy = "habitacionList")
    private List<Comodidadasociada> comodidadasociadaList;
    @JoinColumn(name = "nombretipohabitacion", referencedColumnName = "nombretipohabitacion")
    @ManyToOne(optional = false)
    private Tipohabitacion nombretipohabitacion;

    public Habitacion() {
    }

    public Habitacion(String idhabitacion) {
        this.idhabitacion = idhabitacion;
    }

    public String getIdhabitacion() {
        return idhabitacion;
    }

    public void setIdhabitacion(String idhabitacion) {
        this.idhabitacion = idhabitacion;
    }

    @XmlTransient
    public List<Reservahabitacion> getReservahabitacionList() {
        return reservahabitacionList;
    }

    public void setReservahabitacionList(List<Reservahabitacion> reservahabitacionList) {
        this.reservahabitacionList = reservahabitacionList;
    }

    @XmlTransient
    public List<Hospedaje> getHospedajeList() {
        return hospedajeList;
    }

    public void setHospedajeList(List<Hospedaje> hospedajeList) {
        this.hospedajeList = hospedajeList;
    }

    @XmlTransient
    public List<Comodidadasociada> getComodidadasociadaList() {
        return comodidadasociadaList;
    }

    public void setComodidadasociadaList(List<Comodidadasociada> comodidadasociadaList) {
        this.comodidadasociadaList = comodidadasociadaList;
    }

    public Tipohabitacion getNombretipohabitacion() {
        return nombretipohabitacion;
    }

    public void setNombretipohabitacion(Tipohabitacion nombretipohabitacion) {
        this.nombretipohabitacion = nombretipohabitacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhabitacion != null ? idhabitacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Habitacion)) {
            return false;
        }
        Habitacion other = (Habitacion) object;
        if ((this.idhabitacion == null && other.idhabitacion != null) || (this.idhabitacion != null && !this.idhabitacion.equals(other.idhabitacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Habitacion[ idhabitacion=" + idhabitacion + " ]";
    }
    
}
