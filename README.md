# MS Art Ballet — Backend

API REST per la gestione di una scuola di danza: anagrafiche utenti, catalogo di corsi, lezioni e sale, iscrizioni, prenotazioni e transazioni.

> **Questo progetto è diviso in due repository:**
> - **Backend (questo repo)** — API REST Spring Boot: https://github.com/RobertoVisconti/be-capstone-msartballet
> - **Frontend** — client React + TypeScript: https://github.com/RobertoVisconti/fe-msartballet

Progetto capstone realizzato per il corso Epicode.

---

## Stack

| | |
|---|---|
| Linguaggio | Java 25 |
| Framework | Spring Boot 4.1.0 (Web MVC, Data JPA, Security, Validation) |
| Database | PostgreSQL |
| Autenticazione | JWT (jjwt) |
| Upload immagini | Cloudinary |
| Invio email | Mailgun (via Unirest) |
| Altro | Lombok, Spring Boot DevTools |

---

## Modello del dominio

L'anagrafica utenti usa l'ereditarietà JPA: `Utente` è la classe base, specializzata in **`Allievo`**, **`Insegnante`**, **`Ospite`** e **`Admin`**.

Le altre entità coprono il dominio della scuola:

- **Catalogo** — `Disciplina`, `Corso`, `Sala`, `Prodotto`, `Spettacolo`, `Media`
- **Attività** — `Lezione` (una lezione appartiene a un corso e si svolge in una sala)
- **Relazioni** — `Iscrizione` (allievo → corso), `Prenotazione` (utente → lezione)
- **Pagamenti** — `Transazione` (l'acquisto di un prodotto, di un corso o dell'affitto di una sala)
- **Supporto** — `TokenAttivazione`, `TokenResetPassword`

---

## Ruoli e autenticazione

Quattro ruoli operativi: `ADMIN`, `ALLIEVO`, `INSEGNANTE`, `OSPITE`.

L'autenticazione è a **JWT Bearer token**, con autorizzazione per ruolo dichiarata endpoint per endpoint (`@PreAuthorize`).

**Flusso di registrazione** — non esiste auto-registrazione per allievi e insegnanti:

1. Un admin crea l'account (`POST /auth/admin/allievi`, `/auth/admin/insegnanti`, `/auth/admin/admins`)
2. Il sistema invia via Mailgun un link di attivazione contenente un token monouso
3. L'utente sceglie la propria password (`POST /auth/attiva-account`) e riceve subito un JWT

Sono previsti anche il recupero password (`/auth/password-dimenticata` + `/auth/reset-password`) e il rinvio del link di attivazione (`/auth/reinvia-attivazione`).

**Prenotazione senza account** — un visitatore può prenotare una lezione di prova senza registrarsi (`POST /prenotazioni/ospite`, endpoint pubblico): il sistema crea o riusa un `Ospite` a partire dall'email.

Al primo avvio un admin di default viene creato automaticamente dalle credenziali in `env.properties`.

---

## Avvio in locale

### Prerequisiti

- JDK 25
- PostgreSQL in esecuzione, con un database creato (es. `be-capstone-msartballet`)
- Un account Cloudinary e uno Mailgun (anche in sandbox) per upload immagini ed email

### 1. Configurazione

Il file `env.properties` contiene le credenziali ed è escluso dal versionamento: va creato nella **root del progetto**. Questo è il template completo con tutte le chiavi da valorizzare:

```properties
WEB_SERVER_PORT=3007
DB_URL=jdbc:postgresql://localhost:5432/be-capstone-msartballet
DB_USERNAME=postgres
DB_PASSWORD=your_db_password
CLOUDINARY_API_KEY=your_cloudinary_api_key
CLOUDINARY_SECRET=your_cloudinary_secret
CLOUDINARY_NAME=your_cloudinary_cloud_name
JWT_SECRET=chiave_segreta_di_almeno_32_caratteri
MAILGUN_API_KEY=your_mailgun_api_key
MAILGUN_DOMAIN_NAME=your_mailgun_domain
ADMIN_EMAIL=admin@esempio.it
ADMIN_PASSWORD=password_admin_iniziale
FRONTEND_URL=http://localhost:5173
```

Qualche nota sulle chiavi meno ovvie:

- `JWT_SECRET` deve essere lungo almeno 32 caratteri
- `ADMIN_EMAIL` e `ADMIN_PASSWORD` sono le credenziali dell'admin creato automaticamente al primo avvio
- `FRONTEND_URL` viene usata sia per la policy CORS sia per costruire i link nelle email di attivazione e recupero password

Lo schema del database viene generato automaticamente da Hibernate (`ddl-auto=update`).

### 2. Avvio

```bash
./mvnw spring-boot:run
```

L'API risponde su `http://localhost:3007` (o sulla porta impostata in `WEB_SERVER_PORT`).

> `FRONTEND_URL` definisce anche l'origine consentita dalla configurazione CORS: deve corrispondere all'URL su cui gira il frontend.

---

## Mappa delle API

| Area | Base path | Note |
|---|---|---|
| Autenticazione | `/auth` | login, attivazione account, reset password, creazione utenti (admin) |
| Utenti | `/utenti` | profilo personale (`/utenti/me`), cambio password, foto profilo |
| Gestione utenti | `/utenti/allievi`, `/utenti/insegnanti`, `/utenti/ospiti`, `/utenti/admins` | riservate all'admin: lista con filtri, dettaglio, modifica, disattiva/riattiva |
| Catalogo | `/discipline`, `/corsi`, `/sale`, `/prodotti`, `/spettacoli`, `/media` | lettura pubblica, scrittura riservata all'admin |
| Lezioni | `/lezioni` | lettura pubblica, gestione riservata all'admin |
| Iscrizioni | `/iscrizioni` | `GET /iscrizioni/mie` per l'allievo, gestione completa per l'admin |
| Prenotazioni | `/prenotazioni` | `POST /prenotazioni/ospite` pubblico, `GET /prenotazioni/mie` per l'allievo, gestione completa per l'admin |
| Transazioni | `/transazioni` | riservate all'admin |
| Upload | `/uploads` | caricamento file su Cloudinary |

Tutti gli endpoint di lista sono **paginati** (`Pageable` → `Page<T>`) e la maggior parte accetta filtri opzionali via query string, implementati con il **Specification pattern** (es. `GET /prenotazioni?idCorso=...&dataDa=2026-09-01&dataA=2026-09-30&stato=CONFERMATA`).

---

## Scelte implementative

- **Importi calcolati lato server** — l'`importo` di una transazione non viene mai accettato dal client: è ricavato dal prezzo reale dell'entità collegata (prodotto, corso o sala) al momento della creazione.
- **Validatori custom** — `@AcquistoValido` garantisce che una transazione sia collegata a **esattamente uno** fra prodotto, corso e sala; `@IntervalloOrarioValido` verifica la coerenza degli orari di una lezione (a cui si aggiunge il controllo di sovrapposizione sulla stessa sala).
- **Protezione dai duplicati** — sia sul percorso autenticato sia su quello ospite, non è possibile prenotare due volte la stessa lezione con lo stesso utente/email.
- **Filtri componibili** — ogni entità con liste consistenti ha la propria `Specification` (`AllievoSpecification`, `CorsoSpecification`, `IscrizioneSpecification`, `LezioneSpecification`, `PrenotazioneSpecification`, `TransazioneSpecification`), così i filtri opzionali si combinano senza moltiplicare i metodi del repository.
- **Gestione errori centralizzata** — eccezioni di dominio dedicate (`NotFoundException`, `BadRequestException`, `ValidationException`) tradotte in risposte HTTP coerenti da un exception handler globale.

---

## Autore

Roberto Visconti — progetto capstone Epicode.
