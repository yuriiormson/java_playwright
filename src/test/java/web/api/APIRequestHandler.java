package web.api;

import com.microsoft.playwright.APIRequestContext;
import lombok.extern.slf4j.Slf4j;
import web.utils.DateTimeUtils;
import org.json.JSONObject;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class APIRequestHandler {
    public static void sendApiRequestToSyncTheApp(
            APIRequestContext apiRequestContext,
            String projectsSynchronizationUrl,
            String dsParam) {
        boolean isSuccess = false;
        int maxTries = 30;

        for (int i = 1; i <= maxTries; i++) {
            log.info("Tries to request API sync = " + i);
            try {
                String encodedToString = apiRequestContext
                        .get(projectsSynchronizationUrl + "/" +
                                "SyncProject?" + "dsParam1=" + dsParam
                                + "&" + "dsParam1=" + dsParam).text();

                JSONObject jsonResponse = new JSONObject(encodedToString);
                log.info("JSON Response = " + jsonResponse);

                isSuccess = jsonResponse.getBoolean("Success");

                if (isSuccess) {
                    log.info("Synced at " + DateTimeUtils.getCurrentDateTime());
                    break;
                } else {
                    String message = jsonResponse.getString("Message");
                    log.error("Application sync failed: " + message);
                }
            } catch (Exception e) {
                log.error("An error occurred during API Request: " + e.getMessage());
                e.printStackTrace();
            }
        }

        assertThat(isSuccess).isTrue();
    }

}
