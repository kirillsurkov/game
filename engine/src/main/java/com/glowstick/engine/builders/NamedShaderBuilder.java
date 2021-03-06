package com.glowstick.engine.builders;

import com.glowstick.engine.extension.Named;
import com.glowstick.engine.graphics.Shader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public abstract class NamedShaderBuilder<T extends Shader> implements Named {
    @Autowired
    private ResourceLoader resourceLoader;

    abstract protected T build(int program);

    private String loadSource(String shaderName, String fileName) throws IOException {
        Resource res = resourceLoader.getResource("classpath:shaders/" + shaderName + "/" + fileName);
        InputStreamReader streamReader = new InputStreamReader(res.getInputStream());
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        return String.join("\n", bufferedReader.lines().collect(Collectors.toList()));
    }

    public T build(int width, int height) throws IOException {
        String vShaderSrc = loadSource(this.getName(), "vertex.glsl");
        int vShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vShader, vShaderSrc);
        glCompileShader(vShader);

        String fShaderSrc = loadSource(this.getName(), "fragment.glsl");
        int fShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fShader, fShaderSrc);
        glCompileShader(fShader);

        int program = glCreateProgram();
        glAttachShader(program, vShader);
        glAttachShader(program, fShader);
        glLinkProgram(program);

        System.out.println(glGetShaderInfoLog(fShader));
        System.out.println(glGetShaderInfoLog(vShader));

        T shader = this.build(program);
        shader.setWidth(width);
        shader.setHeight(height);

        return shader;
    }
}
