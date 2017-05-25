package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Comodidadasociada;
import modelo.Hospedaje;
import modelo.Reservahabitacion;
import modelo.Tipohabitacion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-24T23:45:12")
@StaticMetamodel(Habitacion.class)
public class Habitacion_ { 

    public static volatile ListAttribute<Habitacion, Hospedaje> hospedajeList;
    public static volatile SingularAttribute<Habitacion, String> idhabitacion;
    public static volatile ListAttribute<Habitacion, Comodidadasociada> comodidadasociadaList;
    public static volatile ListAttribute<Habitacion, Reservahabitacion> reservahabitacionList;
    public static volatile SingularAttribute<Habitacion, Tipohabitacion> nombretipohabitacion;

}