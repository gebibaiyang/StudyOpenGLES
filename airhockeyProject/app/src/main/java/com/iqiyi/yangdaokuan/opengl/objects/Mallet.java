package com.iqiyi.yangdaokuan.opengl.objects;

import com.iqiyi.yangdaokuan.opengl.Constants;
import com.iqiyi.yangdaokuan.opengl.data.VertexArray;
import com.iqiyi.yangdaokuan.opengl.program.ColorShaderProgram;
import com.iqiyi.yangdaokuan.opengl.util.Geometry;

import java.util.List;

import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.glDrawArrays;

public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 3;
    public final float radius;
    public final float height;
    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public Mallet(float radius, float height, int numPointsArroundMallet) {
        ObjectBuilder.GeneratedData generatedData = ObjectBuilder.createMallet(
                new Geometry.Point(0, 0, 0),
                radius, height, numPointsArroundMallet);
        this.radius = radius;
        this.height = height;
        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }

    public void bindData(ColorShaderProgram colorShaderProgram) {
        vertexArray.setVertexAttribPointer(0, colorShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }

    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}
