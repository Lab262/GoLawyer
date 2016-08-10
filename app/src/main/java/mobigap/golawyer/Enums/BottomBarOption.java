package mobigap.golawyer.Enums;

/**
 * Created by luisresende on 23/07/16.
 */
public enum BottomBarOption {
    MAP, SERVICE,PROFILE;

    public static BottomBarOption getBottomBarOptionByOrdinal(int number){
        for (BottomBarOption bottomBarOption : BottomBarOption.values()){
            if (bottomBarOption.ordinal() == number){
                return bottomBarOption;
            }
        }
        return null;
    }
}
