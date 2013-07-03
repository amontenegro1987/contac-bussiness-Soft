package contac.utils.jpa;

/**
 *  This class represent the essentials of a query. A fromClause, a conditionClause, a condition (hasToEval)
 * that establishes if this query needs to be evalued, the namedParameter and the real value of the
 * namedParameter.
 *
 * Created by IntelliJ IDEA.
 * User: sfernandez
 * Date: 14-sep-2006
 * Time: 15:17:23
 */
public class QueryFragment {

    /**
     * Document Me!
     */
    private boolean hasToEval;

    /**
     * Document Me!
     */
    private String conditionClause;

    /**
     * Document Me!
     */
    private String fromClause;

    /**
     * Document Me!
     */
    private String namedParam;

    /**
     * Document Me!
     */
    private Object realParam;

    /**
     * Document Me!
     */
    private boolean optional;
    /**
     * Constructor
     */
    public QueryFragment(boolean hasToEval,String fromClause,  String conditionClause, String namedParam, Object realParam) {
        this.hasToEval = hasToEval;
        this.conditionClause = conditionClause;
        this.fromClause = fromClause;
        this.namedParam = namedParam;
        this.realParam = realParam;
        this.optional = false;
    }

    public QueryFragment(boolean hasToEval,String fromClause,  String conditionClause, String namedParam, Object realParam, boolean optional) {
        this.hasToEval = hasToEval;
        this.conditionClause = conditionClause;
        this.fromClause = fromClause;
        this.namedParam = namedParam;
        this.realParam = realParam;
        this.optional = optional;
    }

    /**
     * Document Me!
     */
    public boolean isHasToEval() {
        return hasToEval;
    }

    /**
     * Document Me!
     */
    public void setHasToEval(boolean hasToEval) {
        this.hasToEval = hasToEval;
    }

    /**
     * Document Me!
     */
    public String getConditionClause() {
        return conditionClause;
    }

    /**
     * Document Me!
     */
    public void setConditionClause(String conditionClause) {
        this.conditionClause = conditionClause;
    }

    /**
     * Document Me!
     */
    public String getFromClause() {
        return fromClause;
    }

    /**
     * Document Me!
     */
    public void setFromClause(String fromClause) {
        this.fromClause = fromClause;
    }

    /**
     * Document Me!
     */
    public String getNamedParam() {
        return namedParam;
    }

    /**
     * Document Me!
     */
    public void setNamedParam(String namedParam) {
        this.namedParam = namedParam;
    }

    /**
     * Document Me!
     */
    public Object getRealParam() {
        return realParam;
    }

    /**
     * Document Me!
     */
    public void setRealParam(Object realParam) {
        this.realParam = realParam;
    }


    public boolean isOptional() {
        return optional;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }
}