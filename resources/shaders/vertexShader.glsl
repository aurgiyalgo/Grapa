#version 130

in vec3 position;
in vec2 textureCoords;
in vec3 normals;

out vec2 passTextureCoords;
out vec3 surfaceNormal;
out vec3 toLightVector;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;

void main() {

    gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1);
    passTextureCoords = textureCoords;

}