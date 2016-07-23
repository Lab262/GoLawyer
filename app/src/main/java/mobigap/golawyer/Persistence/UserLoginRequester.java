package mobigap.golawyer.Persistence;

import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;

import java.net.URI;
import java.util.HashMap;

import mobigap.golawyer.Model.User;
import mobigap.golawyer.Model.UserModelRequest;

/**
 * Created by luisresende on 22/07/16.
 */
public class UserLoginRequester extends SpringAndroidSpiceRequest<UserModelRequest> {

    private static final String BASE_URL = JsonSpiceService.BASE_URL + "GetLogin/" + JsonSpiceService.SERVER_TOKEN;
    private String email;
    private String password;

    public UserLoginRequester(String email, String password) {
        super(UserModelRequest.class);
        this.email = email;
        this.password = password;
    }

    @Override
    public UserModelRequest loadDataFromNetwork() throws Exception {

        HashMap parameters = new HashMap();
        parameters.put("email", email);
        parameters.put("password", password);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> request = new HttpEntity<Object>(parameters, requestHeaders);
//        System.out.print(request.getBody() + "   request body");

        getRestTemplate().getMessageConverters().add(new MappingJacksonHttpMessageConverter());

//        URI response = getRestTemplate().po


        return getRestTemplate().postForObject(BASE_URL, request, UserModelRequest.class);
    }
}
