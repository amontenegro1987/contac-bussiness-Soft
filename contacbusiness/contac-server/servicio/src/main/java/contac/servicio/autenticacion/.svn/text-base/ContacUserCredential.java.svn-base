package contac.servicio.autenticacion;

/**
 * Created by IntelliJ IDEA.
 * User: Eddy Montenegro
 * Date: 08-10-2010
 * Time: 11:05:04 PM
 */
public class ContacUserCredential {

    private String username;
    private String password;

    public ContacUserCredential(String username, String password){

        if (username == null || password == null)
            throw new NullPointerException("Nombre de usuario o password no pueden ser nulos.");

        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int hashCode() {
        return getUsername().hashCode() * 13 + getPassword().hashCode() * 13;
    }

    public boolean equals(Object obj){
        if (this == obj)
            return true;

        if (!(obj instanceof ContacUserCredential)){
            return false;
        }else{
            ContacUserCredential other = (ContacUserCredential)obj;
            return getUsername().equals(other.getUsername()) && getPassword().equals(other.getPassword());
        }
    }

    public String toString(){
        StringBuffer buff = new StringBuffer();
        buff.append("(");
        buff.append("ContacUserCredential: name=");
        buff.append(getUsername());
        buff.append(")");

        return buff.toString();
    }
}
