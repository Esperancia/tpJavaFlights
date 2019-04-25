package com.esgis.examjava;

import com.esgis.examjava.Aeroport;
import java.sql.Timestamp;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-08T08:59:51")
@StaticMetamodel(Vol.class)
public class Vol_ { 

    public static volatile SingularAttribute<Vol, String> codeAeroDep;
    public static volatile SingularAttribute<Vol, Aeroport> AeroDep;
    public static volatile SingularAttribute<Vol, Date> dateDep;
    public static volatile SingularAttribute<Vol, Aeroport> AeroArr;
    public static volatile SingularAttribute<Vol, String> refEquipage;
    public static volatile SingularAttribute<Vol, Integer> nbrePassagers;
    public static volatile SingularAttribute<Vol, Timestamp> version;
    public static volatile SingularAttribute<Vol, String> codeAeroArr;
    public static volatile SingularAttribute<Vol, String> refAvion;
    public static volatile SingularAttribute<Vol, Date> dateArr;
    public static volatile SingularAttribute<Vol, Integer> numVol;
    public static volatile SingularAttribute<Vol, Integer> poidsCharge;
    public static volatile SingularAttribute<Vol, Long> id;

}