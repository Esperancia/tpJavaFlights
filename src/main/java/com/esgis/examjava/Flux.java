//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.04.07 à 09:18:37 AM WAT 
//


package com.esgis.examjava;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour flux complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="flux">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="chemin">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="/Applications/FichiersNonSynchro/master_irt/m2irt/Examens/examJava/data/ouest"/>
 *               &lt;enumeration value="/Applications/FichiersNonSynchro/master_irt/m2irt/Examens/examJava/data/central"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="nommage">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value=".*OUEST.*"/>
 *               &lt;enumeration value="CENTRAL"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="frequence">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="60"/>
 *               &lt;enumeration value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="lot" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "flux", namespace = "http://devoir.local", propOrder = {
    "chemin",
    "nommage",
    "frequence",
    "lot"
})
@XmlRootElement
public class Flux {

    @XmlElement(required = true)
    protected String chemin;
    @XmlElement(required = true)
    protected String nommage;
    @XmlElement(required = true)
    protected String frequence;
    @XmlElement(required = true)
    protected String lot;

    /**
     * Obtient la valeur de la propriété chemin.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChemin() {
        return chemin;
    }

    /**
     * Définit la valeur de la propriété chemin.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChemin(String value) {
        this.chemin = value;
    }

    /**
     * Obtient la valeur de la propriété nommage.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNommage() {
        return nommage;
    }

    /**
     * Définit la valeur de la propriété nommage.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNommage(String value) {
        this.nommage = value;
    }

    /**
     * Obtient la valeur de la propriété frequence.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrequence() {
        return frequence;
    }

    /**
     * Définit la valeur de la propriété frequence.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrequence(String value) {
        this.frequence = value;
    }

    /**
     * Obtient la valeur de la propriété lot.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLot() {
        return lot;
    }

    /**
     * Définit la valeur de la propriété lot.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLot(String value) {
        this.lot = value;
    }

}
