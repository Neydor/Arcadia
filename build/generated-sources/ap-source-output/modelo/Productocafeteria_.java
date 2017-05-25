package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Consumo;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-24T21:15:19")
@StaticMetamodel(Productocafeteria.class)
public class Productocafeteria_ { 

    public static volatile SingularAttribute<Productocafeteria, Double> precio;
    public static volatile ListAttribute<Productocafeteria, Consumo> consumoList;
    public static volatile SingularAttribute<Productocafeteria, String> idproductocafeteria;
    public static volatile SingularAttribute<Productocafeteria, Integer> cantidad;
    public static volatile SingularAttribute<Productocafeteria, String> nombre;

}