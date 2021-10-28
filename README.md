# Client-Server-MojBroj

<h2>Kratak opis aplikacije</h2>

Aplikacija resava *Moj Broj problem*.

<h2>Upustvo za korišćenje aplikacije</h2>

Nakon sto je pokrenuta server strana, ona slusa na portu i ceka pokretanje klijent strane. Korisniku se pri pokretanju klijent strane, pojavljuje prozor sa 7 polja za unos brojeva. Nakon sto korisnik unese redom ciljni broj i 6 *gradivnih brojeva* klikom na dugme "Resi" dobija izgenerisan izraz izgradjen od *gradivnih brojeva* i osnovnih operacija 
(+, -, *, /), a  cija je vrednost zadati ciljni broj.

<h2>Detaljniji opis aplikacije</h2>
Napisana je u programskom jeziku Java u razvojnom okruzenju IntelliJ IDEA. Za GUI je koriscene su funkcionalnosti JavaFX bibilioteke.

Od kontejnera korisceni su nizovi, skupovi, hes mape, redovi.

Za realizaciju klijent-server arhitekrure korisceni su TCP soketi ( Server i ServerSocket klase).

<h2>Jezici i tehnologije koriscene u izradi</h2>
Programski jezik: Java

IDE: IntelliJ IDEA

GUI: JavaFX

Socket class and ServerSocket class

<h2> Pokretanje</h2>

<h3>Windows:</h3>

Izvrisni .exe fajlovi server i klijent strane nalaze se u osnovnom direktorijumu repozitorijuma pod nazivima redom MojBroj_server i MojBroj_client.

<h3>Linux:</h3>
Za pokretanja server aplikacije neophodno je pozicioniranje (relativno u odnosu na bazni direktorijum repozitorijuma) u direktorijum out/artifacts/server_jar. Nakon toga potrebno je izvrsavanje komande chmod +x untitled104.jar. Na kraju komanda, java -jar untitled104.jar pokrece klijent stranu aplikacije.

Za pokretanja klijent aplikacije neophodno je pozicioniranje (relativno u odnosu na bazni direktorijum repozitorijuma) u direktorijum out/artifacts/klijent_jar. Nakon toga potrebno je izvrsavanje komande chmod +x untitled104.jar. Na kraju komanda, java -jar untitled104.jar pokrece klijent stranu aplikacije.



