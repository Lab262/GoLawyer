package mobigap.golawyer.Model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by luisresende on 22/07/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserModelRequest {
    private String itens;

    private String retorno;

    private String msg;
}