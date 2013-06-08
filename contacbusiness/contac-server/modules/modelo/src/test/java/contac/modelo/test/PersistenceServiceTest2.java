package contac.modelo.test;

import contac.logger.ContacApacheLog4j;
import contac.modelo.eao.clasificadorEAO.ClasificadorEAO;
import contac.modelo.eao.clasificadorEAO.ClasificadorEAOPersistence;
import contac.modelo.eao.genericEAO.GenericPersistenceEAOException;
import contac.modelo.entity.Clasificador;
import contac.modelo.entity.TiposClasificador;
import org.apache.log4j.Logger;
import org.testng.annotations.Configuration;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Contac Business Project
 * User: Eddy Montenegro
 * Date: 01-19-2011
 * Time: 10:37:47 PM
 */
@Test(groups = {"debug"})
public class PersistenceServiceTest2 {

    /**
     * Logger
     */
    private static final Logger logger = Logger.getLogger(PersistenceServiceTest2.class);

    //Entities Access Objects
    private ClasificadorEAO clasificadorEAO;

    @Test(enabled = false)
    @Configuration(beforeTestClass = true)
    public void setUpClass() throws Throwable {

        //Configurar servicio de logger
        new ContacApacheLog4j();

        //Init EAO's Services
        clasificadorEAO = new ClasificadorEAOPersistence();
    }

    /**
     * Testing consulta clasificadores
     */
    @Test(enabled = false)
    public void buscarClasificadores() throws Exception{

        logger.info("Consultar listado de clasificadores por tipos");

        try {

            List<Clasificador> segmentos = clasificadorEAO.buscarPorTipoClasificador(TiposClasificador.SEGMENTO.getValue());
            List<Clasificador> familias = clasificadorEAO.buscarPorTipoClasificador(TiposClasificador.FAMILIA.getValue());
            List<Clasificador> clases = clasificadorEAO.buscarPorTipoClasificador(TiposClasificador.CLASE.getValue());
            List<Clasificador> articulos = clasificadorEAO.buscarPorTipoClasificador(TiposClasificador.ARTICULO.getValue());

            logger.info("SEGMENTOS :::");

            for (Clasificador clasificador : segmentos) {
                logger.info(clasificador.getCbs() + "-" + clasificador.getDescripcion());
            }

            logger.info("FAMILIAS :::");

            for (Clasificador clasificador : familias) {
                logger.info(clasificador.getCbs() + "-" + clasificador.getDescripcion());
            }

            logger.info("CLASES :::");

            for (Clasificador clasificador : clases) {
                logger.info(clasificador.getCbs() + "-" + clasificador.getDescripcion());
            }

            logger.info("ARTICULOS :::");

            for (Clasificador clasificador : articulos) {
                logger.info(clasificador.getCbs() + "-" + clasificador.getDescripcion());
            }

        } catch (GenericPersistenceEAOException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }
}
