package com.iqiyi.yangdaokuan.opengl.objects;

import com.iqiyi.yangdaokuan.opengl.data.VertexArray;
import com.iqiyi.yangdaokuan.opengl.program.SkyBoxShaderProgram;

import java.nio.ByteBuffer;

import static android.opengl.GLES10.GL_TRIANGLES;
import static android.opengl.GLES20.GL_UNSIGNED_BYTE;
import static android.opengl.GLES20.glDrawElements;

public class SkyBox {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private final VertexArray vertexArray;
    private final ByteBuffer indexArray;

    public SkyBox() {
        vertexArray = new VertexArray(new float[]{
                -1, 1, 1,
                1, 1, 1,
                -1, -1, 1,
                1, -1, 1,
                -1, 1, -1,
                1, 1, -1,
                -1, -1, -1,
                1, -1, -1,
        });
        indexArray = ByteBuffer.allocate(6 * 6).put(new byte[]{
                //front
                1, 3, 0,
                0, 3, 2,

                //back
                4, 5, 6,
                5, 6, 7,

                //left
                0, 2, 4,
                4, 2, 6,

                // right
                5, 7, 1,
                1, 7, 3,

                //top
                5, 1, 4,
                4, 1, 0,

                // bottom
                6, 2, 7,
                7, 2, 3
        });
        indexArray.position(0);
    }

    public void bindData(SkyBoxShaderProgram skyBoxShaderProgram) {
        vertexArray.setVertexAttribPointer(0, skyBoxShaderProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }

    public void draw() {
        glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_BYTE, indexArray);
    }
}
