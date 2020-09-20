package processing;

import java.util.ArrayList;
import java.util.Arrays;

import processing.core.PApplet;
import processing.core.PConstants;

public class idk implements IProcessingApp {
	
	ArrayList<int[]> pos;
	int nmr;
	float a;
	int radius;

	@Override
	public void setup(PApplet p) {
		this.a = 0.0f;
		this.radius = 75;
		this.nmr = 0;
		this.pos = new ArrayList<int[]>();
	}

	@Override
	public void draw(PApplet p, float dt) {
		p.background(0);
		for (int i = 0; i < nmr; ++i) {
			int x = pos.get(i)[0];	
			int y = pos.get(i)[1];
			p.fill(0, 255, 0); // Green
			p.fill(0, 0, 255); // Blue
			p.fill(255, 0, 0); //Red
			//System.out.println((float)Math.cos(a));
			p.ellipse(x + (float)Math.cos(a) * radius, y + (float)Math.sin(a) * radius, 50, 50);
		}
		if (nmr > 0) {
			System.out.println(a);
			a += (float)Math.PI/30;
			System.out.println("Here" + (float)Math.PI/6);
		}
	}

	@Override
	public void keyPressed(PApplet p) {
		switch (p.keyCode) {
		case PConstants.UP:
			for (int i = 0; i < pos.size(); ++i)
				pos.get(i)[1] -= 10;
			//y -= 10;
			break;
		case PConstants.DOWN:
			for (int i = 0; i < pos.size(); ++i)
				pos.get(i)[1] += 10;
			//y += 10;
			break;
		case PConstants.RIGHT:
			for (int i = 0; i < pos.size(); ++i)
				pos.get(i)[0] += 10;
			//x += 10;
			break;
		case PConstants.LEFT:
			for (int i = 0; i < pos.size(); ++i)
				pos.get(i)[0] -= 10;
			//x -= 10;
			break;
		}
	}

	@Override
	public void mousePressed(PApplet p) {
		//System.out.println(p.mouseX + ", " + p.mouseY);
		this.nmr ++;
		int _pos[] = {p.mouseX, p.mouseY};
		pos.add(_pos);
		for (int i = 0; i < pos.size(); ++i) {
			System.out.println(Arrays.toString(pos.get(i)));
		}
		System.out.println("-----mousePressed Event-----");
	}

}
