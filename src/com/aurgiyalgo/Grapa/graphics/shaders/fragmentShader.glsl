#version 400 core

in vec2 passTextureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector;

out vec4 outColor;

uniform sampler2D textureSampler;
uniform vec3 lightColor;

void main() {

    outColor = texture(textureSampler, passTextureCoords);
    
}