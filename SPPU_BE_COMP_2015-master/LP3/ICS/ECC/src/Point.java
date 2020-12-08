
public class Point {
	long x,y;
public Point(long x,long y) {
	this.x=x;
	this.y=y;
}
Point multiply(long n) {
	
	return new Point(x*n,y*n);
	
}
Point multiply(Point a) {
	
	return new Point(a.x*x,a.y*y);
}
Point add(Point a) {
	return new Point(a.x+x,a.y+y);
}
Point sub(Point a) {
	return new Point(x-a.x,y-a.y);
}
@Override
public String toString() {
	// TODO Auto-generated method stub
	return "["+x+" ,"+y+"]";
}
}
