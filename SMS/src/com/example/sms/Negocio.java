package com.example.sms;

import android.telephony.SmsManager;

public class Negocio {
public void primerMensaje(){
	sendSMS("35399", "TESTDR");
}





private void sendSMS(String Telefono, String Mensaje){
	SmsManager sms = SmsManager.getDefault();
	sms.sendTextMessage(Telefono, null, Mensaje, null, null);
	
}
}
