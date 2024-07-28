#version 300 es
precision mediump float;

uniform sampler2D unfiltered;
uniform vec2 resolution;

uniform vec2 windowsLeftBottom;

uniform vec2 lightPos;
uniform vec2 lightDir;
uniform float lightPower;

out vec4 outColor;

//const int layAmount = 50;

void main(void) {
    vec2 pixel = gl_FragCoord.xy;
    pixel.y *= -1.0;
    vec2 worldPixel = windowsLeftBottom + pixel;

    vec2 dirToLight = lightPos - worldPixel;
    float distToLight = length(dirToLight);
    float dirProduct = pow(max(dot(-dirToLight / distToLight, lightDir), 0.0), 4.0);

    float lightAmount = lightPower * 200.0 / max(pow(distToLight, 2.0), 0.001);

    vec4 mainColor = texture(unfiltered, pixel / resolution);
    outColor = vec4(lightAmount * dirProduct);
}