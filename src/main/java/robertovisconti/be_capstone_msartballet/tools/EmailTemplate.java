package robertovisconti.be_capstone_msartballet.tools;

public final class EmailTemplate {

    private static final String LOGO_URL =
            "https://res.cloudinary.com/af8jn7zb/image/upload/v1788936849/email-assets/yipqqdevyrhbublnaahd.png";

    private EmailTemplate() {
    }

    public static String build(String nome, String messaggio, String testoBottone, String link, String nota) {
        return """
                <!DOCTYPE html>
                <html lang="it">
                <body style="margin:0;padding:0;background-color:#000000;font-family:Georgia,'Times New Roman',serif;">
                  <table role="presentation" width="100%%" cellpadding="0" cellspacing="0" style="background-color:#000000;padding:40px 0;">
                    <tr><td align="center">
                      <table role="presentation" width="480" cellpadding="0" cellspacing="0" style="background-color:#0d0d0d;border:1px solid rgba(234,231,228,0.14);border-radius:8px;overflow:hidden;">
                        <tr><td style="padding:28px 40px 20px;text-align:center;border-bottom:1px solid rgba(234,231,228,0.14);">
                          <table role="presentation" cellpadding="0" cellspacing="0" align="center" style="margin:0 auto;">
                            <tr>
                              <td style="padding-right:10px;vertical-align:middle;">
                                <img src="%s" width="26" height="40" alt="" style="display:block;border:0;">
                              </td>
                              <td style="vertical-align:middle;">
                                <span style="font-size:19px;letter-spacing:5px;color:#eae7e4;text-transform:uppercase;">MS Art Ballet</span>
                              </td>
                            </tr>
                          </table>
                        </td></tr>
                        <tr><td style="padding:40px;color:#eae7e4;font-size:15px;line-height:1.7;">
                          <p style="margin:0 0 20px;">Ciao %s,</p>
                          <p style="margin:0 0 32px;color:#a8a4a1;">%s</p>
                          <table role="presentation" cellpadding="0" cellspacing="0" style="margin:0 auto 32px;">
                            <tr><td style="background-color:#d10068;border-radius:4px;">
                              <a href="%s" style="display:inline-block;padding:14px 34px;color:#ffffff;text-decoration:none;font-size:13px;letter-spacing:1.5px;text-transform:uppercase;font-family:Arial,Helvetica,sans-serif;">%s</a>
                            </td></tr>
                          </table>
                          <p style="margin:0;color:#6e6a68;font-size:13px;">%s</p>
                        </td></tr>
                        <tr><td style="padding:20px 40px;text-align:center;border-top:1px solid rgba(234,231,228,0.14);">
                          <p style="margin:0;color:#4e4b4a;font-size:11px;letter-spacing:0.5px;">© 2026 MS Art Ballet</p>
                        </td></tr>
                      </table>
                    </td></tr>
                  </table>
                </body>
                </html>
                """.formatted(LOGO_URL, nome, messaggio, link, testoBottone, nota);
    }
}
