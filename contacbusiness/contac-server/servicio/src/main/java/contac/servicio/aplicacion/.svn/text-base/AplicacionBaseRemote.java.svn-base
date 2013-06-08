package contac.servicio.aplicacion;

import contac.modelo.entity.ActividadEconomica;
import contac.modelo.entity.Moneda;
import contac.modelo.entity.Pais;
import contac.modelo.entity.UnidadMedida;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 01-17-2011
 * Time: 11:04:54 AM
 */
public interface AplicacionBaseRemote extends Remote {

    /**
     * Obtener listado de paises
     * @return List<Pais>
     * @throws RemoteException, Exception
     */
    public List<Pais> getPaises() throws RemoteException;

    /**
     * Obtener listado de monedas
     * @return List<Moneda>
     * @throws RemoteException, Exception
     */
    public List<Moneda> getMonedas() throws RemoteException;

    /**
     * Obtener listado de actividades economicas
     * @return List<ActividadEconomica>
     * @throws RemoteException, Exception
     */
    public List<ActividadEconomica> getActividadesEconomicas() throws RemoteException;

    /**
     * Obtener listado de unidades de medida
     * @return List<UnidadMedida>
     * @throws RemoteException, Exception
     */
    public List<UnidadMedida> getUnidadesMedida() throws RemoteException;
    
}
