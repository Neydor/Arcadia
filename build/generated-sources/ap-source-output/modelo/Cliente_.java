package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Hospedaje;
import modelo.Reservahabitacion;
import modelo.Reservasalon;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-24T23:45:12")
@StaticMetamodel(Cliente.class)
public class Cliente_ { 

    public static volatile SingularAttribute<Cliente, String> apellidos;
    public static volatile ListAttribute<Cliente, Hospedaje> hospedajeList;
    public static volatile SingularAttribute<Cliente, String> telefono;
    public static volatile ListAttribute<Cliente, Reservasalon> reservasalonList;
    public static volatile SingularAttribute<Cliente, String> idcliente;
    public static volatile ListAttribute<Cliente, Reservahabitacion> reservahabitacionList;
    public static volatile SingularAttribute<Cliente, String> nombres;

}