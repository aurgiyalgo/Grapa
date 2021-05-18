#version 130

in vec2 passTextureCoords;
in vec3 surfaceNormals;
in vec3 toLightVector;

out vec4 outColor;

uniform sampler2D textureSampler;
uniform vec3 lightColor;

void main() {

    outColor = texture(textureSampler, passTextureCoords);
    
}