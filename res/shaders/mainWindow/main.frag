#version 300 es
precision mediump float;

uniform vec2 resolution;
uniform float time;

layout (location = 0) out vec4 outColor;

#include "../Utils.glsl"

float shakeTimer(float t) {
    float down = 1.0 - pow(min(1.0, mod(t / 5.0, 10.0)), 5.0);
    float up = pow(mod(0.5 * t, 25.0), 0.2);
    return min(down, up);
}

float shake(float t) {
    float n = pow(sin(9.12 * t), 2.0) * pow(cos(10.52 * t), 5.0) + sin(5.85 * t);
    return n * shakeTimer(t);
    //    return 0.0;
}

float shakeNoise(vec2 p, float t) {
    t += 1.0;
    float shakeTimer = 1.0 - pow(shakeTimer(t), 10.0) * 0.5;
    return mix(noise(p), noise(p / shakeTimer), smoothFunc(shakeTimer));
}

//const vec3 BorderColor = hsv2rgb(vec3(0.4, 0.6, 0.5));

void main() {
    vec2 pixel = vec2(gl_FragCoord.xy);
    vec2 uv = pixel / resolution;

    outColor = vec4(1.0);

    float t = time * 10.0;

    vec2 centerized = pixel - resolution / 2.0;

    // Border and shake
    float hue = pow(noise(uv * 100.0), 2.0) * 0.001;
    outColor.rgb *= hsv2rgb(vec3(hue, 1.0, 1.0));;
    vec2 shakedPos = centerized + 5.0 * vec2(shake(t), shake(t + 1.22));
    float padding = 100.0;
    vec2 size = (resolution / 2.0 - padding) - 50.0;
    float dist = sdfRoundedBox(shakedPos + shakeNoise(shakedPos, t), size, padding);
    float absDist = abs(dist);
    if (dist < 0.0 && dist > -50.0) {
        dist /= -50.0;
        outColor *= 1.0 - pow(dist, 2.0);
    } else if (dist < 0.0) {
        outColor *= 0.0;
        //        outColor = vec4(0.0, 1.0, 0.0, 1.0);
    }

    // outColor *= smoothFunc(-200.0, ,dist) dist / (resolution.x / 2.0);

    if (outColor.a == 0.0) {
        outColor *= 0.0;
    }
}