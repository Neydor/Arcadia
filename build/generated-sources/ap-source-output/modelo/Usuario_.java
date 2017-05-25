package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-05-24T21:15:19")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> apellidos;
    public static volatile SingularAttribute<Usuario, String> clave;
    public static volatile SingularAttribute<Usuario, Date> fechadeingreso;
    public static volatile SingularAttribute<Usuario, Date> fechacambiaclave;
    public static volatile SingularAttribute<Usuario, String> cargo;
    public static volatile SingularAttribute<Usuario, String> idusuario;
    public static volatile SingularAttribute<Usuario, String> nombres;

}