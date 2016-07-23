package mobigap.golawyer.Model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import java.util.ArrayList;

/**
 * Created by luisresende on 22/07/16.
 */


public class User {

    private String email;
    private String password;
    private String oab;

//    @JsonProperty( "physical_person" )
//    private PhysicalPerson physical_person;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getOab() {
        return oab;
    }
    public void setOab(String oab) {
        this.oab = oab;
    }


    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (password == null ? 0 : password.hashCode());
        result = prime * result + (email == null ? 0 : email.hashCode());
        result = prime * result + (oab == null ? 0 : oab.hashCode());
        result = prime * result;
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        return true;
    }

    /**
     * This method generates a unique cache key for this request. In this case
     * our cache key depends just on the keyword.
     * @return
     */
    public String createCacheKey() {
        return "users." + password;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class List {
        private static final long serialVersionUID = 6836514467436078182L;

        private ArrayList<User> users;

        public ArrayList<User> getUsers() {
            return users;
        }

        public void setUsers(ArrayList<User> users) {
            this.users = users;
        }

        public static long getSerialversionuid() {
            return serialVersionUID;
        }
    }

}
