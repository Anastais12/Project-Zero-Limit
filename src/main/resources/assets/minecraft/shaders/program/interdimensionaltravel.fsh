#version 120

uniform sampler2D DiffuseSampler;
uniform float Strength;
uniform vec2 OutSize;

// Target color in linear space (approximation of #dd85ff)
const vec3 targetColor = vec3(221.0, 133.0, 255.0) / 255.0;
const float threshold = 0.15; // Tolerance for similarity

vec2 warp(vec2 uv) {
    uv += sin(uv.yx * 40.0) * 0.001 * Strength;
    uv += cos(uv.yx * 25.0 + uv.xx * 10.0) * 0.01;
    return uv;
}

void main() {
    vec2 uv = gl_FragCoord.xy / OutSize;

    // Sample original color
    vec3 originalColor = texture2D(DiffuseSampler, uv).rgb;

    // Compare with target color
    float diff = distance(originalColor, targetColor);

    // If similar to target, use original color
    if (diff < threshold) {
        gl_FragColor = vec4(originalColor, 1.0);
        return;
    }

    // Otherwise apply warp effect
    vec2 warpedCoord = warp(uv);
    vec3 color = texture2D(DiffuseSampler, warpedCoord).rgb;

    // Grayscale
    float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));

    // Purple tint
    vec3 redTint = vec3(1.0, 0.0, 0.0);
    vec3 tinted = gray * redTint * 1.2;

    gl_FragColor = vec4(tinted, 1.0);
}