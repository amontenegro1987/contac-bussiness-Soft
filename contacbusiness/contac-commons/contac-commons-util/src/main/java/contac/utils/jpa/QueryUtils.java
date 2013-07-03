package contac.utils.jpa;

import org.apache.log4j.Logger;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: sfernandez
 * Date: 14-sep-2006
 * Time: 15:19:35
 */

public class QueryUtils  {

    // Crea la query.

    private static final Logger logger = Logger.getLogger(QueryUtils.class);

    public static String ejbQLcreator (String initialFromClause, String initialCondClause, List<QueryFragment> queryFrags) {


        String fromClause = initialFromClause;
        String conditionalClause = initialCondClause;


        boolean first = true;
        for (QueryFragment qf: QueryUtils.querySimplifier(queryFrags))
            if (qf.isHasToEval()){
                fromClause = fromClause + qf.getFromClause();
                String operator = (!qf.isOptional())?" and ":" or ";
                conditionalClause = conditionalClause + (first ? "where " : operator) + qf.getConditionClause();
                first = false;
            }

        String ejbQuery = fromClause+conditionalClause;

        logger.debug ("El query es : "+ejbQuery);

        return ejbQuery;
    }

    public static Query ejbQLParametersSolver (Query query, List<QueryFragment> queryFrags){


        for (QueryFragment qf: queryFrags)
            if ((qf.isHasToEval()) && (qf.getNamedParam() != null))
                query.setParameter (qf.getNamedParam(), qf.getRealParam());

        return query;
    }

    /**
     * Document Me!
     */
    public static  List<QueryFragment> querySimplifier (List<QueryFragment> queryToSimplify){
        List<String> existentes = new ArrayList<String> ();
        for (QueryFragment qf: queryToSimplify) {
           if (!qf.isHasToEval())
              continue;
           if (existentes.contains(qf.getFromClause()))
                qf.setFromClause("");
            else
               existentes.add (qf.getFromClause());
        }
        return queryToSimplify;

    }

}
