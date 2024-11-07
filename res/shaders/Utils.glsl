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

float noise(vec2 p) {
    p /= 50.0;
    p.x += time * 1.52;
    p.y += time * 1.01;
    float n = sin(1.02 * p.x) + cos(1.07 * p.y);
    float norm = n / 2.0;
    float neg = norm * 0.5 - 0.5;
    return neg * 25.0;
}

vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}