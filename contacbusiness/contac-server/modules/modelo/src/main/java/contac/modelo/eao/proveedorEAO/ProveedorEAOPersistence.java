package contac.modelo.eao.proveedorEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.Compania;
import contac.modelo.entity.Producto;
import contac.modelo.entity.Proveedor;
import contac.utils.jpa.QueryFragment;
import contac.utils.jpa.QueryUtils;
import org.apache.log4j.Logger;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Proveedor DAO implementacion base Java SE Adapter
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-26-2010
 * Time: 10:10:56 PM
 */
public class ProveedorEAOPersistence extends GenericPersistenceEAO<Proveedor, Integer> implements ProveedorEAO {

    //Apache logger
    private static final Logger logger = Logger.getLogger(ProveedorEAOPersistence.class);

    @Override
    public List<Proveedor> findAllOrderByCodigo() throws GenericPersistenceEAOException {

        //Init service
        initService();

        List<Proveedor> proveedores = em.createQuery("select p from Proveedor p order by p.codigo").getResultList();

        return proveedores;
    }

    @Override
    public List<Proveedor> find(long codigo, String razonSocial) throws PersistenceClassNotFoundException, GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        String fromClause = "Select p from Proveedor p ";
        String conditionClause = "";

        List<QueryFragment> querySolver = new ArrayList<QueryFragment>();

        if (codigo > 0) {
            //Agregando parametro de codigo
            querySolver.add(new QueryFragment((codigo > 0), "", " p.codigo = :codigo ",
                    "codigo", codigo));
        } else {
            //Agregando parametro razon social
            querySolver.add(new QueryFragment(((razonSocial != null) && (!razonSocial.equals(""))), "", " p.razonSocial like :razonSocial ",
                    "razonSocial", "%".concat(razonSocial).concat("%")));
            //Agregando parametro nombre comercial
            querySolver.add(new QueryFragment(((razonSocial != null) && (!razonSocial.equals(""))), "", " p.nombreComercial like :razonSocial ",
                    "razonSocial", "%".concat(razonSocial).concat("%")));
        }

        String ejbQuery = QueryUtils.ejbQLcreator(fromClause, conditionClause, querySolver);
        Query query = em.createQuery(ejbQuery);

        List<Proveedor> listProveedores = QueryUtils.ejbQLParametersSolver(query, querySolver).getResultList();

        return listProveedores;
    }

    @Override
    public Proveedor findByCodigo(long codigo) throws PersistenceClassNotFoundException, GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        //Create query
        List<Proveedor> proveedores = em.createQuery("Select p From Proveedor p Where p.codigo = :codigo").
                setParameter("codigo", codigo).getResultList();

        if (proveedores == null || proveedores.size() < 1)
            throw new PersistenceClassNotFoundException("Se encontraron " + proveedores.size() + " proveedores con codigo: " + codigo);

        return proveedores.get(0);
    }
}