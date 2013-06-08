/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.inventario;

import contac.modelo.PersistenceManagementServiceFactory;
import contac.modelo.PersistenceManagementServiceFactoryException;
import org.apache.log4j.Logger;

/**
 * Servicio de Exception para manager de Inventario
 * User: EMontenegro
 * Date: 11-02-11
 * Time: 09:41 PM
 */
public class ManagerInventarioServiceBusinessException extends Exception {

    /* Apache Log4j */
    private static final Logger logger = Logger.getLogger(ManagerInventarioServiceBusinessException.class);

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param message, Message exception
     * @generated
     */
    public ManagerInventarioServiceBusinessException(String message) {

        //Super constructor
        super(message);

        //Perform a rollback runtime transaction
        try {
            PersistenceManagementServiceFactory.rollback();
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param message, Message exception
     * @param cause,   Cause exception
     * @generated
     */
    public ManagerInventarioServiceBusinessException(String message, Throwable cause) {

        //Super constructor
        super(message, cause);

        //Perform a rollback runtime transaction
        try {
            PersistenceManagementServiceFactory.rollback();
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @param cause, Throwable cause exception
     * @generated
     */
    public ManagerInventarioServiceBusinessException(Throwable cause) {

        //Super constructor
        super(cause);

        //Perform a rollback runtime transaction
        try {
            PersistenceManagementServiceFactory.rollback();
        } catch (PersistenceManagementServiceFactoryException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
