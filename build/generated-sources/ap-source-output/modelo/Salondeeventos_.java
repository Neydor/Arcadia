package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Reservasalon;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-24T21:15:19")
@StaticMetamodel(Salondeeventos.class)
public class Salondeeventos_ { 

    public static volatile SingularAttribute<Salondeeventos, Double> precio;
    public static volatile SingularAttribute<Salondeeventos, Integer> capacidadmax;
    public static volatile SingularAttribute<Salondeeventos, Integer> idsalon;
    public static volatile ListAttribute<Salondeeventos, Reservasalon> reservasalonList;

}