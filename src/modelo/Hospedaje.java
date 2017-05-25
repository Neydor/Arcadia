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
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
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
@Table(name = "hospedaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Hospedaje.findAll", query = "SELECT h FROM Hospedaje h")
    , @NamedQuery(name = "Hospedaje.findByIdhospedaje", query = "SELECT h FROM Hospedaje h WHERE h.idhospedaje = :idhospedaje")
    , @NamedQuery(name = "Hospedaje.findByFecha", query = "SELECT h FROM Hospedaje h WHERE h.fecha = :fecha")
    , @NamedQuery(name = "Hospedaje.findByNrodias", query = "SELECT h FROM Hospedaje h WHERE h.nrodias = :nrodias")
    , @NamedQuery(name = "Hospedaje.findByNumpersonas", query = "SELECT h FROM Hospedaje h WHERE h.numpersonas = :numpersonas")
    , @NamedQuery(name = "Hospedaje.findByEstado", query = "SELECT h FROM Hospedaje h WHERE h.estado = :estado")})
public class Hospedaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhospedaje")
    private Integer idhospedaje;
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
    @ManyToMany(mappedBy = "hospedajeList")
    private List<Habitacion> habitacionList;
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente idcliente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospedaje")
    private List<Consumo> consumoList;

    public Hospedaje() {
    }

    public Hospedaje(Integer idhospedaje) {
        this.idhospedaje = idhospedaje;
    }

    public Hospedaje(Integer idhospedaje, Date fecha, int nrodias, int numpersonas, String estado) {
        this.idhospedaje = idhospedaje;
        this.fecha = fecha;
        this.nrodias = nrodias;
        this.numpersonas = numpersonas;
        this.estado = estado;
    }

    public Integer getIdhospedaje() {
        return idhospedaje;
    }

    public void setIdhospedaje(Integer idhospedaje) {
        this.idhospedaje = idhospedaje;
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

    @XmlTransient
    public List<Consumo> getConsumoList() {
        return consumoList;
    }

    public void setConsumoList(List<Consumo> consumoList) {
        this.consumoList = consumoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhospedaje != null ? idhospedaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Hospedaje)) {
            return false;
        }
        Hospedaje other = (Hospedaje) object;
        if ((this.idhospedaje == null && other.idhospedaje != null) || (this.idhospedaje != null && !this.idhospedaje.equals(other.idhospedaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Hospedaje[ idhospedaje=" + idhospedaje + " ]";
    }
    
}
