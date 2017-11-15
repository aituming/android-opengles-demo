attribute vec4 aPosition;
uniform mat4 vMatrix;
varying  vec4 vPosition;

void main() {
	gl_Position = vMatrix * aPosition;
	vPosition = aPosition;
}