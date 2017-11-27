attribute vec4 aPosition;
uniform mat4 vMatrix;
attribute vec2 aTextCoor;
varying vec2 vTextureCoord;
void main() {
	gl_Position = vMatrix * aPosition;
	vTextureCoord = aTextCoor;
}