package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ConsumoPK;
import modelo.Hospedaje;
import modelo.Productocafeteria;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-24T21:15:19")
@StaticMetamodel(Consumo.class)
public class Consumo_ { 

    public static volatile SingularAttribute<Consumo, Hospedaje> hospedaje;
    public static volatile SingularAttribute<Consumo, Productocafeteria> productocafeteria;
    public static volatile SingularAttribute<Consumo, ConsumoPK> consumoPK;
    public static volatile SingularAttribute<Consumo, Integer> cantidad;

}