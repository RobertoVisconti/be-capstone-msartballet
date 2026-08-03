package robertovisconti.be_capstone_msartballet.tools;


import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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

    public void inviaEmail(String destinatario, String oggetto, String testo) {
        HttpResponse<JsonNode> risposta = Unirest.post("https://api.mailgun.net/v3/" + domainName + "/messages")
                .basicAuth("api", apiKey)
                .queryString("from", "Scuola di Danza <postmaster@" + domainName + ">")
                .queryString("to", destinatario)
                .queryString("subject", oggetto)
                .queryString("text", testo)
                .asJson();
        if (!risposta.isSuccess()) {
            System.out.println("Invio email a " + destinatario + " fallito: " + risposta.getStatus() + " " + risposta.getStatusText());
        }
    }
}
