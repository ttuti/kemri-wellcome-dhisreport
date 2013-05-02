/**
 *  Copyright 2012 Society for Health Information Systems Programmes, India (HISP India)
 *
 *  This file is part of DHIS2 Reporting module.
 *
 *  DHIS2 Reporting module is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.

 *  DHIS2 Reporting module is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with DHIS2 Reporting module.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package org.kemri.wellcome.dhisreport.api.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;

/**
 *
 * @author bobj
 */
@XmlType( name = "dataValueTemplate", propOrder = { "dataelement", "disaggregation", "query" } )
@XmlRootElement( name = "dataValueTemplate" )
@Entity
@Table(name=DataValueTemplate.TABLE_NAME)
public class DataValueTemplate
    implements Serializable
{
    private static final long serialVersionUID = 7043880893067405638L;
    public static final String TABLE_NAME ="dhisreport_datavalue_template";

	// Regex testing for update/delete
    private static final String SQL_SANITY_CHECK = ".*((?i)update|delete).*";
    
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    protected Integer id;
    
    @ManyToOne
    @JoinColumn(name="report_definition_id",nullable=false)
    protected ReportDefinition reportDefinition;
    
    @ManyToOne
    @JoinColumn(name="dataelement_id",nullable=false)
    protected DataElement dataelement;
    
    @ManyToOne
    @JoinColumn(name="disaggregation_id",nullable=false)
    protected Disaggregation disaggregation;

    @Column(name="query")
    protected String query;

    @XmlTransient
    public ReportDefinition getReportDefinition()
    {
        return reportDefinition;
    }

    public void setReportDefinition( ReportDefinition reportDefinition )
    {
        this.reportDefinition = reportDefinition;
    }

    @XmlAttribute( name = "dataElement", required = true )
    @XmlIDREF
    public DataElement getDataelement()
    {
        return dataelement;
    }

    public void setDataelement( DataElement dataelement )
    {
        this.dataelement = dataelement;
    }

    @XmlAttribute( name = "disaggregation", required = true )
    @XmlIDREF
    public Disaggregation getDisaggregation()
    {
        return disaggregation;
    }

    public void setDisaggregation( Disaggregation disaggregation )
    {
        this.disaggregation = disaggregation;
    }

    @XmlTransient
    public Integer getId()
    {
        return id;
    }

    public void setId( Integer id )
    {
        this.id = id;
    }

    @XmlElement( name = "annotation", required = false )
    public String getQuery()
    {
        return query;
    }

    public void setQuery( String query )
    {
        this.query = query;
    }

    public boolean potentialUpdateDelete()
    {
        return query.matches( SQL_SANITY_CHECK );
    }

    @Override
    public boolean equals( Object obj )
    {
        if ( obj == null )
        {
            return false;
        }
        if ( getClass() != obj.getClass() )
        {
            return false;
        }
        final DataValueTemplate other = (DataValueTemplate) obj;

        if ( this.dataelement != other.dataelement
            && (this.dataelement == null || !this.dataelement.equals( other.dataelement )) )
        {
            return false;
        }
        if ( this.disaggregation != other.disaggregation
            && (this.disaggregation == null || !this.disaggregation.equals( other.disaggregation )) )
        {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 53 * hash + (this.dataelement != null ? this.dataelement.hashCode() : 0);
        hash = 53 * hash + (this.disaggregation != null ? this.disaggregation.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString()
    {
        return "DVT: " + this.getId() + " : " + this.getDataelement().getName() + " : "
            + this.getDisaggregation().getName();
    }

}
