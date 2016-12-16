package mobigap.golawyer.Model;

import android.support.v4.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import mobigap.golawyer.Enums.TypeProfile;
import mobigap.golawyer.Requests.Requester;

/**
 * Created by luisresende on 06/12/16.
 */

public class DemandModel {

    //Variables for all steps
    private int step;
    private TypeProfile typeProfile;
    private JSONObject objectsItens;
    private String idUser;
    private String idLawyer;
    private String idOrder;
    private Boolean isCounterProposal;

    //Variables for step 1
    private HashMap<Integer,Pair<String,String>> informationDemand = new HashMap<>();

    //Variables for step 2
    private String feedbackText;

    //Variables for step 3
    private ArrayList<ServiceStatusChatModel> messagesChat = new ArrayList<>();

    //Variables for step 4
    private String titleCompletedDemand;
    private String dateCompletedDemand;
    private String messageCompletedDemand;
    private Boolean isChargeDemand;

    //Variables for step 5
    private String textEvaluation;
    private Boolean isEvaluate;

    //Variables for step 6
    private String textFeedback;


    private static String keyStep = "passo";
    private static String keyTypeUser = "tipo";
    private static String keyItens = "itens";
    private static String keyIdUser = "id_user";
    private static String keyIdLawyer = "id_advogado";
    private static String keyIdOrder = "id_pedido";
    private static String keyCounterProposal = "flag_contraproposta";
    private static String keyInformationDemand = "tipos_servico";
    private static String keyValue = "valor";
    private static String keyTitle = "titulo";
    private static String keyText = "texto";
    private static String keyMessages = "mensagens_chat";
    private static String keyDate = "data_inicio";
    private static String keyChargeDemand = "flag_cobranca_demanda";
    private static String keyIsEvaluate = "flag_avaliacao";

    public DemandModel(JSONObject jsonObject) {
        try {
            this.step = Integer.parseInt(jsonObject.getString(keyStep));
            this.typeProfile = TypeProfile.getTypeProfileByString(jsonObject.getString(keyTypeUser));
            this.objectsItens = jsonObject.getJSONArray(keyItens).getJSONObject(0);
            if (step!=6) {

                this.idLawyer = this.objectsItens.getString(keyIdLawyer);
                this.idUser = this.objectsItens.getString(keyIdUser);
                this.idOrder = this.objectsItens.getString(keyIdOrder);

                switch (step) {
                    case 1:
                        parseStepOne();
                        break;
                    case 2:
                        parseStepTwo();
                        break;
                    case 3:
                        parseStepThree();
                        break;
                    case 4:
                        parseStepFour();
                        break;
                    case 5:
                        parseStepFive();
                        break;
                }
            }else {
                parseStepSix();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Boolean parseStringBoolean(String response){
        if (response.equals(Requester.responseSuccess)){
            return true;
        }else {
            return false;
        }
    }

    private void parseStepOne(){
        try {
            JSONArray jsonArray = this.objectsItens.getJSONArray(keyInformationDemand);
            this.isCounterProposal = parseStringBoolean(this.objectsItens.getString(keyCounterProposal));

            for (int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Pair<String,String> pair = new Pair<>(jsonObject.getString(keyTitle),jsonObject.getString(keyValue));
                informationDemand.put(i,pair);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseStepTwo(){
        if(typeProfile==TypeProfile.LAWYER) {
            try {
                this.feedbackText = this.objectsItens.getString(keyText);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseStepThree(){
        try {
            JSONArray jsonArray = this.objectsItens.getJSONArray(keyMessages);
            for (int i=0; i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ServiceStatusChatModel serviceStatusChatModel = new ServiceStatusChatModel(jsonObject);
                messagesChat.add(serviceStatusChatModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseStepFour(){
        try {
            this.isChargeDemand = parseStringBoolean(this.objectsItens.getString(keyChargeDemand));
            this.titleCompletedDemand = this.objectsItens.getString(keyTitle);
            this.dateCompletedDemand = this.objectsItens.getString(keyDate);
            this.messageCompletedDemand = this.objectsItens.getString(keyText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseStepFive(){
        try {
            this.isEvaluate = parseStringBoolean(this.objectsItens.getString(keyIsEvaluate));
            this.textEvaluation = this.objectsItens.getString(keyText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseStepSix(){
        try {
            this.textFeedback = this.objectsItens.getString(keyText);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public HashMap<Integer, Pair<String, String>> getInformationDemand() {
        return informationDemand;
    }

    public void setInformationDemand(HashMap<Integer, Pair<String, String>> informationDemand) {
        this.informationDemand = informationDemand;
    }

    public Boolean getCounterProposal() {
        return isCounterProposal;
    }

    public void setCounterProposal(Boolean counterProposal) {
        isCounterProposal = counterProposal;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getIdLawyer() {
        return idLawyer;
    }

    public void setIdLawyer(String idLawyer) {
        this.idLawyer = idLawyer;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public JSONObject getObjectsItens() {
        return objectsItens;
    }

    public void setObjectsItens(JSONObject objectsItens) {
        this.objectsItens = objectsItens;
    }

    public TypeProfile getTypeProfile() {
        return typeProfile;
    }

    public void setTypeProfile(TypeProfile typeProfile) {
        this.typeProfile = typeProfile;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public ArrayList<ServiceStatusChatModel> getMessagesChat() {
        return messagesChat;
    }

    public String getTitleCompletedDemand() {
        return titleCompletedDemand;
    }

    public String getDateCompletedDemand() {
        return dateCompletedDemand;
    }

    public String getMessageCompletedDemand() {
        return messageCompletedDemand;
    }

    public Boolean getChargeDemand() {
        return isChargeDemand;
    }

    public String getTextEvaluation() {
        return textEvaluation;
    }

    public Boolean getEvaluate() {
        return isEvaluate;
    }

    public String getTextFeedback() {
        return textFeedback;
    }
}
