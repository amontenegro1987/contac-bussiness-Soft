package contac.modelo.eao.bancoEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Banco;

import java.util.HashMap;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2011.
 * User: hmurbina
 * Date: Oct 12, 2010
 * Time: 10:32:56 PM
 */
public class BancoEAOPersistence extends GenericPersistenceEAO<Banco, Integer> implements BancoEAO {

    @Override
    public List<Banco> findByNombreComercial(String nombreComercial) throws GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        //Asignamos los parametros.
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("nombreComercial", "%" + nombreComercial + "%");

        //Obtenemos los resultados
        List<Banco> bancos = namedQuery("Banco.findByNombreComercial", parametros);

        //Evaluate type of result
        if ((bancos == null) || (bancos.size() < 1))
            throw new PersistenceClassNotFoundException("nombreComercial: " + nombreComercial);

        return bancos;

    }


    @Override
    public List<Banco> findByRazonSocial(String razonSocial) throws GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        //Asignamos los parametros.
        HashMap<String, String> parametros = new HashMap<String, String>();
        parametros.put("razonSocial", "%" + razonSocial + "%");

        //Obtenemos los resultados
        List<Banco> bancos = namedQuery("Banco.findByRazonSocial", parametros);

        //Evaluate type of result
        if ((bancos == null) || (bancos.size() < 1))
            throw new PersistenceClassNotFoundException("razonSocial: " + razonSocial);

        return bancos;

    }
}
