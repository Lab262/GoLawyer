package mobigap.golawyer.Enums;

/**
 * Created by luisresende on 08/07/16.
 */
public enum ServiceStatusEnum {
    DEMAND, PAYMENT, CHAT, DELIVERY, RATE, ENDED;

    public static ServiceStatusEnum getTypeProfileByOrdinal(int number){
        for (ServiceStatusEnum serviceStatus : ServiceStatusEnum.values()){
            if (serviceStatus.ordinal() == number){
                return serviceStatus;
            }
        }
        return null;
    }
}
