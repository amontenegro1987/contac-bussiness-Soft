/**
 * Copyright 2011 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.servicio.catalogo;

import contac.modelo.entity.Clasificador;
import contac.modelo.entity.Linea;

import java.rmi.RemoteException;
import java.util.List;

/**
 * Manager Catalogo Service Business .  Define todas las operaciones soportadas por este servicio
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 09-04-2010
 * Time: 11:14:38 PM
 */
public interface ManagerCatalogoServiceBusiness extends ManagerCatalogoServiceRemote {

    /**
     * Buscar lineas de producto
     * @return List<Linea>
     * @throws ManagerCatalogoServiceBusinessException, Exception
     * @throws RemoteException, Exception
     */
    public List<Linea> buscarLineas() throws ManagerCatalogoServiceBusinessException, RemoteException;

    /**
     * Buscar Clasificador por su identificador
     * @param id, Identificador del clasificador
     * @return Clasificador
     * @throws ManagerCatalogoServiceBusinessException, Exception
     * @throws RemoteException, Exception
     */
    public Clasificador buscarClasificadorPorId(Integer id) throws ManagerCatalogoServiceBusinessException,
            RemoteException;

    /**
     * Buscar clasificador por codigo CBS
     * @param cbs, codigo cbs
     * @return Clasificador
     * @throws ManagerCatalogoServiceBusinessException, Exception
     * @throws RemoteException, Exception
     */
    public Clasificador buscarClasificadorPorCodigoCBS(long cbs) throws ManagerCatalogoServiceBusinessException,
            RemoteException;

    /**
     * Buscar clasificadores
     * @return List<Clasificador>
     * @throws ManagerCatalogoServiceBusinessException, Exception
     * @throws RemoteException, Exception
     */
    public List<Clasificador> buscarClasificadores() throws ManagerCatalogoServiceBusinessException, RemoteException;

    /**
     * Buscar clasificadores por tipo
     * @param tipoClasificador, tipo de clasificador
     * @return List<Clasificador>
     * @throws ManagerCatalogoServiceBusinessException, Exception
     */
    public List<Clasificador> buscarClasificadoresPorTipo(int tipoClasificador) throws ManagerCatalogoServiceBusinessException,
            RemoteException;

    /**
     * Buscar clasificadores por parametros
     * @param cbs, Codigo de bienes y servicios
     * @param descripcion, Descripcion  del codigo de bien y servicio
     * @return List<Clasificador>
     * @throws ManagerCatalogoServiceBusinessException, Exception
     * @throws RemoteException, Exception
     */
    public List<Clasificador> buscarClasificadoresCBS(long cbs, String descripcion) throws ManagerCatalogoServiceBusinessException,
            RemoteException;
}
