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
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.*;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.kemri.wellcome.dhisreport.api.model.Identifiable;

@Entity
@Table(name=ImportCount.TABLE_NAME)
@XmlAccessorType( XmlAccessType.FIELD )
@JsonIgnoreProperties(ignoreUnknown=true)
public class ImportCount implements Serializable, Identifiable
{
	private static final long serialVersionUID = -5319134589685978965L;

	public static final String TABLE_NAME ="import_count";
	
	@XmlTransient
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@XmlTransient
	@Column(name="uid", unique=true)
	private String uid;
	
	@XmlAttribute( required = true )
	@Column(name="imported", nullable=false)
    private int imported;

    @XmlAttribute( required = true )
    @Column(name="updated", nullable=false)
    private int updated;

    @XmlAttribute( required = true )
    @Column(name="ignored", nullable=false)
    private int ignored;
    
    @XmlTransient
    @OneToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(name="import_summary_id",nullable=false)
    @JsonBackReference
    private ImportSummary importSummary;
    
    @XmlTransient
    @Column(name="name",nullable=false, unique = true)
    private String name;

    public ImportCount()
    {
    	if(uid == null){
    		uid = UUID.randomUUID().toString();
    	}
    	if(name == null){
    		name = uid;
    	}
    }

    public ImportCount( int imported, int updated, int ignored )
    {
        this.imported = imported;
        this.updated = updated;
        this.ignored = ignored;
    }

    public int getImported()
    {
        return imported;
    }

    public void setImported( int imported )
    {
        this.imported = imported;
    }

    public int getUpdated()
    {
        return updated;
    }

    public void setUpdated( int updated )
    {
        this.updated = updated;
    }

    public int getIgnored()
    {
        return ignored;
    }

    public void setIgnored( int ignored )
    {
        this.ignored = ignored;
    }
    
    @Override
    public String toString()
    {
        return "[imports = " + imported + ", updates = " + updated + ", ignores = " + ignored + "]";
    }

    public void incrementImported()
    {
        imported++;
    }

    public void incrementUpdated()
    {
        updated++;
    }

    public void incrementIgnored()
    {
        ignored++;
    }

    public void incrementImported( int n )
    {
        imported += n;
    }

    public void incrementUpdated( int n )
    {
        updated += n;
    }

    public void incrementIgnored( int n )
    {
        ignored += n;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUid() {
		if(uid==null || uid.isEmpty())
			uid=UUID.randomUUID().toString();
		return uid;
	}

	public void setUid(String uid) {
		if(uid==null || uid.isEmpty())
			uid=UUID.randomUUID().toString();
		this.uid = uid;
		this.name=uid;
	}

	public ImportSummary getImportSummary() {
		return importSummary;
	}

	public void setImportSummary(ImportSummary importSummary) {
		this.importSummary = importSummary;
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
