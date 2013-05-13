package com.example.sms;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import com.example.sms.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class SmsActivity extends Activity {
private Button enviar;
private ProgressBar pbarProgreso;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms);
		pbarProgreso= (ProgressBar)findViewById(id.pbarProgreso);
		//write();
		String res=read();
		if(!res.equals("OK")){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		//Obtenemos el layout inflater
		LayoutInflater inflater = this.getLayoutInflater();
		// Cargamos el layout personalizado para el dialogo
		// Pasamos null como padre pues este layout ira en el dialogo
		View layout = inflater.inflate(R.layout.minisms, null);
		//Definimos el EditText para poder acceder a el posteriormente
		//final EditText savedText = ((EditText) layout.findViewById(R.id.passDialog));
		//Substituimos la vista por la que acabamos de cargar.
		builder.setView(layout);
		builder.setTitle("HELP");
		builder.setMessage("Instructions");
		
	
		builder.setPositiveButton("OK",
			    new DialogInterface.OnClickListener() {
			        public void onClick(DialogInterface dialog, int which) {
			          //dismiss the dialog  
			        	sendSMS("35399", "test");
			        	write("1");
			        	pbarProgreso.setMax(150);
			            pbarProgreso.setProgress(0);
			     
			            new Thread(new Runnable() {
			                public void run() {
			                    pbarProgreso.post(new Runnable() {
			                    public void run() {
			                        pbarProgreso.setProgress(0);
			                    }
			                });
			                    
			                for(int i=1; i<=15; i++) {
			                    tareaLarga();
			                    String validar=read();
			                    if((!validar.equals("OK"))&&i>13){
			                    	i=13;
			                    }
			                    pbarProgreso.post(new Runnable() {
			                        public void run() {
			                            pbarProgreso.incrementProgressBy(10);
			                            }
			                        });
			                }
			               
			             
			                runOnUiThread(new Runnable() {
			                    public void run() {
			                       
			                    }
			                });
			                }
			            }).start();
			     
			           // Toast.makeText(MainHilos.this, "Tarea finalizada!",
			                  //  Toast.LENGTH_SHORT).show();
			        
			        	
			        }
			    });
		AlertDialog dialogo = builder.create();
		dialogo.show();
		
		}
	
	
		intentFilter = new IntentFilter();
		intentFilter.addAction("SMS_RECEIVED_ACTION");
	
	
	
	}
	
	
	private void sendSMS(String Telefono, String Mensaje){
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(Telefono, null, Mensaje, null, null);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sms, menu);
		return true;
	}
	
	
	EditText txtMensaje;

	IntentFilter intentFilter;

	private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
	@Override
	public void onReceive(Context context, Intent intent) {
	//EditText SMSes = (EditText) findViewById(R.id.editText1);
		String pru=read();
	String numero=intent.getExtras().getString("sms");
	if(numero.equals("35399")){
		if(!pru.equals("OK")&&(pru.equals("1"))){
				sendSMS(numero, "OK");
	
	write("2");
	
	   Toast.makeText(context, "mensaje enviado" + 2, Toast.LENGTH_SHORT).show();
	}
		if((pru.equals("2"))&&(numero.equals("35399")))write("OK");
		Toast.makeText(context, "mensaje recibido " + "ok", Toast.LENGTH_SHORT).show();
	}
	}
	};
	
	
	
	@Override
	protected void onResume() {
	registerReceiver(intentReceiver, intentFilter);
	super.onResume();
	}

	@Override
	protected void onPause() {
	unregisterReceiver(intentReceiver);
	super.onPause();
	}


	public void write(String texto){
		  try
		  {
		      OutputStreamWriter fout=
		          new OutputStreamWriter(
		              openFileOutput("prueba_int.txt", Context.MODE_PRIVATE));
		   
		      fout.write(texto);
		      fout.close();
		  }
		 catch (Exception ex)
		 {
		     Log.e("Ficheros", "Error al escribir fichero a memoria interna");
		 }
	}
	
	public String read(){
		String texto ="vacio";
		try
		{
		    BufferedReader fin =
		        new BufferedReader(
		            new InputStreamReader(
		                openFileInput("prueba_int.txt")));
		 

		    texto = fin.readLine();
		    fin.close();
		}
		catch (Exception ex)
		{
		    Log.e("Ficheros", "Error al leer fichero desde memoria interna");
		}
		return texto;
	}
	private void tareaLarga()
	{
	    try {
	        Thread.sleep(1000);
	    } catch(InterruptedException e) {}
	}
	
}