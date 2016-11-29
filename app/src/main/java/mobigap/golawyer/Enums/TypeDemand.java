package mobigap.golawyer.Enums;

/**
 * Created by luisresende on 29/11/16.
 */

public enum TypeDemand {

    COPY,COURT,PROTOCOL,DISTRIBUTION,CHARTER,CERTIFICATE,OTHERS;

    private static String nameCopy = "copia";
    private static String nameCourt = "audiencia";
    private static String nameProtocol = "protocolo";
    private static String nameDistribution = "distribuicao";
    private static String nameCharter = "alvara";
    private static String nameCertificate = "certidao";
    private static String nameOthers = "outros";

    public static TypeDemand getTypeDemandByOrdinal(int number){
        for (TypeDemand typeDemand : TypeDemand.values()){
            if (typeDemand.ordinal() == number){
                return typeDemand;
            }
        }
        return null;
    }

    public static String getStringTypeDemand(int number){
        TypeDemand typeDemand = TypeDemand.getTypeDemandByOrdinal(number);
        if (typeDemand==COPY){
            return nameCopy;
        }else if (typeDemand==COURT){
            return nameCourt;
        }else if (typeDemand==PROTOCOL){
            return nameProtocol;
        }else if (typeDemand==DISTRIBUTION){
            return nameDistribution;
        }else if (typeDemand==CHARTER){
            return nameCharter;
        }else if (typeDemand==CERTIFICATE){
            return nameCertificate;
        }else if (typeDemand==OTHERS){
            return nameOthers;
        }else {
            return "";
        }
    }


}
