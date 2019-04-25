package com.esgis.examjava;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-08T08:59:51")
@StaticMetamodel(Aeroport.class)
public class Aeroport_ { 

    public static volatile SingularAttribute<Aeroport, String> reference;
    public static volatile SingularAttribute<Aeroport, String> ville;
    public static volatile SingularAttribute<Aeroport, String> codePays;
    public static volatile SingularAttribute<Aeroport, Long> id;
    public static volatile SingularAttribute<Aeroport, String> coordonnees;
    public static volatile SingularAttribute<Aeroport, String> commentaire;
    public static volatile SingularAttribute<Aeroport, Timestamp> version;

}