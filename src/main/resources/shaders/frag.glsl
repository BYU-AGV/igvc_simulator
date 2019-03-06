#version 330 core
out vec3 color;
in vec3 posColor;

void main() {
  color = posColor;
}