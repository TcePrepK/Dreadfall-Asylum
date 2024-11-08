package core;

import shaderUtils.ImportChain;
import shaderUtils.LineData;
import shaderUtils.UniformLoader;
import shaderUtils.UniformVariable;
import toolbox.CustomError;
import toolbox.VariableWatcher;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.lwjgl.opengl.GL45.*;

public abstract class BaseShader {
    private static final String FILE_PREFIX = "../res/shaders/";
    private static final Pattern INCLUDE_REGEX = Pattern.compile("^#include\\s+\"([^>]+)\"");
    private static final Pattern TOGGLE_IGNORE_REGEX = Pattern.compile("^/\\*\\s*toggle-import\\s*\\*/");
    private static final Pattern UNIFORM_REGEX = Pattern.compile("^uniform\\s+(\\w+)\\s+(\\w+)\\s*;");

    protected int programID;
    private boolean programEnabled = false;

    private final ArrayList<String> uniformNames = new ArrayList<>();
    private final HashMap<String, UniformVariable> allUniforms = new HashMap<>();
    private final HashMap<String, Object> uniformQueue = new HashMap<>();

    private UniformLoader uniformLoader;

    protected void initialize() {
        programID = glCreateProgram();

        readShaderFiles();
        attachShaders();

        glLinkProgram(programID);
        glValidateProgram(programID);

        final int linkStatus = glGetProgrami(programID, GL_LINK_STATUS);
        if (linkStatus == GL_FALSE) {
            System.err.println("Shader program linking failed");
            System.err.println(glGetProgramInfoLog(programID));
        }

        uniformLoader = new UniformLoader(programID);
        initializeUniforms(uniformNames);
    }

    protected abstract void readShaderFiles();
    protected abstract void attachShaders();

    public void start() {
        glUseProgram(programID);

        programEnabled = true;
        handleUniformQueue();
    }

    public void stop() {
        glUseProgram(0);

        programEnabled = false;
    }

    public void bindTexture(final String id, final int pos) {
        glUniform1i(glGetUniformLocation(programID, id), pos);
    }

    protected <T> void loadUniform(final String uniformName, final T data) {
        if (programEnabled) {
            uniformLoader.loadUniform(uniformName, data);
            return;
        }

        if (uniformQueue.get(uniformName) != null) {
            uniformQueue.replace(uniformName, data);
            return;
        }

        uniformQueue.put(uniformName, data);
    }

    public <T> void attachWatcher(final String uniformName, final VariableWatcher<T> watcher) {
        watcher.addOnChange((data) -> loadUniform(uniformName, data));
        loadUniform(uniformName, watcher.get());
    }

    private void handleUniformQueue() {
        if (!programEnabled) return;

        for (final String uniformName : uniformQueue.keySet()) {
            final Object data = uniformQueue.get(uniformName);
            uniformLoader.loadUniform(uniformName, data);
        }

        uniformQueue.clear();
    }

    private void initializeUniforms(final ArrayList<String> uniforms) {
        for (final String uniformName : uniforms) {
            final UniformVariable<?> uniform = uniformLoader.registerUniform(uniformName);
            allUniforms.put(uniformName, uniform);
        }
    }

    public abstract void cleanUp();

    int loadShaderFromFile(final int type, final String file) {
        System.out.print(" ↳ Loading ");
        switch (type) {
            case GL_VERTEX_SHADER:
                System.out.print("Vertex");
                break;
            case GL_FRAGMENT_SHADER:
                System.out.print("Fragment");
                break;
            case GL_GEOMETRY_SHADER:
                System.out.print("Geometry");
                break;
            case GL_COMPUTE_SHADER:
                System.out.print("Compute");
                break;
            default:
                System.out.print("Unknown");

        }
        System.out.print(" Shader: " + file);

//        System.out.println(" ↳ Loading shader: " + file);
        final int shaderID = glCreateShader(type);

        final HashSet<String> includeList = new HashSet<>();
        includeList.add(file);

        final HashMap<String, Integer> linesPerPath = new HashMap<>();
        linesPerPath.put(file, 0);

        final ArrayList<LineData> lineData = new ArrayList<>();
        lineData.add(new LineData(file, 0, ""));

        final ImportChain importChain = new ImportChain(file, new ArrayList<>());
        final StringBuilder data = readShaderImports(file, includeList, importChain, uniformNames, linesPerPath, lineData);

        glShaderSource(shaderID, data);
        glCompileShader(shaderID);

        final int compileStatus = glGetShaderi(shaderID, GL_COMPILE_STATUS);
        if (compileStatus == GL_FALSE) {
            System.out.println("❌");
            final String info = glGetShaderInfoLog(shaderID, 1024);
            glDeleteShader(shaderID);

            System.out.println("❌ Shader initialization failed! ❌");
            System.out.println();
            if (info.isEmpty()) return shaderID;

            final String[] lines = info.split("\n");
            String prevFile = "";
            for (final String line : lines) {
                final boolean errorMessage = line.startsWith("ERROR: ");
                if (!errorMessage) continue;
                final String[] parts = line.split("\\s*:\\s*");
                final int lineNumber = Integer.parseInt(parts[2]);
                final String fileName = lineData.get(lineNumber).file();
                final int actualLineNumber = lineData.get(lineNumber).line();

                String output = "(" + actualLineNumber + ") > " + parts[4] + "(" + parts[3].replace("'", "") + ")";
                if (!fileName.equals(prevFile)) {
                    output = "<" + fileName + ">\n" + output;
                    prevFile = fileName;
                }

                System.err.println(output);
            }

            throw new CustomError("Shader Initialization Error");
        } else {
            System.out.println("✅");
        }

        return shaderID;
    }

    private static StringBuilder readShaderImports(final String path, final HashSet<String> imports, final ImportChain importChain, final ArrayList<String> uniforms, final HashMap<String, Integer> linesPerPath, final ArrayList<LineData> lineData) {
        try {
            final BufferedReader reader = new BufferedReader(new FileReader("res/shaders/" + path));
            final StringBuilder total = new StringBuilder();

            boolean ignoreIncludes = false;
            String line;
            while ((line = reader.readLine()) != null) {
                final int lineAmount = linesPerPath.get(path) + 1;
                linesPerPath.put(path, lineAmount);

                final Matcher uniformMatch = UNIFORM_REGEX.matcher(line);
                final Matcher includeMatch = INCLUDE_REGEX.matcher(line);
                final Matcher toggleMatch = TOGGLE_IGNORE_REGEX.matcher(line);
                if (includeMatch.find()) {
                    final String importName = includeMatch.group(1);
                    final String importPath = Path.of(path, "..", importName).toString();

                    final ImportChain newChain = new ImportChain(importPath, new ArrayList<>());
                    importChain.imports().add(newChain);
                    if (imports.contains(importPath)) {
                        throw new IOException("Circular Dependency!!!");
                    }

                    linesPerPath.put(importPath, 0);
                    total.append(readShaderImports(importPath, imports, newChain, uniforms, linesPerPath, lineData));
                    continue;
                } else if (toggleMatch.find()) {
                    ignoreIncludes = !ignoreIncludes;
                } else if (uniformMatch.find()) {
                    // TODO: Put these to uniform list!!!
                    final String uniformType = uniformMatch.group(1);
                    final String uniformName = uniformMatch.group(2);
                    uniforms.add(uniformName);
                }

                total.append(line).append("\n");
                lineData.add(new LineData(path, lineAmount, line));
            }

            reader.close();
            return total;
        } catch (final IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return null;
        }
    }
}
