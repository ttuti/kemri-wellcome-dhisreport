/**
 * Copyright 2012 Society for Health Information Systems Programmes, India (HISP India)
 *
 * This file is part of DHIS2 Reporting module.
 *
 * DHIS2 Reporting module is free software: you can redistribute it and/or modify it under the terms of the GNU General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * DHIS2 Reporting module is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with DHIS2 Reporting module. If not, see
 * <http://www.gnu.org/licenses/>.
 *
 *
 */
package org.kemri.wellcome.dhisreport.api.dxf2;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.*;
import javax.xml.bind.util.JAXBSource;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlType( name = "", propOrder = { "dataValue" } )
@XmlRootElement( name = "dataValueSet" )
public class DataValueSet
{
    public static final String XSLT2SDMX = "";

    public static enum Month
    {

        JAN, FEB, MAR, APR, MAY, JUN, JUL, AUG, SEP, NOV, DEC
    };

    @XmlElement( required = true )
    protected List<DataValue> dataValue = new LinkedList<DataValue>();

    @XmlAttribute
    @XmlSchemaType( name = "date" )
    protected XMLGregorianCalendar completeDate;

    @XmlAttribute( required = true )
    protected String dataSet;

    @XmlAttribute( required = true )
    protected String orgUnit;

    @XmlAttribute( required = true )
    protected String period;

    @XmlAttribute( required = false )
    protected String dataElementIdScheme;

    @XmlAttribute( required = false )
    protected String orgUnitIdScheme;
    
    @XmlTransient
    protected String error;

    /**
     * Gets the value of the dataValue property.
     *
     * <p> This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you
     * make to the returned list will be present inside the JAXB object. This is why there is not a
     * <CODE>set</CODE> method for the dataValue property.
     *
     * <p> For example, to add a new item, do as follows:
     * <pre>
     *    getDataValue().add(newItem);
     * </pre>
     *
     *
     * <p> Objects of the following type(s) are allowed in the list
     * {@link DataValue }
     *
     *
     */
    public List<DataValue> getDataValues()
    {
        if ( dataValue == null )
        {
            dataValue = new ArrayList<DataValue>();
        }
        return this.dataValue;
    }

    public String getDataElementIdScheme()
    {
        return dataElementIdScheme;
    }

    public void setDataElementIdScheme( String dataElementIdScheme )
    {
        this.dataElementIdScheme = dataElementIdScheme;
    }

    public String getOrgUnitIdScheme()
    {
        return orgUnitIdScheme;
    }

    public void setOrgUnitIdScheme( String orgUnitIdScheme )
    {
        this.orgUnitIdScheme = orgUnitIdScheme;
    }

    /**
     * Gets the value of the completeDate property.
     *
     * @return possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getCompleteDate()
    {
        return completeDate;
    }

    /**
     * Sets the value of the completeDate property.
     *
     * @param value allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setCompleteDate( XMLGregorianCalendar value )
    {
        this.completeDate = value;
    }

    /**
     * Gets the value of the dataSet property.
     *
     * @return possible object is
     *     {@link String }
     *
     */
    public String getDataSet()
    {
        return dataSet;
    }

    /**
     * Sets the value of the dataSet property.
     *
     * @param value allowed object is
     *     {@link String }
     *
     */
    public void setDataSet( String value )
    {
        this.dataSet = value;
    }

    /**
     * Gets the value of the orgUnit property.
     *
     * @return possible object is
     *     {@link String }
     *
     */
    public String getOrgUnit()
    {
        return orgUnit;
    }

    /**
     * Sets the value of the orgUnit property.
     *
     * @param value allowed object is
     *     {@link String }
     *
     */
    public void setOrgUnit( String value )
    {
        this.orgUnit = value;
    }

    /**
     * Gets the value of the period property.
     *
     * @return possible object is
     *     {@link String }
     *
     */
    public String getPeriod()
    {
        return period;
    }
    
    @Override
    public String toString(){
    	return "[Period : "+period+", Dataset:"+dataSet+"]";
    }

    /**
     * Sets the value of the period property.
     *
     * @param value allowed object is
     *     {@link String }
     *
     */
    public void setPeriod( String value )
    {
        this.period = value;
    }

    public void marshall( OutputStream outputStream )
        throws JAXBException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance( this.getClass() );
        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.marshal( this, outputStream );
    }

    public void marshallSDMX( OutputStream outputStream )
        throws JAXBException, TransformerConfigurationException, TransformerException
    {
        JAXBContext jaxbContext = JAXBContext.newInstance( this.getClass() );
        @SuppressWarnings("unused")
		Marshaller marshaller = jaxbContext.createMarshaller();

        JAXBSource source = new JAXBSource( jaxbContext, this );

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer( new StreamSource( ClassLoader.class
            .getResourceAsStream( "/xslt/dxf2sdmxcross.xsl" ) ) );

        String dateParam = new SimpleDateFormat( "yyyy-MM-dd" ).format( new Date() );
        t.setParameter( "date", dateParam );
        t.transform( source, new StreamResult( outputStream ) );
    }

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
