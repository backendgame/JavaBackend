package web_admin.http.login;

import java.util.HashMap;

import backendgame.com.core.HttpProcessing;
import backendgame.com.core.server.BackendSession;
import web_admin.API;

public class HttpProcessing_Login extends HttpProcessing {

	public HttpProcessing_Login() {
		super(API.LoginScreen_Login);
	}

    @Override
    public String onHttp_GET(BackendSession session, HashMap<String, String> mapParam) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String onHttp_POST(BackendSession session, String paramString) {
        // TODO Auto-generated method stub
        return null;
    }


	
	
}
