package com.lmar.chatapp.util;

import android.util.Log;

import com.google.auth.oauth2.GoogleCredentials;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class AccessToken {
    private static final String firebaseMessagingScope =
            "https://www.googleapis.com/auth/firebase.messaging";

    public String getAccessToken() {
        try {
            String jsonString = "{\n" +
                    "  \"type\": \"service_account\",\n" +
                    "  \"project_id\": \"chatting-lmar\",\n" +
                    "  \"private_key_id\": \"d9396aede2714279205d21ba2141b2b1c71b8982\",\n" +
                    "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHFgy8hPA6tQku\\nr76GGj6aRc4/6Z6xH+wzoyBGxnu1P9LvePHguTNjKlGw1VQsxK8PSxf/wdKYEDRK\\np1KQiRca65wS0mwzfht18V84fGb3tYgtcAnMJPXWW91EenVqoDQd8T5E86iHH8Su\\nG9GPoANuLyM0cH9Gc/oPLhXsoxR74ynuD0mrozbUI22AtUkeMUEqhqNQx8+ZDTUg\\n0nuxgeqGV8B3UdyMMhAbiBKX/2j0n9jXytUCQfM1Rjz4mpO4KousymJUBCii+TKF\\nV1JsFdY/q+8hg5oix5gTmzoJFZNT6cXxU8q038YkMM/vvLax52vR1FB04YatNIiw\\nmj9ehbATAgMBAAECggEALIkBs2BXrwJVegSlbbFMRM2NzF1R+xSFja2VqE95V/vd\\nj2VyUk+Lr8jFOnYWXOdos9acGUH9/rdNQ0/3185bBVIkeU9qFgDFWtZycxvdGteY\\njXWNM4YnGls1fb6ZKo+I6RPQ1rLdBdE0+a/Oar8kVU8lJHNv/U4vZ5rrG6MFj4rF\\n8olvwacPd/BOgQVc/FUI+RnBwJV/qliYrd6l8HnuyueGKiyfslLcMEdGs7yDuZ44\\nUwHz9vDG++zVPLQWu6bOvAMbQEbJ6Akt/cCYfAqCat4MfphwBrr6zGMpgrPzvAyi\\nWixbCtxmAajeFuQHTtolloMeH3NsoYpRIDyxKQcDDQKBgQDtVaniqrExxi+YIeGm\\nVI4cSHFAr21EWl8gYSrCj0/xdIa5zNSVztBRzYsstwb102nHU2PAHctwAvoVKvCg\\nLruk2Mxp+Qr/mEwnr0LpdHJ7LOOfk8zvWRorb4Xr7IVUbDFEd+JO/J0S0Oj8Z69K\\nzJ0+dkkVUAC9Za3VZc+qpoYIRQKBgQDWvlEN2/Sg0IaF54VLAxYmxn+bxu+e1MBq\\nZB0+uTvpq8o29kuMEHtgkRpvYdgcaWQQZUoapeJQlGSQel+raATo9iryhm9q8ihv\\nsEXx/QZz44x0LWdYKfiNu8nWLU0kCj7DYQBy3x1Be0U/DqfDfJAYeqhJBmo8brPs\\nepivdTH4dwKBgDv96WGIcB360NENz3Ix8XIoxafqMB6VEXKn3R0tCLIqGFwi8KGl\\nk3b6E/ILmdDNzfE6nP5VCbWYqwpqvkRfS8NRoeovr54IK0fkv28vpjiIkkkmHSlr\\nRngwSpPmR5Wde56pByWcKUA2Wo5izyaDLkQ5tiOTA3zclOJaR/IFAeBZAoGBALWx\\nDS9mvVIbXNaLDzJqiCGCrZGF/lQquirKThw16cVJvqxGM6FsiJcp7m3zIUDYwvtE\\nWDC3zgZTp8q+X5fNSOo67heJO1gHT8NAJ84ZQY5oJvxdNiVYTZ7OFnYKmkw2BuiU\\neCa5NC2lOO+jXLp/+ANeNGik7tn26HDbQbiitgb9AoGAN44LSXQBiwvbqkdI+kB1\\nGbzCezgct9zG++CZEt5IrVhFbyjGmV9FnLHBkujXZR1TB1QV8oOlmURRS2ol8VO2\\nwWV6qmwDxHTDpdII2A1qndSv5DJNG9QfdCDGznRmMPK9LWcV8jxK9U9fHXYQW0zJ\\nhk8tDoBWcmjx2LtjkoDuVRQ=\\n-----END PRIVATE KEY-----\\n\",\n" +
                    "  \"client_email\": \"firebase-adminsdk-190zt@chatting-lmar.iam.gserviceaccount.com\",\n" +
                    "  \"client_id\": \"117879733784043730683\",\n" +
                    "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                    "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                    "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                    "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-190zt%40chatting-lmar.iam.gserviceaccount.com\",\n" +
                    "  \"universe_domain\": \"googleapis.com\"\n" +
                    "}";
            InputStream stream = new ByteArrayInputStream(jsonString.getBytes(StandardCharsets.UTF_8));
            GoogleCredentials googleCredentials = GoogleCredentials.fromStream(stream).createScoped(firebaseMessagingScope);
            googleCredentials.refresh();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (Exception e) {
            Log.e("AccessToken", "getAccessToken: " + e.getLocalizedMessage());
            return null;
        }
    }
}