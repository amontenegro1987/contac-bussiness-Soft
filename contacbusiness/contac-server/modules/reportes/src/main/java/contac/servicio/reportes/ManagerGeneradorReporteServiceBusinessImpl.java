/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.reportes;

import contac.modelo.PersistenceManagementServiceFactory;
import contac.modelo.PersistenceManagementServiceFactoryException;
import contac.modelo.entity.Roles;
import contac.reports.JRPrintReport;
import contac.reports.JRXReportGenerated;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusiness;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusinessException;
import contac.servicio.seguridad.ManagerSeguridadServiceBusiness;
import contac.servicio.seguridad.ManagerSeguridadServiceBusinessImpl;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 01-30-12
 * Time: 03:53 PM
 * Clase de implementacion al servicio de Generacion de reportes
 */
public class ManagerGeneradorReporteServiceBusinessImpl extends UnicastRemoteObject implements ManagerGeneradorReporteServiceBusiness {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(ManagerGeneradorReporteServiceBusinessImpl.class);

    //Acceso Manager de Autorizacion
    protected ManagerAutorizacionServiceBusiness mgrAutorizacion;
    protected ManagerSeguridadServiceBusiness mgrSeguridad;

    /**
     * Constructor default
     *
     * @throws RemoteException, java.rmi.RemoteException
     */
    public ManagerGeneradorReporteServiceBusinessImpl() throws RemoteException {
    }

    /**
     * Constructor Generador de Reporte
     *
     * @param mgrAutorizacion, ManagerAutorizacionServiceBusiness
     * @throws RemoteException, Exception
     */
    public ManagerGeneradorReporteServiceBusinessImpl(ManagerAutorizacionServiceBusiness mgrAutorizacion) throws RemoteException {

        //Llamar al constructor padre
        this();

        //Inicializar servicio de autorizacion
        this.mgrAutorizacion = mgrAutorizacion;
        mgrSeguridad = new ManagerSeguridadServiceBusinessImpl(this.mgrAutorizacion);
    }

    //Iniciar servicio de transaccion
    private boolean initBusinessService(String rolname) throws ManagerGeneradorReporteServiceBusinessException {

        try {

            //Iniciar servicio de autorizacion
            if (mgrAutorizacion == null)
                logger.error("Servicio de autenticacion inactivo");

            //Check authentication
            mgrAutorizacion.isUserInRole(rolname);

            //Iniciar servicio transaccional
            return PersistenceManagementServiceFactory.beginTransaction();

        } catch (ManagerAutorizacionServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerGeneradorReporteServiceBusinessException(e.getMessage(), e);
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerGeneradorReporteServiceBusinessException(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerGeneradorReporteServiceBusinessException(e.getMessage(), e);
        }
    }

    //Detener servicio de transaccion
    private void stopBusinessService(boolean value) throws ManagerGeneradorReporteServiceBusinessException {

        try {

            //Commit all changes
            PersistenceManagementServiceFactory.commit(value);

            //Detener servicio transaccional
            PersistenceManagementServiceFactory.closeEntityManager(value);

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerGeneradorReporteServiceBusinessException(e.getMessage(), e);
        }
    }

    //Rollback servicio de transaccion
    private void rollbackBusinessService() throws ManagerGeneradorReporteServiceBusinessException {

        try {

            //Rollback all changes
            PersistenceManagementServiceFactory.rollback();

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerGeneradorReporteServiceBusinessException(e.getMessage(), e);
        }
    }


    @Override
    public JasperPrint generateReport(Map parameters, JasperReport report) throws ManagerGeneradorReporteServiceBusinessException,
            RemoteException {

        //Iniciar servicio de autenticacion
        boolean transaction = initBusinessService(Roles.ROLCONTACUSER.toString());

        try {

            final Map p_parameters = parameters;
            final JasperReport p_report = report;

            final JasperPrint[] printReport = {null};

            Session session = PersistenceManagementServiceFactory.getEntityManager().unwrap(Session.class);
            session.doWork(new Work() {
                @Override
                public void execute(Connection connection) throws SQLException {
                    try {
                        JRXReportGenerated reportGenerated = new JRXReportGenerated(p_report, p_parameters,
                                connection);
                        printReport[0] = JRPrintReport.fillReport(reportGenerated);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                        throw new SQLException(e);
                    }

                    connection.close();
                }
            });

            return printReport[0];

        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
            throw new ManagerGeneradorReporteServiceBusinessException(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ManagerGeneradorReporteServiceBusinessException(e.getMessage(), e);
        } finally {
            stopBusinessService(transaction);
        }
    }
}
