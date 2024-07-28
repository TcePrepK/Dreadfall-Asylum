#version 300 es

uniform vec2 resolution;

layout (location = 0) out vec4 outColor;

void main(void) {
    vec2 pixel = vec2(gl_FragCoord.xy);
    vec2 uv = pixel / resolution;

    outColor = vec4(1.0, 0.0, 0.0, 1.0);

    // Corners
    vec2 dist = resolution / 2.0 - abs(pixel - resolution / 2.0);
    float radius = 10.0;
    if (dist.x < radius && dist.y < radius && length(radius - dist) > radius) {
        outColor *= 0.0;
    }
}