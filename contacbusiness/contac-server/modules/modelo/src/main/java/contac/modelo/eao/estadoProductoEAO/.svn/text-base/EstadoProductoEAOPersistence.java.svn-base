package contac.modelo.eao.estadoProductoEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.EstadoProducto;
import contac.modelo.entity.Producto;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Contac Business Software Corporation. All rights reserved
 * User: Eddy Montenegro
 * Date: 17/09/11
 * Time: 15:40
 */
public class EstadoProductoEAOPersistence extends GenericPersistenceEAO<EstadoProducto, Integer> implements EstadoProductoEAO {

    //Apache log4j
    private static final Logger logger = Logger.getLogger(EstadoProductoEAOPersistence.class);

    @Override
    public EstadoProducto findByNombre(String nombre) throws PersistenceClassNotFoundException, GenericPersistenceEAOException {

        logger.debug("Buscando estado producto con parametros: [nombre]: " + nombre);

        //Construct query
         String query = "select e from EstadoProducto e where e.nombre = ?1";

          //Creando listado de parametros
        List parametros = new ArrayList();
        parametros.add(nombre);

        List<EstadoProducto> estados = (List<EstadoProducto>)findByCriteria(query, parametros);

        if (estados.size() <= 0)
            throw new PersistenceClassNotFoundException(nombre);

        if (estados.size() > 1)
            throw new PersistenceClassNotFoundException("estados encontrados: " + estados.size());

        return estados.get(0);
    }
}
