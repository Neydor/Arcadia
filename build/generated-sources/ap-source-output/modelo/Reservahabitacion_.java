package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cliente;
import modelo.Habitacion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-24T21:15:19")
@StaticMetamodel(Reservahabitacion.class)
public class Reservahabitacion_ { 

    public static volatile SingularAttribute<Reservahabitacion, Date> fecha;
    public static volatile SingularAttribute<Reservahabitacion, String> estado;
    public static volatile ListAttribute<Reservahabitacion, Habitacion> habitacionList;
    public static volatile SingularAttribute<Reservahabitacion, Integer> nrodias;
    public static volatile SingularAttribute<Reservahabitacion, Cliente> idcliente;
    public static volatile SingularAttribute<Reservahabitacion, Integer> idreservahabitacion;
    public static volatile SingularAttribute<Reservahabitacion, Integer> numpersonas;

}