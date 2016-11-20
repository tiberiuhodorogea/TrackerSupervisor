package com.example.tiber.trackersupervisor.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tiber.trackersupervisor.Clase.AsyncRequests.MyAsyncTask;
import com.example.tiber.trackersupervisor.Clase.ServerConnection;
import com.example.tiber.trackersupervisor.R;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.Exceptions.KeyNotMappedException;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.RequestedAction;
import com.example.tiber.trackersupervisor.SharedClasses.Communication.ResponseEnum;
import com.example.tiber.trackersupervisor.SharedClasses.Objects.Client;

public class AddClientActivity extends AppCompatActivity {

    EditText etClientName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_client);
        etClientName = (EditText) findViewById(R.id.etClientName);

    }


  public void onAddClientClick(View v){
      String clientName = etClientName.getText().toString();
      if(clientName.equals("") ||
              clientName.contains(" ") ||
              clientName.contains("\\") ||
              clientName.contains("\"") ||
              clientName.contains("\'")){
          Toast.makeText(this,"Invalid name",Toast.LENGTH_SHORT).show();
      }
       {//valid
           Client client = new Client(-1,clientName,getResources().getInteger(R.integer.SUPERVISOR_ID));
        new AddClientAsync(this,client).execute();

      }
  }

    class AddClientAsync extends MyAsyncTask {
        private Client client;

        public AddClientAsync(Context context, Client client) {
            super(context);
            this.client = client;
        }

        ResponseEnum response;
        @Override
        protected Void doInBackground(Void... params) {
            response = null;
            ServerConnection<Client,ResponseEnum> connection =
                    new ServerConnection<Client,ResponseEnum>(context);

            try {
                response = connection.execute(RequestedAction.ADD_CLIENT,client);
            } catch (KeyNotMappedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if( response == null ){
                Toast.makeText(context,"Some problems..",Toast.LENGTH_SHORT).show();
            }
            else if ( response == ResponseEnum.DUPLICATE_CLIENT_NAME ){
                Toast.makeText(context,client.getName() + " already existant for current user( maybe it's de-activated?... )", Toast.LENGTH_LONG).show();
            }
            else if(response == ResponseEnum.OK ){
                Toast.makeText(context,"Client added",Toast.LENGTH_SHORT).show();
                ((AppCompatActivity)context).setResult(MainActivity.REFRESH_RESULT_CODE);
                ((AppCompatActivity)context).finish();
            }
            else {
                Toast.makeText(context,"Some problems..",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
