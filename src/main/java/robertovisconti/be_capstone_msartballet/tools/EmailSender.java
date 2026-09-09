package robertovisconti.be_capstone_msartballet.tools;


import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailSender {

    private final String domainName;
    private final String apiKey;

    public EmailSender(
            @Value("${mailgun.domainName}") String domainName,
            @Value("${mailgun.apiKey}") String apiKey

    ) {
        this.domainName = domainName;
        this.apiKey = apiKey;
    }

    public void inviaEmail(String destinatario, String oggetto, String testo, String html) {
        HttpResponse<JsonNode> risposta = Unirest.post("https://api.mailgun.net/v3/" + domainName + "/messages")
                .basicAuth("api", apiKey)
                .queryString("from", "MS Art Ballet <postmaster@" + domainName + ">")
                .queryString("to", destinatario)
                .queryString("subject", oggetto)
                .queryString("text", testo)
                .queryString("html", html)
                .asJson();
        if (!risposta.isSuccess()) {
            log.error("Invio email a {} fallito ({}): {}", destinatario, risposta.getStatus(), risposta.getStatusText());
        }
    }
}
