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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.kemri.wellcome.dhisreport.api.model.Identifiable;

@Entity
@Table(name=ImportConflict.TABLE_NAME)
@XmlAccessorType( XmlAccessType.FIELD )
@JsonIgnoreProperties(ignoreUnknown=true)
public class ImportConflict implements Serializable,Identifiable
{
	private static final long serialVersionUID = -5319134589685978965L;

	public static final String TABLE_NAME ="import_conflicts";
	
	@XmlTransient
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@XmlTransient
	@Column(name="uid", unique=true)
	private String uid;
	
	@XmlAttribute( required = true )
	@Column(name="object", nullable=false)
    private String object;

    @XmlAttribute( required = true )
    @Column(name="value", nullable=false)
    private String value;
    
    @XmlTransient
    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(name="import_summary_id",nullable=false)
    @JsonBackReference
    private ImportSummary importSummary;
    
    @XmlTransient
    @Column(name = "name",nullable=false, unique = true)
    private String name;

    public ImportConflict(){
    	
    }

    public ImportConflict( String object, String value )
    {
        this.object = object;
        this.value = value;
    }

    public String getObject()
    {
        return object;
    }

    public void setObject( String object )
    {
        this.object = object;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "ImportConflict{" + "object= '" + object + '\'' + ", value='" + value + '\'' + '}';
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
		this.name=name;
		
	}
}
