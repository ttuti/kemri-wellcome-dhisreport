package org.hisp.dhis.dxf2.importsummary;

/*
 * Copyright (c) 2011, University of Oslo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * * Neither the name of the HISP project nor the names of its contributors may
 *   be used to endorse or promote products derived from this software without
 *   specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.kemri.wellcome.dhisreport.api.model.Identifiable;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlRootElement( name = "importSummary" )
@Entity
@Table(name= ImportSummary.TABLE_NAME)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ImportSummary implements Serializable, Identifiable
{
	private static final long serialVersionUID = -1515831250492632236L;

	public static final String TABLE_NAME="import_summary";
	
	@XmlTransient
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    protected Integer id;
	
	@XmlTransient
    @Column(name="uid", unique=true)
    protected String uid;
	
	@XmlTransient
    @Column(name="report_name")
    protected String reportName;
	
	@XmlTransient
    @Column(name="report_date")
    protected Date reportDate;
	
    @XmlElement( required = true )
    @Column(name="status", nullable=false)
    @Enumerated(EnumType.STRING)
    private ImportStatus status;

    @XmlElement( required = true )
    @Column(name="description", nullable=false)
    private String description;
    
    @XmlElement( required = true )
    @JsonManagedReference
    @OneToOne(mappedBy="importSummary", fetch = FetchType.LAZY, cascade= CascadeType.PERSIST)
    private ImportCount dataValueCount;
    
    @XmlElementWrapper( name = "conflicts", required = false )
    @XmlElement( name = "conflict" )
    @JsonManagedReference
    @OneToMany(mappedBy="importSummary", fetch = FetchType.LAZY, cascade= CascadeType.PERSIST)
    private List<ImportConflict> conflicts;

    @XmlElement( required = true )
    @Column(name="dataset_complete", nullable=false)
    private String dataSetComplete;
    
    @XmlTransient
    @Column(name = "name",nullable=false, unique = true)
    private String name;

    public ImportSummary()
    {
    	if(uid == null){
    		uid = UUID.randomUUID().toString();
    	}
    	if(name == null){
    		name = uid;
    	}
    }

    public ImportSummary( ImportStatus status, String description )
    {
        this.status = status;
        this.description = description;
    }

    public ImportStatus getStatus()
    {
        return status;
    }

    public void setStatus( ImportStatus status )
    {
        this.status = status;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public ImportCount getDataValueCount()
    {
        return dataValueCount;
    }

    public void setDataValueCount( ImportCount dataValueCount )
    {
        this.dataValueCount = dataValueCount;
    }

    public List<ImportConflict> getConflicts()
    {
        return conflicts;
    }

    public void setConflicts( List<ImportConflict> conflicts )
    {
        this.conflicts = conflicts;
    }

    public String getDataSetComplete()
    {
        return dataSetComplete;
    }

    public void setDataSetComplete( String dataSetComplete )
    {
        this.dataSetComplete = dataSetComplete;
    }

	@Override
	public String getUid() {
		if(uid==null || uid.isEmpty())
			uid=UUID.randomUUID().toString();
		return uid;
	}

	@Override
	public void setUid(String uid) {
		if(uid==null || uid.isEmpty())
			uid=UUID.randomUUID().toString();
		this.uid=uid;
		this.name=uid;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id=id;		
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	@Override
	public String getName() {
		name = uid;
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
		
	}
}
