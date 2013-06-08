/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.reports;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.Serializable;
import java.util.Map;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 01-30-12
 * Time: 04:43 PM
 */
public class JRXReportGenerated implements Serializable{

    //Reporte stream content jasper
    private JasperReport jasperReport;

    //Listado de parametros
    private Map parameters;

    //Bean collection object to show report data
    private JRBeanCollectionDataSource dataSource;

    public JasperReport getJasperReport() {
        return jasperReport;
    }

    public void setJasperReport(JasperReport jasperReport) {
        this.jasperReport = jasperReport;
    }

    public Map getParameters() {
        return parameters;
    }

    public void setParameters(Map parameters) {
        this.parameters = parameters;
    }

    public JRBeanCollectionDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(JRBeanCollectionDataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Constructor JRXReportGenerated
     * @param jasperReport, JasperReport
     * @param parameters, Map
     * @param dataSource, JRBeanCollectionDataSource
     */
    public JRXReportGenerated(JasperReport jasperReport, Map parameters, JRBeanCollectionDataSource dataSource) {
        this.jasperReport = jasperReport;
        this.parameters = parameters;
        this.dataSource = dataSource;
    }
}
