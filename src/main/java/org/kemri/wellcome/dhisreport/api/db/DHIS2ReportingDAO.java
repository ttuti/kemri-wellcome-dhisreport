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
package org.kemri.wellcome.dhisreport.api.db;

import java.util.Collection;

import org.hisp.dhis.dxf2.importsummary.ImportConflict;
import org.hisp.dhis.dxf2.importsummary.ImportCount;
import org.hisp.dhis.dxf2.importsummary.ImportSummary;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingException;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingService;
import org.kemri.wellcome.dhisreport.api.model.DataElement;
import org.kemri.wellcome.dhisreport.api.model.DataValueTemplate;
import org.kemri.wellcome.dhisreport.api.model.Disaggregation;
import org.kemri.wellcome.dhisreport.api.model.ReportDefinition;
import org.kemri.wellcome.dhisreport.api.model.Role;
import org.kemri.wellcome.dhisreport.api.model.User;
import org.kemri.wellcome.dhisreport.api.utils.Period;
import org.kemri.wellcome.dhisreport.api.model.Location;

/**
 * Database methods for {@link DHIS2ReportingService}.
 */
public interface DHIS2ReportingDAO
{

    public DataElement getDataElement( Integer id );

    public DataElement getDataElementByUid( String uid );
    
    public User getUser( Integer id );

    public User getUserByUsername( String username );
    
    public DataValueTemplate getDataValueTemplate(ReportDefinition reportDefinition,DataElement dataelement,Disaggregation disaggregation);
    
    public User getUserByEmail( String email );
    
    public User saveUser( User user );
    
    public Role getRole( Integer id );

    public Role getRoleByName( String roleName );
    
    public ImportSummary getImportSummary( Integer id );

    public DataElement saveDataElement( DataElement de );
    
    public ImportCount saveImportCount( ImportCount ic );
    
    public ImportConflict saveImportConflict( ImportConflict icf );
    
    public ImportSummary saveImportSummary( ImportSummary is );

    public void deleteDataElement( DataElement de );

    public Collection<DataElement> getAllDataElements();
    
    public Collection<Role> getAllRoles();
    
    public Collection<Location> getAllLocations();

    public Disaggregation getDisaggregation( Integer id );

    public Disaggregation getDisaggregationByUid( String uid );

    public Disaggregation saveDisaggregation( Disaggregation disagg );

    public Collection<Disaggregation> getAllDisaggregations();

    public void deleteDisaggregation( Disaggregation disagg );

    public ReportDefinition getReportDefinition( Integer id );

    public ReportDefinition getReportDefinitionByUid( String uid );
    
    public Location getLocation( Integer id );

    public ReportDefinition saveReportDefinition( ReportDefinition rd );
    
    public Location saveLocation( Location ln );

    public Collection<ReportDefinition> getAllReportDefinitions();

    public void deleteReportDefinition( ReportDefinition rd );
    
    public void deleteLocation( Location ln );
    
    public void deleteDataValueTemplate( DataValueTemplate dvt);

    public String evaluateDataValueTemplate( DataValueTemplate dvt, Period period, Location location )
        throws DHIS2ReportingException;

    public DataValueTemplate getDataValueTemplate( Integer id );

    public DataValueTemplate saveDataValueTemplate( DataValueTemplate dvt );

    public Location getLocationByOU_Code( String OU_Code );
}
