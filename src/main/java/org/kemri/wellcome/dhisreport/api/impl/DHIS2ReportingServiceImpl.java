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
package org.kemri.wellcome.dhisreport.api.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.hisp.dhis.dxf2.importsummary.ImportSummary;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingException;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingService;
import org.kemri.wellcome.dhisreport.api.db.DHIS2ReportingDAO;
import org.kemri.wellcome.dhisreport.api.db.DhisServerDAO;
import org.kemri.wellcome.dhisreport.api.dxf2.DataValue;
import org.kemri.wellcome.dhisreport.api.dxf2.DataValueSet;
import org.kemri.wellcome.dhisreport.api.model.*;
import org.kemri.wellcome.dhisreport.api.utils.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

/**
 * It is a default implementation of {@link DHIS2ReportingService}.
 */

@Service("service")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class DHIS2ReportingServiceImpl implements DHIS2ReportingService
{

	protected final Logger log = Logger.getLogger(DHIS2ReportingServiceImpl.class);
    
    @Autowired
    private DHIS2ReportingDAO dao;
    
    @Autowired
	private DhisServerDAO serverDAO;

    @Override
    public ReportDefinition fetchReportTemplates()
        throws DHIS2ReportingException
    {
        return serverDAO.getDhisServer().fetchReportTemplates();
    }

    @Override
    public ImportSummary postDataValueSet( DataValueSet dvset )
    {
        try {
			return serverDAO.getDhisServer().postReport( dvset );
		} catch (DHIS2ReportingException e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
    }

    @Override
    public DataElement getDataElement( Integer id )
    {
        return dao.getDataElement( id );
    }

    @Override
    public DataElement getDataElementByUid( String uid )
    {
        return dao.getDataElementByUid( uid );
    }

    @Override
    public DataElement saveDataElement( DataElement de )
    {
        return dao.saveDataElement( de );
    }

    @Override
    public void purgeDataElement( DataElement de )
    {
        dao.deleteDataElement( de );
    }

    @Override
    public Disaggregation getDisaggregation( Integer id )
    {
        return dao.getDisaggregation( id );
    }

    @Override
    public Disaggregation saveDisaggregation( Disaggregation disagg )
    {
        return dao.saveDisaggregation( disagg );
    }

    @Override
    public ReportDefinition getReportDefinition( Integer id )
    {
        return dao.getReportDefinition( id );
    }

    public ReportDefinition getReportDefinitionByUId( String uid )
    {
        return dao.getReportDefinitionByUid( uid );
    }

    @Override
    public ReportDefinition saveReportDefinition( ReportDefinition reportDefinition )
    {
        return dao.saveReportDefinition( reportDefinition );
    }

    @Override
    public Collection<DataElement> getAllDataElements()
    {
        return dao.getAllDataElements();
    }

    @Override
    public void purgeDisaggregation( Disaggregation disagg )
    {
        dao.deleteDisaggregation( disagg );
    }

    @Override
    public Collection<Disaggregation> getAllDisaggregations()
    {
        return dao.getAllDisaggregations();
    }

    @Override
    public void purgeReportDefinition( ReportDefinition rd )
    {
        dao.deleteReportDefinition( rd );
    }

    @Override
    public Collection<ReportDefinition> getAllReportDefinitions()
    {
        return dao.getAllReportDefinitions();
    }

    @Override
    public String evaluateDataValueTemplate( DataValueTemplate dv, Period period, Location location )
        throws DHIS2ReportingException
    {
        return dao.evaluateDataValueTemplate( dv, period, location );
    }

    /**
     * Create a datavalueset report TODO: handle the sql query exceptions which are bound to happen
     * 
     * @param reportDefinition
     * @param period
     * @param location
     * @return
     */
    @Override
    public DataValueSet evaluateReportDefinition( ReportDefinition reportDefinition, Period period, Location location )
    {
        Collection<DataValueTemplate> templates = reportDefinition.getDataValueTemplates();
        DataValueSet dataValueSet = new DataValueSet();
        dataValueSet.setDataElementIdScheme( "code" );
        dataValueSet.setOrgUnitIdScheme( "code" );
        dataValueSet.setPeriod( period.getAsIsoString() );
        // dataValueSet.setOrgUnit( "OU_" + location.getId() ); /* Removed because will set directly from the controller */
        dataValueSet.setDataSet( reportDefinition.getCode() );

        Collection<DataValue> dataValues = dataValueSet.getDataValues();

        for ( DataValueTemplate dvt : templates )
        {
            DataValue dataValue = new DataValue();
            dataValue.setDataElement( dvt.getDataelement().getCode() );
            dataValue.setCategoryOptionCombo( dvt.getDisaggregation().getCode() );

            try
            {
                String value = dao.evaluateDataValueTemplate( dvt, period, location );
                if ( value != null )
                {
                    dataValue.setValue( value );
                    dataValues.add( dataValue );
                }
            }
            catch ( DHIS2ReportingException ex )
            {
                // TODO: percolate this through to UI
                log.warn( ex.getMessage() );
            }
        }

        return dataValueSet;
    }

    @Override
    public void saveReportTemplates( ReportTemplates rt )
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
    
    @Override
    public void unMarshallandSaveReportTemplates( InputStream is )
    {
        JAXBContext jaxbContext=null;
        Unmarshaller jaxbUnmarshaller=null;
        ReportTemplates reportTemplates=null;
		try {
			jaxbContext = JAXBContext.newInstance( ReportTemplates.class );
			jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			reportTemplates = (ReportTemplates) jaxbUnmarshaller.unmarshal( is );
			if(reportTemplates==null)
				log.error("\n reportTemplates : null");
		} catch (JAXBException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

        for ( DataElement de : reportTemplates.getDataElements() ){
            saveDataElement( de );
        }
        for ( Disaggregation disagg : reportTemplates.getDisaggregations() ){
            saveDisaggregation( disagg );
        }
        for ( ReportDefinition rd : reportTemplates.getReportDefinitions() )
        {
        	ReportDefinition reportDefination = saveReportDefinition( rd ); 
            for ( DataValueTemplate dvt : rd.getDataValueTemplates() )
            {
                dvt.setReportDefinition( reportDefination );
                saveDataValueTemplate(dvt);
            }                      
        }        
    }

    @Override
    public ReportTemplates getReportTemplates()
    {
        ReportTemplates rt = new ReportTemplates();

        rt.setDataElements( getAllDataElements() );
        rt.setDisaggregations( getAllDisaggregations() );
        rt.setReportDefinitions( getAllReportDefinitions() );

        return rt;
    }

    @Override
    public void marshallReportTemplates( OutputStream os, ReportTemplates rt )
        throws Exception
    {
        JAXBContext jaxbContext = JAXBContext.newInstance( ReportTemplates.class );
        Marshaller marshaller = jaxbContext.createMarshaller();

        marshaller.marshal( rt, os );
    }

    @Override
    public DataValueTemplate getDataValueTemplate( Integer id )
    {
        return dao.getDataValueTemplate( id );
    }

    @Override
    public void saveDataValueTemplate( DataValueTemplate dvt )
    {
        dao.saveDataValueTemplate( dvt );

    }

    @Override
    public Location getLocationByOU_Code( String OU_Code )
    {
        return dao.getLocationByOU_Code( OU_Code );
    }

	@Override
	public String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	@Override
	public void purgeDataValueTemplate(DataValueTemplate dvt) {		
		dao.deleteDataValueTemplate(dvt);
	}

	@Override
	public void purgeLocation(Location ln) {
		dao.deleteLocation(ln);
		
	}

	@Override
	public Location saveLocation(Location ln) {
		return dao.saveLocation( ln );
	}

	@Override
	public Location getLocation(Integer id) {
		return dao.getLocation( id );
	}

	@Override
	public Collection<Location> getAllLocations() {
		return dao.getAllLocations();
	}
}
