package com.iqiyi.yangdaokuan.opengl.objects;

import android.graphics.Color;

import com.iqiyi.yangdaokuan.opengl.data.VertexArray;
import com.iqiyi.yangdaokuan.opengl.program.ParticleShaderProgram;

import static com.iqiyi.yangdaokuan.opengl.util.Geometry.Point;
import static com.iqiyi.yangdaokuan.opengl.util.Geometry.Vector;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glDrawArrays;
import static com.iqiyi.yangdaokuan.opengl.Constants.BYTES_PER_FLOAT;

public class ParticleSystem {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int VECTOR_COMPONENT_COUNT = 3;
    private static final int PARTICLE_START_TIME_COMPONENT_COUNT = 1;
    private static final int TOTLE_COMPONENT_COUNT = POSITION_COMPONENT_COUNT
            + COLOR_COMPONENT_COUNT
            + VECTOR_COMPONENT_COUNT
            + PARTICLE_START_TIME_COMPONENT_COUNT;
    private static final int STRIDE = TOTLE_COMPONENT_COUNT * BYTES_PER_FLOAT;

    private final float[] particles;
    private final VertexArray vertexArray;
    private final int maxParticleCount;

    private int currentParticleCount;
    private int nextParticle;

    public ParticleSystem(int maxParticleCount) {
        particles = new float[maxParticleCount * TOTLE_COMPONENT_COUNT];
        vertexArray = new VertexArray(particles);
        this.maxParticleCount = maxParticleCount;
    }

    public void addParticel(Point position, int color, Vector direction, float particleStartTime) {
        final int particleOffset = nextParticle * TOTLE_COMPONENT_COUNT;
        int currentOffset = particleOffset;
        nextParticle++;
        if (currentParticleCount < maxParticleCount) {
            currentParticleCount++;
        }
        if (nextParticle == maxParticleCount) {
            nextParticle = 0;
        }
        particles[currentOffset++] = position.x;
        particles[currentOffset++] = position.y;
        particles[currentOffset++] = position.z;

        particles[currentOffset++] = Color.red(color) / 255f;
        particles[currentOffset++] = Color.green(color) / 255f;
        particles[currentOffset++] = Color.blue(color) / 255f;

        particles[currentOffset++] = direction.x;
        particles[currentOffset++] = direction.y;
        particles[currentOffset++] = direction.z;

        particles[currentOffset++] = particleStartTime;

        vertexArray.updateBuffer(particles, particleOffset, TOTLE_COMPONENT_COUNT);
    }

    public void bindData(ParticleShaderProgram particleShaderProgram) {
        int dataOffset = 0;
        vertexArray.setVertexAttribPointer(dataOffset, particleShaderProgram.getPositionLocation(),
                POSITION_COMPONENT_COUNT, STRIDE);
        dataOffset += POSITION_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset, particleShaderProgram.getColorLocation(),
                COLOR_COMPONENT_COUNT, STRIDE);
        dataOffset += COLOR_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset, particleShaderProgram.getDirectionVectorLocation(),
                VECTOR_COMPONENT_COUNT, STRIDE);
        dataOffset += VECTOR_COMPONENT_COUNT;
        vertexArray.setVertexAttribPointer(dataOffset, particleShaderProgram.getParticleStartTimeLocation(),
                PARTICLE_START_TIME_COMPONENT_COUNT, STRIDE);
    }

    public void draw() {
        glDrawArrays(GL_POINTS, 0, currentParticleCount);
    }
}
