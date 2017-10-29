attribute vec4 aPosition;
uniform mat4 vMatrix;
void main() {
	gl_Position = vMatrix * aPosition;
}