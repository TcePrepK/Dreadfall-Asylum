#version 300 es
precision mediump float;

uniform vec2 resolution;
uniform float time;

uniform float test;

layout (location = 0) out vec4 outColor;

float smoothFunc(float s) {
    return 3.0 * s * s - 2.0 * s * s * s;
}

float smoothFunc(float a, float b, float x) {
    float lambda = min(1.0, max(0.0, (x - a) / (b - a)));
    return smoothFunc(lambda);
}

float sdfBox(vec2 p, vec2 s) {
    vec2 d = abs(p) - s;
    return length(max(d, 0.0)) + min(max(d.x, d.y), 0.0);
}

float sdfRoundedBox(vec2 p, vec2 s, float r) {
    return sdfBox(p, s) - r;
}

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

float noise(vec2 p) {
    p /= 50.0;
    p.x += time * 1.52;
    p.y += time * 1.01;
    float n = sin(1.02 * p.x) + cos(1.07 * p.y);
    float norm = n / 2.0;
    float neg = norm * 0.5 - 0.5;
    return neg * 25.0;
}

float shakeNoise(vec2 p, float t) {
    t += 1.0;
    float shakeTimer = 1.0 - pow(shakeTimer(t), 10.0) * 0.5;
    return mix(noise(p), noise(p / shakeTimer), smoothFunc(shakeTimer));
}

const vec3 BorderColor = vec3(1.0, 0.2, 0.5);

void main() {
    vec2 pixel = vec2(gl_FragCoord.xy);
    vec2 uv = pixel / resolution;

    outColor = vec4(1.0);

    float t = time * 10.0;

    vec2 centerized = pixel - resolution / 2.0;

    // Border and shake
    outColor.rgb *= BorderColor;
    vec2 shakedPos = centerized + 5.0 * vec2(shake(t), shake(t + 1.22));
    float padding = 100.0;
    vec2 size = (resolution / 2.0 - padding) - 50.0;
    float dist = sdfRoundedBox(shakedPos + shakeNoise(shakedPos, t), size, padding);
    float absDist = abs(dist);
    if (dist < 0.0 && dist > -25.0) {
        dist /= -25.0;
        float fixedDist = pow(dist, 0.5);

        outColor *= max(0.0, 1.0 - fixedDist);
    } else if (dist < 0.0) {
        outColor *= 0.0;
        //        outColor = vec4(0.0, 1.0, 0.0, 1.0);
    }

    // Black border
    //    {
    //        float padding = 75.0;
    //        float dist = sdfBox(centerized, resolution / 2.0 - padding) - 25.0;
    //        if (dist > 0.0) {
    //            outColor = vec4(vec3(1.0), 1.0 - dist / 10.0);
    //        }
    //    }

    //    outColor *= smoothFunc(-200.0, ,dist) dist / (resolution.x / 2.0);

    //    if (outColor.a == 0.0) {
    //        outColor *= 0.0;
    //    }
}