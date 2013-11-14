package contac.servicio.autenticacion.login;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Servicio de configuracion del modulo de autenticacion 
 *
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 11:07:39 PM
 */
public class ContacLoginConfig extends Configuration {

    private static ContacLoginConfig entity;

    public static void init(){
        entity = new ContacLoginConfig();
        Configuration.setConfiguration(entity);
    }

    public static ContacLoginConfig getEntity(){
        return entity;
    }
    
    @Override
    public AppConfigurationEntry[] getAppConfigurationEntry(String name) {

        List entries = new ArrayList();

        AppConfigurationEntry appEntry = new AppConfigurationEntry(ContacLogin.class.getName(),
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, Collections.EMPTY_MAP);

        entries.add(appEntry);

         return (AppConfigurationEntry[]) entries.toArray(new AppConfigurationEntry[entries.size()]);
    }
}
