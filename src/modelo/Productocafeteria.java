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
@Table(name = "productocafeteria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Productocafeteria.findAll", query = "SELECT p FROM Productocafeteria p")
    , @NamedQuery(name = "Productocafeteria.findByIdproductocafeteria", query = "SELECT p FROM Productocafeteria p WHERE p.idproductocafeteria = :idproductocafeteria")
    , @NamedQuery(name = "Productocafeteria.findByNombre", query = "SELECT p FROM Productocafeteria p WHERE p.nombre = :nombre")
    , @NamedQuery(name = "Productocafeteria.findByPrecio", query = "SELECT p FROM Productocafeteria p WHERE p.precio = :precio")
    , @NamedQuery(name = "Productocafeteria.findByCantidad", query = "SELECT p FROM Productocafeteria p WHERE p.cantidad = :cantidad")})
public class Productocafeteria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idproductocafeteria")
    private String idproductocafeteria;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "precio")
    private double precio;
    @Basic(optional = false)
    @Column(name = "cantidad")
    private int cantidad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productocafeteria")
    private List<Consumo> consumoList;

    public Productocafeteria() {
    }

    public Productocafeteria(String idproductocafeteria) {
        this.idproductocafeteria = idproductocafeteria;
    }

    public Productocafeteria(String idproductocafeteria, String nombre, double precio, int cantidad) {
        this.idproductocafeteria = idproductocafeteria;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public String getIdproductocafeteria() {
        return idproductocafeteria;
    }

    public void setIdproductocafeteria(String idproductocafeteria) {
        this.idproductocafeteria = idproductocafeteria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
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
        hash += (idproductocafeteria != null ? idproductocafeteria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Productocafeteria)) {
            return false;
        }
        Productocafeteria other = (Productocafeteria) object;
        if ((this.idproductocafeteria == null && other.idproductocafeteria != null) || (this.idproductocafeteria != null && !this.idproductocafeteria.equals(other.idproductocafeteria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Productocafeteria[ idproductocafeteria=" + idproductocafeteria + " ]";
    }
    
}
