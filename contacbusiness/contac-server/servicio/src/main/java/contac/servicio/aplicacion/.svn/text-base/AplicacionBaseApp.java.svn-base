package contac.servicio.aplicacion;

import contac.modelo.entity.ActividadEconomica;
import contac.modelo.entity.Moneda;
import contac.modelo.entity.Pais;
import contac.modelo.entity.UnidadMedida;
import contac.servicio.administracion.ManagerAdministracionServiceBusiness;
import contac.servicio.administracion.ManagerAdministracionServiceBusinessException;
import contac.servicio.administracion.ManagerAdministracionServiceBusinessImpl;
import contac.servicio.autenticacion.ContacAutenticacionService;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusiness;
import contac.servicio.seguridad.ManagerAutorizacionServiceBusinessImpl;
import org.apache.log4j.Logger;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Applicacion base extendida remotamente
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 12-10-2010
 * Time: 10:32:16 AM
 */
public class AplicacionBaseApp {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(AplicacionBaseApp.class);

    /**
     * AplicacionBaseApp instance
     */
    private static AplicacionBaseApp instance;

    //Map relacionando un pais con su identificador
    protected Map<Integer, Pais> paisesMap;
    protected List<Pais> paises;

    //Map relacionando una moneda con su identificador
    protected Map<Integer, Moneda> monedasMap;
    protected List<Moneda> monedas;

    //Map relacionando una actividad economica con su identificador
    protected Map<Integer, ActividadEconomica> actividadesEconMap;
    protected List<ActividadEconomica> actividadEconomicas;

    //Map relacionando una unidad de medida con su identificador
    protected Map<Integer, UnidadMedida> unidadesMedidaMap;
    protected List<UnidadMedida> unidadesMedida;

    /**
     * Default Constructor
     */
    public AplicacionBaseApp() {

        logger.info("Inicializando AplicacionBaseApp...");

        try {

            //Manager Administracion service
            ManagerAutorizacionServiceBusiness mgrAutorizacion = new ManagerAutorizacionServiceBusinessImpl(ContacAutenticacionService.getSubjectAutenticacionService());
            ManagerAdministracionServiceBusiness mgrAdministracion = new ManagerAdministracionServiceBusinessImpl(mgrAutorizacion);

            //--------------------------------------------------------------------
            //------------------------------------------------------------<PAISES>
            //--------------------------------------------------------------------
            try {

                //Buscar listado de paises
                List<Pais> paises = mgrAdministracion.buscarPaises();

                this.paisesMap = new HashMap<Integer, Pais>();
                this.paises = paises;

                for (Pais pais : paises) {
                    paisesMap.put(pais.getId(), pais);
                }

            } catch (ManagerAdministracionServiceBusinessException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("No se puede inicializar ApplicacionBaseApp: " + e.getMessage(), e);
            }

            //--------------------------------------------------------------------
            //-----------------------------------------------------------<MONEDAS>
            //--------------------------------------------------------------------
            try {

                //Buscar listado de monedas
                List<Moneda> monedas = mgrAdministracion.buscarMonedas();

                this.monedasMap = new HashMap<Integer, Moneda>();
                this.monedas = monedas;

                for (Moneda moneda : monedas) {
                    monedasMap.put(moneda.getId(), moneda);
                }

            } catch (ManagerAdministracionServiceBusinessException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("No se puede inicializar ApplicacionBaseApp: " + e.getMessage(), e);
            }

            //----------------------------------------------------------------------
            //-----------------------------------------------<ACTIVIDADES ECONOMICAS>
            //----------------------------------------------------------------------
            try {

                //Buscar listado de actividades economicas
                List<ActividadEconomica> actividadEconomicas = mgrAdministracion.buscarActividadesEconomicas();

                this.actividadesEconMap = new HashMap<Integer, ActividadEconomica>();
                this.actividadEconomicas = actividadEconomicas;

                for (ActividadEconomica actividadEcon : actividadEconomicas) {
                    actividadesEconMap.put(actividadEcon.getId(), actividadEcon);
                }

            } catch (ManagerAdministracionServiceBusinessException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("No se puede inicializar ApplicacionBaseApp: " + e.getMessage(), e);
            }

            //----------------------------------------------------------------------
            //-----------------------------------------------------<UNIDADES MEDIDA>
            //----------------------------------------------------------------------
            try {

                //Buscar listado de unidades de medida
                List<UnidadMedida> unidadesMedida = mgrAdministracion.buscarUnidadesMedida();

                this.unidadesMedidaMap = new HashMap<Integer, UnidadMedida>();
                this.unidadesMedida = unidadesMedida;

                for (UnidadMedida unidadMedida : unidadesMedida) {
                    unidadesMedidaMap.put(unidadMedida.getId(), unidadMedida);
                }

            } catch (ManagerAdministracionServiceBusinessException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException("No se puede inicializar ApplicacionBaseApp: " + e.getMessage(), e);
            }

        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    //---------------------------------------------------<Paises>

    /**
     * Retorna listado de paises
     * @return List<Pais>
     */
    public List<Pais> getPaises() {
        return paises;
    }

    /**
     * Retorna un pais por su identificador
     * @param id, Integer
     * @return Pais
     */
    public Pais getPais(Integer id) {
        return paisesMap.get(id);
    }

    /**
     * Retorna map de paises
     * @return Map
     */
    public Map<Integer, Pais> getPaisesMap() {
        return paisesMap;
    }

    //---------------------------------------------------<Monedas>

    /**
     * Retorna map de monedas
     * @return Map<Integer, Moneda>
     */
    public Map<Integer, Moneda> getMonedasMap() {
        return monedasMap;
    }

    /**
     * Retorna listado de monedas
     * @return List<Moneda>
     */
    public List<Moneda> getMonedas() {
        return monedas;
    }

    /**
     * Retorna moneda por su identificador
     * @param id, Integer
     * @return Moneda
     */
    public Moneda getMoneda(Integer id) {
        return monedasMap.get(id);
    }

    //--------------------------------------------------<Actividades Economicas>

    /**
     * Retorna map de actividades economicas
     * @return Map<Integer, ActividadEconomica>
     */
    public Map<Integer, ActividadEconomica> getActividadEconomicaMap() {
        return actividadesEconMap;
    }

    /**
     * Retorna listado de actividades economicas
     * @return List<ActividadEconomica>
     */
    public List<ActividadEconomica> getActividadEconomicas() {
        return actividadEconomicas;
    }

    /**
     * Retorna actividad economica por su identificador
     * @param id, Integer
     * @return ActividadEconomica
     */
    public ActividadEconomica getActividadEconomica(Integer id) {
        return actividadesEconMap.get(id);
    }

    //------------------------------------------------------<Unidades Medida>

    /**
     * Retorna map de unidades medida
     * @return Map<Integer, UnidadMedida>
     */
    public Map<Integer, UnidadMedida> getUnidadesEconomicaMap() {
        return unidadesMedidaMap;
    }

    /**
     * Retorna listado de unidades medida
     * @return List<UnidadMedida>
     */
    public List<UnidadMedida> getUnidadesMedida() {
        return unidadesMedida;
    }

    /**
     * Retornar unidad de medida por su identificador
     * @param id, Integer
     * @return UnidadMedida
     */
    public UnidadMedida getUnidadMedida(Integer id) {
        return unidadesMedidaMap.get(id);
    }

    //--------------------------------------------------<Instance>

    /**
     * Return aplicacion base
     * @return AplicacionBaseApp
     */
    public static AplicacionBaseApp getInstance() {

        if (instance == null) {
            instance = new AplicacionBaseApp();            
        }

        return instance;
    }
}
