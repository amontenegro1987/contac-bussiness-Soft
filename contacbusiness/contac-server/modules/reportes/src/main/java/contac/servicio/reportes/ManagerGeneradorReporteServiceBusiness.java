/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.reportes;

import contac.reports.JRXReportGenerated;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 01-30-12
 * Time: 03:52 PM
 * Interfaz de acceso al servicio de reportes
 */
public interface ManagerGeneradorReporteServiceBusiness extends ManagerGeneradorReporteServiceRemote {

    /**
     * Generate Reports
     *
     * @param parameters, Parameters
     * @param report,     JasperReport
     * @return JasperPrint
     * @throws ManagerGeneradorReporteServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public JasperPrint generateReport(Map parameters, JasperReport report) throws ManagerGeneradorReporteServiceBusinessException,
            RemoteException;
}
