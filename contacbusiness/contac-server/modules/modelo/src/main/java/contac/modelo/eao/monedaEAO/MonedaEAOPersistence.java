package contac.modelo.eao.monedaEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Moneda;

import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: hmurbina
 * Date: Oct 12, 2010
 * Time: 10:33:13 PM
 */
public class MonedaEAOPersistence extends GenericPersistenceEAO<Moneda, Integer> implements MonedaEAO {


    @Override
    public List<Moneda> findByNombre(String nombre) throws GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        //Asignamos los parametros.
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("nombre", "%" + nombre + "%");

        //Obtenemos los resultados
        List<Moneda> moneda = namedQuery("Moneda.findByNombre", parametros);

        //Evaluate type of result
        if ((moneda == null) || (moneda.size() < 1))
            throw new PersistenceClassNotFoundException("nombre: " + nombre);

        return moneda;

    }

}
