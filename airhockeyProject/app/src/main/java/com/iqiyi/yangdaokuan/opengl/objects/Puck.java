package com.iqiyi.yangdaokuan.opengl.objects;

import com.iqiyi.yangdaokuan.opengl.data.VertexArray;
import com.iqiyi.yangdaokuan.opengl.program.ColorShaderProgram;
import com.iqiyi.yangdaokuan.opengl.util.Geometry;

import java.util.List;

public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;
    public final float radius, height;
    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public Puck(float radius, float height, int numPointsArroundPuck) {
        ObjectBuilder.GeneratedData generatedData = ObjectBuilder.createPuck(
                new Geometry.Cylinder(new Geometry.Point(0, 0, 0),
                        radius, height), numPointsArroundPuck);
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
