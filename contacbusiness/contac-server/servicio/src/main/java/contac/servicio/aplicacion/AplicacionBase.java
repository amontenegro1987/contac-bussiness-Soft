package contac.servicio.aplicacion;

import contac.modelo.entity.ActividadEconomica;
import contac.modelo.entity.Moneda;
import contac.modelo.entity.Pais;
import contac.modelo.entity.UnidadMedida;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 01-17-2011
 * Time: 11:05:21 AM
 */
public class AplicacionBase extends UnicastRemoteObject implements AplicacionBaseRemote{

    /**
     * Default Constructor
     * @throws RemoteException, Exception
     */
    public AplicacionBase() throws RemoteException {}

    //*********************************************************************************
    //APPLICATIONS UTILITY METHODS
    //*********************************************************************************
    @Override
    public List<Pais> getPaises() throws RemoteException {
        return AplicacionBaseApp.getInstance().getPaises();
    }

    @Override
    public List<Moneda> getMonedas() throws RemoteException {
        return AplicacionBaseApp.getInstance().getMonedas();
    }

    @Override
    public List<ActividadEconomica> getActividadesEconomicas() throws RemoteException {
        return AplicacionBaseApp.getInstance().getActividadEconomicas();
    }

    @Override
    public List<UnidadMedida> getUnidadesMedida() throws RemoteException {
        return AplicacionBaseApp.getInstance().getUnidadesMedida();
    }
}
