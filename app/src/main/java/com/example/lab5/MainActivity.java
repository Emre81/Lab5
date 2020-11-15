package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    static final String PLAYER_1 = "X";
    static final String PLAYER_2 = "O";
    boolean player1Turn = true;
    byte[][] board = new byte[3][3];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TableLayout table = findViewById(R.id.board);
        for (int i = 0; i<3; i++){
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j<3; j++){
                Button btn = (Button) row.getChildAt(j);
                btn.setOnClickListener(new CellListener(i, j));
            }
        }



    }

    class CellListener implements View.OnClickListener{
        int row, col;

        public CellListener(int row, int col){
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View v) {
            if (!isValidMove(row, col)){
                Toast.makeText(MainActivity.this, "Cell is already occupied", Toast.LENGTH_LONG).show();

                return;
            }

            if (player1Turn){
                ((Button)v).setText(PLAYER_1);
                board[row][col] = 1;
            }
            else{
                ((Button)v).setText(PLAYER_2);
                board[row][col] = 2;
            }

            if(gameEnded(row, col) == -1){
                player1Turn = !player1Turn;
            }else if (gameEnded(row, col) == 0){
                Toast.makeText(MainActivity.this, "It is a Draw",
                        Toast.LENGTH_LONG).show();
            } else if (gameEnded(row, col) == 1){
            Toast.makeText(MainActivity.this, "Player 1 Wins",
                    Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(MainActivity.this, "Player 2 Wins",
                        Toast.LENGTH_LONG).show();
            }
        }

        public boolean isValidMove(int row, int col){
            return (board[row][col] == 0);
        }

        public int gameEnded(int row, int col){
            int symbol = board[row][col];
            boolean win = true;
            for (int i = 0; i<3; i++){
                if (board[i][col] != symbol){
                    win = false;
                    break;
                }
            }
            if (win){
                return symbol;
            }


            return -1;
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean("player1Turn", player1Turn);
        byte[] boardSingle = new byte[9];
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                boardSingle[3*i+j] =board[i][j];
            }
        }
        outState.putByteArray("board", boardSingle);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        player1Turn = savedInstanceState.getBoolean("player1Turn");
        byte[] boardSingle = savedInstanceState.getByteArray("board");
        for (int i=0; i<9; i++){
            board[i / 3][i % 3] = boardSingle[i];

        }
        TableLayout table = findViewById(R.id.board);
        for (int i = 0; i<3; i++){
            TableRow row = (TableRow) table.getChildAt(i);
            for (int j = 0; j<3; j++){
                Button btn = (Button) row.getChildAt(j);
                if (board[i][j]==1){
                    btn.setText("X");
                }
                else if (board[i][j]==2){
                    btn.setText("O");
                }
            }
        }
    }
}