/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kemri.wellcome.dhisreport.api.model;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author ttuti
 */
@Entity
@Table(name = "anc_indicator")
public class AncIndicator implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "Indicator_ID")
    private Integer indicatorID;
    @Column(name = "Period_ID")
    private Integer periodID;
    @Column(name = "Organisation_unit_ID")
    private Integer organisationunitID;
    @Column(name = "Indicator_UID")
    private String indicatorUID;
    @Column(name = "Period_UID")
    private Integer periodUID;
    @Column(name = "Organisation_unit_UID")
    private String organisationunitUID;
    @Column(name = "Indicator")
    private String indicator;
    @Column(name = "Period")
    private String period;
    @Column(name = "Organisation_unit")
    private String organisationunit;
    @Column(name = "Indicator_code")
    private String indicatorcode;
    @Column(name = "Period_code")
    private Integer periodcode;
    @Column(name = "Organisation_unit_code")
    private String organisationunitcode;
    @Column(name = "Reporting_month")
    private String reportingmonth;
    @Column(name = "Organisation_unit_parameter")
    private String organisationunitparameter;
    @Column(name = "Organisation_unit_is_parent")
    private String organisationunitisparent;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Outreach")
    private BigDecimal outreach;
    @Column(name = "Fixed")
    private BigDecimal fixed;
    @Column(name = "Total")
    private BigDecimal total;

    public AncIndicator() {
    }

    public AncIndicator(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIndicatorID() {
        return indicatorID;
    }

    public void setIndicatorID(Integer indicatorID) {
        this.indicatorID = indicatorID;
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

    public String getIndicatorUID() {
        return indicatorUID;
    }

    public void setIndicatorUID(String indicatorUID) {
        this.indicatorUID = indicatorUID;
    }

    public Integer getPeriodUID() {
        return periodUID;
    }

    public void setPeriodUID(Integer periodUID) {
        this.periodUID = periodUID;
    }

    public String getOrganisationunitUID() {
        return organisationunitUID;
    }

    public void setOrganisationunitUID(String organisationunitUID) {
        this.organisationunitUID = organisationunitUID;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
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

    public String getIndicatorcode() {
        return indicatorcode;
    }

    public void setIndicatorcode(String indicatorcode) {
        this.indicatorcode = indicatorcode;
    }

    public Integer getPeriodcode() {
        return periodcode;
    }

    public void setPeriodcode(Integer periodcode) {
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

    public BigDecimal getOutreach() {
        return outreach;
    }

    public void setOutreach(BigDecimal outreach) {
        this.outreach = outreach;
    }

    public BigDecimal getFixed() {
        return fixed;
    }

    public void setFixed(BigDecimal fixed) {
        this.fixed = fixed;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
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
        if (!(object instanceof AncIndicator)) {
            return false;
        }
        AncIndicator other = (AncIndicator) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication1.AncIndicator[ id=" + id + " ]";
    }
    
}
