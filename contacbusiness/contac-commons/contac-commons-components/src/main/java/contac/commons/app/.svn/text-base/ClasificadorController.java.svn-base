package contac.commons.app;

import contac.modelo.entity.Clasificador;
import contac.modelo.entity.TiposClasificador;
import contac.servicio.catalogo.ManagerCatalogoServiceBusiness;
import contac.servicio.catalogo.ManagerCatalogoServiceBusinessException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Eddy Montenegro
 */
public class ClasificadorController extends BaseController {
    
    //Apache log4j
    private static final Logger logger = Logger.getLogger(ClasificadorController.class);

    //Parametros
    private long cbs = 0;
    private String descripcion;

    private List<Clasificador> clasificadores = new ArrayList<Clasificador>();
    private List<Clasificador> segmentos = new ArrayList<Clasificador>();
    private List<Clasificador> familias = new ArrayList<Clasificador>();
    private List<Clasificador> clases = new ArrayList<Clasificador>();
    private List<Clasificador> articulos = new ArrayList<Clasificador>();

    public long getCbs() {
        return cbs;
    }

    public void setCbs(long cbs) {
        this.cbs = cbs;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Buscar Segmentos del clasificador
     * @return List<Clasificador>
     */
    public List<Clasificador> getSegmentos() {
        return segmentos;
    }

    /**
     * Busca familias del clasificador
     * @return List<Clasificador>
     */
    public List<Clasificador> getFamilias() {
        return familias;
    }

    /**
     * Busca clases del clasificador
     * @return List<Clasificador>
     */
    public List<Clasificador> getClases() {
        return clases;
    }

    /**
     * Busca articulos del clasificador
     * @return List<Clasificador>
     */
    public List<Clasificador> getArticulos() {
        return articulos;
    }

    public List<Clasificador> getClasificadores() {
        return clasificadores;
    }

    /**
     * Buscar clasificadores CBS
     * @throws Exception, Exception
     */
    public void buscarClasificadoresCBS() throws Exception {
        
        logger.debug("Buscar clasificadores CBS");

        try {

            //Obtener manager catalogo
            ManagerCatalogoServiceBusiness mgrCatalogo = getMgrCatalogoService();

            //Buscamos clasificadores
            List<Clasificador> clasificadores = mgrCatalogo.buscarClasificadores();
            
            //separar clasificadores list
            separarClasificadoresList(clasificadores);
            
        } catch (ManagerCatalogoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Buscar clasificadores CBS
     */
    public void buscarClasificadoresCBSPorParametros() throws Exception {
        
        logger.debug("Buscar clasificadores CBS con parametros");

        try {

            //Obtener manager de catalogo
            ManagerCatalogoServiceBusiness mgrCatalogo = getMgrCatalogoService();

            //Buscamos clasificadores
            List<Clasificador> clasificadores = mgrCatalogo.buscarClasificadoresCBS(getCbs(), getDescripcion());

            //Separar clasificadores list
            this.clasificadores.clear();
            this.clasificadores.addAll(clasificadores);
            
        } catch (ManagerCatalogoServiceBusinessException e) {
            logger.error(e.getMessage(), e);
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * Separar clasificadores
     * @param clasificadores, List<Clasificador>
     */
    private void separarClasificadoresList(List<Clasificador> clasificadores) {

        //Limpiar listados de clasificadores
        articulos.clear();
        clases.clear();
        familias.clear();
        segmentos.clear();

        //Separar clasificadores por tipo
        for (Clasificador clasificador : clasificadores) {

            if (clasificador.getTipoClasificador() == (byte)TiposClasificador.ARTICULO.getValue())
                articulos.add(clasificador);

            if (clasificador.getTipoClasificador() == (byte)TiposClasificador.CLASE.getValue())
                clases.add(clasificador);

            if (clasificador.getTipoClasificador() == (byte)TiposClasificador.FAMILIA.getValue())
                familias.add(clasificador);

            if (clasificador.getTipoClasificador() == (byte)TiposClasificador.SEGMENTO.getValue())
                segmentos.add(clasificador);
        }
    }
}
