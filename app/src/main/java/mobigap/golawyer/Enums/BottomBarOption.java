package mobigap.golawyer.Enums;

/**
 * Created by luisresende on 23/07/16.
 */
public enum BottomBarOption {
    MAP, SERVICE,PROFILE;

    public static TypeProfile getBottomBarOptionByOrdinal(int number){
        for (TypeProfile typeProfile : TypeProfile.values()){
            if (typeProfile.ordinal() == number){
                return typeProfile;
            }
        }
        return null;
    }
}
