package com.example.dns;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.dnsapk.R;

public class HelpActivity extends Activity {
	TextView textViewHelp;
	String help;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		setTitle("Pomoc");
		textViewHelp = (TextView)findViewById(R.id.textViewHelp);
		String help = "Aby aplikacja poprawnie funkcjonowała należy wykonać poniższe czynności: \n\n" +
				 "1. Należy stworzyć dwa klucze szyfrujące RSA, klucz prywatny i klucz publiczny.\n" +
				 "a) Klucz prywatny musi mieć nazwę ''id_rsa''.\n" +
				 "b) zawartość klucza publicznego należy przesłać do pliku o nazwie authorized_keys.\n" +
				 "c) Plik authorized_keys powinien znajdować się na maszynie, na której będziemy zarządzać usłygą DNS i powinien być umieszczony w katalogu domowym administratora w katalogu .ssh. Jeżeli nie ma takiego katalogu, należy go stworzyć.\n" +
				 "d) Klucz prywatny ''id_rsa'' należy przesłać na urządzenie, na którym została zainstalowana aplikacja. Podczas pierwszego uruchomienia aplikacji został utworzony katalog ''ssh'' w pamięci urządzenia. W tym katalogu powinien znajdować się klucz prywatny.\n\n" +
				 "2. Na maszynie z zainstalowaną usługą DNS należy utworzyć katalog ''domeny'' w katalogu /etc/bind. W tym katalogu należy umieścić bazę danych domen, którymi zarządzasz.\n\n" +
				 "3. W pliku konfiguracyjnym named.conf.local należy poprawnie zdefiniować logi zapytań serwera DNS.\n" +
				 "a) Należy utworzyć katalog ''named'' w katalogu /var/log.\n" +
				 "b) Katalogowi /var/log/named należy ustawić bind jako właściciela katalogu oraz nadać odpowiednie uprawnienia:\n\n" +
				 "-chown -R bind:root /var/log/named\n"+
				 "-chmod -R 775 /var/log/named\n\n" +
				 "c) Plik z logami powinien nosić nazwę queries.log. Pełna ścieżka do pliku: /var/log/named/queries.log. Nie należy go tworzyć, zostanie on automatycznie utworzony po poprawnej konfiguracji logów.\n\n" +
				 "Przykładowa konfiguracja logów:\n" +
				 "logging {\n" +
			     "channel queries_file {\n" +
			     "file ''/var/log/named/queries.log'' versions 3 size 5m\n" +
			     "severity dynamic;\n" +
	        	 "print-time yes;\n" +
	        	 "};\n" +
	        	 "category queries { queries_file; };\n" +
	        	 "};\n";
		textViewHelp.setText(help);
	}
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.help, menu);
		return true;
	}
	*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
