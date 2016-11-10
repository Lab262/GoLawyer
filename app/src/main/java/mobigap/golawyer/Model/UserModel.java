package mobigap.golawyer.Model;

import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by luisresende on 01/11/16.
 */

public class UserModel {

    private String id;
    private String name;
    private String typeAccount;
    private String email;
    private String cpf;
    private String oab;
    private String curriculum;
    private String photo;
    private String cep;
    private String neighborhood;
    private String city;

    public static String keyID = "id_user";
    public static String keyName = "nome";
    private static String keyTypeAccount = "tipo_conta";
    private static String keyEmail = "email";
    private static String keyCpf = "cpf";
    public static String keyOab = "oab";
    public static String keyCurriculum = "curriculo";
    public static String keyPhoto = "imagem";
    private static String keyCep = "cep";
    private static String keyNeighborhood = "bairro";
    private static String keyCity = "cidade";

    public UserModel(SharedPreferences preferences){
        this.id = preferences.getString(keyID,null);
        this.name = preferences.getString(keyName,null);
        this.typeAccount = preferences.getString(keyTypeAccount,null);
        this.email = preferences.getString(keyEmail,null);
        this.cpf = preferences.getString(keyCpf,null);
        this.oab = preferences.getString(keyOab,null);
        this.curriculum = preferences.getString(keyCurriculum,null);
        this.photo = preferences.getString(keyPhoto,null);
        this.cep = preferences.getString(keyCep,null);
        this.neighborhood = preferences.getString(keyNeighborhood,null);
        this.city = preferences.getString(keyCity,null);
    }

    public UserModel(JSONObject jsonObject){
        try {
            this.id = jsonObject.getString(keyID);
            this.name = jsonObject.getString(keyName);
            this.typeAccount = jsonObject.getString(keyTypeAccount);
            this.email = jsonObject.getString(keyEmail);
            this.cpf = jsonObject.getString(keyCpf);
            this.oab = jsonObject.getString(keyOab);
            this.curriculum = jsonObject.getString(keyCurriculum);
            this.photo = jsonObject.getString(keyPhoto);
            this.cep = jsonObject.getString(keyCep);
            this.neighborhood = jsonObject.getString(keyNeighborhood);
            this.city = jsonObject.getString(keyCity);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void savePropertyUser(SharedPreferences.Editor editor){
        editor.putString(keyID,this.id);
        editor.putString(keyName,this.name);
        editor.putString(keyTypeAccount,this.typeAccount);
        editor.putString(keyEmail,this.email);
        editor.putString(keyCpf,this.cpf);
        editor.putString(keyOab,this.oab);
        editor.putString(keyCurriculum,this.curriculum);
        editor.putString(keyPhoto,this.photo);
        editor.putString(keyCep,this.cep);
        editor.putString(keyNeighborhood,this.neighborhood);
        editor.putString(keyCity,this.city);
        editor.commit();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getOab() {
        return oab;
    }

    public void setOab(String oab) {
        this.oab = oab;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
