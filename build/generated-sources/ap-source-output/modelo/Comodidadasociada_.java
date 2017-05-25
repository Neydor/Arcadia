package modelo;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Habitacion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-24T23:45:12")
@StaticMetamodel(Comodidadasociada.class)
public class Comodidadasociada_ { 

    public static volatile ListAttribute<Comodidadasociada, Habitacion> habitacionList;
    public static volatile SingularAttribute<Comodidadasociada, Integer> idcomodidad;
    public static volatile SingularAttribute<Comodidadasociada, BigDecimal> porcentajeincremento;
    public static volatile SingularAttribute<Comodidadasociada, String> nombre;

}