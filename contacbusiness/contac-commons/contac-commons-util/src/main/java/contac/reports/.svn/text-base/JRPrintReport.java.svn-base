/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.reports;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.log4j.Logger;

/**
 * Contac Business Software. All rights reserved 2012
 * User: emortiz
 * Date: 01-30-12
 * Time: 04:58 PM
 * Print Viewer implementation utilities Jasper reports
 */
public class JRPrintReport {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(JRPrintReport.class);


    /**
     * Fill report with a JRBeanCollectionDataSource
     * @param jrxReportGenerated, JRXReportGenerated
     * @return JasperPrint
     */
    public static JasperPrint fillReport(JRXReportGenerated jrxReportGenerated) throws Exception {

        try {

            //Fill report.
            return JasperFillManager.fillReport(jrxReportGenerated.getJasperReport(), jrxReportGenerated.getParameters(),
                    jrxReportGenerated.getDataSource());

        } catch (JRException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}
