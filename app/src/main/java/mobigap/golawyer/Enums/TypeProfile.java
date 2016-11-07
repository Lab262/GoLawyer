package mobigap.golawyer.Enums;

/**
 * Created by luisresende on 08/07/16.
 */
public enum TypeProfile {
    CLIENT, LAWYER;

    private static String nameClient = "Cliente";
    private static String nameLawyer = "Advogado";

    public static TypeProfile getTypeProfileByOrdinal(int number){
        for (TypeProfile typeProfile : TypeProfile.values()){
            if (typeProfile.ordinal() == number){
                return typeProfile;
            }
        }
        return null;
    }

    public static String getStringTypeProfile(int number){
        TypeProfile typeProfile = TypeProfile.getTypeProfileByOrdinal(number);
        if (typeProfile==CLIENT){
            return nameClient;
        }else if (typeProfile==LAWYER){
            return nameLawyer;
        }else {
            return "";
        }
    }
}
