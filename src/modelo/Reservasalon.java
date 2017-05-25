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
import javax.persistence.JoinTable;
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
@Table(name = "reservasalon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reservasalon.findAll", query = "SELECT r FROM Reservasalon r")
    , @NamedQuery(name = "Reservasalon.findByIdreservasalon", query = "SELECT r FROM Reservasalon r WHERE r.idreservasalon = :idreservasalon")
    , @NamedQuery(name = "Reservasalon.findByFechainicio", query = "SELECT r FROM Reservasalon r WHERE r.fechainicio = :fechainicio")
    , @NamedQuery(name = "Reservasalon.findByFechafin", query = "SELECT r FROM Reservasalon r WHERE r.fechafin = :fechafin")
    , @NamedQuery(name = "Reservasalon.findByNumeroasistentes", query = "SELECT r FROM Reservasalon r WHERE r.numeroasistentes = :numeroasistentes")
    , @NamedQuery(name = "Reservasalon.findByNombreevento", query = "SELECT r FROM Reservasalon r WHERE r.nombreevento = :nombreevento")
    , @NamedQuery(name = "Reservasalon.findByEstado", query = "SELECT r FROM Reservasalon r WHERE r.estado = :estado")})
public class Reservasalon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idreservasalon")
    private Integer idreservasalon;
    @Basic(optional = false)
    @Column(name = "fechainicio")
    @Temporal(TemporalType.DATE)
    private Date fechainicio;
    @Basic(optional = false)
    @Column(name = "fechafin")
    @Temporal(TemporalType.DATE)
    private Date fechafin;
    @Basic(optional = false)
    @Column(name = "numeroasistentes")
    private int numeroasistentes;
    @Basic(optional = false)
    @Column(name = "nombreevento")
    private String nombreevento;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @JoinTable(name = "salonreservado", joinColumns = {
        @JoinColumn(name = "idreservasalon", referencedColumnName = "idreservasalon")}, inverseJoinColumns = {
        @JoinColumn(name = "idsalon", referencedColumnName = "idsalon")})
    @ManyToMany
    private List<Salondeeventos> salondeeventosList;
    @JoinColumn(name = "idcliente", referencedColumnName = "idcliente")
    @ManyToOne(optional = false)
    private Cliente idcliente;

    public Reservasalon() {
    }

    public Reservasalon(Integer idreservasalon) {
        this.idreservasalon = idreservasalon;
    }

    public Reservasalon(Integer idreservasalon, Date fechainicio, Date fechafin, int numeroasistentes, String nombreevento, String estado) {
        this.idreservasalon = idreservasalon;
        this.fechainicio = fechainicio;
        this.fechafin = fechafin;
        this.numeroasistentes = numeroasistentes;
        this.nombreevento = nombreevento;
        this.estado = estado;
    }

    public Integer getIdreservasalon() {
        return idreservasalon;
    }

    public void setIdreservasalon(Integer idreservasalon) {
        this.idreservasalon = idreservasalon;
    }

    public Date getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(Date fechainicio) {
        this.fechainicio = fechainicio;
    }

    public Date getFechafin() {
        return fechafin;
    }

    public void setFechafin(Date fechafin) {
        this.fechafin = fechafin;
    }

    public int getNumeroasistentes() {
        return numeroasistentes;
    }

    public void setNumeroasistentes(int numeroasistentes) {
        this.numeroasistentes = numeroasistentes;
    }

    public String getNombreevento() {
        return nombreevento;
    }

    public void setNombreevento(String nombreevento) {
        this.nombreevento = nombreevento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @XmlTransient
    public List<Salondeeventos> getSalondeeventosList() {
        return salondeeventosList;
    }

    public void setSalondeeventosList(List<Salondeeventos> salondeeventosList) {
        this.salondeeventosList = salondeeventosList;
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
        hash += (idreservasalon != null ? idreservasalon.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Reservasalon)) {
            return false;
        }
        Reservasalon other = (Reservasalon) object;
        if ((this.idreservasalon == null && other.idreservasalon != null) || (this.idreservasalon != null && !this.idreservasalon.equals(other.idreservasalon))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Reservasalon[ idreservasalon=" + idreservasalon + " ]";
    }
    
}
