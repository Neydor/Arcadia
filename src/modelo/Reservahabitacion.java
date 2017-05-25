/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "reservahabitacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservahabitacion.findAll", query = "SELECT r FROM Reservahabitacion r")
    , @NamedQuery(name = "Reservahabitacion.findByIdreservahabitacion", query = "SELECT r FROM Reservahabitacion r WHERE r.idreservahabitacion = :idreservahabitacion")
    , @NamedQuery(name = "Reservahabitacion.findByFecha", query = "SELECT r FROM Reservahabitacion r WHERE r.fecha = :fecha")
    , @NamedQuery(name = "Reservahabitacion.findByNrodias", query = "SELECT r FROM Reservahabitacion r WHERE r.nrodias = :nrodias")
    , @NamedQuery(name = "Reservahabitacion.findByNumpersonas", query = "SELECT r FROM Reservahabitacion r WHERE r.numpersonas = :numpersonas")
    , @NamedQuery(name = "Reservahabitacion.findByEstado", query = "SELECT r FROM Reservahabitacion r WHERE r.estado = :estado")})
public class Reservahabitacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreservahabitacion")
    private Integer idreservahabitacion;
    @Basic(optional = false)
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Basic(optional = false)
    @Column(name = "nrodias")
    private int nrodias;
    @Basic(optional = false)
    @Column(name = "numpersonas")
    private int numpersonas;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @ManyToMany(mappedBy = "reservahabitacionList")
    private List<Habitacion> habitacionList;
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente idcliente;

    public Reservahabitacion() {
    }

    public Reservahabitacion(Integer idreservahabitacion) {
        this.idreservahabitacion = idreservahabitacion;
    }

    public Reservahabitacion(Integer idreservahabitacion, Date fecha, int nrodias, int numpersonas, String estado) {
        this.idreservahabitacion = idreservahabitacion;
        this.fecha = fecha;
        this.nrodias = nrodias;
        this.numpersonas = numpersonas;
        this.estado = estado;
    }

    public Integer getIdreservahabitacion() {
        return idreservahabitacion;
    }

    public void setIdreservahabitacion(Integer idreservahabitacion) {
        this.idreservahabitacion = idreservahabitacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNrodias() {
        return nrodias;
    }

    public void setNrodias(int nrodias) {
        this.nrodias = nrodias;
    }

    public int getNumpersonas() {
        return numpersonas;
    }

    public void setNumpersonas(int numpersonas) {
        this.numpersonas = numpersonas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Habitacion> getHabitacionList() {
        return habitacionList;
    }

    public void setHabitacionList(List<Habitacion> habitacionList) {
        this.habitacionList = habitacionList;
    }

    public Cliente getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Cliente idcliente) {
        this.idcliente = idcliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idreservahabitacion != null ? idreservahabitacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservahabitacion)) {
            return false;
        }
        Reservahabitacion other = (Reservahabitacion) object;
        if ((this.idreservahabitacion == null && other.idreservahabitacion != null) || (this.idreservahabitacion != null && !this.idreservahabitacion.equals(other.idreservahabitacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Reservahabitacion[ idreservahabitacion=" + idreservahabitacion + " ]";
    }
    
}
