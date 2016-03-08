package br.edu.ifpb.edittextlistenerapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import br.edu.ifpb.edittextlistenerapp.R;

public class SplashActivity extends Activity implements Runnable {

    ImageView imageView;

    // Define o tempo de carregamento da SplashActivity.
    private static final long SPLASH_TIME_OUT = 6000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(this, SPLASH_TIME_OUT);

        ImageView image = (ImageView)findViewById(R.id.imageView);

     	// Importa o RotateAnimation para implementar na imagem no xml.
        RotateAnimation rotate = new RotateAnimation(0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);

        rotate.setDuration(4000);
        rotate.setRepeatCount(Animation.INFINITE);
        image.setAnimation(rotate);
    }

 	// Método responsável para chamar a próxima activity.
    @Override
    public void run() {
        Intent intent = new Intent(this, BuscarNomeActivity.class);
        startActivity(intent);
        finish();
    }
}