package uk.co.desirableobjects.oauth.scribe

import org.scribe.model.Token
import org.scribe.model.Verb
import org.scribe.model.OAuthRequest
import org.scribe.oauth.OAuthService
import org.scribe.model.Response
import java.util.concurrent.TimeUnit

class OauthResourceService {

    static def transactional = false

    Response accessResource(OAuthService service, Token accessToken, Verb verb, String url, Map body, int connectTimeout, int receiveTimeout) {

        OAuthRequest oAuthRequest = new OAuthRequest(verb, url)
        oAuthRequest.setConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
        oAuthRequest.setReadTimeout(receiveTimeout, TimeUnit.MILLISECONDS)
        body.each {k, v->
            oAuthRequest.addBodyParameter(k, v)
        }
        service.signRequest(accessToken, oAuthRequest)
        return oAuthRequest.send()

    }

    Response accessResource(OAuthService service, Token accessToken, Verb verb, String url, String xmlPayload, int connectTimeout, int receiveTimeout) {

        OAuthRequest oAuthRequest = new OAuthRequest(verb, url)
        oAuthRequest.setConnectTimeout(connectTimeout, TimeUnit.MILLISECONDS)
        oAuthRequest.setReadTimeout(receiveTimeout, TimeUnit.MILLISECONDS)
        oAuthRequest.addPayload(xmlPayload)
        oAuthRequest.addHeader("Content-Length", Integer.toString(xmlPayload.length()))
        oAuthRequest.addHeader("Content-Type", "text/xml")
        service.signRequest(accessToken, oAuthRequest)
        return oAuthRequest.send()

    }
}
