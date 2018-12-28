package com.iqiyi.yangdaokuan.opengl;

import android.content.Context;
import android.graphics.Color;
import android.opengl.GLSurfaceView;

import com.iqiyi.yangdaokuan.opengl.objects.ParticleShooter;
import com.iqiyi.yangdaokuan.opengl.objects.ParticleSystem;
import com.iqiyi.yangdaokuan.opengl.program.ParticleShaderProgram;
import com.iqiyi.yangdaokuan.opengl.util.MatrixHelper;
import com.iqiyi.yangdaokuan.opengl.util.TextureHelper;

import static android.opengl.GLES20.GL_BLEND;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_ONE;
import static android.opengl.GLES20.glBlendFunc;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glEnable;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;
import static com.iqiyi.yangdaokuan.opengl.util.Geometry.Point;
import static com.iqiyi.yangdaokuan.opengl.util.Geometry.Vector;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.glClearColor;

public class ParticlesRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private float[] viewProjectMatrix = new float[16];

    private ParticleShaderProgram particleShaderProgram;
    private ParticleSystem particleSystem;
    private ParticleShooter redParticleShooter;
    private ParticleShooter greenParticleShooter;
    private ParticleShooter blueParticleShooter;
    private long globalStarTime;

    private float angleVarianceInDegrees = 5f;
    private float speedVariance = 1f;
    private int texture;

    public ParticlesRenderer(Context ct) {
        context = ct;
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        glClearColor(0, 0, 0, 0);

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE);

        particleShaderProgram = new ParticleShaderProgram(context);
        particleSystem = new ParticleSystem(10000);
        globalStarTime = System.nanoTime();

        final Vector particleDirection = new Vector(0, 0.5f, 0);
        redParticleShooter = new ParticleShooter(new Point(-1, 0, 0),
                particleDirection,
                Color.rgb(255, 50, 5),
                angleVarianceInDegrees, speedVariance);
        greenParticleShooter = new ParticleShooter(new Point(0, 0, 0),
                particleDirection,
                Color.rgb(25, 255, 25),
                angleVarianceInDegrees, speedVariance);
        blueParticleShooter = new ParticleShooter(new Point(1, 0, 0),
                particleDirection,
                Color.rgb(5, 50, 255),
                angleVarianceInDegrees, speedVariance);

        texture = TextureHelper.loadTexture(context, R.drawable.particle_texture);
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        glViewport(0, 0, i, i1);
        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) i / (float) i1, 1f, 10);
        setIdentityM(viewMatrix, 0);
        translateM(viewMatrix, 0, 0, -1.5f, -5f);
        multiplyMM(viewProjectMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        glClear(GL_COLOR_BUFFER_BIT);
        float currentTime = (System.nanoTime() - globalStarTime) / 1000000000f;
        redParticleShooter.addParticles(particleSystem, currentTime, 5);
        greenParticleShooter.addParticles(particleSystem, currentTime, 5);
        blueParticleShooter.addParticles(particleSystem, currentTime, 5);

        particleShaderProgram.useProgram();
        particleShaderProgram.setUniform(viewProjectMatrix, currentTime, texture);
        particleSystem.bindData(particleShaderProgram);
        particleSystem.draw();
    }
}
