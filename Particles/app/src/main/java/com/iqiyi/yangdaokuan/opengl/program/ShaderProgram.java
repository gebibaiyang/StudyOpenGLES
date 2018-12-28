package com.iqiyi.yangdaokuan.opengl.program;

import android.content.Context;

import com.iqiyi.yangdaokuan.opengl.util.ShaderHelper;
import com.iqiyi.yangdaokuan.opengl.util.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;

public class ShaderProgram {
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_TIME = "u_Time";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String U_COLOR = "u_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    protected static final String A_DIRECTION_VECTOR = "a_DirectionVector";
    protected static final String A_PARTICLE_START_TIME = "a_ParticleStarTime";

    protected final int program;

    protected ShaderProgram(Context context, int vertexShaderResourcesID, int fragmentShaderResourcesId) {
        program = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFormResource(context, vertexShaderResourcesID),
                TextResourceReader.readTextFileFormResource(context, fragmentShaderResourcesId)
        );
    }

    public void useProgram() {
        glUseProgram(program);
    }
}
