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

import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.hisp.dhis.dxf2.importsummary.ImportStatus;
import org.hisp.dhis.dxf2.importsummary.ImportSummary;
import org.kemri.wellcome.dhisreport.api.DHIS2ReportingException;
import org.kemri.wellcome.dhisreport.api.dhis.Dhis2Server;
import org.kemri.wellcome.dhisreport.api.dxf2.DataValueSet;
import org.kemri.wellcome.hisp.dhis.dxf2.Dxf2Exception;

/**
 *
 * @author bobj
 */
@Entity
@Table(name = HttpDhis2Server.TABLE_NAME )
public class HttpDhis2Server extends AbstractPersistentEntity implements Dhis2Server
{

    private static final long serialVersionUID = -7136146353442401523L;
    
    public static final String TABLE_NAME = "dhisv2_credentials";

	private static Logger log = Logger.getLogger( HttpDhis2Server.class );

    public static final String REPORTS_METADATA_PATH = "/api/forms.xml";

    public static final String DATAVALUESET_PATH = "/api/dataValueSets?dataElementIdScheme=code&orgUnitIdScheme=uid";
    
    @Column(name = "url", nullable = false, unique = true)
    private String sUrl;
    
    @Transient
    private URL url;
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @Column(name = "password", nullable = false, unique = true)
    private String password;
    
    public String getsUrl() {
		return sUrl;
	}

	public void setsUrl(URL url) {
		this.sUrl = url.toString();
	}

	public URL getUrl()
    {
		try{
			url = new URL( sUrl );
		}catch(MalformedURLException e){
			log.error(e.getMessage());
		}
		
        return url;
    }

    public void setUrl( URL url )
    {
        this.url = url;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername( String username )
    {
        this.username = username;
    }

    public HttpDhis2Server()
    {
    }

    @Override
    public boolean isConfigured()
    {
        if ( username == null | password == null | url == null )
        {
            return false;
        }
        if ( username.isEmpty() | password.isEmpty() | url.getHost().isEmpty() )
        {
            return false;
        }

        return true;
    }

    @Override
    public ImportSummary postReport( DataValueSet report )
        throws DHIS2ReportingException
    {
        log.debug( "Posting datavalueset report" );
        ImportSummary summary = null;

        StringWriter xmlReport = new StringWriter();
        try{
            JAXBContext jaxbDataValueSetContext = JAXBContext.newInstance( DataValueSet.class );
            Marshaller dataValueSetMarshaller = jaxbDataValueSetContext.createMarshaller();
            // output pretty printed
            dataValueSetMarshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, true );
            dataValueSetMarshaller.marshal( report, xmlReport );
        }catch ( JAXBException ex ){
        	log.error(ex.getMessage());
            throw new Dxf2Exception( "Problem marshalling dataValueSet", ex );            
        }

        String host = getUrl().getHost();
        int port = getUrl().getPort();

        HttpHost targetHost = new HttpHost( host, port, getUrl().getProtocol() );
        DefaultHttpClient httpclient = new DefaultHttpClient();
        BasicHttpContext localcontext = new BasicHttpContext();

        try
        {
            HttpPost httpPost = new HttpPost( getUrl().getPath() + DATAVALUESET_PATH );
            Credentials creds = new UsernamePasswordCredentials( username, password );
            Header bs = new BasicScheme().authenticate( creds, httpPost, localcontext );
            httpPost.addHeader( "Authorization", bs.getValue() );
            httpPost.addHeader( "Content-Type", "application/xml; charset=utf-8" );
            httpPost.addHeader( "Accept", "application/xml" );

            httpPost.setEntity( new StringEntity( xmlReport.toString() ) );
            HttpResponse response = httpclient.execute( targetHost, httpPost, localcontext );
            HttpEntity entity = response.getEntity();
            
            StringWriter writer = new StringWriter();
            IOUtils.copy(response.getEntity().getContent(), writer, "UTF-8");
            String theString = writer.toString();
            
            log.error("\n Entity: "+theString+"\n");

            if ( entity != null ){
                JAXBContext jaxbImportSummaryContext = JAXBContext.newInstance( ImportSummary.class );
                Unmarshaller importSummaryUnMarshaller = jaxbImportSummaryContext.createUnmarshaller();
                summary = (ImportSummary) importSummaryUnMarshaller.unmarshal( entity.getContent() );
            }else{
                summary = new ImportSummary();
                summary.setStatus( ImportStatus.ERROR );
            }
        }
        catch ( Exception ex ){
        	log.error(ex.getMessage());
            throw new Dhis2Exception( this, "Problem accessing Dhis2 server", ex );
        }finally{
            httpclient.getConnectionManager().shutdown();
        }
        return summary;
    }

    @Override
    public ReportDefinition fetchReportTemplates()
        throws Dhis2Exception
    {
        throw new UnsupportedOperationException( "Not supported yet." );
    }
}
