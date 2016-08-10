package mobigap.golawyer.Enums;

/**
 * Created by luisresende on 08/07/16.
 */
public enum TypeProfile {
    CLIENT, LAWYER;

    public static TypeProfile getTypeProfileByOrdinal(int number){
        for (TypeProfile typeProfile : TypeProfile.values()){
            if (typeProfile.ordinal() == number){
                return typeProfile;
            }
        }
        return null;
    }
}
