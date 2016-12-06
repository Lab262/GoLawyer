package mobigap.golawyer.Requests;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mobigap.golawyer.Enums.TypeDemand;
import mobigap.golawyer.Model.ProfileInformationEditModel;
import mobigap.golawyer.Model.ProfileInformationModel;
import mobigap.golawyer.Model.UserDataModel;

/**
 * Created by luisresende on 01/11/16.
 */

public class UserRequest {

    private static String urlLogin = "GetLogin";
    private static String urlForgotPassword = "SetRecuperarSenha";
    private static String urlRegisterUser = "SetCadastro";
    private static String urlGetProfileData = "GetDadosPerfil";
    private static String urlSetProfileData = "SetDadosPerfil";
    private static String urlGetOrders = "GetPedidos";
    private static String urlSetOrder = "SetPedido";

    private static String keyLogin = "login";
    private static String keyPassword = "senha";
    private static String keyEmail = "email";
    private static String keyTypeAccount = "tipo_conta";
    private static String keyPhoto = "foto";
    private static String keyName = "nome";
    private static String keyOAB = "oab";
    private static String keyDoc = "doc";
    private static String keyPhoneNumber = "telefone";
    private static String keyCurriculum = "curriculo";
    private static String keyZipCode = "cep";
    private static String keyAddress = "endereco";
    private static String keyNeighborhood = "bairro";
    private static String keyCity = "cidade";
    private static String keyState = "estado";
    private static String keyIdUser = "id_user";
    private static String keyDemand = "demanda";
    private static String keyValueProposal = "valor_proposta";
    private static String keyLocal = "cartorio";
    private static String keyObservation = "observacoes";
    private static String keyIdLawyer = "id_advogado";

    public static void login(String email, String password, JsonHttpResponseHandler jsonHttpResponseHandler){
        Map<String,String> params = new HashMap<>();
        params.put(keyLogin,email);
        params.put(keyPassword,password);
        Requester.postRequest(urlLogin,Requester.getRequestParams(params), jsonHttpResponseHandler);
    }

    public static void forgotPassword(String email, JsonHttpResponseHandler jsonHttpResponseHandler){
        Map<String,String> params = new HashMap<>();
        params.put(keyEmail,email);
        Requester.postRequest(urlForgotPassword,Requester.getRequestParams(params), jsonHttpResponseHandler);
    }

    public static void registerUser(String typeAccount,String photo, String name, String oab , String doc,
                                    String phoneNumber, String email, String curriculum, String zipCode, String address,
                                    String neighborhood, String city, String state, String password,
                                    JsonHttpResponseHandler jsonHttpResponseHandler){
        Map<String,String> params = new HashMap<>();
        params.put(keyTypeAccount,typeAccount);
        params.put(keyPhoto,photo);
        params.put(keyName,name);
        params.put(keyOAB,oab);
        params.put(keyDoc,doc);
        params.put(keyPhoneNumber,phoneNumber);
        params.put(keyEmail,email);
        params.put(keyCurriculum,curriculum);
        params.put(keyZipCode,zipCode);
        params.put(keyAddress,address);
        params.put(keyNeighborhood,neighborhood);
        params.put(keyCity,city);
        params.put(keyState,state);
        params.put(keyPassword,password);
        Requester.postRequest(urlRegisterUser,Requester.getRequestParams(params), jsonHttpResponseHandler);
    }

    public static void getProfileData(String idUser, JsonHttpResponseHandler jsonHttpResponseHandler){
        Map<String,String> params = new HashMap<>();
        params.put(keyIdUser,idUser);
        Requester.postRequest(urlGetProfileData,Requester.getRequestParams(params), jsonHttpResponseHandler);
    }

    public static void getOrders(String idUser, JsonHttpResponseHandler jsonHttpResponseHandler){
        Map<String,String> params = new HashMap<>();
        params.put(keyIdUser,idUser);
        Requester.postRequest(urlGetOrders,Requester.getRequestParams(params), jsonHttpResponseHandler);
    }

    public static void getImage(String urlImage, AsyncHttpResponseHandler asyncHttpResponseHandler){
        Requester.getRequest(urlImage,asyncHttpResponseHandler);
    }

    public static void setOrder(TypeDemand typeDemand, String valueProposal, String local, String observation,
                                String idUser, String idLawyer, JsonHttpResponseHandler jsonHttpResponseHandler){
        Map<String,String> params = new HashMap<>();
        params.put(keyDemand,TypeDemand.getStringTypeDemand(typeDemand.ordinal()));
        params.put(keyValueProposal,valueProposal);
        params.put(keyLocal,local);
        params.put(keyObservation,observation);
        params.put(keyIdLawyer,idLawyer);
        params.put(keyIdUser,idUser);
        Requester.postRequest(urlSetOrder,Requester.getRequestParams(params), jsonHttpResponseHandler);
    }

    public static void updateProfileData(String idUser,ArrayList<ProfileInformationEditModel> profileInformationEditModels,
                                    JsonHttpResponseHandler jsonHttpResponseHandler){
        Map<String,String> params = new HashMap<>();
        params.put(keyIdUser,idUser);

        for (ProfileInformationEditModel profileInformationEditModel: profileInformationEditModels){
            params.put(profileInformationEditModel.nameFieldWS,profileInformationEditModel.field);
        }

        Requester.postRequest(urlSetProfileData,Requester.getRequestParams(params), jsonHttpResponseHandler);
    }

}
