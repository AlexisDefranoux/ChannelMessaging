package alexis.defranoux.channelmessaging;

/**
 * Created by defranoa on 27/01/2017.
 */
public class MessageAEnvoyer {

    public final String response;
    public final int code;

    public MessageAEnvoyer(String response, int code) {
        this.response = response;
        this.code = code;
    }
}
