package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Cliente;
import modelo.Salondeeventos;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-24T21:15:19")
@StaticMetamodel(Reservasalon.class)
public class Reservasalon_ { 

    public static volatile SingularAttribute<Reservasalon, Date> fechainicio;
    public static volatile ListAttribute<Reservasalon, Salondeeventos> salondeeventosList;
    public static volatile SingularAttribute<Reservasalon, String> estado;
    public static volatile SingularAttribute<Reservasalon, Integer> numeroasistentes;
    public static volatile SingularAttribute<Reservasalon, Integer> idreservasalon;
    public static volatile SingularAttribute<Reservasalon, String> nombreevento;
    public static volatile SingularAttribute<Reservasalon, Date> fechafin;
    public static volatile SingularAttribute<Reservasalon, Cliente> idcliente;

}