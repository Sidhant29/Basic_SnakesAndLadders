package com.example.sidhant.sl;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameScreenMain extends AppCompatActivity implements SensorEventListener{
    ArrayList<Integer> players;
    TextView tvPlayer1,tvPlayer2,tvPlayer3,tvPlayer4,tvDiaplay,tvRoll;
    Sensor accelSensor;
    SensorManager sensorManager;
    float x1;
    float y1;
    float z1;

    public static final String TAG="Check";
    Integer playerCount;
    Integer playerStatus=0;
    Boolean gameIsOn;

    Integer diceVal=-1;
    Integer playerTurn;
    int[][] board = new int[][]{
            { 3, 8, 28, 58, 75, 90, 80 },
            { 21, 30, 84, 77, 86, 91, 100 },
            { 17, 52, 95, 88, 57, 97, 95 },
            { 13, 29, 70, 18, 40, 79, 51 }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen_main);
        Intent i = getIntent();

        playerCount = i.getIntExtra("PlayerCount",2);
        players = new ArrayList<Integer>(4);

        tvRoll= (TextView) findViewById(R.id.tvRoll);
        tvDiaplay= (TextView) findViewById(R.id.tvDisplay);
        tvPlayer1= (TextView) findViewById(R.id.tvPlayer1);
        tvPlayer2= (TextView) findViewById(R.id.tvPlayer2);
        tvPlayer3= (TextView) findViewById(R.id.tvPlayer3);
        tvPlayer4= (TextView) findViewById(R.id.tvPlayer4);

        Log.d(TAG, "onCreate: ");
        players.add(-1);
        players.add(-1);
        players.add(-1);
        players.add(-1);
        gameIsOn = true;
        playerTurn=0;
        tvDiaplay.setText(String.valueOf("Player " + (playerTurn+1) + " Roll the Dice " ));

        x1=y1=z1=0;

        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(GameScreenMain.this,accelSensor,1000*700);
    }

    void updatePlayerUI(){
        Log.d(TAG, "updatePlayerUI: ");

        switch(playerTurn){
            case 0:tvPlayer1.setText("Player 1 position: "+ players.get(playerTurn).toString());
                if(playerStatus==1) {
                    tvPlayer1.append(" You Climbed a Ladder!");
                }
                else if(playerStatus==-1){
                    tvPlayer1.append(" Snake Bit You!");
                }
                break;
            case 1:tvPlayer2.setText("Player 2 position: "+ players.get(playerTurn).toString());
                if(playerStatus==1) {
                    tvPlayer2.append(" You Climbed a Ladder!");
                }
                else if(playerStatus==-1){
                    tvPlayer2.append(" Snake Bit You!");
                }
                break;
            case 2:tvPlayer3.setText("Player 3 position: "+ players.get(playerTurn).toString());
                if(playerStatus==1) {
                    tvPlayer3.append(" You Climbed a Ladder!");
                }
                else if(playerStatus==-1){
                    tvPlayer3.append(" Snake Bit You!");
                }
                break;
            case 3:tvPlayer4.setText("Player 4 position: "+ players.get(playerTurn).toString());
                if(playerStatus==1) {
                    tvPlayer4.append(" You Climbed a Ladder!");
                }
                else if(playerStatus==-1){
                    tvPlayer4.append(" Snake Bit You!");
                }
                break;
        }
        playerStatus=0;
    }
 /*   void playGame()  {

            Log.d(TAG, "playGame3: ");
            for(Integer i:players){
                Log.d(TAG, "playGame1: ");
                tvDiaplay.setText(String.valueOf("Player "+(playerTurn+1)+" Roll the Dice " ));
                diceVal=-1;

                if(gameIsOn==false){

                    tvDiaplay.setText("Player "+(1)+" WINS");
                }

            }

    }*/

    void updatePosition() {
        Log.d(TAG, "updatePosition: ");
        if (players.get(playerTurn) == -1) {
            if (diceVal == 1 || diceVal == 6) {
                players.set(playerTurn, 1);
                return;
            }
        } else if ((players.get(playerTurn) + diceVal) > 100) {
            return;
        } else {
            int pos = players.get(playerTurn) + diceVal;
            for (int i = 0; i < 7; i++) {
                if (pos == board[0][i]) {
                    playerStatus=1;
                    Log.d(TAG, "updatePosition: Ladder");
                    pos = board[1][i];
                }
            }
            for (int i = 0; i < 7; i++) {
                if (pos == board[2][i]) {
                    playerStatus=-1;
                    pos = board[3][i];
                }

            }
            players.set(playerTurn, pos);
            if (pos == 100) {
                gameIsOn = false;
            }

        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            Random r = new Random();
            Float x, y, z;

            x = sensorEvent.values[0];
            y = sensorEvent.values[1];
            z = sensorEvent.values[2];

            if (x1 == 0 && y1 == 0 && z1 == 0) {

                x1 = x;
                y1 = y;
                z1 = z;

            } else {

                float diffX = x1 - x;
                float diffY = y1 - y;
                float diffZ = z1 - z;

                if (diffX < (float) 5.0) {
                    diffX = (float) 0.0;
                }
                if (diffY < (float) 5.0) {
                    diffY = (float) 0.0;
                }
                if (diffZ < (float) 5.0) {
                    diffZ = (float) 0.0;
                }

                x1 = x;
                y1 = y;
                z1 = z;

                if (diffX > diffY) {

                    if(gameIsOn) {
                        Log.d(TAG, "onClick: ");
                        diceVal = r.nextInt(6) + 1;

                        updatePosition();
                        updatePlayerUI();
                        tvRoll.setText("You rolled a "+diceVal.toString());
                    }
                    if(!gameIsOn){
                        tvDiaplay.setText("Player " + (String.valueOf(playerTurn+1)) + " WINS");
                        return;
                    }
                    playerTurn++;
                    if(playerTurn>playerCount-1){
                        playerTurn=0;
                    }
                    tvDiaplay.setText(String.valueOf("Player "+(playerTurn+1)+" Roll the Dice " ));


                }

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
