package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cliente;
import modelo.Consumo;
import modelo.Habitacion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-24T21:15:19")
@StaticMetamodel(Hospedaje.class)
public class Hospedaje_ { 

    public static volatile SingularAttribute<Hospedaje, Date> fecha;
    public static volatile SingularAttribute<Hospedaje, String> estado;
    public static volatile ListAttribute<Hospedaje, Habitacion> habitacionList;
    public static volatile SingularAttribute<Hospedaje, Integer> nrodias;
    public static volatile ListAttribute<Hospedaje, Consumo> consumoList;
    public static volatile SingularAttribute<Hospedaje, Integer> idhospedaje;
    public static volatile SingularAttribute<Hospedaje, Cliente> idcliente;
    public static volatile SingularAttribute<Hospedaje, Integer> numpersonas;

}