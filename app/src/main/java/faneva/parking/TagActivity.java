package faneva.parking;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import faneva.parking.util.Service;

public class TagActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    private Button btnInsert;
    private TextView tm;
    private TextView tc;
    private TextView tt;
    private TextView tco;
    private TextView tma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        btnInsert = (Button) findViewById(R.id.button);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (!nfcAdapter.isEnabled())
        {
            //Demander à l’utilisateur d’activer l’option NFC
            Toast.makeText(getApplicationContext(), "Veuillez Activer NFC", Toast.LENGTH_SHORT).show();

        }
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass())
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        btnInsert.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String marq= tm.getText().toString();
                String cat= tc.getText().toString();
                String typ= tt.getText().toString();
                String col= tco.getText().toString();
                String mat= tma.getText().toString();
                if(marq==null )
                {
                    Toast.makeText(getApplicationContext(), "Veuillez tag le vehicule", Toast.LENGTH_SHORT).show();
                }
                try {
                    Toast.makeText(getApplicationContext(), marq, Toast.LENGTH_SHORT).show();
                    String ret = Service.ajouterVehicule(marq, cat, typ, col, mat);
                    Toast.makeText(getApplicationContext(),
                            ret, Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Toast.makeText(getApplicationContext(), marq, Toast.LENGTH_SHORT).show();
            }

        });
    }





    @Override
    public void onResume()
    {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, null,
                null);
    }
    @Override
    public void onPause()
    {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }
    public void processNfcIntent (Intent intent)
    {
        //Infos sur le tag
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        byte[] id =tag.getId();
        String[] technologies = tag.getTechList();
        int content = tag.describeContents();
        Ndef ndef = Ndef.get(tag);
        boolean isWritable = ndef.isWritable();
        boolean canMakeReadOnly = ndef.canMakeReadOnly();
        //Récupération des messages
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage[] msgs;
        //Boucle sur les enregistrements
        if (rawMsgs != null)
        {
            msgs = new NdefMessage[rawMsgs.length];
            for (int i = 0; i < rawMsgs.length; i++)
            {
                msgs[i] = (NdefMessage) rawMsgs[i];
                NdefRecord record = msgs[i].getRecords()[i];
                byte[] idRec = record.getId();
                short tnf = record.getTnf();
                byte[] type = record.getType();
                String message =new String(record.getPayload());

                message=message.substring(3);
                String[] desc = message.split(",");
                 tm=(TextView)findViewById(R.id.txtmarque);
                 tc=(TextView)findViewById(R.id.txtcategorie);
                 tt=(TextView)findViewById(R.id.txttype);
                 tco=(TextView)findViewById(R.id.txtcouleur);
                 tma=(TextView)findViewById(R.id.txtmatri);
                 tm.setText(desc[0]);tt.setText(desc[1]);tc.setText(desc[2]);tco.setText(desc[3]);tma.setText(desc[4]);



                // Log.d("message",message);
                //Utiliser ?
                //Laisser Android choisir l’appli par défaut si type URI ?
                if (Arrays.equals(type, NdefRecord.RTD_URI))
                {
                    Uri uri = record.toUri();
                    Intent ii = new Intent(Intent.ACTION_VIEW);
                    ii.setData(uri);
                    startActivity(ii);
                }
            }
        }
        else
        {
            //Tag de type inconnu, tester une récupération du contenu hexadécimal ?
            byte[] empty = new byte[] {};
            NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN,
                    empty, empty, empty);
            NdefMessage msg = new NdefMessage(new NdefRecord[] {record});
            msgs = new NdefMessage[] {msg};

        }
        //Traiter les informations...
    }
    @Override
    public void onNewIntent(Intent intent)
    {
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action) ||
                NfcAdapter.ACTION_TECH_DISCOVERED.equals(action))
        {
//Méthode qui va traiter le tag NFC
            processNfcIntent(intent) ;
        }
    }




}
