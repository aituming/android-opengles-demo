attribute vec4 aPosition;
uniform mat4 vMatrix;
varying  vec3 vColor;

void main() {
	gl_Position = vMatrix * aPosition;
	vColor = aPosition.xyz;
}
