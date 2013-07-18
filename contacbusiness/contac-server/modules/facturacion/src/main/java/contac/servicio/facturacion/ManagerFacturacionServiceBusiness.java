/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.facturacion;

import contac.modelo.entity.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2012.
 * User: emortiz
 * Date: 02-16-12
 * Time: 10:02 AM.
 */
public interface ManagerFacturacionServiceBusiness extends ManagerFacturacionServiceRemote {

    /**
     * Crear factura comercial
     *
     * @param noFactura,          Numero de Factura
     * @param tipoFactura,        Tipo de factura
     * @param idCliente,          Cliente a quien se factura
     * @param idAlmacen,          Almacen de facturacion
     * @param tasaCambio,         Tasa de cambio
     * @param nombreCliente,      Nombre del cliente
     * @param idMoneda,           Identificador de moneda
     * @param direccionEntrega,   Direccion de entrega
     * @param fechaAlta,          Fecha de alta
     * @param exonerada,          Es exonerada de impuesto
     * @param retencionFuente,    Retencion en la fuente
     * @param retencionMunicipal, Retencion municipal
     * @param idProforma,         Viene de una proforma
     * @param articulos,          Listado de articulos
     * @return Factura
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Factura crearFactura(long noFactura, int tipoFactura, Integer idCliente, Integer idAlmacen,
                                Integer idAgenteVentas, BigDecimal porcDescuento, BigDecimal porcIva,
                                BigDecimal porcRetFuente, BigDecimal porcRetMunicipal, BigDecimal tasaCambio,
                                String nombreCliente, Integer idMoneda, Direccion direccionEntrega, Date fechaAlta,
                                boolean exonerada, boolean retencionFuente, boolean retencionMunicipal,
                                Integer idProforma, List<ArticuloFactura> articulos)
            throws ManagerFacturacionServiceBusinessException, RemoteException;

    /**
     * Modificar factura cliente
     *
     * @param idFactura,          Identificador de factura a guardar
     * @param tasaCambio,         Tasa de cambio
     * @param direccionEntrega,   Direccion de entrega
     * @param porcDescuento,      Porcentaje de descuento
     * @param porcIva,            Porcentaje de Iva
     * @param porcRetFuente,      Porcentaje de retencion en la fuente
     * @param porcRetMunicipal,   Porcentaje de retencion municipal
     * @param fechaAlta,          Fecha alta factura
     * @param exonerada,          Exonerada
     * @param retencionFuente,    Retencion en la fuente
     * @param retencionMunicipal, Retencion municipal
     * @param idProforma,         Identificador de proforma
     * @param articulos,          Listado de articulos
     * @return Factura
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Factura modificarFactura(Integer idFactura, BigDecimal tasaCambio, Direccion direccionEntrega,
                                    BigDecimal porcDescuento, BigDecimal porcIva, BigDecimal porcRetFuente,
                                    BigDecimal porcRetMunicipal, Date fechaAlta, boolean exonerada, boolean retencionFuente,
                                    boolean retencionMunicipal, Integer idProforma, List<ArticuloFactura> articulos)
            throws ManagerFacturacionServiceBusinessException, RemoteException;

    /**
     * Anular factura
     *
     * @param idFactura, Identificador de factura
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void anularFactura(Integer idFactura) throws ManagerFacturacionServiceBusinessException, RemoteException;

    /**
     * Eliminar factura
     *
     * @param idFactura, Identificador de factura
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void eliminarFactura(Integer idFactura) throws ManagerFacturacionServiceBusinessException, RemoteException;

    /**
     * Crear proforma a cliente
     *
     * @param idCliente,        Identificador de cliente
     * @param idAlmacen,        Identificador de almacen
     * @param idTasaCambio,     Identificador de tasa de cambio
     * @param nombreCliente,    Nombre del cliente
     * @param direccionEntrega, Direccion de entrega
     * @param fechaAlta,        Fecha de alta de proforma
     * @param articulos,        Listado de articulos en proforma
     * @return Proforma
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Proforma crearProforma(Integer idCliente, Integer idAlmacen, Integer idTasaCambio, String nombreCliente,
                                  Direccion direccionEntrega, Date fechaAlta, List<ArticuloFactura> articulos)
            throws ManagerFacturacionServiceBusinessException, RemoteException;

    /**
     * Buscar todos los agentes de ventas registrados
     *
     * @return List<AgenteVentas>
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<AgenteVentas> buscarAgentesVentas() throws ManagerFacturacionServiceBusinessException, RemoteException;

    /**
     * Buscar Agentes de ventas para un almacen
     *
     * @return List<AgenteVentas>
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<AgenteVentas> buscarAgentesVentasUsuario() throws ManagerFacturacionServiceBusinessException, RemoteException;

    /**
     * Crear Agente de Ventas para un almacen
     *
     * @param codigo,    Codigo de agente
     * @param nombre,    Nombre de agente de ventas
     * @param idAlmacen, Almacen asignado
     * @param isActivo,  Activo para poder facturar
     * @return AgenteVentas
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public AgenteVentas crearAgenteVentas(long codigo, String nombre, Integer idAlmacen, boolean isActivo) throws
            ManagerFacturacionServiceBusinessException, RemoteException;

    /**
     * Modificar Agente de Ventas para un almacen
     *
     * @param idAgenteVentas, Identificador de agente de ventas
     * @param nombre,         Nombre de agente de ventas
     * @param idAlmacen,      Almacen asignado
     * @param isActivo,       Activo para poder facturar
     * @return AgenteVentas
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public AgenteVentas modificarAgenteVentas(Integer idAgenteVentas, String nombre, Integer idAlmacen, boolean isActivo) throws
            ManagerFacturacionServiceBusinessException, RemoteException;

    /**
     * Buscar Agente de ventas por su identificador
     *
     * @param codigo, Codigo identificador de agente
     * @return AgenteVentas
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public AgenteVentas buscarAgentesVentasPorCodigo(long codigo) throws ManagerFacturacionServiceBusinessException,
            RemoteException;

    /**
     * Obtener fecha del servidor
     *
     * @return Date
     * @throws ManagerFacturacionServiceBusinessException,
     *          Exception
     */
    public Date getFechaServidor() throws ManagerFacturacionServiceBusinessException, RemoteException;

    /**
     * Buscar tasas de cambio facturacion
     *
     * @return List
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<TasaCambio> buscarTasasCambioFacturacion() throws ManagerFacturacionServiceBusinessException, RemoteException;

    /**
     * Buscar facturas por estado
     *
     * @param alias, Estado de factura
     * @return List<Factura>
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Factura> buscarFacturasPorEstado(String alias) throws ManagerFacturacionServiceBusinessException,
            RemoteException;

    /**
     * Buscar facturas comerciales por rangos de fecha
     *
     * @param fechaDesde,    Fecha desde
     * @param fechaHasta,    Fecha hasta
     * @param idAlmacen,     Almacen de facturacion
     * @param idTipoFactura, Tipo de factura
     * @return List
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Factura> buscarFacturasPorFecha(Date fechaDesde, Date fechaHasta, Integer idAlmacen,
                                                Integer idTipoFactura) throws ManagerFacturacionServiceBusinessException,
            RemoteException;

    /**
     * Buscar Listado de facturas por Identificador de Factura - Hibernate Load producto
     *
     * @param idFactura, Identificador de Factura
     * @return List<ArticuloFactura>
     * @throws ManagerFacturacionServiceBusinessException,
     *          Exception
     */
    public List<ArticuloFactura> buscarArticulosFactura(Integer idFactura) throws ManagerFacturacionServiceBusinessException,
            RemoteException;

    /**
     * Usuario logueado tiene permisos de editar datos especificos de factura
     *
     * @throws ManagerFacturacionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void usuarioEditaDatosFactura() throws ManagerFacturacionServiceBusinessException, RemoteException;

}

