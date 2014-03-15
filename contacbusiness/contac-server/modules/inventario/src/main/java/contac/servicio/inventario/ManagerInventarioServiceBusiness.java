/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.inventario;

import contac.modelo.entity.*;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

/**
 * Contac Business Software. All rigths reserved 2011.
 * Interfaz de Accesso Manager Inventario Service
 * User: EMontenegro
 * Date: 11-02-11
 * Time: 09:40 PM
 */
public interface ManagerInventarioServiceBusiness extends ManagerInventarioServiceRemote {

    /**
     * Busca listado de almacenes por usuario autorizacion
     *
     * @return List
     * @throws ManagerInventarioServiceBusinessException,
     *                                   Exception
     * @throws java.rmi.RemoteException, Exception
     */
    public List<Almacen> buscarAlmacenesPorUsuario() throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Buscar almacen del usuario autorizacion
     *
     * @return List
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Almacen buscarAlmacenUsuario() throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Buscar orden de entrada por su identificador
     *
     * @param idOrdenEntrada, Integer
     * @return ordenEntrada
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenEntrada buscarOrdenEntradaPorId(Integer idOrdenEntrada) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Busca ordenes de entrada por tipo de entrada
     *
     * @param tipoEntrada, int
     * @return List<ordenEntrada>
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<OrdenEntrada> buscarOrdenesEntradaPorTipo(int tipoEntrada) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Buscar ordenes de entrada por estados
     *
     * @param estados, List<String>
     * @return List
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<OrdenEntrada> buscarOrdenesEntradaPorEstados(List<String> estados, Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Buscar facturas comerciales por rangos de fecha
     *
     * @param fechaDesde,    Fecha desde
     * @param fechaHasta,    Fecha hasta
     * @param idAlmacen,     Almacen de facturacion
     * @return List
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<OrdenEntrada> buscarFacturasPorFechaAlmacen(List<String> estados, Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Buscar ordenes de entrada por rangos de fecha
     *
     * @param fechaInicio, Date
     * @param fechaFin,    Date
     * @return List
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<OrdenEntrada> buscarOrdenesEntradaPorRangoFechas(Date fechaInicio, Date fechaFin) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Buscar Orden de salida por su identificador
     *
     * @param idOrdenSalida, Integer
     * @return OrdenSalida
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenSalida buscarOrdenSalidaPorId(Integer idOrdenSalida) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Buscar Ordenes de salida por estados
     *
     * @param estados, List<String></String>
     * @return List
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<OrdenSalida> buscarOrdenesSalidaPorEstados(List<String> estados, Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Buscar Ordenes de salida por rangos de fecha
     * @param estados, List<String>
     * @param fechaInicio, Date
     * @param fechaFin,    Date
     * @param idAlmacen, Integer
     * @return List
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<OrdenSalida> buscarOrdnesSalidaPorRangosFechas(List<String> estados, Date fechaInicio, Date fechaFin, Integer idAlmacen) throws ManagerInventarioServiceBusinessException,
            RemoteException;

     /**
     * Buscar Orden de Traslado por su identificador
     *
     * @param idOrdenTraslado, Integer
     * @return OrdenTraslado
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenTraslado buscarOrdenTrasladoPorId(Integer idOrdenTraslado) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Buscar Ordenes de traslado por estados
     *
     * @param estados, List
     * @Param fechaDesde, Date
     * @Param fechaHasta, Date
     * @Param idAlmacen, Integer
     * @return List
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<OrdenTraslado> buscarOrdenesTrasladoPorEstados(List<String> estados, Date fechaDesde, Date fechaHasta, Integer idAlmacen) throws ManagerInventarioServiceBusinessException,
            RemoteException;

     /**
     * Buscar estado del Registro de Levantamiento Inventario Físico
     *
     * @param idLevantamiento, Identificador de Orden
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void buscarEstadoAjuste(Integer idLevantamiento) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Buscar registro de Levantamiento de Inventario por rangos de fecha
     *
     * @param fechaInicio, Date
     * @param fechaFin,    Date
     * @param idAlmacen,   Integer
     * @return List
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<OrdenLevantamientoFisico> buscarOrdenesLevantamientoFisicoPorFechas(Date fechaInicio, Date fechaFin, Integer idAlmacen) throws ManagerInventarioServiceBusinessException,
            RemoteException;


    /**
     * Buscar ordenes de traslado por rangos de fecha
     *
     * @param fechaInicio, Date
     * @param fechaFin,    Date
     * @return List
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<OrdenTraslado> buscarOrdenesTrasladoPorRangosFechas(Date fechaInicio, Date fechaFin, Integer idAlmacen, Integer idAlmacenSalida) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Crear orden de entrada de inventario
     *
     * @param tipoEntrada,      Tipo de entrada de inventario
     * @param fechaAlta,        Fecha alta ingreso inventario
     * @param fechaSolicitud,   Fecha solicitud de ingreso inventario
     * @param idAlmacenIngreso, Codigo almacen de ingreso
     * @param personaEntrega,   Nombre persona entrega
     * @param descripcion,      Descripcion de la entrega
     * @param articulos,        Listado de articulos ingreso inventario
     * @return OrdenEntrada
     * @throws ManagerInventarioServiceBusinessException,
     *                                   Exception
     * @throws java.rmi.RemoteException, Exception
     */
    public OrdenEntrada crearOrdenEntrada(int tipoEntrada, Date fechaAlta, Date fechaSolicitud, Integer idAlmacenIngreso,
                                          String personaEntrega, String descripcion, List<ArticuloEntrada> articulos)
            throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Modificar orden de entrada de inventario
     *
     * @param idOrdenEntrada, Identificador de orden de entrada
     * @param tipoEntrada,    Tipo de entrada de inventario
     * @param fechaAlta,      Fecha alta ingreso inventario
     * @param fechaSolicitud, Fecha solicitud de ingreso inventario
     * @param personaEntrega, Nombre persona entrega
     * @param descripcion,    Descripcion de la orden de entrega
     * @param articulos,      Listado de articulos ingreso inventario
     * @return OrdenEntrada
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenEntrada modificarOrdenEntrada(Integer idOrdenEntrada, int tipoEntrada, Date fechaAlta, Date fechaSolicitud,
                                              String personaEntrega, String descripcion, List<ArticuloEntrada> articulos)
            throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Anular orden de entrada de inventario
     *
     * @param idOrdenEntrada, Indentificador de orden de entrada
     * @return OrdenEntrada
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenEntrada anularOrdenEntrada(Integer idOrdenEntrada) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Eliminar orden de entrada de inventario
     *
     * @param idOrdenEntrada, Identificador de orden de entrada
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void eliminarOrdenEntrada(Integer idOrdenEntrada) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Crear orden de traslado de inventario
     *
     * @param fechaAlta,        Fecha de Alta solicitud
     * @param fechaSolicitud,   Fecha de solicitud de inventario
     * @param idAlmacenSalida,  Identificador de almacen de salida inventario
     * @param idAlmacenIngreso, Identificador de almacen de ingreso inventario
     * @param personaEntrega,   Persona entrega
     * @param personaRecibe,    Persona recibe
     * @param descripcion,      Descripcion del traslado
     * @param articulos,        Listado de articulos en el traslado
     * @return ordenTraslado
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenTraslado crearOrdenTraslado(Date fechaAlta, Date fechaSolicitud, Integer idAlmacenSalida, Integer idAlmacenIngreso,
                                            String personaEntrega, String personaRecibe, String descripcion, List<ArticuloTraslado> articulos)
            throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Validar Orden de Traslado a Imprimir
     *
     * @param idOrdenTraslado, Identificador de solicitud de traslado
     * @return OrdenTraslado
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void validarImpresionOrdenTraslado(Integer idOrdenTraslado) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Validar Orden de Baja a Imprimir
     *
     * @param idOrdenSalida, Identificador de solicitud de traslado
     * @return OrdenSalida
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void validarImpresionOrdenBaja(Integer idOrdenSalida) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Validar Orden de Entrada a Imprimir
     *
     * @param idOrdenEntrada, Identificador de orden de entrada
     * @return OrdenEntrada
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void validarImpresionOrdenEntrada(Integer idOrdenEntrada) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Aplicar orden de traslado de inventario
     *
     * @param idOrdenTraslado, Identificador de solicitud de traslado
     * @return OrdenTraslado
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void aplicarTraslado(Integer idOrdenTraslado) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Modificar orden de traslado de inventario
     *
     * @param idOrdenTraslado, Identificador de solicitud de traslado
     * @param fechaAlta,       Fecha de alta de solicitud de inventario
     * @param fechaSolicitud,  Fecha de solicitud de inventario
     * @param personaEntrega,  Persona entrega
     * @param personaRecibe,   Persona recibe
     * @param descripcion,     Descripcion del traslado
     * @param articulos,       Listado de articulos en el traslado
     * @return OrdenTraslado
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenTraslado modificarOrdenTraslado(Integer idOrdenTraslado, Date fechaAlta, Date fechaSolicitud, String personaEntrega,
                                                String personaRecibe, String descripcion, List<ArticuloTraslado> articulos)
            throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Anular orden de traslado
     *
     * @param idOrdenTraslado, Identificador de orden de traslado
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void anularOrdenTraslado(Integer idOrdenTraslado) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Crear orden de salida
     *
     * @param fechaAlta,       Fecha de alta de solicitud de salida
     * @param fechaSolicitud,  Fecha de solicitud
     * @param idAlmacenSalida, Identificador de almacen de salida
     * @param personaAutoriza, Persona de autorizacion
     * @param descripcion,     Descripcion de salida de orden
     * @param articulos,       Listado de articulos de orden de salida
     * @return OrdenSalida
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenSalida crearOrdenSalida(Date fechaAlta, Date fechaSolicitud, Integer idAlmacenSalida, String personaAutoriza,
                                        String descripcion, List<ArticuloSalida> articulos) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Modificar orden de salida
     *
     * @param idOrdenSalida,   Identificador de orden salida
     * @param fechaAlta,       Fecha de alta de solicitud de salida
     * @param fechaSolicitud,  Fecha de solicitud
     * @param personaAutoriza, Persona de autorizacion
     * @param descripcion,     Descripcion de salida de orden
     * @param articulos,       Listado de articulos de orden de salida
     * @return OrdenSalida
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenSalida modificarOrdenSalida(Integer idOrdenSalida, Date fechaAlta, Date fechaSolicitud, String personaAutoriza,
                                            String descripcion, List<ArticuloSalida> articulos) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Anular Orden de Salida
     *
     * @param idOrdenSalida, Identificador de orden de salida
     * @return OrdenSalida
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenSalida anularOrdenSalida(Integer idOrdenSalida) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Crear orden de Levantamiento de inventario
     *
     * @param fechaAlta,        Fecha de alta de solicitud
     * @param fechaSolicitud,   Fecha de solicitud
     * @param idAlmacenIngreso, Identificador de almacen de ingreso
     * @param descripcion,      Descripcion de levantamiento
     * @param articulos,        Listado de articulos en levantamiento
     * @return OrdenLevantamientoFisico
     * @throws ManagerInventarioServiceBusinessException,
     *                                   Exception
     * @throws java.rmi.RemoteException, Exception
     */
    public OrdenLevantamientoFisico crearOrdenLevantamientoFisico(Date fechaAlta, Date fechaSolicitud, Integer idAlmacenIngreso,
                                                                  String descripcion, List<ArticuloLevantamientoFisico> articulos)
            throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Modificar orden de entrada de inventario
     *
     * @param idOrdenLevantamiento, Identificador de orden de Levantamiento
     * @param fechaAlta,            Fecha alta ingreso inventario
     * @param fechaSolicitud,       Fecha solicitud de ingreso inventario
     * @param descripcion,          Descripcion de la orden de entrega
     * @param articulos,            Listado de articulos ingreso inventario
     * @return OrdenLevantamientoFisico
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenLevantamientoFisico modificarOrdenLevantamientoFisico(Integer idOrdenLevantamiento, Date fechaAlta,
                                                                      Date fechaSolicitud, String descripcion,
                                                                      List<ArticuloLevantamientoFisico> articulos)
            throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Anular orden de levantamiento fisico
     *
     * @param idOrdenLevantamientoFisico, Identificador de orden de levantamiento
     * @return OrdenLevantamientoFisico
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenLevantamientoFisico anularOrdenLevantamientoFisico(Integer idOrdenLevantamientoFisico) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Eliminar orden de levantamiento Fisico
     *
     * @param idOrdenLevantamientoFisico, Identificador de levantamiento fisico
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void eliminarOrdenLevantamientoFisico(Integer idOrdenLevantamientoFisico) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Aplicar ajuste levantamiento inventario
     *
     * @param idOrdenLevantamiento, Identificador de orden de levantamiento
     * @param tipoAjuste,           Identificador de tipo de ajuste (PARCIAL, TOTAL)
     * @return OrdenLevantamientoFisico
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, RemoteException
     */
    public OrdenLevantamientoFisico aplicarAjusteLevantamientoInventario(Integer idOrdenLevantamiento, int tipoAjuste) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Calcular ajuste levantamiento inventario
     *
     * @param idOrdenLevantamiento, Identificador de orden de levantamiento
     * @return OrdenLevantemientoFisico
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public OrdenLevantamientoFisico calcularAjusteLevantamientoInventario(Integer idOrdenLevantamiento) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Buscar Ordenes de Levantamiento Inventario por estados
     *
     * @param estados, List<String>
     * @return List<OrdenLevantamientoFisico>
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<OrdenLevantamientoFisico> buscarOrdenesLevantamientoFisicoPorEstados(List<String> estados) throws ManagerInventarioServiceBusinessException,
            RemoteException;

    /**
     * Buscar Movimientos de Inventario para un producto en un almacen
     *
     * @param codigoProducto, Codigo de producto
     * @param idAlmacen,      Identificador del almacen
     * @param fechaDesde,     Fecha desde
     * @param fechaHasta,     Fecha hasta
     * @return List
     * @throws ManagerInventarioServiceBusinessException,
     *          Exception
     */
    public List<MovimientoInventario> buscarMovimientosInventario(String codigoProducto, Integer idAlmacen, Date fechaDesde,
                                                                  Date fechaHasta) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Cerrar inventario mensual
     *
     * @param fechaDesde, Fecha inicio para iniciar el cierre
     * @param fechaHasta, Fecha fin para finalizar el cierre
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void cerrarInventarioMensual(Date fechaDesde, Date fechaHasta) throws ManagerInventarioServiceBusinessException, RemoteException;

    /**
     * Calcular Maximos y minimos de inventario
     *
     * @throws ManagerInventarioServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void calcularMaximosMinimos() throws ManagerInventarioServiceBusinessException, RemoteException;
}
