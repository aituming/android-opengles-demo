uniform vec4 uColor;
varying vec3 vColor;
void main()
{
	gl_FragColor = vec4(vColor, 1.0);
}
