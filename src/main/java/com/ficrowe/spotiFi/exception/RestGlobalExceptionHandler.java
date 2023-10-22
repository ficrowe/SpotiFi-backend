// package com.ficrowe.spotiFi.exception;
//
// import com.ficrowe.spotiFi.delegate.SpotifyAuthDelegate;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpStatus;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
// import org.springframework.web.client.HttpClientErrorException;
// @RestControllerAdvice
// public class RestGlobalExceptionHandler {
//
//    @Autowired
//    private SpotifyAuthDelegate spotifyAuthDelegate;
//
//    private Logger LOGGER = LoggerFactory.getLogger(this.getClass());
//
//    @ExceptionHandler({HttpClientErrorException.Unauthorized.class})
//    public void unauthorisedExceptionHandler(HttpClientErrorException.Unauthorized exception,
// HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception
// {
//        LOGGER.error(exception.getMessage(), exception);
//
////
// spotifyAuthDelegate.setSpotifyApiRedirectUrl(URI.create("http://localhost:8080/spotify/retry?action=" + failedAction));
////        URI authorizationCodeUri = spotifyAuthDelegate.authorizationCodeUri();
//////        httpServletResponse.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:5173");
////        httpServletResponse.sendRedirect(authorizationCodeUri.toString());
////        String uriQuery = authorizationCodeUri.getQuery();
////        String clientId =
// authorizationCodeUri.toString().substring(uriQuery.indexOf("client_id="), uriQuery.indexOf("&"));
////        LOGGER.info(uriQuery);
////        LOGGER.info(clientId);
//
//        refreshToken(exception);
//    }
//
//    @ExceptionHandler({HttpClientErrorException.class})
//    public void handleClientErrorException(HttpClientErrorException exception) throws Exception {
//        LOGGER.error(exception.getMessage(), exception);
//
//        if (exception.getStatusCode() == HttpStatus.UNAUTHORIZED) {
//            refreshToken(exception);
//        }
////        return exception.getStatusCode();
//        throw exception;
//    }
//
//    @ExceptionHandler({Exception.class})
//    public void handleException(Exception exception) throws Exception {
//        LOGGER.error(exception.getMessage(), exception);
//        throw exception;
//    }
//
//    private void refreshToken(Exception exception) throws Exception {
//        String redirectUrl = spotifyAuthDelegate.refreshAccessToken();
//
//        if (redirectUrl == "http://127.0.0.1:5173/shortcuts") {
//            String className = exception.getStackTrace()[0].getClassName();
//            String failedAction = exception.getStackTrace()[0].getMethodName();
//            LOGGER.info("Retrying spotify action: {}", failedAction);
//            Class.forName(className).getDeclaredMethod(failedAction).invoke(this);
//            return;
//        } else {
//            throw exception;
//        }
//    }
//
// }
