/**
 * Copyright 2012 Contac Business Software. All rights reserved.
 * CONTAC PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package contac.modelo.eao.tasaCambioEAO;

import contac.modelo.eao.genericEAO.GenericPersistenceEAO;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.eao.genericEAO.PersistenceClassNotFoundException;
import contac.modelo.entity.TasaCambio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Contac Business Software. All rights reserved 2012
 * User: hmurbina
 * Date: Oct 12, 2010
 * Time: 10:33:27 PM
 */
public class TasaCambioEAOPersistence extends GenericPersistenceEAO<TasaCambio, Integer> implements TasaCambioEAO {

    @Override
    public TasaCambio findByActivaFacturacion() throws GenericPersistenceEAOException {
        
        //Iniciar servicio
        initService();
        
        String query = "Select t from TasaCambio t where t.activaFacturacion = true";

        //Obtener tasas de cambio
        List<TasaCambio> tasasCambio = em.createQuery(query).getResultList();

        if (tasasCambio == null || tasasCambio.isEmpty())
            throw new GenericPersistenceEAOException("Tasa de cambio no encontrada.");

        return tasasCambio.get(0);
    }

    @Override
    public List getTasaCambioByBancoAndFecha(String banco, String fecha) throws GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        //Asignamos los parametros.
        HashMap<String, Object> parametros = new HashMap<String, Object>();

        try {
            parametros.put("banco", "%" + banco + "%");
            parametros.put("fechaConversion", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fecha));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //Obtenemos los resultados
        List tasaCambio = namedQuery("TasaCambio.findByFechaBanco", parametros);

        //Evaluate type of result
        if ((tasaCambio == null) || (tasaCambio.size() < 1))
            throw new PersistenceClassNotFoundException("Banco: " + banco + " Fecha: " + fecha);

        return tasaCambio;

    }

    @Override
    public List<TasaCambio> findByFechas(Date fechaInicio, Date fechaFinal) throws GenericPersistenceEAOException {

        //Iniciar servicio
        initService();

        //Creating query
        String query = "Select tc from TasaCambio tc where tc.fechaConversion >= :fechaInicio and tc.fechaConversion <= :fechaFinal";

        //Return result list
        return em.createQuery(query).setParameter("fechaInicio", fechaInicio).setParameter("fechaFinal", fechaFinal).getResultList();
    }

    @Override
    public List<TasaCambio> findByFacturacion() throws GenericPersistenceEAOException {
        
        //Iniciar servicio
        initService();
        
        //Creating query
        String query = "Select tc from TasaCambio tc where tc.activaFacturacion = true order by tc.fechaConversion desc";
        
        //Return result list
        return em.createQuery(query).getResultList();
    }

}
