package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Habitacion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-24T23:45:12")
@StaticMetamodel(Tipohabitacion.class)
public class Tipohabitacion_ { 

    public static volatile SingularAttribute<Tipohabitacion, Double> precio;
    public static volatile ListAttribute<Tipohabitacion, Habitacion> habitacionList;
    public static volatile SingularAttribute<Tipohabitacion, String> nombretipohabitacion;

}