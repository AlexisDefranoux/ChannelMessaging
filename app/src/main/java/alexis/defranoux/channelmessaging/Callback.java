package alexis.defranoux.channelmessaging;

/**
 * Created by defranoa on 20/01/2017.
 */
public class Callback {

    private String response;
    public final int code;
    public final String accesstoken;

    public Callback(String response, int code, String accesstoken) {
        this.response = response;
        this.code = code;
        this.accesstoken = accesstoken;
    }
}
