package contac.servicio.administracion;

import contac.modelo.entity.*;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Manager Administracion Service Business .  Define todas las operaciones soportadas por este servicio
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-26-2010
 * Time: 10:44:37 PM
 */
public interface ManagerAdministracionServiceBusiness extends ManagerAdministracionServiceBusinessRemote {

    /**
     * Anular Registro de Almacen
     */

    public Almacen anularAlmacen(Integer idAlmacen) throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     *
     * Eliminar Registro de Almacen
     */

    public void eliminarAlmacen(Integer idAlmacen) throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Activar Registro de Almacen
     */

    public Almacen activarAlmacen(Integer idAlmacen) throws ManagerAdministracionServiceBusinessException, RemoteException;


    /**
     * Registra una nueva compania.
     *
     * @param nit,                    numero comercial de la compania
     * @param razonSocial,            razon social
     * @param nombreComercial,        nombre comercial
     * @param fechaConstitucion,      fecha constitucion
     * @param fechaAltaContribuyente, fecha alta contribuyente
     * @param eslogan,                frase de la compania (vision)
     * @param direccion,              direccion de las oficinas corporativas
     * @param clasificaciones,        clasificador comercial
     * @param monedaReferencia,       moneda referencia de la compania
     * @param almacenes,              listado de almacenes o sucursales
     * @param tipoPersona,            tipo de persona
     * @param tipoPersonaDesc,        descripcion tipo de persona
     * @param logotipo,               imagen de la compania o logotipo
     * @return Compania
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Excepcion,
     * @throws RemoteException, Exception
     */
    public Compania registrarCompania(String nit, String razonSocial, String nombreComercial, Date fechaConstitucion,
                                      Date fechaAltaContribuyente, String eslogan, Direccion direccion, Set<Clasificador> clasificaciones,
                                      Moneda monedaReferencia, Set<Almacen> almacenes, byte tipoPersona, String tipoPersonaDesc,
                                      byte[] logotipo) throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Modificar datos de compania
     *
     * @param id                      numero identificador de compania
     * @param nit,                    numero comercial de la compania
     * @param razonSocial,            razon social
     * @param nombreComercial,        nombre comercial
     * @param fechaConstitucion,      fecha constitucion
     * @param fechaAltaContribuyente, fecha alta contribuyente
     * @param eslogan,                frase de la compania (vision)
     * @param direccion,              direccion de las oficinas corporativas
     * @param clasificaciones,        clasificador comercial
     * @param monedaReferencia,       moneda referencia de la compania
     * @param almacenes,              listado de almacenes o sucursales
     * @param tipoPersona,            tipo de persona
     * @param tipoPersonaDesc,        descripcion tipo de persona
     * @param logotipo,               imagen de la compania o logotipo
     * @return Compania
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Excepcion
     * @throws RemoteException, Exception
     */
    public Compania modificarCompania(Integer id, String nit, String razonSocial, String nombreComercial, Date fechaConstitucion,
                                      Date fechaAltaContribuyente, String eslogan, Direccion direccion, Set<Clasificador> clasificaciones,
                                      Moneda monedaReferencia, Set<Almacen> almacenes, byte tipoPersona, String tipoPersonaDesc,
                                      byte[] logotipo) throws ManagerAdministracionServiceBusinessException, RemoteException;


    /**
     * Inactivar compania
     *
     * @param id, codigo identificador de la compania
     * @return Compania
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Compania inactivarCompania(Integer id) throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Busca compania por su identificador
     *
     * @param id, codigo identificador de la compania
     * @return Compania
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Compania buscarCompaniaPorId(Integer id) throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Busca compania por codigo
     *
     * @param nit, Codigo de compania nit
     * @return Compania
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Compania buscarCompaniaPorCodigoNIT(String nit) throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Retorna un listado de todas las companias registradas ACTIVAS, INACTIVAS
     *
     * @return List<Compania>
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Compania> buscarCompanias() throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Retorna un listado de todas las companias registradas ACTIVAS
     *
     * @return List<Compania>
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Compania> buscarCompaniasActivas() throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Registrar nuevo almacen
     *
     * @param idCompania,              Compania a la que pertenece
     * @param codigo,                  Codigo asignado
     * @param descripcion,             Descripcion almacen
     * @param referencia,              Referencia almacen
     * @param direccion,               Direccion de ubicacion
     * @param tipoAlmacen,             Tipo de almacen
     * @param tipoAlmacenDesc,         Descripcion de tipo de almacen
     * @param serie,                   Serie
     * @param consecutivo,             No. consecutivo
     * @param noAutorizacionComercial, No. autorizacion comercial
     * @return Almacen
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Almacen registrarAlmacen(Integer idCompania, int codigo, String descripcion, String referencia, Direccion direccion,
                                    byte tipoAlmacen, String tipoAlmacenDesc, char serie, long consecutivo, String noAutorizacionComercial
    ) throws ManagerAdministracionServiceBusinessException, RemoteException;


    /**
     * Registrar nuevo banco
     *
     * @param nombreComercial, Nombre comercial del banco
     * @param razonSocial,     Razon social del banco
     * @return Banco
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Banco registrarBanco(String nombreComercial, String razonSocial) throws ManagerAdministracionServiceBusinessException,
            RemoteException;

    /**
     * Modificar registro banco
     *
     * @param id,              Identificador de banco
     * @param nombreComercial, Nombre comercial
     * @param razonSocial,     razon social
     * @return Banco
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Banco modificarBanco(Integer id, String nombreComercial, String razonSocial) throws ManagerAdministracionServiceBusinessException,
            RemoteException;

    /**
     * Remover registro de banco
     *
     * @param id, Identificador de banco
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public void removerBanco(Integer id) throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * registrar tasa de cambio
     *
     * @param tipoTasaCambio,     Tipo de tasa de cambio
     * @param tasaConversion,     Tasa de conversion
     * @param fechaConversion,    Fecha de conversion
     * @param idMonedaReferencia, Moneda de referencia
     * @param idMonedaConversion, Moneda de conversion
     * @param idBanco,            Banco de la tasa de cambio
     * @param activaFacturacion,  Tasa de cambio activa para facturacion
     * @return TasaCambio
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public TasaCambio registrarTasaCambio(int tipoTasaCambio, BigDecimal tasaConversion, Date fechaConversion, Integer idMonedaReferencia,
                                          Integer idMonedaConversion, Integer idBanco, boolean activaFacturacion)
            throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Modificar tasa de cambio
     *
     * @param idTasaCambio,       Identificador de tasa de cambio
     * @param tipoTasaCambio,     Tipo de tasa de cambio
     * @param tasaConversion,     Tasa de conversion
     * @param fechaConversion,    Fecha de conversion
     * @param idMonedaReferencia, Moneda de referencia
     * @param idMonedaConversion, Moneda de conversion
     * @param idBanco,            Banco de la tasa de cambio
     * @param activaFacturacion,  Tasa de cambio activa facturacion
     * @return TasaCambio
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public TasaCambio modificarTasaCambio(Integer idTasaCambio, int tipoTasaCambio, BigDecimal tasaConversion, Date fechaConversion,
                                          Integer idMonedaReferencia, Integer idMonedaConversion, Integer idBanco,
                                          boolean activaFacturacion) throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Remover tasa de cambio
     *
     * @param idTasaCambio, Integer
     * @throws ManagerAdministracionServiceBusinessException,
     *                    Exception
     * @throws Exception, Exception
     */
    public void removerTasaCambio(Integer idTasaCambio) throws ManagerAdministracionServiceBusinessException, Exception;

    /**
     * Buscar listado de bancos
     *
     * @return List
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Banco> buscarBancos() throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Buscar listado de tasas de cambio
     *
     * @return List
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<TasaCambio> buscarTasasCambio() throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Buscar listado de tasas de cambio mes actual
     *
     * @return List
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<TasaCambio> buscarTasasCambioMesActual() throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Retorna un listado de todos los paises
     *
     * @return List<Pais>
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Pais> buscarPaises() throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Buscar Pais por su identificador
     *
     * @param id, Identificador de pais
     * @return Pais
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Pais buscarPaisPorId(Integer id) throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Retorna un listado de todas las monedas
     *
     * @return List<Moneda>
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Moneda> buscarMonedas() throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Retorna un listado de todas las monedas referencia de la compania
     *
     * @return List
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<Moneda> buscarMonedasReferencia() throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Busca una moneda por su identificador
     *
     * @param id, Identificador
     * @return Moneda
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public Moneda buscarMonedaPorId(Integer id) throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Buscar Actividades economicas
     *
     * @return List<ActividadEconomica>
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<ActividadEconomica> buscarActividadesEconomicas() throws ManagerAdministracionServiceBusinessException, RemoteException;

    /**
     * Buscar Unidades de medida
     *
     * @return List<UnidadMedida>
     * @throws ManagerAdministracionServiceBusinessException,
     *                          Exception
     * @throws RemoteException, Exception
     */
    public List<UnidadMedida> buscarUnidadesMedida() throws ManagerAdministracionServiceBusinessException, RemoteException;

}
