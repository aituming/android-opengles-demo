uniform vec4 uColor;
uniform float uRad; // 半径
uniform float nCount; // 划分份数
varying vec4 vPosition;
void main()
{
	vec3 color;
	float span = 2.0 * uRad / nCount;
	int i = int( (vPosition.x + uRad) / span);
	int j = int( (vPosition.y + uRad) / span);
	int k = int((vPosition.z + uRad) / span);
	int whichColor = int( mod( float( i + j + k ), 2.0) );
	if (whichColor == 1) {
		color = vec3(0.678, 0.231, 0.129);
	} else {
		color = vec3(1.0, 1.0, 1.0);
	}
	gl_FragColor = vec4(color, 1.0);
}