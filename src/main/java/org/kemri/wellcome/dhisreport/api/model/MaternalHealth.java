/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 
package org.kemri.wellcome.dhisreport.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

*//**
 *
 * @author ttuti
 *//*
@Entity
@Table(name = "maternal_health")
public class MaternalHealth implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "Period_ID")
    private Integer periodID;
    @Column(name = "Organisation_unit_ID")
    private Integer organisationunitID;
    @Column(name = "Period_UID")
    private String periodUID;
    @Column(name = "Organisation_unit_UID")
    private String organisationunitUID;
    @Column(name = "Period")
    private String period;
    @Column(name = "Organisation_unit")
    private String organisationunit;
    @Column(name = "Period_code")
    private String periodcode;
    @Column(name = "Organisation_unit_code")
    private String organisationunitcode;
    @Column(name = "Reporting_month")
    private String reportingmonth;
    @Column(name = "Organisation_unit_parameter")
    private String organisationunitparameter;
    @Column(name = "Organisation_unit_is_parent")
    private String organisationunitisparent;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ANC_2_Coverage")
    private BigDecimal aNC2Coverage;
    @Column(name = "PHU_del_rate")
    private BigDecimal pHUdelrate;
    @Column(name = "ANC_TT2_coverage")
    private BigDecimal aNCTT2coverage;
    @Column(name = "ANC_LLITN_coverage")
    private BigDecimal aNCLLITNcoverage;
    @Column(name = "ANC_IPT_2_Coverage")
    private BigDecimal aNCIPT2Coverage;
    @Column(name = "Births_by_skilled")
    private BigDecimal birthsbyskilled;
    @Column(name = "Mat_death_rate_reg_birth")
    private String matdeathrateregbirth;
    @Column(name = "NVP_during_labour_rate")
    private String nVPduringlabourrate;
    @Basic(optional = false)
    @Column(name = "startPeriod")
    @Temporal(TemporalType.DATE)
    private Date startPeriod;
    @Basic(optional = false)
    @Column(name = "endPeriod")
    @Temporal(TemporalType.DATE)
    private Date endPeriod;

    public MaternalHealth(Integer id) {
        this.id = id;
    }

    public MaternalHealth(Integer id, Date startPeriod, Date endPeriod) {
        this.id = id;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPeriodID() {
        return periodID;
    }

    public void setPeriodID(Integer periodID) {
        this.periodID = periodID;
    }

    public Integer getOrganisationunitID() {
        return organisationunitID;
    }

    public void setOrganisationunitID(Integer organisationunitID) {
        this.organisationunitID = organisationunitID;
    }

    public String getPeriodUID() {
        return periodUID;
    }

    public void setPeriodUID(String periodUID) {
        this.periodUID = periodUID;
    }

    public String getOrganisationunitUID() {
        return organisationunitUID;
    }

    public void setOrganisationunitUID(String organisationunitUID) {
        this.organisationunitUID = organisationunitUID;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getOrganisationunit() {
        return organisationunit;
    }

    public void setOrganisationunit(String organisationunit) {
        this.organisationunit = organisationunit;
    }

    public String getPeriodcode() {
        return periodcode;
    }

    public void setPeriodcode(String periodcode) {
        this.periodcode = periodcode;
    }

    public String getOrganisationunitcode() {
        return organisationunitcode;
    }

    public void setOrganisationunitcode(String organisationunitcode) {
        this.organisationunitcode = organisationunitcode;
    }

    public String getReportingmonth() {
        return reportingmonth;
    }

    public void setReportingmonth(String reportingmonth) {
        this.reportingmonth = reportingmonth;
    }

    public String getOrganisationunitparameter() {
        return organisationunitparameter;
    }

    public void setOrganisationunitparameter(String organisationunitparameter) {
        this.organisationunitparameter = organisationunitparameter;
    }

    public String getOrganisationunitisparent() {
        return organisationunitisparent;
    }

    public void setOrganisationunitisparent(String organisationunitisparent) {
        this.organisationunitisparent = organisationunitisparent;
    }

    public BigDecimal getANC2Coverage() {
        return aNC2Coverage;
    }

    public void setANC2Coverage(BigDecimal aNC2Coverage) {
        this.aNC2Coverage = aNC2Coverage;
    }

    public BigDecimal getPHUdelrate() {
        return pHUdelrate;
    }

    public void setPHUdelrate(BigDecimal pHUdelrate) {
        this.pHUdelrate = pHUdelrate;
    }

    public BigDecimal getANCTT2coverage() {
        return aNCTT2coverage;
    }

    public void setANCTT2coverage(BigDecimal aNCTT2coverage) {
        this.aNCTT2coverage = aNCTT2coverage;
    }

    public BigDecimal getANCLLITNcoverage() {
        return aNCLLITNcoverage;
    }

    public void setANCLLITNcoverage(BigDecimal aNCLLITNcoverage) {
        this.aNCLLITNcoverage = aNCLLITNcoverage;
    }

    public BigDecimal getANCIPT2Coverage() {
        return aNCIPT2Coverage;
    }

    public void setANCIPT2Coverage(BigDecimal aNCIPT2Coverage) {
        this.aNCIPT2Coverage = aNCIPT2Coverage;
    }

    public BigDecimal getBirthsbyskilled() {
        return birthsbyskilled;
    }

    public void setBirthsbyskilled(BigDecimal birthsbyskilled) {
        this.birthsbyskilled = birthsbyskilled;
    }

    public String getMatdeathrateregbirth() {
        return matdeathrateregbirth;
    }

    public void setMatdeathrateregbirth(String matdeathrateregbirth) {
        this.matdeathrateregbirth = matdeathrateregbirth;
    }

    public String getNVPduringlabourrate() {
        return nVPduringlabourrate;
    }

    public void setNVPduringlabourrate(String nVPduringlabourrate) {
        this.nVPduringlabourrate = nVPduringlabourrate;
    }

    public Date getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Date startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MaternalHealth)) {
            return false;
        }
        MaternalHealth other = (MaternalHealth) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.MaternalHealth[ id=" + id + " ]";
    }
    
}
*/